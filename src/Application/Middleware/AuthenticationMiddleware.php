<?php
declare(strict_types=1);

namespace App\Application\Middleware;

use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Http\Server\MiddlewareInterface as Middleware;
use Psr\Http\Server\RequestHandlerInterface as RequestHandler;
use Slim\Psr7\Response;
use App\Entity\Dto\TokenDto;
use Firebase\JWT\JWT;
use ReallySimpleJWT\Token;


class AuthenticationMiddleware implements Middleware
{
    private $container;

    function __construct($container) {
        $this->container = $container;
    }

    /**
     * {@inheritdoc}
     */
    public function process(Request $request, RequestHandler $handler): ResponseInterface
    {
        $response = new Response();

        if(strtolower(substr($request->getUri()->getPath(), 0, 5 )) =='/auth')
        {
            $response = $handler->handle($request);
        }
        else if (isset($_SERVER['HTTP_AUTHORIZATION'])) {

            $token = explode(' ', $_SERVER['HTTP_AUTHORIZATION'])[1];
            $secret = $this->container->get('settings')['secureKey'];

            if(Token::validate($token, $secret))
            {
                $values = Token::getPayload($token, $secret);

                $request = $request->withAttribute('userId',  $values['user_id']);
                $response = $handler->handle($request);
            }
            else
            {
                $response = $response->withStatus(401);
            }
        }
        else if($request->getMethod() != 'OPTIONS')
        {
            $response = $response->withStatus(401);
        }

        return $response;
    }
}
