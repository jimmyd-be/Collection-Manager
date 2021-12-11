package be.jimmyd.cm.entities;

import javax.persistence.*;

@Entity
@Table(name = "cm_usercollection")
public class UserCollection {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "collectionid", referencedColumnName = "id", nullable = false)
    private Collection collection;

    @ManyToOne
    @JoinColumn(name = "roleid", referencedColumnName = "id", nullable = false)
    private Role role;

    private UserCollection(){

    }

    private UserCollection(Builder builder) {
        id = builder.id;
        user = builder.user;
        collection = builder.collection;
        role = builder.role;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Collection getCollection() {
        return collection;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public static final class Builder {
        private long id;
        private User user;
        private Collection collection;
        private Role role;

        public Builder() {
        }

        public Builder withId(long val) {
            id = val;
            return this;
        }

        public Builder withUser(User val) {
            user = val;
            return this;
        }

        public Builder withCollection(Collection val) {
            collection = val;
            return this;
        }

        public Builder withRole(Role val) {
            role = val;
            return this;
        }

        public UserCollection build() {
            return new UserCollection(this);
        }
    }
}
