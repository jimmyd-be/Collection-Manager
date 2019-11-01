<?php
declare(strict_types=1);

use App\Application\Middleware\SessionMiddleware;
use App\Application\Middleware\JsonBodyParserMiddleware;
use App\Application\Middleware\AuthenticationMiddleware;
use Slim\App;

return function (App $app) {
    //$app->add(SessionMiddleware::class);
    $app->add(new AuthenticationMiddleware($app->getContainer()));
    $app->add(JsonBodyParserMiddleware::class);
};
