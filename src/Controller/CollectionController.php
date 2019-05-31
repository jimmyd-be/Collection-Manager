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
use App\Entity\Collection;
use App\Entity\Field;
use App\Entity\Token;
use App\Entity\Dto\CollectionDto;
use ReallySimpleJWT\Token as JWT;

class CollectionController
{
    protected $container;

    private $collectionRepo;
    private $typeRepo;
    private $userRepo;
    private $fieldTypeRepo;
    private $fieldRepo;

    // constructor receives container instance
    public function __construct(ContainerInterface $container) {
        $this->container = $container;

        $this->collectionRepo = $container->get(CollectionRepository::class);
        $this->typeRepo = $container->get(CollectiontypeRepository::class);
        $this->userRepo = $container->get(UserRepository::class);
        $this->fieldTypeRepo = $container->get(FieldtypeRepository::class);
        $this->fieldRepo = $container->get(FieldRepository::class);
        
    }

    public function getByUser($request, $response, $args): Response
    {
        $userId = (int)$request->getAttribute('userId');

        $collections = $this->collectionRepo->getByUser($userId);

        $dtoArray = array();

        foreach($collections as $col)
        {
            $newDto = new CollectionDto();
            $newDto->id = $col->getId();
            $newDto->name = $col->getName();
            $newDto->type = $col->getTypeid()->getType();

            array_push($dtoArray, $newDto);
        }

        $response->getBody()->write(json_encode($dtoArray));
        return  $response->withHeader('Content-Type', 'application/json');
    }

    public function add($request, $response, $args): Response
    {
        $userId = (int)$request->getAttribute('userId');
        $input = $request->getParsedBody();

        $type = $this->typeRepo->getbyName($input['type']);
        $user = $this->userRepo->getById($userId);

        $newCollection = new Collection();
        $newCollection->setName($input['name']);
        $newCollection->setActive(true);
        $newCollection->setTypeid($type);
        $newCollection->setOwner($user);

        $newCollection = $this->collectionRepo->save($newCollection);  
        
        $collectionArray = array();

        array_push($collectionArray, $newCollection);
        
        foreach($input['fields'] as $field)
        {
            $newField = new field();
            $newField->setName($field['name']);
            $newField->setChoises($field['value']);
            $newField->setRequired($field['required']);
            $newField->setPlaceholder($field['placeholder']);

            if(isset($field['options']))
            {
                $newField->setOtheroptions($field['options']);
            }
            $newField->setFieldorder($field['fieldOrder']);
            $newField->setPlace($field['place']);
            $newField->setLabel($field['label']);
            $newField->setMultivalues($field['multivalues']);
            $newField->setActive(true);
            $newField->setLabelposition($field['labelPosition']);
            //$newField->setWidget($field['']);
            $newField->setType($this->fieldTypeRepo->getbyName($field['type']));
            //$newField->setCollectionbasetype($field['']);
            $newField->setCollectionid($collectionArray);

            $newField = $this->fieldRepo->save($newField);
            $newCollection->addField($newField);
        }

        $this->collectionRepo->save($newCollection);  

        return $response;
    }


}