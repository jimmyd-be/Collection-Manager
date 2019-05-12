<?php



use Doctrine\ORM\Mapping as ORM;

/**
 * Collection
 *
 * @ORM\Table(name="collection", uniqueConstraints={@ORM\UniqueConstraint(name="collection_UN_name_owner", columns={"name", "owner"})}, indexes={@ORM\Index(name="typeId", columns={"typeId"}), @ORM\Index(name="owner", columns={"owner"})})
 * @ORM\Entity
 */
class Collection
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
     * @var bool|null
     *
     * @ORM\Column(name="active", type="boolean", nullable=true)
     */
    private $active;

    /**
     * @var \User
     *
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="owner", referencedColumnName="id")
     * })
     */
    private $owner;

    /**
     * @var \Collectiontype
     *
     * @ORM\ManyToOne(targetEntity="Collectiontype")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="typeId", referencedColumnName="id")
     * })
     */
    private $typeid;

    /**
     * @var \Doctrine\Common\Collections\Collection
     *
     * @ORM\ManyToMany(targetEntity="Field", inversedBy="collectionid")
     * @ORM\JoinTable(name="collectionfield",
     *   joinColumns={
     *     @ORM\JoinColumn(name="collectionId", referencedColumnName="id")
     *   },
     *   inverseJoinColumns={
     *     @ORM\JoinColumn(name="fieldId", referencedColumnName="id")
     *   }
     * )
     */
    private $fieldid;

    /**
     * @var \Doctrine\Common\Collections\Collection
     *
     * @ORM\ManyToMany(targetEntity="Item", inversedBy="collectionid")
     * @ORM\JoinTable(name="collectionitem",
     *   joinColumns={
     *     @ORM\JoinColumn(name="collectionId", referencedColumnName="id")
     *   },
     *   inverseJoinColumns={
     *     @ORM\JoinColumn(name="itemId", referencedColumnName="id")
     *   }
     * )
     */
    private $itemid;

    /**
     * Constructor
     */
    public function __construct()
    {
        $this->fieldid = new \Doctrine\Common\Collections\ArrayCollection();
        $this->itemid = new \Doctrine\Common\Collections\ArrayCollection();
    }

}
