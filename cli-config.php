<?php
   declare(strict_types = 1);

   
use Doctrine\ORM\EntityManager;
use Doctrine\ORM\Tools\Console\ConsoleRunner;
use Doctrine\DBAL\Tools\Console\Helper\ConnectionHelper;
use Symfony\Component\Console\Helper\HelperSet;
use Slim\App;

require_once __DIR__ . '/vendor/autoload.php';
require_once __DIR__ . '/bootstrap.php';


return function (App $app): HelperSet {
    $container = $app->getContainer();

    $em = $container[EntityManager::class];

    $helper = new HelperSet(array(
        'db' => new ConnectionHelper($em->getConnection()),
        'em' => new EntityManagerHelper($em)
    ));

    return $helper;
};

ConsoleRunner::run(
    ConsoleRunner::createHelperSet($helper)
);