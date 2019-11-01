<?php

namespace App\Mappers;

use App\Entity\Item;
use App\Entity\Dto\ItemDto;
use Psr\Container\ContainerInterface;
use App\Entity\Dto\ItemDataDto;

class ItemMapper
{
    public function __construct(ContainerInterface $container) {}

    public function mapItemToDto(Item $item, $data): ItemDto
    {
        $newDto = new ItemDto();
        $newDto->id = $item->getId();
        $newDto->name = $item->getName();
        $newDto->image = $item->getImage();

        $newData = array();

        foreach($data as $itemData)
        {
            $newDataDto = new ItemDataDto();
            $newDataDto->fieldId = $itemData->getFieldid()->getId();
            $newDataDto->value = $itemData->getFieldvalue();

            array_push($newData, $newDataDto);
        }

        $newDto->data = $newData;
        
        return $newDto;
    }

}