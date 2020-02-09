<?php
declare (strict_types = 1);

namespace App\Controller;

use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Container\ContainerInterface;
use App\Repository\FieldRepository;
use App\Entity\Field;
use App\Mappers\CollectionFieldMapper;

class FieldController
{
    protected ContainerInterface $container;

    private FieldRepository $fieldRepo;
    private CollectionFieldMapper $fieldMapper;

    // constructor receives container instance
    public function __construct(ContainerInterface $container)
    {
        $this->container = $container;

        $this->fieldRepo = $container->get(FieldRepository::class);
        $this->fieldMapper = new CollectionFieldMapper($container);
    }

    public function getByCollection(Request $request, Response $response, array $args): Response
    {

        $userId = (int)$request->getAttribute('userId');
        $collectionId = (int)$args['id'];

        if ($collectionId != 0) {

            $customFields = $this->fieldRepo->getCustomByCollectionId($collectionId);
            $basicFields = $this->fieldRepo->getBasicByCollectionId($collectionId);

            $allFields = array_merge($customFields, $basicFields);

            $fields = array();

            foreach ($allFields as $field) {
                array_push($fields, $this->fieldMapper->fieldToDto($field));
            }

            $response->getBody()->write(json_encode($fields));
            return  $response->withHeader('Content-Type', 'application/json');
        }
        return $response;
    }

    public function getBasicByCollection(Request $request, Response $response, array $args): Response
    {
        $userId = (int)$request->getAttribute('userId');
        $collectionId = (int)$args['id'];

        $basicFields = $this->fieldRepo->getBasicByCollectionId($collectionId);
        $fields = array();

        foreach ($basicFields as $field) {
            array_push($fields, $this->fieldMapper->fieldToDto($field));
        }

        $response->getBody()->write(json_encode($fields));
        return  $response->withHeader('Content-Type', 'application/json');

        return $response;
    }

    public function getCustomByCollection(Request $request, Response $response, array $args): Response
    {
        $userId = (int)$request->getAttribute('userId');
        $collectionId = (int)$args['id'];

        $customFields = $this->fieldRepo->getCustomByCollectionId($collectionId);
        $fields = array();

        foreach ($customFields as $field) {
            array_push($fields, $this->fieldMapper->fieldToDto($field));
        }

        $response->getBody()->write(json_encode($fields));
        return  $response->withHeader('Content-Type', 'application/json');

        return $response;
    }
}
