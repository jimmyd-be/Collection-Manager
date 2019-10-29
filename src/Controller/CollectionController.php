<?php
declare(strict_types=1);

namespace App\Controller;

use Psr\Http\Message\ResponseInterface as Response;
use Psr\Container\ContainerInterface;
use App\Repository\CollectionRepository;
use App\Repository\CollectiontypeRepository;
use App\Repository\UserRepository;
use App\Repository\FieldtypeRepository;
use App\Repository\FieldRepository;
use App\Repository\RoleRepository;
use App\Repository\UserCollectionRepository;
use App\Entity\Collection;
use App\Entity\Field;
use App\Entity\Token;
use App\Entity\Dto\CollectionDto;
use App\Entity\Usercollection;
use ReallySimpleJWT\Token as JWT;
use App\Mappers\CollectionMapper;
use App\Mappers\CollectionUserMapper;

class CollectionController
{
    protected $container;

    private $collectionRepo;
    private $typeRepo;
    private $userRepo;
    private $fieldTypeRepo;
    private $fieldRepo;
    private $collectionMapper;
    private $userRepository;
    private $roleRepository;
    private $userCollectionRepository;
    private $collectionUserMapper;

    // constructor receives container instance
    public function __construct(ContainerInterface $container) {
        $this->container = $container;

        $this->collectionRepo = $container->get(CollectionRepository::class);
        $this->typeRepo = $container->get(CollectiontypeRepository::class);
        $this->userRepo = $container->get(UserRepository::class);
        $this->fieldTypeRepo = $container->get(FieldtypeRepository::class);
        $this->fieldRepo = $container->get(FieldRepository::class);
        $this->userRepository = $container->get(UserRepository::class);
        $this->roleRepository = $container->get(RoleRepository::class);
        $this->userCollectionRepository = $container->get(UserCollectionRepository::class);

        $this->collectionMapper = new CollectionMapper($container);
        $this->collectionUserMapper = new CollectionUserMapper($container);
        
    }

    public function getByUser($request, $response, $args): Response
    {
        $userId = (int)$request->getAttribute('userId');

        $collections = $this->collectionRepo->getByUser($userId);

        $dtoArray = array();

        foreach($collections as $col)
        {
            array_push($dtoArray, $this->collectionMapper->mapCollectionToDto($col));
        }

        $response->getBody()->write(json_encode($dtoArray));
        return  $response->withHeader('Content-Type', 'application/json');
    }

    public function add($request, $response, $args): Response
    {
        $userId = (int)$request->getAttribute('userId');
        $input = $request->getParsedBody();
        $user = $this->userRepo->getById($userId);

        $newCollection = $this->collectionMapper->mapDtoToCollection($input);
        //$newCollection->setOwner($user);

        $collectionArray = array();

        array_push($collectionArray, $newCollection);
        
        foreach($newCollection->getFieldId() as $field)
        {
            $newField = $this->fieldRepo->save($field);

        }

        $this->collectionRepo->save($newCollection);  

        return $response;
    }

    public function edit($request, $response, $args): Response
    {
        $userId = (int)$request->getAttribute('userId');
        $input = $request->getParsedBody();
        
        $newCollection = $this->collectionMapper->mapDtoToCollection($input);

        $collection = $this->collectionRepo->getById((int)$newCollection->getId());
//TODO check for owner permission
        if((int)$collection->getOwner()->getId() == $userId)
        {

            $collection = $this->collectionRepo->getById((int)$newCollection->getId());

            $collection->setName($newCollection->getName());

            //Add and edit actions
            foreach($newCollection->getFieldId() as $field)
            {
                if($field->getId() == null)
                {
                    $newField = $this->fieldRepo->save($field);
                    $collection->addField($newField);
                }
                else{
                    $newField = $this->fieldRepo->edit($field);
                }
            }

            //delete actions
            foreach($collection->getFieldId() as $field)
            {
                $deleted = true;

                foreach($newCollection->getFieldId() as $newField)
                {
                    if((int)$newField->getId() == (int)$field->getId())
                    {
                        $deleted = false;
                    }

                }

                if($deleted)
                {
                    $collection->deleteField($field);

                    $field->setActive(false);
                    $this->fieldRepo->edit($field);
                }
            }

            $this->collectionRepo->save($collection);
        }
        else{
            $response = $response->withStatu(405);
        }

        return $response;
    }

    public function getById($request, $response, $args): Response
    {
        $userId = (int)$request->getAttribute('userId');
        $collectionId = (int)$args['id'];
        $collection = $this->collectionRepo->getById($collectionId);

        $collections = $this->collectionRepo->getByUser($userId);

        $hasPermission = true;

        if($hasPermission)
        {
            $response->getBody()->write(json_encode($this->collectionMapper->mapCollectionToDto($collection)));
        }
        else{ 
            $response = $response->withStatus(403);
        }

        return $response;
    }

    public function delete($request, $response, $args): Response
    {
        $userId = (int)$request->getAttribute('userId');

        $collectionId = (int)$args['id'];
        $collection = $this->collectionRepo->getById($collectionId);
//TODO check for owner permission
        if((int)$collection->getOwner()->getId() == $userId)
        {
            $collection->setActive(false);
            $this->collectionRepo->save($collection); 
        }
        else{
            $response = $response->withStatu(405);
        }

        return $response;
    }

    public function share($request, $response, $args): Response
    {
        //TODO check for owner permission
        $userId = (int)$request->getAttribute('userId');
        $input = $request->getParsedBody();

        $collectionId = (int)$args['id'];
        $collection = $this->collectionRepo->getById($collectionId);
        $user = $this->userRepository->getUserByNameOrMail($input['username'], $input['username']);
        $role = $this->roleRepository->getById($input['role']);

        $userCollection = $this->userCollectionRepository->getByUserAndCollection($collection->getId(), $user->getId());

        if(empty($userCollection))
        {
            $userCollection = new Usercollection();
            $userCollection->setCollectionid($collection);
            $userCollection->setUserid($user);
        }

        $userCollection->setRoleid($role);

        $this->userCollectionRepository->save($userCollection);

        return $response;
    }

    function getAllCollectionUsers($request, $response, $args): Response
    {
        //TODO check permission

        $users = $this->userCollectionRepository->getByCollection((int)$args['id']);

        $results = $this->collectionUserMapper->mapListToDtoList($users);

        $response->getBody()->write(json_encode($results));
        return  $response->withHeader('Content-Type', 'application/json');
    }

    function deleteUserFromCollection($request, $response, $args): Response
    {
        //TODO check permissions + check if one owner is still available

        $userCollection = $this->userCollectionRepository->getByUserAndCollection((int)$args['id'], (int)$args['userId']);

        if(!empty($userCollection))
        {
            $this->userCollectionRepository->delete($userCollection);
        }
        
        return $response;
    }


}