<?php
declare(strict_types=1);

namespace App\Repository;

use Doctrine\ORM;

use Doctrine\ORM\EntityRepository as EntityRepository;
use Doctrine\ORM\EntityManager;
use App\Entity\User;

class UserRepository
{
    private $em;
    private $repo;

    function __construct(EntityManager $entityManager) {
        $this->em = $entityManager;
        $this->repo = $entityManager->getRepository(User::class);
    }

    public function save(User $user)
    {
        $this->em->persist($user);
        $this->em->flush();
    }

    public function getUserByNameOrMail(string $username, string $mail): ?User
    {
        $query = $this-> em->createQuery('SELECT u FROM App\Entity\User u WHERE u.mail = :mail or u.username = :username');
        $query->setParameter('username', $username);
        $query->setParameter('mail', $mail);

        $users = $query->getResult();

        if(count($users) > 0)
        {
            return $users[0];
        }

        return null;
    }

}