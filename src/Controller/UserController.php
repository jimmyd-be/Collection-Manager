<?php
declare(strict_types=1);

namespace App\Controller;

use Psr\Http\Message\ResponseInterface as Response;
use Psr\Container\ContainerInterface;
use App\Repository\UserRepository;
use App\Mappers\UserMapper;

class UserController
{
    protected $container;
    private $userMapper;
    private $userRepo;

    // constructor receives container instance
    public function __construct(ContainerInterface $container) {
        $this->container = $container;

        $this->userRepo = $container->get(UserRepository::class);
        $this->userMapper = new UserMapper($container);
    }

    public function getUser($request, $response, $args): Response
    {
        $userId = (int)$request->getAttribute('userId');
        $user = $this->userRepo->getById($userId);

        $response->getBody()->write(json_encode($this->userMapper->mapToDto($user)));
        return  $response->withHeader('Content-Type', 'application/json');
    }

}