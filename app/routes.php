<?php
declare(strict_types=1);

//use App\Application\Actions\User\ListUsersAction;
//use App\Application\Actions\User\ViewUserAction;
use Slim\App;
use Slim\Interfaces\RouteCollectorProxyInterface as Group;
use App\Controller\AuthController;
use Psr\Container\ContainerInterface;


return function (App $app) {
    $container = $app->getContainer();

    $container->set('AuthController', function (ContainerInterface $c) {
        return new AuthController($c);
    });

    $app->group('/auth', function (Group $group) use ($container) {
        $group->post('/login', \AuthController::class . ':login');
        $group->post('/logout', \AuthController::class . ':logout');
        //$group->post('/request-pass', AuthController::class);
        //$group->post('/register', AuthController::class);
        //$group->post('/reset-pass', AuthController::class);
    });

};
