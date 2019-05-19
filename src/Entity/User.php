<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * User
 *
 * @ORM\Table(name="user", uniqueConstraints={@ORM\UniqueConstraint(name="user_UN_name", columns={"username"}), @ORM\UniqueConstraint(name="user_UN_mail", columns={"mail"})})
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
    private $id;

    /**
     * @var string
     *
     * @ORM\Column(name="username", type="string", length=255, nullable=false)
     */
    private $username;

    /**
     * @var string
     *
     * @ORM\Column(name="mail", type="string", length=255, nullable=false)
     */
    private $mail;

    /**
     * @var string|null
     *
     * @ORM\Column(name="userPassword", type="string", length=255, nullable=true)
     */
    private $userpassword;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="creationDate", type="datetime", nullable=false)
     */
    private $creationdate;

    /**
     * @var bool|null
     *
     * @ORM\Column(name="isAdmin", type="boolean", nullable=true)
     */
    private $isadmin;

    /**
     * @var bool|null
     *
     * @ORM\Column(name="active", type="boolean", nullable=true)
     */
    private $active;


}
