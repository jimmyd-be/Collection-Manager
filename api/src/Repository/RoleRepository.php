<?php
declare(strict_types=1);

namespace App\Repository;

use Doctrine\ORM;

use Doctrine\ORM\EntityRepository as EntityRepository;
use Doctrine\ORM\EntityManager;
use App\Entity\Role;

class RoleRepository
{
    private EntityManager $em;
    private $repo;

    function __construct(EntityManager $entityManager) {
        $this->em = $entityManager;
        $this->repo = $entityManager->getRepository(Role::class);
    }

    function getActiveRoles(): array {
        $query =$this->em->createQuery("SELECT t FROM App\Entity\Role t WHERE t.active = 1");

        return $query->getResult();
    }

    function getById(int $id): ?Role {
        $query =$this->em->createQuery("SELECT t FROM App\Entity\Role t WHERE t.active = 1 and t.id = :id");
        $query->setParameter('id', $id);

        return $query->getOneOrNullResult();
    }

    function getByName(string $name): ?Role {
        $query =$this->em->createQuery("SELECT t FROM App\Entity\Role t WHERE t.active = 1 and t.name = :name");
        $query->setParameter('name', $name);

        return $query->getOneOrNullResult();
    }

}