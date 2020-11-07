<?php
declare (strict_types = 1);

namespace App\Controller;

use App\Domain\External\External;
use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Container\ContainerInterface;
use App\Repository\FieldRepository;
use App\Repository\UserRepository;
use App\Repository\CollectionRepository;
use App\Repository\ItemRepository;
use App\Repository\ItemdataRepository;
use App\Entity\Item;
use App\Entity\Itemdata;
use App\Mappers\ItemMapper;

class ItemController
{
    protected ContainerInterface $container;

    private FieldRepository $fieldRepo;
    private UserRepository $userRepo;
    private CollectionRepository $collectionRepo;
    private ItemRepository $itemRepo;
    private ItemdataRepository $itemDataRepo;
    private ItemMapper $itemMapper;
    private External $external;

    // constructor receives container instance
    public function __construct(ContainerInterface $container)
    {
        $this->container = $container;

        $this->fieldRepo = $container->get(FieldRepository::class);
        $this->userRepo = $container->get(UserRepository::class);
        $this->collectionRepo = $container->get(CollectionRepository::class);
        $this->itemRepo = $container->get(ItemRepository::class);
        $this->itemDataRepo = $container->get(ItemdataRepository::class);

        $this->itemMapper = new ItemMapper($container);
        $this->external = new External($container);
    }

    public function addToCollection(Request $request, Response $response, array $args): Response
    {
        $collectionId = (int)$args['id'];
        $userId = (int)$request->getAttribute('userId');
        $input = $request->getParsedBody();

        $collection = $this->collectionRepo->getById($collectionId);

        $customFields = $this->fieldRepo->getCustomByCollectionId($collectionId);
        $basicFields = $this->fieldRepo->getBasicByCollectionId($collectionId);

        $allFields = array_merge($customFields, $basicFields);

        $keys = array_keys($input);

        $titleKey = '';

        foreach ($basicFields as $field) {
            if ($field->getName() == 'title') {
                $titleKey = $field->getId() . "_0";
            }
        }

        $newItem = new Item();
        $newItem->setName($input[$titleKey]);
        $newItem->setCreationdate(new \DateTime());
        $newItem->setAuthor($this->userRepo->getById($userId));
        $newItem->setActive(true);
        $newItem = $this->itemRepo->save($newItem);

        $collection->addItem($newItem);

        $this->collectionRepo->save($collection);

        $count = 0;

        foreach ($allFields as $field) {
            if ($field->getName() != 'title') {
                $keysFound = array_filter($keys, function ($var) use ($field) {
                    return (stripos($var, $field->getId()) !== false);
                });
                
                if (count($keysFound) > 0) {
                    foreach ($keysFound as $key) {
                        $newItemData = new Itemdata();
                        $newItemData->setFieldvalue($input[$key]);
                        $newItemData->setValueCount($count);
                        $newItemData->setItemid($newItem);
                        $newItemData->setFieldid($field);
                        $this->itemDataRepo->save($newItemData);
                        $count = $count + 1;
                    }
                }
            }
        }

        return $response;
    }

    public function getItemFromCollection(Request $request, Response $response, array $args): Response
    {
        $collectionId = (int)$args['id'];
        $page = (int)$args['page'];
        $itemsOnPage = (int)$args['itemsOnPage'];
        $userId = (int)$request->getAttribute('userId');

        $items = $this->itemRepo->getItemsByCollection($collectionId, $page, $itemsOnPage);

        $returnValue = array();

        foreach($items as $item)
        {
            array_push($returnValue, $this->itemMapper->mapItemToDto($item, $this->itemDataRepo->getByItem((int)$item->getId())));
        }

        $response->getBody()->write(json_encode($returnValue));
        return  $response->withHeader('Content-Type', 'application/json');
    }

    public function deleteItemFromCollection(Request $request, Response $response, array $args): Response
    {
        $itemId = (int)$args['itemId'];
        $collectionId = (int)$args['collectionId'];

        $item = $this->itemRepo->getItembyId($itemId);
        $collection = $this->collectionRepo->getById($collectionId);

        if($item != null && $collection != null)
        {
            $collection->deleteItem($item);

            $this->collectionRepo->save($collection);

            $item->deleteCollection($collection);

            if(count($item->getCollectionid()) == 0)
            {
                $item->setActive(false);
                $this->itemRepo->save($item);
            }
        }
        else{
            $response = $response->withStatus(400);
        }

        return $response;
    }

    public function getItemById(Request $request, Response $response, array $args): Response
    {
        $itemid = (int)$args['id'];
        $userId = (int)$request->getAttribute('userId');

        $item = $this->itemRepo->getItembyId($itemid);

        $fullItem = $this->itemMapper->mapItemToDto($item, $this->itemDataRepo->getByItem((int)$item->getId()));

        $response->getBody()->write(json_encode($fullItem));
        return  $response->withHeader('Content-Type', 'application/json');
    }

    public function editItem(Request $request, Response $response, array $args): Response
    {
        $itemId = (int)$args['id'];
        $collectionId = (int)$args['collectionId'];
        $userId = (int)$request->getAttribute('userId');
        $input = $request->getParsedBody();

        $customFields = $this->fieldRepo->getCustomByCollectionId($collectionId);
        $basicFields = $this->fieldRepo->getBasicByCollectionId($collectionId);

        $allFields = array_merge($customFields, $basicFields);

        $keys = array_keys($input);

        $titleKey = '';

        foreach ($basicFields as $field) {
            if ($field->getName() == 'title') {
                $titleKey = $field->getId() . "_0";
            }
        }

        $newItem = $this->itemRepo->getItembyId($itemId);
        $newItem->setName($input[$titleKey]);
        $newItem = $this->itemRepo->save($newItem);

        $this->itemDataRepo->deleteFromItem($itemId);

        $count = 0;

        foreach ($allFields as $field) {
            if ($field->getName() != 'title') {
                $keysFound = array_filter($keys, function ($var) use ($field) {
                    return (stripos($var, $field->getId()) !== false);
                });

                if (count($keysFound) > 0) {
                    foreach ($keysFound as $key) {
                        $newItemData = new Itemdata();
                        $newItemData->setFieldvalue($input[$key]);
                        $newItemData->setValueCount($count);
                        $newItemData->setItemid($newItem);
                        $newItemData->setFieldid($field);
                        $this->itemDataRepo->save($newItemData);
                        $count = $count + 1;
                    }
                }
            }
        }

        return $response;
    }

    public function searchItemExternally(Request $request, Response $response, array $args): Response
    {
        $type = $args['type'];
        $search = $request->getQueryParams()['search'];

        $result = $this->external->search($search, $type);

        $response->getBody()->write(json_encode($result));
        return  $response->withHeader('Content-Type', 'application/json');
    }

    public function addExternalItemToCollection($request, $response, $args): Response
    {
        $collectionId = (int)$args['collectionId'];
        $source = $args['source'];
        $externalId = $args['externalId'];
        $userId = (int)$request->getAttribute('userId');

        $collection = $this->collectionRepo->getById($collectionId);
        $fields = $this->fieldRepo->getBasicByCollectionId($collectionId);

        $itemArray = $this->external->getItem($source, $externalId, $fields);
        
        $itemData = $itemArray['itemData'];
        
        $item = $itemArray['item'];
        $item->setCreationdate(new \DateTime());
        $item->setAuthor($this->userRepo->getById($userId));
        $item->setActive(true);
        $item = $this->itemRepo->save($item);

        $collection->addItem($item);

        $this->collectionRepo->save($collection);

        $count = 0;
        foreach($itemData as &$data)
        {
            $data->setItemid($item);
            $data->setValueCount($count++);

            $this->itemDataRepo->save($data);
        }

        return $response;
    }
}
