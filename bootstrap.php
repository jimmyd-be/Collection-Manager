<?php

use Doctrine\Common\Annotations\AnnotationReader;
use Doctrine\Common\Cache\FilesystemCache;
use Doctrine\ORM\EntityManager;
use Doctrine\ORM\Mapping\Driver\AnnotationDriver;
use Doctrine\ORM\Tools\Setup;
use Slim\Factory\App;
use Slim\Factory\AppFactory;
use Psr\Container\ContainerInterface;


require_once __DIR__ . '/vendor/autoload.php';

return function (App $app) {

    $container = $app->getContainer();

    $container[EntityManager::class] = function (ContainerInterface $container): EntityManager {
        $config = Setup::createAnnotationMetadataConfiguration(
            $container['settings']['doctrine']['metadata_dirs'],
            $container['settings']['doctrine']['dev_mode']
        );

        $config->setMetadataDriverImpl(
            new AnnotationDriver(
                new AnnotationReader,
                $container['settings']['doctrine']['metadata_dirs']
            )
        );

        $config->setMetadataCacheImpl(
            new FilesystemCache(
                $container['settings']['doctrine']['cache_dir']
            )
        );

        return EntityManager::create(
            $container['settings']['doctrine']['connection'],
            $config
        );
    };

    return $container;

};