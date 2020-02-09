<?php
declare(strict_types=1);

namespace App\Controller;

use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Message\ServerRequestInterface as Request;
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
use App\Utils\PermissionUtil;

class CollectionController
{
    protected ContainerInterface $container;

    private CollectionRepository $collectionRepo;
    private CollectiontypeRepository $typeRepo;
    private UserRepository $userRepo;
    private FieldtypeRepository $fieldTypeRepo;
    private FieldRepository $fieldRepo;
    private CollectionMapper $collectionMapper;
    private UserRepository $userRepository;
    private RoleRepository $roleRepository;
    private UserCollectionRepository $userCollectionRepository;
    private CollectionUserMapper $collectionUserMapper;
    private PermissionUtil $permissionUtil;

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
        $this->permissionUtil = new PermissionUtil($container);
    }

    public function getByUser(Request $request, Response $response, array $args): Response
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

    public function add(Request $request, Response $response, array $args): Response
    {
        $userId = (int)$request->getAttribute('userId');
        $input = $request->getParsedBody();
        $user = $this->userRepo->getById($userId);

        $newCollection = $this->collectionMapper->mapDtoToCollection($input);

        $collectionArray = array();

        array_push($collectionArray, $newCollection);
        
        foreach($newCollection->getFieldId() as $field)
        {
            $newField = $this->fieldRepo->save($field);

        }

        $collection = $this->collectionRepo->save($newCollection);
        $role = $this->roleRepository->getByName("Owner");

        $userCollection = new Usercollection();

        $userCollection->setCollectionid($collection);
        $userCollection->setUserid($user);
        $userCollection->setRoleid($role);
        
        $this->userCollectionRepository->save($userCollection);        

        return $response;
    }

    public function edit(Request $request, Response $response, array $args): Response
    {
        $userId = (int)$request->getAttribute('userId');
        $input = $request->getParsedBody();
        
        $newCollection = $this->collectionMapper->mapDtoToCollection($input);

        $collection = $this->collectionRepo->getById((int)$newCollection->getId());

        if($this->permissionUtil->checkUserPermission(intval($collection->getId()), $userId, "Owner"))
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
            $response = $response->withStatus(405);
        }

        return $response;
    }

    public function getById(Request $request, Response $response, array $args): Response
    {
        $userId = (int)$request->getAttribute('userId');
        $collectionId = (int)$args['id'];

        if($this->permissionUtil->checkUserPermission($collectionId, $userId, "Viewer"))
        {
            $collection = $this->collectionRepo->getById($collectionId);
       
            $response->getBody()->write(json_encode($this->collectionMapper->mapCollectionToDto($collection)));
        }
        else{ 
            $response = $response->withStatus(403);
        }


        return $response;
    }

    public function delete(Request $request, Response $response, array $args): Response
    {
        $userId = (int)$request->getAttribute('userId');

        $collectionId = (int)$args['id'];
        $collection = $this->collectionRepo->getById($collectionId);

        if($this->permissionUtil->checkUserPermission($collectionId, $userId, "Owner"))
        {
            $collection->setActive(false);
            $this->collectionRepo->save($collection); 
        }
        else{
            $response = $response->withStatus(405);
        }

        return $response;
    }

    public function share(Request $request, Response $response, array $args): Response
    {
        $userId = (int)$request->getAttribute('userId');
        $input = $request->getParsedBody();

        $collectionId = (int)$args['id'];

        if($this->permissionUtil->checkUserPermission($collectionId, $userId, "Owner"))
        {
            $collection = $this->collectionRepo->getById($collectionId);
            $user = $this->userRepository->getUserByNameOrMail($input['userName'], $input['userName']);
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
        }
        else{
            $response = $response->withStatus(405);
        }

        return $response;
    }

    function getAllCollectionUsers(Request $request, Response $response, array $args): Response
    {
        if($this->permissionUtil->checkUserPermission((int)$args['id'], (int)$request->getAttribute('userId'), "Owner"))
        {
            $users = $this->userCollectionRepository->getByCollection((int)$args['id']);

            $results = $this->collectionUserMapper->mapListToDtoList($users);

            $response->getBody()->write(json_encode($results));
            return  $response->withHeader('Content-Type', 'application/json');
        }
        else{
            $response = $response->withStatus(405);
            return $response;
        }
    }

    function deleteUserFromCollection(Request $request, Response $response, array $args): Response
    {
        $userId = (int)$args['userId'];
        $collectionId = (int)$args['id'];

        if($this->permissionUtil->checkUserPermission($collectionId, $userId, "Owner"))
        {
            $userCollection = $this->userCollectionRepository->getByUserAndCollection($collectionId, $userId);

            $canBeDeleted = true;

            if($userCollection->getRoleid()->getRole() === 'Owner'){
                $ownerCount = 0;

                foreach($userCollection as $uCol)
                {
                    if($uCol->getRoleid()->getRole() === 'Owner')
                    {
                        $ownerCount++;
                    }
                }

                if($ownerCount <= 1)
                {
                    $canBeDeleted = false;
                }

            }

            if(!empty($userCollection) && $canBeDeleted)
            {
                $this->userCollectionRepository->delete($userCollection);
            }
            else {
                $response = $response->withStatus(405);
            }
        }
        else{
            $response = $response->withStatus(405);
        }

        return $response;
    }


}