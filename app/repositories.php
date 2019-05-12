<?php
declare(strict_types=1);

use App\Domain\User\UserRepository;
use App\Infrastructure\Persistence\User\InMemoryUserRepository;
use DI\Container;
use Slim\App;

return function (App $app) {
    /** @var Container $container */
    $container = $app->getContainer();

    $container->set(UserRepository::class, UserRepository::class);
    $container->set(CollectionRepository::class, CollectionRepository::class);
    $container->set(CollectiontypeRepository::class, CollectiontypeRepository::class);
    $container->set(FieldRepository::class, FieldRepository::class);
    $container->set(FieldtypeRepository::class, FieldtypeRepository::class);
    $container->set(ItemdataRepository::class, ItemdataRepository::class);
    $container->set(ItemRepository::class, ItemRepository::class);
    $container->set(RoleRepository::class, RoleRepository::class);
    $container->set(UserCollectionRepository::class, UserCollectionRepository::class);

};
