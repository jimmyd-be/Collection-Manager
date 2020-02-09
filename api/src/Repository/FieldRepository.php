<?php
declare(strict_types=1);


namespace App\Repository;

use Doctrine\ORM;

use Doctrine\ORM\EntityRepository as EntityRepository;
use Doctrine\ORM\EntityManager;
use App\Entity\Field;

class FieldRepository
{
    private EntityManager $em;
    private $repo;

    function __construct(EntityManager $entityManager) {
        $this->em = $entityManager;
        $this->repo = $entityManager->getRepository(Field::class);
    }

    public function save(Field $field): Field
    {
        $this->em->persist($field);
        $this->em->flush();

        return $field;
    }   
    
    public function edit(Field $field): Field
    {
        $this->em->merge($field);
        $this->em->flush();

        return $field;
    }   

    public function getCustomByCollectionId(int $collectionId): array
    {
        $query = $this->em->createQuery("SELECT f, t FROM App\Entity\Field f JOIN f.collectionid c JOIN f.type t WHERE f.active = 1 AND c.id = :collectionId");
        $query->setParameter('collectionId', $collectionId);

        return $query->getResult();
    }

    public function getBasicByCollectionId(int $collectionId): array
    {
        $query = $this->em->createQuery("SELECT c, t FROM App\Entity\Collection c JOIN c.typeid t WHERE c.id = :collectionId");
        $query->setParameter('collectionId', $collectionId);

        $collection = $query->getOneOrNullResult();

        if($collection != null)
        {
            $query = $this->em->createQuery("SELECT f FROM App\Entity\Field f JOIN f.collectionbasetype t WHERE f.active = 1 AND t.id = :typeId");
            $query->setParameter('typeId', $collection->getTypeid()->getId());

            return $query->getResult();
        }

        return array();
    }

}