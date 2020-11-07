<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Usercollection
 *
 * @ORM\Table(name="cm_usercollection", indexes={@ORM\Index(name="roleId", columns={"roleId"}), @ORM\Index(name="collectionId", columns={"collectionId"}), @ORM\Index(name="IDX_FE6C4A1B64B64DCC", columns={"userId"})})
 * @ORM\Entity
 */
class Usercollection
{
    /**
     * @var User
     *
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="NONE")
     * @ORM\OneToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="userId", referencedColumnName="id")
     * })
     */
    private User $userid;

    /**
     * @var Collection
     *
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="NONE")
     * @ORM\OneToOne(targetEntity="Collection")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="collectionId", referencedColumnName="id")
     * })
     */
    private Collection $collectionid;

    /**
     * @var Role
     *
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="NONE")
     * @ORM\OneToOne(targetEntity="Role")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="roleId", referencedColumnName="id")
     * })
     */
    private Role $roleid;

    public function getUserid(): User{
		return $this->userid;
	}

	public function setUserid(User $userid){
		$this->userid = $userid;
	}

  public function getCollectionid() : Collection
  {
		return $this->collectionid;
	}

	public function setCollectionid(Collection $collectionid){
		$this->collectionid = $collectionid;
	}

	public function getRoleid() : Role{
		return $this->roleid;
	}

	public function setRoleid(Role $roleid){
		$this->roleid = $roleid;
	}


}
