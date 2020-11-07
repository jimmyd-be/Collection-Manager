<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Collection
 *
 * @ORM\Table(name="cm_collection", indexes={@ORM\Index(name="typeId", columns={"typeId"})})
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

    /**
     * @var Collectiontype
     *
     * @ORM\ManyToOne(targetEntity="Collectiontype")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="typeId", referencedColumnName="id")
     * })
     */
    private Collectiontype $typeid;

    /**
     * @var \Doctrine\Common\Collections\Collection
     *
     * @ORM\ManyToMany(targetEntity="Field", inversedBy="collectionid")
     * @ORM\JoinTable(name="cm_collectionfield",
     *   joinColumns={
     *     @ORM\JoinColumn(name="collectionId", referencedColumnName="id")
     *   },
     *   inverseJoinColumns={
     *     @ORM\JoinColumn(name="fieldId", referencedColumnName="id")
     *   }
     * )
     */
    private Field $fieldid;

    /**
     * @var \Doctrine\Common\Collections\Collection
     *
     * @ORM\ManyToMany(targetEntity="Item", inversedBy="collectionid")
     * @ORM\JoinTable(name="cm_collectionitem",
     *   joinColumns={
     *     @ORM\JoinColumn(name="collectionId", referencedColumnName="id")
     *   },
     *   inverseJoinColumns={
     *     @ORM\JoinColumn(name="itemId", referencedColumnName="id")
     *   }
     * )
     */
    private Item $itemid;

    /**
     * Constructor
     */
    public function __construct()
    {
        $this->fieldid = new \Doctrine\Common\Collections\ArrayCollection();
        $this->itemid = new \Doctrine\Common\Collections\ArrayCollection();
    }
    

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

	public function getTypeid() : Collectiontype{
		return $this->typeid;
	}

	public function setTypeid(Collectiontype $typeid){
		$this->typeid = $typeid;
	}

	public function getFieldid() : Field{
		return $this->fieldid;
	}

	public function setFieldid(Field $fieldid){
		$this->fieldid = $fieldid;
	}

	public function getItemid() : Item{
		return $this->itemid;
	}

	public function setItemid(Item $itemid){
		$this->itemid = $itemid;
    }

    public function addItem(Item $item)
    {
        $this->itemid[] = $item;
    }

    public function deleteItem(Item $item)
    {
        $this->itemid->removeElement($item);
    }
    
    public function addField(Field $field)
    {
        $this->fieldid[] = $field;
    }

    public function deleteField(Field $field)
    {
        $this->fieldid->removeElement($field);
    }

}
