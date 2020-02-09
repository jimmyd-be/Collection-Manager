<?php
declare(strict_types=1);

namespace App\Domain\External;

use Psr\Container\ContainerInterface;
use App\Domain\External\Movie\Imdb;

class External {

    private $externalSources = array();
    private $maxReturnItems = 10;

    public function __construct(ContainerInterface $container) {
        array_push($this->externalSources, new Imdb($container, $this->maxReturnItems));
    }

    public function search($search, $type ) {

        $result = array();
        foreach($this->externalSources as $source){
            if(strcasecmp($source->getType(),$type) ==0)
            {
                $result = array_merge($result, $source->searchItem($search));
            }
        }
        
        return $result;
    }

}