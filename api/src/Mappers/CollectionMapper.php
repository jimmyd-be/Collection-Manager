<?php

namespace App\Mappers;

use App\Entity\Collection;
use App\Entity\Field;
use App\Entity\Dto\CollectionDto;
use Psr\Container\ContainerInterface;
use App\Repository\CollectiontypeRepository;
use App\Repository\FieldtypeRepository;
use App\Mappers\CollectionFieldMapper;

class CollectionMapper
{
    private $typeRepo;
    private $fieldTypeRepo;
    private $fieldMapper;

    public function __construct(ContainerInterface $container) {
        $this->typeRepo = $container->get(CollectiontypeRepository::class);
        $this->fieldTypeRepo = $container->get(FieldtypeRepository::class);

        $this->fieldMapper = new CollectionFieldMapper($container);
    }

    public function mapCollectionToDto(Collection $collection): CollectionDto
    {
        $newDto = new CollectionDto();
        $newDto->id = $collection->getId();
        $newDto->name = $collection->getName();
        $newDto->type = $collection->getTypeid()->getType();

        $fields = array();

        foreach($collection->getFieldid() as $field)
        {
            array_push($fields, $this->fieldMapper->fieldToDto($field));
        }
        $newDto->fields = $fields;

        return $newDto;
    }

    public function mapDtoToCollection(array $input): Collection
    {
        $type = $this->typeRepo->getbyName($input['type']);

        $newCollection = new Collection();

        $newCollection->setId($input['id']);
        $newCollection->setName($input['name']);
        $newCollection->setActive(true);
        $newCollection->setTypeid($type);

        foreach($input['fields'] as $field)
        {
            $newField = new field();
            
            if(isset($field['id']))
            {
                $newField->setId($field['id']);
            }
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
            //$newField->setCollectionid($collectionArray);

            $newCollection->addField($newField);
        }

        return $newCollection;
    }


    
}