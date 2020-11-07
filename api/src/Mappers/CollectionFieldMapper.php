<?php

namespace App\Mappers;

use App\Entity\Collection;
use App\Entity\Field;
use App\Entity\Dto\CollectionDto;
use App\Entity\Dto\FieldDto;
use Psr\Container\ContainerInterface;
use App\Repository\CollectiontypeRepository;
use App\Repository\FieldtypeRepository;

class CollectionFieldMapper
{

    public function __construct(ContainerInterface $container) {
    }


    public function fieldToDto(Field $field): FieldDto {
        $newField = new FieldDto();
        $newField->id = $field->getId();
        $newField->name = $field->getName();
        $newField->type = $field->getType()->getType();
        $newField->options = $field->getChoises();
        $newField->required = $field->getRequired();
        $newField->placeholder = $field->getPlaceholder();
        $newField->place = $field->getPlace();
        $newField->multivalues = $field->getMultivalues();
        $newField->labelPosition = $field->getLabelposition();
        $newField->label = $field->getLabel();
        $newField->widget = $field->getWidget();  

        if (null !== $field->getFieldorder()) {
            $newField->fieldOrder = $field->getFieldorder();
        }
        else {
            $newField->fieldOrder = 0;
        }

        return $newField;
    }


    
}