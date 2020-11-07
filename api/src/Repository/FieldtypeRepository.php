<?php
declare(strict_types=1);

namespace App\Repository;

use Doctrine\ORM;

use Doctrine\ORM\EntityRepository as EntityRepository;
use Doctrine\ORM\EntityManager;
use App\Entity\Fieldtype;

class FieldtypeRepository
{
    private EntityManager $em;
    private $repo;

    function __construct(EntityManager $entityManager) {
        $this->em = $entityManager;
        $this->repo = $entityManager->getRepository(Fieldtype::class);

    }

    function getbyName(string $name): ?Fieldtype
    {
        $query =$this->em->createQuery("SELECT t FROM App\Entity\Fieldtype t WHERE t.type = :name AND t.active = 1");
        $query->setParameter('name', $name);

        return $query->getOneOrNullResult();
    }

}