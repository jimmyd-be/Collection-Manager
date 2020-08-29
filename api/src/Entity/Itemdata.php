<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Itemdata
 *
 * @ORM\Table(name="cm_itemdata", indexes={@ORM\Index(name="fieldId", columns={"fieldId"}), @ORM\Index(name="IDX_78D91ABB5F8B771", columns={"itemId"})})
 * @ORM\Entity
 */
class Itemdata
{
    /**
     * @var string
     *
     * @ORM\Column(name="fieldValue", type="string", length=255, nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="NONE")
     */
    private $fieldvalue;

    /**
     * @var \Item
     *
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="NONE")
     * @ORM\OneToOne(targetEntity="Item")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="itemId", referencedColumnName="id")
     * })
     */
    private $itemid;

 
    /**
     * @var \Field
     *
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="NONE")
     * @ORM\OneToOne(targetEntity="Field")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="fieldId", referencedColumnName="id")
     * })
     */
    private $fieldid;

    /**
     * @var string
     *
     * @ORM\Column(name="valueCount", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="NONE")
     */
    private $valueCount;

    public function getValueCount(){
      return $this->valueCount;
    }

    public function setValueCount($count){
      $this->valueCount = $count;
    }

    public function getFieldvalue(){
		return $this->fieldvalue;
	}

	public function setFieldvalue($fieldvalue){
		$this->fieldvalue = $fieldvalue;
	}

	public function getItemid(){
		return $this->itemid;
	}

	public function setItemid($itemid){
		$this->itemid = $itemid;
	}

	public function getFieldid(){
		return $this->fieldid;
	}

	public function setFieldid($fieldid){
		$this->fieldid = $fieldid;
	}


}
