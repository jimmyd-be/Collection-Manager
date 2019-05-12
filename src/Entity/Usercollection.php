<?php



use Doctrine\ORM\Mapping as ORM;

/**
 * Usercollection
 *
 * @ORM\Table(name="userCollection", indexes={@ORM\Index(name="roleId", columns={"roleId"}), @ORM\Index(name="collectionId", columns={"collectionId"}), @ORM\Index(name="IDX_FE6C4A1B64B64DCC", columns={"userId"})})
 * @ORM\Entity
 */
class Usercollection
{
    /**
     * @var \User
     *
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="NONE")
     * @ORM\OneToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="userId", referencedColumnName="id")
     * })
     */
    private $userid;

    /**
     * @var \Collection
     *
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="NONE")
     * @ORM\OneToOne(targetEntity="Collection")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="collectionId", referencedColumnName="id")
     * })
     */
    private $collectionid;

    /**
     * @var \Role
     *
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="NONE")
     * @ORM\OneToOne(targetEntity="Role")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="roleId", referencedColumnName="id")
     * })
     */
    private $roleid;


}
