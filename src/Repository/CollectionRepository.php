<?php
declare(strict_types=1);

namespace App\Repository;

use Doctrine\ORM;

use Doctrine\ORM\EntityRepository as EntityRepository;
use Doctrine\ORM\EntityManager;
use App\Entity\Collection;

class CollectionRepository
{
    private $em;
    private $repo;

    function __construct(EntityManager $entityManager) {
        $this->em = $entityManager;
        $this->repo = $entityManager->getRepository(Collection::class);
    }

    public function getByUser(int $userId): ?iterable
    {
        $query = $this->em->createQuery("SELECT c, t FROM App\Entity\Collection c JOIN c.owner u JOIN c.typeid t WHERE u.id = :userId AND c.active = 1");

        $query->setParameter('userId', $userId);

        return $query->getResult();
    }

    public function getById(int $collectionId): ?Collection
    {
        $query = $this->em->createQuery("SELECT c, t, u FROM App\Entity\Collection c JOIN c.owner u JOIN c.typeid t WHERE c.id = :id");

        $query->setParameter('id', $collectionId);

        return $query->getOneOrNullResult();
    }


    public function save(Collection $collection): Collection
    {
        $this->em->persist($collection);
        $this->em->flush();

        return $collection;
    }

}