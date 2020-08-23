<?php

namespace App\Mappers;

use App\Entity\User;
use App\Entity\Dto\UserDto;
use Psr\Container\ContainerInterface;

class UserMapper
{
    public function __construct(ContainerInterface $container) {}

    public function mapToDto(User $user): UserDto
    {
        $userDto = new UserDto();
        $userDto->id = $user->getId();
        $userDto->mail = $user->getMail();
        $userDto->username = $user->getUsername();
        $userDto->isAdmin = $user->getIsadmin();
        $userDto->creationDate = $user->getCreationdate()->format('Y-m-d H:i:s');
        $userDto->theme = $user->getTheme();

        return $userDto;
    }

    public function mapListToDto($users)
    {
        $result = array();

        foreach ($users as &$user) {
            array_push($result, $this->mapToDto($user));
        }

        return $result;
    }

}