<?php
declare(strict_types=1);

use Slim\App;
use Slim\Interfaces\RouteCollectorProxyInterface as Group;
use App\Controller\AuthController;
use App\Controller\CollectionController;
use App\Controller\FieldController;
use App\Controller\ItemController;
use App\Controller\UserController;
use Psr\Container\ContainerInterface;


return function (App $app) {
    $container = $app->getContainer();

    $container->set('AuthController', function (ContainerInterface $c) {
        return new AuthController($c);
    });

    $container->set('CollectionController', function (ContainerInterface $c) {
        return new CollectionController($c);
    });

    $container->set('FieldController', function (ContainerInterface $c) {
        return new FieldController($c);
    });

    $container->set('ItemController', function (ContainerInterface $c) {
        return new ItemController($c);
    });
    $container->set('UserController', function (ContainerInterface $c) {
        return new UserController($c);
    });

    $app->group('/auth', function (Group $group) use ($container) {
        $group->post('/login', \AuthController::class . ':login');
        $group->post('/logout', \AuthController::class . ':logout');
        //$group->post('/request-pass', AuthController::class);
        $group->post('/register', AuthController::class . ':register');
        //$group->post('/reset-pass', AuthController::class);
    });

    $app->group('/user', function (Group $group) use ($container) {
        $group->get('', \UserController::class . ':getUser');
        $group->post('/delete', \UserController::class . ':deleteUser');
        $group->post('/edit', \UserController::class . ':editUser');
        $group->post('/edit/password', \UserController::class . ':editPassword');
    });

    $app->group('/collection', function (Group $group) use ($container) {
        $group->get('/user', \CollectionController::class . ':getByUser');
        $group->post('/add', \CollectionController::class . ':add');
        $group->post('/edit', \CollectionController::class . ':edit');
        $group->delete('/{id}', \CollectionController::class . ':delete');
        $group->get('/{id}', \CollectionController::class . ':getById');
    });

    $app->group('/field', function (Group $group) use ($container) {
        $group->get('/collection/{id}', \FieldController::class . ':getByCollection');
        $group->get('/basic/collection/{id}', \FieldController::class . ':getBasicByCollection');
        $group->get('/custom/collection/{id}', \FieldController::class . ':getCustomByCollection');
    });

    $app->group('/item', function (Group $group) use ($container) {
        $group->post('/add/collection/{id}', \ItemController::class . ':addToCollection');
        $group->post('/edit/{id}/{collectionId}', \ItemController::class . ':editItem');
        $group->get('/get/collection/{id}/{page}/{itemsOnPage}', \ItemController::class . ':getItemFromCollection');
        $group->get('/get/{id}', \ItemController::class . ':getItemById');
        $group->delete('/{itemId}/collection/{collectionId}', \ItemController::class . ':deleteItemFromCollection');
    });

    $app->options('/{routes:.+}', function ($request, $response, $args) {
        return $response;
    });
    
    $app->add(function ($request, $handler) {
        $response = $handler->handle($request);
        return $response
                ->withHeader('Access-Control-Allow-Origin', 'http://localhost:4200')
                ->withHeader('Access-Control-Allow-Headers', 'X-Requested-With, Content-Type, Accept, Origin, Authorization')
                ->withHeader('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, PATCH, OPTIONS');
    });

    $app->map(['GET', 'POST', 'PUT', 'DELETE', 'PATCH'], '/{routes:.+}', function ($request, $response) {
        throw new HttpNotFoundException($request);
    });

};
