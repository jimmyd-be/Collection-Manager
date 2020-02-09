<?php

namespace App\Domain\External;

use App\Entity\Item;

interface IExternal {

    public function getType(): string;
    public function getSource(): string;
    public function searchItem($search);
    public function getItem($externalId, $fields);
    
}