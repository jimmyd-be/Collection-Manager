<?php
declare(strict_types=1);

namespace App\Controller;

use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Container\ContainerInterface;
use App\Repository\UserRepository;
use App\Mappers\UserMapper;

class UserController
{
    protected ContainerInterface $container;
    private UserMapper $userMapper;
    private UserRepository $userRepo;

    // constructor receives container instance
    public function __construct(ContainerInterface $container) {
        $this->container = $container;

        $this->userRepo = $container->get(UserRepository::class);
        $this->userMapper = new UserMapper($container);
    }

    public function getUser(Request $request, Response $response, array $args): Response
    {
        $userId = (int)$request->getAttribute('userId');
        $user = $this->userRepo->getById($userId);

        $response->getBody()->write(json_encode($this->userMapper->mapToDto($user)));
        return  $response->withHeader('Content-Type', 'application/json');
    }

    public function deleteUser(Request $request, Response $response, array $args): Response
    {
        $userId = (int)$request->getAttribute('userId');
        $currentUser = $this->userRepo->getById($userId);

        $input = $request->getParsedBody();

        if($currentUser != null && password_verify($input['password'], $currentUser->getUserpassword()))
        {
            $currentUser->setActive(false);
            $this->userRepo->save($currentUser);
        }
        else {
            $response = $response->withStatus(405);
        }

        return $response;
    }

    public function editUser(Request $request, Response $response, array $args): Response
    {
        $userId = (int)$request->getAttribute('userId');
        $currentUser = $this->userRepo->getById($userId);

        $input = $request->getParsedBody();

        if($currentUser != null && password_verify($input['password'], $currentUser->getUserpassword()))
        {
            if($input['newUser'] && !empty($input['newUser']))
            {
                $currentUser->setUsername($input['newUser']);
            }

            if($input['newMail'] && !empty($input['newMail']))
            {
                $currentUser->setMail($input['newMail']);
            }

            if($input['theme'] && !empty($input['theme']))
            {
                $currentUser->setTheme($input['theme']);
            }

            $this->userRepo->save($currentUser);
        }
        else {
            $response = $response->withStatus(405);
        }

        return $response;
    }

    public function editPassword(Request $request, Response $response, array $args): Response
    {
        $userId = (int)$request->getAttribute('userId');
        $currentUser = $this->userRepo->getById($userId);

        $input = $request->getParsedBody();

        if($currentUser != null && password_verify($input['currentPassword'], $currentUser->getUserpassword()) && 
        !empty($input['password']) && $input['password'] == $input['passwordRepeat'])
        {
            $currentUser->setUserpassword(password_hash($input['password'], PASSWORD_DEFAULT));
            $this->userRepo->save($currentUser);
        }
        else {
            $response = $response->withStatus(405);
        }

        return $response;
    }

}