package be.jimmyd.cm.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cm_usercollection")
@Data
@IdClass(UserCollectionEntityPK.class)
public class UserCollectionEntity {
    private long userId;
    private long collectionId;
    private int roleId;

    @Id
    @Column(name = "userId")
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "collectionId")
    public long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(long collectionId) {
        this.collectionId = collectionId;
    }

    @Id
    @Column(name = "roleId")
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCollectionEntity that = (UserCollectionEntity) o;
        return userId == that.userId &&
                collectionId == that.collectionId &&
                roleId == that.roleId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, collectionId, roleId);
    }
}
