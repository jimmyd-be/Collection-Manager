<?php
declare(strict_types=1);

namespace App\Controller;

use Psr\Http\Message\ResponseInterface as Response;
use Psr\Container\ContainerInterface;

class AuthController
{
    protected $container;

    // constructor receives container instance
    public function __construct(ContainerInterface $container) {
        $this->container = $container;
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
}