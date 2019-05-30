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
        $query = $this->em->createQuery("SELECT c, t FROM App\Entity\Collection c JOIN c.owner u JOIN c.typeid t   WHERE u.id = :userId");

        $query->setParameter('userId', $userId);

        return $query->getResult();
    }

}