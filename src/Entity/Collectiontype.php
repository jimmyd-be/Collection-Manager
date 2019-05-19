<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Collectiontype
 *
 * @ORM\Table(name="collectionType", uniqueConstraints={@ORM\UniqueConstraint(name="collectionType_UN_type", columns={"type"})})
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
    private $id;

    /**
     * @var string
     *
     * @ORM\Column(name="type", type="string", length=255, nullable=false)
     */
    private $type;

    /**
     * @var bool|null
     *
     * @ORM\Column(name="active", type="boolean", nullable=true)
     */
    private $active;


}
