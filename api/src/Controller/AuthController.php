<?php
declare(strict_types=1);

namespace App\Controller;

use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Container\ContainerInterface;
use App\Repository\UserRepository;
use App\Entity\User;
use App\Entity\Token;
use App\Entity\Dto\TokenDto;
use ReallySimpleJWT\Token as JWT;


class AuthController
{
    protected ContainerInterface $container;

    private UserRepository $userRepo;

    // constructor receives container instance
    public function __construct(ContainerInterface $container) {
        $this->container = $container;

        $this->userRepo = $container->get(UserRepository::class);
    }
    
    public function login(Request $request, Response $response, array $args): Response {
      
      $input = $request->getParsedBody();

      $currentUser =  $this->userRepo->getUserByNameOrMail($input['email'], $input['email']);

      if($currentUser != null && $currentUser->getActive() && password_verify($input['password'], $currentUser->getUserpassword()))
      {
        $token = new Token($currentUser->getId(), new \DateTime('tomorrow'));

        $jwt = JWT::create($currentUser->getId(), $this->container->get('settings')['secureKey'], time() + 86400, 'localhost');

        $payload = json_encode(new TokenDto($jwt));
        $response->getBody()->write($payload);

      }
      else{
        $response = $response->withStatus(401);

      }


      return $response;
    }


    public function logout(Request $request, Response $response, array $args): Response {
        // your code here
        // use $this->view to render the HTML
        return $response;
      }

      public function register(Request $request, Response $response, array $args): Response {

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