<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Field
 *
 * @ORM\Table(name="field", indexes={@ORM\Index(name="collectionBaseType", columns={"collectionBaseType"}), @ORM\Index(name="type", columns={"type"})})
 * @ORM\Entity
 */
class Field
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
     * @var string|null
     *
     * @ORM\Column(name="name", type="string", length=255, nullable=true)
     */
    private $name;

    /**
     * @var string|null
     *
     * @ORM\Column(name="choises", type="string", length=255, nullable=true)
     */
    private $choises;

    /**
     * @var bool|null
     *
     * @ORM\Column(name="required", type="boolean", nullable=true)
     */
    private $required;

    /**
     * @var string|null
     *
     * @ORM\Column(name="placeHolder", type="string", length=255, nullable=true)
     */
    private $placeholder;

    /**
     * @var string|null
     *
     * @ORM\Column(name="label", type="string", length=255, nullable=true)
     */
    private $label;

    /**
     * @var string|null
     *
     * @ORM\Column(name="otherOptions", type="string", length=255, nullable=true)
     */
    private $otheroptions;

    /**
     * @var int
     *
     * @ORM\Column(name="fieldOrder", type="integer", nullable=false)
     */
    private $fieldorder;

    /**
     * @var string
     *
     * @ORM\Column(name="place", type="string", length=10, nullable=false)
     */
    private $place;

    /**
     * @var bool|null
     *
     * @ORM\Column(name="multiValues", type="boolean", nullable=true)
     */
    private $multivalues;

    /**
     * @var bool|null
     *
     * @ORM\Column(name="active", type="boolean", nullable=true)
     */
    private $active;

    /**
     * @var string|null
     *
     * @ORM\Column(name="labelPosition", type="string", length=45, nullable=true)
     */
    private $labelposition;

    /**
     * @var string|null
     *
     * @ORM\Column(name="widget", type="string", length=45, nullable=true)
     */
    private $widget;

    /**
     * @var \Fieldtype
     *
     * @ORM\ManyToOne(targetEntity="Fieldtype")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="type", referencedColumnName="id")
     * })
     */
    private $type;

    /**
     * @var \Collectiontype
     *
     * @ORM\ManyToOne(targetEntity="Collectiontype")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="collectionBaseType", referencedColumnName="id")
     * })
     */
    private $collectionbasetype;

    /**
     * @var \Doctrine\Common\Collections\Collection
     *
     * @ORM\ManyToMany(targetEntity="Collection", mappedBy="fieldid")
     */
    private $collectionid;

    /**
     * Constructor
     */
    public function __construct()
    {
        $this->collectionid = new \Doctrine\Common\Collections\ArrayCollection();
    }

    public function getId(){
		return $this->id;
	}

	public function setId($id){
		$this->id = $id;
	}

	public function getName(){
		return $this->name;
	}

	public function setName($name){
		$this->name = $name;
	}

	public function getChoises(){
		return $this->choises;
	}

	public function setChoises($choises){
		$this->choises = $choises;
	}

	public function getRequired(){
		return $this->required;
	}

	public function setRequired($required){
		$this->required = $required;
	}

	public function getPlaceholder(){
		return $this->placeholder;
	}

	public function setPlaceholder($placeholder){
		$this->placeholder = $placeholder;
	}

	public function getLabel(){
		return $this->label;
	}

	public function setLabel($label){
		$this->label = $label;
	}

	public function getOtheroptions(){
		return $this->otheroptions;
	}

	public function setOtheroptions($otheroptions){
		$this->otheroptions = $otheroptions;
	}

	public function getFieldorder(){
		return $this->fieldorder;
	}

	public function setFieldorder($fieldorder){
		$this->fieldorder = $fieldorder;
	}

	public function getPlace(){
		return $this->place;
	}

	public function setPlace($place){
		$this->place = $place;
	}

	public function getMultivalues(){
		return $this->multivalues;
	}

	public function setMultivalues($multivalues){
		$this->multivalues = $multivalues;
	}

	public function getActive(){
		return $this->active;
	}

	public function setActive($active){
		$this->active = $active;
	}

	public function getLabelposition(){
		return $this->labelposition;
	}

	public function setLabelposition($labelposition){
		$this->labelposition = $labelposition;
	}

	public function getWidget(){
		return $this->widget;
	}

	public function setWidget($widget){
		$this->widget = $widget;
	}

	public function getType(){
		return $this->type;
	}

	public function setType($type){
		$this->type = $type;
	}

	public function getCollectionbasetype(){
		return $this->collectionbasetype;
	}

	public function setCollectionbasetype($collectionbasetype){
		$this->collectionbasetype = $collectionbasetype;
	}

	public function getCollectionid(){
		return $this->collectionid;
	}

	public function setCollectionid($collectionid){
		$this->collectionid = $collectionid;
	}

}
