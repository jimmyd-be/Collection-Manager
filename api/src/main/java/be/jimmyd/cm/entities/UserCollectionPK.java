package be.jimmyd.cm.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Data
public class UserCollectionPK implements Serializable {

    @Column(name = "userId")
    @Id
    private long userId;

    @Column(name = "collectionId")
    @Id
    private long collectionId;

    @Column(name = "roleId")
    @Id
    private int roleId;
}
