<?php
declare(strict_types=1);

use DI\Container;
use Monolog\Logger;
use Slim\App;

return function (App $app) {
    /** @var Container $container */
    $container = $app->getContainer();

    // Global Settings Object
    $container->set('settings', [
        'displayErrorDetails' => true, // Should be set to false in production,
        'secureKey' => 'wk67FeVfD7H92RHCjDZ82sPVfxq*whXH8',
        'logger' => [
            'name' => 'slim-app',
            'path' => isset($_ENV['docker']) ? 'php://stdout' : __DIR__ . '/../logs/app.log',
            'level' => Logger::DEBUG,
        ],
        'doctrine' => [
            // if true, metadata caching is forcefully disabled
            'dev_mode' => true,

            // path where the compiled metadata info will be cached
            // make sure the path exists and it is writable
            'cache_dir' => __DIR__ . '/var/doctrine',

            // you should add any other path containing annotated entity classes
            'metadata_dirs' => [__DIR__ . '/src/Entity'],

            'connection' => [
                'driver' => 'pdo_mysql',
                'host' => '127.0.0.1',
                'port' => 32768,
                'dbname' => 'newcm',
                'user' => 'root',
                'password' => 'testDatabase'
                //,  'charset' => 'utf-8'
            ]
            ],
    ]);
};
