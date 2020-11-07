<?php
declare(strict_types=1);

use DI\Container;
use Monolog\Handler\StreamHandler;
use Monolog\Logger;
use Monolog\Processor\UidProcessor;
use Psr\Log\LoggerInterface;
use Slim\App;
use Doctrine\ORM\Tools\Setup;
use Doctrine\ORM\EntityManager;
use Doctrine\ORM\Mapping\Driver\AnnotationDriver;
use Doctrine\Common\Annotations\AnnotationReader;
use Doctrine\Common\Cache\FilesystemCache;

return function (App $app) {
    $container = $app->getContainer();

    $container->set(LoggerInterface::class, function (Container $c) {
        $settings = $c->get('settings');

        $loggerSettings = $settings['logger'];
        $logger = new Logger($loggerSettings['name']);

        $processor = new UidProcessor();
        $logger->pushProcessor($processor);

        $handler = new StreamHandler($loggerSettings['path'], $loggerSettings['level']);
        $logger->pushHandler($handler);

        return $logger;
    });

    $container->set(EntityManager::class,function (Container $container): EntityManager {
        $settings = $container->get('settings');

        $config = Setup::createAnnotationMetadataConfiguration(
            
            $settings['doctrine']['metadata_dirs'],
            $settings['doctrine']['dev_mode']
        );

        $config->setMetadataDriverImpl(
            new AnnotationDriver(
                new AnnotationReader,
                $settings['doctrine']['metadata_dirs']
            )
        );

        $config->setMetadataCacheImpl(
            new FilesystemCache(
                $settings['doctrine']['cache_dir']
            )
        );

        return EntityManager::create(
            $settings['doctrine']['connection'],
            $config
        );
    
    });
};
