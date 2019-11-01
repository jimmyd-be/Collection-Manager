<?php
declare(strict_types=1);

namespace App\Repository;

use Doctrine\ORM;

use Doctrine\ORM\EntityRepository as EntityRepository;
use Doctrine\ORM\EntityManager;
use App\Entity\Usercollection;

class UserCollectionRepository
{
    private $em;
    private $repo;

    function __construct(EntityManager $entityManager) {
        $this->em = $entityManager;
        $this->repo = $entityManager->getRepository(Usercollection::class);
    }

    public function save(Usercollection $userCollection): Usercollection
    {
        $this->em->persist($userCollection);
        $this->em->flush();

        return $userCollection;
    }

    public function delete(Usercollection $userCollection)
    {
        $this->em->remove($userCollection);
        $this->em->flush();
    }

    public function getByUserAndCollection($collectionId, $userId): ?Usercollection
    {
        $query = $this->em->createQuery("SELECT c FROM App\Entity\Usercollection c JOIN c.userid u JOIN c.collectionid ci WHERE u.id = :userId and ci.id = :collectionId");

        $query->setParameter('userId', $userId);
        $query->setParameter('collectionId', $collectionId);

        return $query->getOneOrNullResult();
    }

    public function getByCollection(int $collectionId)
    {
        $query = $this->em->createQuery("SELECT c FROM App\Entity\Usercollection c JOIN c.userid u JOIN c.collectionid ci JOIN c.roleid r WHERE ci.id = :collectionId and ci.active = 1");

        $query->setParameter('collectionId', $collectionId);

        return $query->getResult();
    }

}