<?php

namespace App\Mappers;

use App\Entity\Role;
use App\Entity\Dto\RoleDto;
use Psr\Container\ContainerInterface;

class RoleMapper
{
    public function __construct(ContainerInterface $container) {}

    public function mapToDto(Role $role): RoleDto
    {
        $roleDto = new RoleDto();
        $roleDto->id = $role->getId();
        $roleDto->name = $role->getRole();
        $roleDto->active = $role->getActive();

        return $roleDto;
    }

    public function mapListToDtoList($roles): array
    {
        $result = array();

        foreach ($roles as $role) {
            array_push($result, $this->mapToDto($role));
        }

        return $result;
    }

}