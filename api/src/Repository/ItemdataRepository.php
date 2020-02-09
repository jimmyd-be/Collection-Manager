<?php
declare(strict_types=1);

namespace App\Repository;

use Doctrine\ORM;

use Doctrine\ORM\EntityRepository as EntityRepository;
use Doctrine\ORM\EntityManager;
use App\Entity\Itemdata;

class ItemdataRepository
{
    private EntityManager $em;
    private $repo;

    function __construct(EntityManager $entityManager) {
        $this->em = $entityManager;
        $this->repo = $entityManager->getRepository(Itemdata::class);
    }

    public function save(Itemdata $item): Itemdata
    {
        $this->em->persist($item);
        $this->em->flush();

        return $item;
    }

    public function getByItem(int $id): array
    {
        $dql = "SELECT i, f FROM App\Entity\Itemdata i JOIN i.itemid c JOIN i.fieldid f WHERE c.id = :itemId AND f.active = 1";
        $query = $this->em->createQuery($dql)
                            ->setParameter('itemId', $id);

        return $query->getResult();
    }

    public function deleteFromItem(int $itemId): void
    {
        $dql = "DELETE FROM itemdata WHERE itemId = ?";
        $this->em->getConnection()->executeUpdate($dql, array($itemId));
    }



}