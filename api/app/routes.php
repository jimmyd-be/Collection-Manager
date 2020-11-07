<?php
declare(strict_types=1);

use Slim\App;
use Slim\Interfaces\RouteCollectorProxyInterface as Group;
use Slim\Exception\HttpNotFoundException;
use App\Controller\AuthController;
use App\Controller\CollectionController;
use App\Controller\FieldController;
use App\Controller\ItemController;
use App\Controller\UserController;
use App\Controller\RoleController;
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
    $container->set('RoleController', function (ContainerInterface $c) {
        return new RoleController($c);
    });



    $app->group('/api/auth', function (Group $group) use ($container) {
        $group->post('/login', AuthController::class . ':login');
        $group->post('/logout', AuthController::class . ':logout');
        //$group->post('/request-pass', AuthController::class);
        $group->post('/register', AuthController::class . ':register');
        //$group->post('/reset-pass', AuthController::class);
    });

    $app->group('/api/admin', function (Group $group) use ($container) {
        $group->get('/users', UserController::class . ':getAllUser');
    });

    $app->group('/api/user', function (Group $group) use ($container) {
        $group->get('', UserController::class . ':getUser');
        $group->post('/delete', UserController::class . ':deleteUser');
        $group->post('/edit', UserController::class . ':editUser');
        $group->post('/edit/password', UserController::class . ':editPassword');
    });

    $app->group('/api/collection', function (Group $group) use ($container) {
        $group->get('/user', CollectionController::class . ':getByUser');
        $group->get('/types', CollectionController::class . ':getCollectionTypes');
        $group->post('/add', CollectionController::class . ':add');
        $group->post('/edit', CollectionController::class . ':edit');
        $group->delete('/{id}', CollectionController::class . ':delete');
        $group->get('/{id}', CollectionController::class . ':getById');
        $group->post('/{id}/share', CollectionController::class . ':share');
        $group->get('/{id}/users', CollectionController::class . ':getAllCollectionUsers');
        $group->delete('/{id}/user/{userId}', CollectionController::class . ':deleteUserFromCollection');
    });

    $app->group('/api/field', function (Group $group) use ($container) {
        $group->get('/collection/{id}', FieldController::class . ':getByCollection');
        $group->get('/basic/collection/{id}', FieldController::class . ':getBasicByCollection');
        $group->get('/custom/collection/{id}', FieldController::class . ':getCustomByCollection');
    });

    $app->group('/api/role', function (Group $group) use ($container) {
        $group->get('/active', RoleController::class . ':getActiveRoles');
    });

    $app->group('/api/item', function (Group $group) use ($container) {
        $group->post('/add/collection/{id}', ItemController::class . ':addToCollection');
        $group->post('/external/add/collection/{collectionId}/{source}/{externalId}', ItemController::class . ':addExternalItemToCollection');
        $group->post('/edit/{id}/{collectionId}', ItemController::class . ':editItem');
        $group->get('/get/collection/{id}/{page}/{itemsOnPage}', ItemController::class . ':getItemFromCollection');
        $group->get('/get/{id}', ItemController::class . ':getItemById');
        $group->get('/external/{type}', ItemController::class . ':searchItemExternally');
        $group->delete('/{itemId}/collection/{collectionId}', ItemController::class . ':deleteItemFromCollection');
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
