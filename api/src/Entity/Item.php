<?php
namespace App\Entity;

use DateTime;
use Doctrine\ORM\Mapping as ORM;

/**
 * Item
 *
 * @ORM\Table(name="cm_item", indexes={@ORM\Index(name="author", columns={"author"})})
 * @ORM\Entity
 */
class Item
{

    /**
     *
     * @var int
     *
     * @ORM\Column(name="id", type="bigint", nullable=false, options={"unsigned"=true})
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private int $id;

    /**
     *
     * @var string
     *
     * @ORM\Column(name="name", type="string", length=255, nullable=false)
     */
    private string $name;

    /**
     *
     * @var \DateTime
     *
     * @ORM\Column(name="creationDate", type="datetime", nullable=false)
     */
    private DateTime $creationdate;

    /**
     *
     * @var \DateTime|null
     *
     * @ORM\Column(name="lastModified", type="datetime", nullable=true)
     */
    private ?DateTime $lastmodified;

    /**
     *
     * @var int|null
     *
     * @ORM\Column(name="modifiedBy", type="bigint", nullable=true, options={"unsigned"=true})
     */
    private ?int $modifiedby;

    /**
     *
     * @var bool|null
     *
     * @ORM\Column(name="active", type="boolean", nullable=true)
     */
    private ?bool $active;

    /**
     *
     * @var string|null
     *
     * @ORM\Column(name="image", type="string", length=255, nullable=true)
     */
    private ?string $image;

    /**
     *
     * @var User
     *
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="author", referencedColumnName="id")
     * })
     */
    private User $author;

    /**
     *
     * @var \Doctrine\Common\Collections\Collection
     *
     * @ORM\ManyToMany(targetEntity="Collection", mappedBy="itemid")
     */
    private Collection $collectionid;

    /**
     * Constructor
     */
    public function __construct()
    {
        $this->collectionid = new \Doctrine\Common\Collections\ArrayCollection();
    }

    public function getId(): int
    {
        return $this->id;
    }

    public function setId(int $id)
    {
        $this->id = $id;
    }

    public function getName(): string
    {
        return $this->name;
    }

    public function setName(string $name)
    {
        $this->name = $name;
    }

    public function getCreationdate(): DateTime
    {
        return $this->creationdate;
    }

    public function setCreationdate(DateTime $creationdate)
    {
        $this->creationdate = $creationdate;
    }

    public function getLastmodified(): DateTime
    {
        return $this->lastmodified;
    }

    public function setLastmodified(DateTime $lastmodified)
    {
        $this->lastmodified = $lastmodified;
    }

    public function getModifiedby(): int
    {
        return $this->modifiedby;
    }

    public function setModifiedby(int $modifiedby)
    {
        $this->modifiedby = $modifiedby;
    }

    public function getActive(): ?bool
    {
        return $this->active;
    }

    public function setActive(?bool $active)
    {
        $this->active = $active;
    }

    public function getImage(): ?string
    {
        return $this->image;
    }

    public function setImage(?string $image)
    {
        $this->image = $image;
    }

    public function getAuthor(): User
    {
        return $this->author;
    }

    public function setAuthor(User $author)
    {
        $this->author = $author;
    }

    public function getCollectionid(): Collection
    {
        return $this->collectionid;
    }

    public function setCollectionid(Collection $collectionid)
    {
        $this->collectionid = $collectionid;
    }

    public function deleteCollection(Collection $collecion)
    {
        $this->collectionid->removeElement($collecion);
    }
}
