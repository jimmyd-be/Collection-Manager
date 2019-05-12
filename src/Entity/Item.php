<?php



use Doctrine\ORM\Mapping as ORM;

/**
 * Item
 *
 * @ORM\Table(name="item", indexes={@ORM\Index(name="author", columns={"author"})})
 * @ORM\Entity
 */
class Item
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
     * @ORM\Column(name="name", type="string", length=255, nullable=false)
     */
    private $name;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="creationDate", type="datetime", nullable=false)
     */
    private $creationdate;

    /**
     * @var \DateTime|null
     *
     * @ORM\Column(name="lastModified", type="datetime", nullable=true)
     */
    private $lastmodified;

    /**
     * @var int|null
     *
     * @ORM\Column(name="modifiedBy", type="bigint", nullable=true, options={"unsigned"=true})
     */
    private $modifiedby;

    /**
     * @var bool|null
     *
     * @ORM\Column(name="active", type="boolean", nullable=true)
     */
    private $active;

    /**
     * @var string|null
     *
     * @ORM\Column(name="image", type="string", length=255, nullable=true)
     */
    private $image;

    /**
     * @var \User
     *
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="author", referencedColumnName="id")
     * })
     */
    private $author;

    /**
     * @var \Doctrine\Common\Collections\Collection
     *
     * @ORM\ManyToMany(targetEntity="Collection", mappedBy="itemid")
     */
    private $collectionid;

    /**
     * Constructor
     */
    public function __construct()
    {
        $this->collectionid = new \Doctrine\Common\Collections\ArrayCollection();
    }

}
