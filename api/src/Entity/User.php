<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * User
 *
 * @ORM\Table(name="cm_user", uniqueConstraints={@ORM\UniqueConstraint(name="user_UN_name", columns={"username"}), @ORM\UniqueConstraint(name="user_UN_mail", columns={"mail"})})
 * @ORM\Entity
 */
class User
{
    /**
     * @var int
     *
     * @ORM\Column(name="id", type="bigint", nullable=false, options={"unsigned"=true})
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private int $id;

    /**
     * @var string
     *
     * @ORM\Column(name="username", type="string", length=255, nullable=false)
     */
    private string $username;

    /**
     * @var string
     *
     * @ORM\Column(name="mail", type="string", length=255, nullable=false)
     */
    private string $mail;

    /**
     * @var string|null
     *
     * @ORM\Column(name="userPassword", type="string", length=255, nullable=true)
     */
    private ?string $userpassword;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="creationDate", type="datetime", nullable=false)
     */
    private \DateTime $creationdate;

    /**
     * @var bool|null
     *
     * @ORM\Column(name="isAdmin", type="boolean", nullable=true)
     */
    private ?bool $isadmin;

    /**
     * @var string
     *
     * @ORM\Column(name="theme", type="string", length=255, nullable=true)
     */
    private ?string $theme;

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

	public function getUsername(): string{
		return $this->username;
	}

	public function setUsername(string $username){
		$this->username = $username;
	}

	public function getMail() : string{
		return $this->mail;
	}

	public function setMail(string $mail){
		$this->mail = $mail;
  }
  
  public function getTheme() : ?string{
		return $this->theme;
	}

	public function setTheme(?string $theme){
		$this->theme = $theme;
	}

	public function getUserpassword(): ?string{
		return $this->userpassword;
	}

	public function setUserpassword(?string $userpassword){
		$this->userpassword = $userpassword;
	}

	public function getCreationdate(): \DateTime{
		return $this->creationdate;
	}

	public function setCreationdate(\DateTime $creationdate){
		$this->creationdate = $creationdate;
	}

	public function getIsadmin() : ?bool{
		return $this->isadmin;
	}

	public function setIsadmin(?bool $isadmin){
		$this->isadmin = $isadmin;
	}

	public function getActive() : bool{
		return $this->active;
	}

	public function setActive(bool $active){
		$this->active = $active;
	}

}
