<?php
declare(strict_types=1);

namespace App\Domain\External\Movie;

use Psr\Container\ContainerInterface;
use Imdb\TitleSearch;
use App\Domain\External\IExternal;
use App\Entity\Dto\ItemSearchDto;

class Imdb implements IExternal{

    private $imdb;
    private $maxReturnItems;
    
    public function __construct(ContainerInterface $container, $maxReturnItems) {
        $this->imdb = new TitleSearch();
        $this->maxReturnItems = $maxReturnItems;
    }

    public function getType(): string
    {
        return 'Movie';
    }

    public function searchItem($search) {

        $movies = array();
        $imdbResult = $this->imdb->search($search, array(TitleSearch::MOVIE));

        foreach($imdbResult as $result) {

            $item = new ItemSearchDto();
            $item->name = $result->title();
            $item->externalId = $result->real_id();
            $item->image= $result->photo(true);
            $item->releaseDate; $result->year();
            $item->source = 'IMDB';

            array_push($movies, $item);

            if(count($movies) >= $this->maxReturnItems) {
            break;
            }
            
        }

        return $movies;
    }

}