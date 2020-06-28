<?php
declare(strict_types=1);

namespace App\Repository;

use Doctrine\ORM;

use Doctrine\ORM\EntityRepository as EntityRepository;
use Doctrine\ORM\EntityManager;
use App\Entity\Collectiontype;

class CollectiontypeRepository
{
    private EntityManager $em;
    private $repo;

    function __construct(EntityManager $entityManager) {
        $this->em = $entityManager;
        $this->repo = $entityManager->getRepository(Collectiontype::class);
    }

    function getbyName(string $name): ?Collectiontype
    {
        $query =$this->em->createQuery("SELECT t FROM App\Entity\Collectiontype t WHERE t.type = :name AND t.active = 1");
        $query->setParameter('name', $name);

        return $query->getOneOrNullResult();
    }

    function getAll()
    {
        $query =$this->em->createQuery("SELECT t FROM App\Entity\Collectiontype t WHERE t.active = 1");

        return $query->getResult();
    }
}