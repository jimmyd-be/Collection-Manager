<?php

namespace App\Mappers;

use App\Entity\Usercollection;
use App\Entity\Dto\UserCollectionDto;
use Psr\Container\ContainerInterface;

class CollectionUserMapper
{
    public function __construct(ContainerInterface $container) {}

    public function mapToDto(Usercollection $userCollection): UserCollectionDto
    {
        $userCollecctionDto = new UserCollectionDto();
        $userCollecctionDto->userId = $userCollection->getUserid()->getId();
        $userCollecctionDto->userName = $userCollection->getUserid()->getUsername();
        $userCollecctionDto->roleId = $userCollection->getRoleid()->getId();
        $userCollecctionDto->roleName = $userCollection->getRoleid()->getName();

        return $userCollecctionDto;
    }

    public function mapListToDtoList($userCollections): array
    {
        $result = array();

        foreach ($userCollections as $usreCollection) {
            array_push($result, $this->mapToDto($usreCollection));
        }

        return $result;
    }

}