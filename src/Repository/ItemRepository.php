<?php
declare(strict_types=1);

use Doctrine\ORM\EntityRepository;
use Doctrine\ORM\EntityManager;

namespace App\Repository;

class ItemRepository extends EntityRepository
{
    function __construct(EntityManager $entityManager) {
        parent::__construct($entityManager, Item::class);
    }

}