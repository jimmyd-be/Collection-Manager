<?php

namespace App\Entity\Dto;


class TokenDto
{
    public $token;

    function __construct($token) {
        $this->token = $token;
    }
    
}