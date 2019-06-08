<?php

namespace App\Mappers;

use App\Entity\Item;
use App\Entity\Dto\ItemDto;
use Psr\Container\ContainerInterface;

class ItemMapper
{
    public function __construct(ContainerInterface $container) {}

    public function mapItemToDto(Item $item): ItemDto
    {
        $newDto = new ItemDto();
        $newDto->id = $item->getId();
        $newDto->name = $item->getName();
        $newDto->image = $item->getImage();

        return $newDto;
    }

}