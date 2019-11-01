<?php

namespace App\Entity;


class Token
{
    private $userId;
    private $expirationDate;

    function __construct($id, $expirationDate) {
        $this->userId = $id;
        $this->expirationDate = $expirationDate;
    }
    
	public function getUserId(){
		return $this->userId;
	}

	public function setUserId($userId){
		$this->userId = $userId;
	}

	public function getExpirationDate(){
		return $this->expirationDate;
	}

	public function setExpirationDate($expirationDate){
		$this->expirationDate = $expirationDate;
	}
    
}