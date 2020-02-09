<?php

namespace App\Domain\External;

interface IExternal {

    public function getType(): string;
    public function searchItem($search);
    
}