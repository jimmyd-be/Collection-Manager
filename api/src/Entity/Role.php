<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Role
 *
 * @ORM\Table(name="cm_role", uniqueConstraints={@ORM\UniqueConstraint(name="role_UN_role", columns={"name"})})
 * @ORM\Entity
 */
class Role
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
     * @ORM\Column(name="name", type="string", length=255, nullable=false)
     */
    private string $name;

    /**
     * @var bool|null
     *
     * @ORM\Column(name="active", type="boolean", nullable=true)
     */
    private ?bool $active;

    public function getId() : int{
		return $this->id;
	}

	public function setId(int $id){
		$this->id = $id;
	}

	public function getName() : string{
		return $this->name;
	}

	public function setName(string $name){
		$this->name = $name;
	}

	public function getActive(): ?bool{
		return $this->active;
	}

	public function setActive(?bool $active){
		$this->active = $active;
	}

}
