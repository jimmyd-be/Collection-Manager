<?php
namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Field
 *
 * @ORM\Table(name="cm_field", indexes={@ORM\Index(name="collectionBaseType", columns={"collectionBaseType"}), @ORM\Index(name="type", columns={"type"})})
 * @ORM\Entity
 */
class Field
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
     * @var string|null
     *
     * @ORM\Column(name="name", type="string", length=255, nullable=true)
     */
    private ?string $name;

    /**
     *
     * @var string|null
     *
     * @ORM\Column(name="choises", type="string", length=255, nullable=true)
     */
    private ?string $choises;

    /**
     *
     * @var bool|null
     *
     * @ORM\Column(name="required", type="boolean", nullable=true)
     */
    private ?bool $required;

    /**
     *
     * @var string|null
     *
     * @ORM\Column(name="placeHolder", type="string", length=255, nullable=true)
     */
    private ?string $placeholder;

    /**
     *
     * @var string|null
     *
     * @ORM\Column(name="label", type="string", length=255, nullable=true)
     */
    private ?string $label;

    /**
     *
     * @var string|null
     *
     * @ORM\Column(name="otherOptions", type="string", length=255, nullable=true)
     */
    private ?string $otheroptions;

    /**
     *
     * @var int
     *
     * @ORM\Column(name="fieldOrder", type="integer", nullable=false)
     */
    private int $fieldorder;

    /**
     *
     * @var string
     *
     * @ORM\Column(name="place", type="string", length=10, nullable=false)
     */
    private string $place;

    /**
     *
     * @var bool|null
     *
     * @ORM\Column(name="multiValues", type="boolean", nullable=true)
     */
    private ?bool $multivalues;

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
     * @ORM\Column(name="labelPosition", type="string", length=45, nullable=true)
     */
    private ?string $labelposition;

    /**
     *
     * @var string|null
     *
     * @ORM\Column(name="widget", type="string", length=45, nullable=true)
     */
    private ?string $widget;

    /**
     *
     * @var Fieldtype
     *
     * @ORM\ManyToOne(targetEntity="Fieldtype")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="type", referencedColumnName="id")
     * })
     */
    private Fieldtype $type;

    /**
     *
     * @var Collectiontype
     *
     * @ORM\ManyToOne(targetEntity="Collectiontype")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="collectionBaseType", referencedColumnName="id")
     * })
     */
    private Collectiontype $collectionbasetype;

    /**
     *
     * @var \Doctrine\Common\Collections\Collection
     *
     * @ORM\ManyToMany(targetEntity="Collection", mappedBy="fieldid")
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

    public function getName(): ?string
    {
        return $this->name;
    }

    public function setName(?string $name)
    {
        $this->name = $name;
    }

    public function getChoises(): ?string
    {
        return $this->choises;
    }

    public function setChoises(?string $choises)
    {
        $this->choises = $choises;
    }

    public function getRequired(): ?bool
    {
        return $this->required;
    }

    public function setRequired(?bool $required)
    {
        $this->required = $required;
    }

    public function getPlaceholder(): ?string
    {
        return $this->placeholder;
    }

    public function setPlaceholder(?string $placeholder)
    {
        $this->placeholder = $placeholder;
    }

    public function getLabel(): ?string
    {
        return $this->label;
    }

    public function setLabel(?string $label)
    {
        $this->label = $label;
    }

    public function getOtheroptions(): ?string
    {
        return $this->otheroptions;
    }

    public function setOtheroptions(?string $otheroptions)
    {
        $this->otheroptions = $otheroptions;
    }

    public function getFieldorder(): int
    {
        return $this->fieldorder;
    }

    public function setFieldorder(int $fieldorder)
    {
        $this->fieldorder = $fieldorder;
    }

    public function getPlace(): ?string
    {
        return $this->place;
    }

    public function setPlace(?string $place)
    {
        $this->place = $place;
    }

    public function getMultivalues(): ?bool
    {
        return $this->multivalues;
    }

    public function setMultivalues(?bool $multivalues)
    {
        $this->multivalues = $multivalues;
    }

    public function getActive(): ?bool
    {
        return $this->active;
    }

    public function setActive(?bool $active)
    {
        $this->active = $active;
    }

    public function getLabelposition(): ?string
    {
        return $this->labelposition;
    }

    public function setLabelposition(?string $labelposition)
    {
        $this->labelposition = $labelposition;
    }

    public function getWidget(): ?string
    {
        return $this->widget;
    }

    public function setWidget(?string $widget)
    {
        $this->widget = $widget;
    }

    public function getType(): Fieldtype
    {
        return $this->type;
    }

    public function setType(Fieldtype $type)
    {
        $this->type = $type;
    }

    public function getCollectionbasetype(): Collectiontype
    {
        return $this->collectionbasetype;
    }

    public function setCollectionbasetype(Collectiontype $collectionbasetype)
    {
        $this->collectionbasetype = $collectionbasetype;
    }

    public function getCollectionid(): Collection
    {
        return $this->collectionid;
    }

    public function setCollectionid(Collection $collectionid)
    {
        $this->collectionid = $collectionid;
    }
}
