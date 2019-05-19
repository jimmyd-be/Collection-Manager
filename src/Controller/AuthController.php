<?php
declare(strict_types=1);

namespace App\Controller;

use Psr\Http\Message\ResponseInterface as Response;
use Psr\Container\ContainerInterface;
use App\Repository\UserRepository;
use App\Entity\User;

class AuthController
{
    protected $container;

    private $userRepo;

    // constructor receives container instance
    public function __construct(ContainerInterface $container) {
        $this->container = $container;

        $this->userRepo = $container->get(UserRepository::class);
    }
    
    public function login($request, $response, $args): Response{
      // your code here
      // use $this->view to render the HTML
      return $response;
    }


    public function logout($request, $response, $args): Response {
        // your code here
        // use $this->view to render the HTML
        return $response;
      }

      public function register($request, $response, $args): Response {

        $input = $request->getParsedBody();

        $userExists = $this->userRepo->getUserByNameOrMail($input['fullName'], $input['email']);

        if($userExists == null)
        {
          $user = new User();
          $user->setUsername($input['fullName']);
          $user->setMail($input['email']);
          $user->setActive(true);
          $user->setUserpassword(password_hash($input['password'], PASSWORD_DEFAULT));
          $user->setCreationdate(new \DateTime("now"));
          $user->setIsadmin(false);

          $this->userRepo->save($user);
          $newResponse = $response->withStatus(200);
        }
        else
        {
          $newResponse = $response->withStatus(409);
          $newResponse->getBody()->write('User or mail already exists');

        }
        
        
        return $newResponse;
      }
}