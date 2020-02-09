<?php

declare(strict_types=1);

namespace App\Domain\External\Movie;

use Psr\Container\ContainerInterface;
use Imdb\TitleSearch;
use Imdb\Title;
use App\Domain\External\IExternal;
use App\Entity\Dto\ItemSearchDto;
use App\Entity\Item;
use App\Entity\Itemdata;

class Imdb implements IExternal
{

    private $imdb;
    private $maxReturnItems;

    public function __construct(ContainerInterface $container, $maxReturnItems)
    {
        $this->imdb = new TitleSearch();
        $this->maxReturnItems = $maxReturnItems;
    }

    public function getType(): string
    {
        return 'Movie';
    }

    public function getSource(): string
    {
        return 'IMDB';
    }

    public function searchItem($search)
    {

        $movies = array();
        $imdbResult = $this->imdb->search($search, array(TitleSearch::MOVIE));

        foreach ($imdbResult as $result) {

            $item = new ItemSearchDto();
            $item->name = $result->title();
            $item->externalId = $result->real_id();
            $item->image = $result->photo(true);
            $item->releaseDate;
            $result->year();
            $item->source = $this->getSource();
            $item->url = $result->main_url();

            array_push($movies, $item);

            if (count($movies) >= $this->maxReturnItems) {
                break;
            }
        }

        return $movies;
    }

    public function getItem($externalId, $fields)
    {
        $title = new Title($externalId);
        $newItem = new Item();
        $newItem->setName($title->title());
        $newItem->setImage($title->photo(true));

        $data = array(); // list of ItemData

        foreach ($fields as &$field) {

            switch ($field->getName()) {
                case 'genre':

                    foreach ($title->genres() as &$genre) {
                        $newData = new Itemdata();

                        $newData->setFieldid($field);
                        $newData->setFieldvalue($genre);
                        if ($newData->getFieldvalue() != null) {
                            array_push($data, $newData);
                        }
                    }
                    break;
                case 'runtime':
                    $newData = new Itemdata();

                    $newData->setFieldid($field);
                    $newData->setFieldvalue($title->runtime());
                    if ($newData->getFieldvalue() != null) {
                        array_push($data, $newData);
                    }
                    break;
                case 'releaseDate':
                    $newData = new Itemdata();

                    $newData->setFieldid($field);
                    $newData->setFieldvalue($title->year());
                    if ($newData->getFieldvalue() != null) {
                        array_push($data, $newData);
                    }
                    break;
                case 'storyline':
                    $newData = new Itemdata();

                    $newData->setFieldid($field);
                    $newData->setFieldvalue($title->storyline());
                    if ($newData->getFieldvalue() != null) {
                        array_push($data, $newData);
                    }
                    break;
                case 'director':
                    foreach ($title->director() as &$director) {
                        $newData = new Itemdata();

                        $newData->setFieldid($field);
                        $newData->setFieldvalue($director['name']);
                        if ($newData->getFieldvalue() != null) {
                            array_push($data, $newData);
                        }
                    }
                    break;
                case 'writer':
                    foreach ($title->writing() as &$writer) {
                        $newData = new Itemdata();

                        $newData->setFieldid($field);
                        $newData->setFieldvalue($writer['name']);
                        if ($newData->getFieldvalue() != null) {
                            array_push($data, $newData);
                        }
                    }
                    break;
                case 'cast':
                    foreach ($title->cast() as &$cast) {
                        $newData = new Itemdata();

                        $newData->setFieldid($field);
                        $newData->setFieldvalue($cast['name']);
                        if ($newData->getFieldvalue() != null) {
                            array_push($data, $newData);
                        }
                    }
                    break;
                case 'cover':
                    $newData = new Itemdata();

                    $newData->setFieldid($field);
                    $newData->setFieldvalue($title->photo(true));
                    if ($newData->getFieldvalue() != null) {
                        array_push($data, $newData);
                    }
                    break;
                case 'rating':
                    $newData = new Itemdata();

                    $newData->setFieldid($field);
                    $newData->setFieldvalue($title->metacriticRating() / 10);
                    if ($newData->getFieldvalue() != null) {
                        array_push($data, $newData);
                    }
                    break;
            }
        }

        return array(
            'item' => $newItem,
            'itemData' => $data
        );
    }
}
