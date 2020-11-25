package be.jimmyd.cm.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "cm_usercollection")
@Data
@IdClass(UserCollectionPK.class)
public class UserCollection {

    @Id
    @Column(name = "userId")
    private long userId;

    @Id
    @Column(name = "collectionId")
    private long collectionId;

    @Id
    @Column(name = "roleId")
    private int roleId;
}
