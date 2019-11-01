<?php
declare(strict_types=1);

namespace App\Utils;

use App\Repository\UserCollectionRepository;
use App\Repository\RoleRepository;
use Psr\Container\ContainerInterface;

class PermissionUtil
{
    private $userCollectionRepository;
    private $roleRepository;

    public function __construct(ContainerInterface $container) {
        $this->userCollectionRepository = $container->get(UserCollectionRepository::class);
        $this->roleRepository = $container->get(RoleRepository::class);
    }

    public function checkUserCollectionPermission(int $collectionId, int $userId, int $roleId)
    {
        $userCollection = $this->userCollectionRepository->getByUserAndCollection($collectionId, $userId);

        if(empty($userCollection))
        {
            return false;
        }
        else if($userCollection->getRoleid()->getId() >= $roleId){
            return true;
        }

        return false;
    }

    public function checkUserPermission(int $collectionId, int $userId, string $roleName)
    {
        $roleId = $this->roleRepository->getByName($roleName)->getId();

        return $this->checkUserCollectionPermission($collectionId, $userId, $roleId);
    }

}