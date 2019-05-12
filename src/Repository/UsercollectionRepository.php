<?php
declare(strict_types=1);

use Doctrine\ORM\EntityRepository;
use Doctrine\ORM\EntityManager;

namespace App\Repository;

class UserCollectionRepository extends EntityRepository
{
    function __construct(EntityManager $entityManager) {
        parent::__construct($entityManager, UserCollection::class);
    }

}