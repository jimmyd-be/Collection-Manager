<?php
declare(strict_types=1);

namespace App\Repository;

use Doctrine\ORM;
use Doctrine\ORM\Tools\Pagination\Paginator;
use Doctrine\ORM\EntityRepository as EntityRepository;
use Doctrine\ORM\EntityManager;
use App\Entity\Item;

class ItemRepository
{
    private EntityManager $em;
    private $repo;

    function __construct(EntityManager $entityManager) {
        $this->em = $entityManager;
        $this->repo = $entityManager->getRepository(Item::class);
    }

    public function save(Item $item): Item
    {
        $this->em->persist($item);
        $this->em->flush();

        return $item;
    }

    public function getItemsByCollection(int $collectionId, int $page, int $totalItems): array
    {
        $dql = "SELECT i FROM App\Entity\Item i JOIN i.collectionid c WHERE c.id = :colId AND i.active = 1 ORDER BY i.name";
        $query = $this->em->createQuery($dql)
                            ->setParameter('colId', $collectionId)
                            ->setFirstResult($page * $totalItems)
                            ->setMaxResults($totalItems);

      // $paginator = new Paginator($query, $fetchJoinCollection = true);

        return $query->getResult();
    }

    public function getItembyId(int $itemId): ?Item
    {
        $dql = "SELECT i, c FROM App\Entity\Item i LEFT JOIN i.collectionid c WHERE i.id = :itemId";
        $query = $this->em->createQuery($dql)
                            ->setParameter('itemId', $itemId);

        return $query->getOneOrNullResult();
    }

}