<?php
declare(strict_types=1);

namespace App\Controller;

use Psr\Http\Message\ResponseInterface as Response;
use Psr\Container\ContainerInterface;
use App\Repository\CollectionRepository;
use App\Entity\User;
use App\Entity\Token;
use App\Entity\Dto\CollectionDto;
use ReallySimpleJWT\Token as JWT;



class CollectionController
{
    protected $container;

    private $collectionRepo;

    // constructor receives container instance
    public function __construct(ContainerInterface $container) {
        $this->container = $container;

        $this->collectionRepo = $container->get(CollectionRepository::class);
    }

    public function getByUser($request, $response, $args): Response
    {
        $userId = (int)$request->getAttribute('userId');

        $collections = $this->collectionRepo->getByUser($userId);

        $dtoArray = array();

        foreach($collections as $col)
        {
            $newDto = new CollectionDto();
            $newDto->id = $col->getId();
            $newDto->name = $col->getName();
            $newDto->type = $col->getTypeid()->getType();

            array_push($dtoArray, $newDto);
        }

        $response->getBody()->write(json_encode($dtoArray));
        return  $response->withHeader('Content-Type', 'application/json');
    }


}