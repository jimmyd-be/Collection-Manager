<?php
declare (strict_types = 1);

namespace App\Controller;

use Psr\Http\Message\ResponseInterface as Response;
use Psr\Container\ContainerInterface;
use App\Repository\RoleRepository;
use App\Mappers\RoleMapper;

class RoleController
{
    protected $container;

    private $roleRepo;
    private $roleMapper;

    // constructor receives container instance
    public function __construct(ContainerInterface $container)
    {
        $this->container = $container;

        $this->roleRepo = $container->get(RoleRepository::class);
        $this->roleMapper = new RoleMapper($container);
    }

    public function getActiveRoles($request, $response, $args): Response
    {
        $roles = $this->roleRepo->getActiveRoles();

        $mapped = $this->roleMapper->mapListToDtoList($roles);

        $response->getBody()->write(json_encode($mapped));
        return  $response->withHeader('Content-Type', 'application/json');
    }

}