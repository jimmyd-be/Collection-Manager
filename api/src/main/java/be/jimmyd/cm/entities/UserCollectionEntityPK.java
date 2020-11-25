package be.jimmyd.cm.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class UserCollectionEntityPK implements Serializable {
    private long userId;
    private long collectionId;
    private int roleId;

    @Column(name = "userId")
    @Id
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Column(name = "collectionId")
    @Id
    public long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(long collectionId) {
        this.collectionId = collectionId;
    }

    @Column(name = "roleId")
    @Id
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
        UserCollectionEntityPK that = (UserCollectionEntityPK) o;
        return userId == that.userId &&
                collectionId == that.collectionId &&
                roleId == that.roleId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, collectionId, roleId);
    }
}
