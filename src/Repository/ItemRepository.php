<?php
declare(strict_types=1);

namespace App\Repository;

use Doctrine\ORM;

use Doctrine\ORM\EntityRepository as EntityRepository;
use Doctrine\ORM\EntityManager;
use App\Entity\Item;

class ItemRepository
{
    private $em;
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

}