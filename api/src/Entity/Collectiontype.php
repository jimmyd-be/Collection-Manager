<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Collectiontype
 *
 * @ORM\Table(name="cm_collectiontype", uniqueConstraints={@ORM\UniqueConstraint(name="collectionType_UN_type", columns={"type"})})
 * @ORM\Entity
 */
class Collectiontype
{
    /**
     * @var int
     *
     * @ORM\Column(name="id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private int $id;

    /**
     * @var string
     *
     * @ORM\Column(name="type", type="string", length=255, nullable=false)
     */
    private string $type;

    /**
     * @var bool|null
     *
     * @ORM\Column(name="active", type="boolean", nullable=true)
     */
    private ?bool $active;

    public function getId():int{
		return $this->id;
	}

	public function setId(int $id){
		$this->id = $id;
	}

	public function getType() : string{
		return $this->type;
	}

	public function setType(string $type){
		$this->type = $type;
	}

	public function getActive(): ?bool{
		return $this->active;
	}

	public function setActive(?bool $active){
		$this->active = $active;
	}


}
