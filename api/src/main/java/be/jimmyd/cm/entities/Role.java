package be.jimmyd.cm.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cm_role")
public class Role {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "role")
    private List<UserCollection> userCollections;

    private Role(){

    }

    private Role(Builder builder) {
        id = builder.id;
        name = builder.name;
        active = builder.active;
        userCollections = builder.userCollections;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Boolean getActive() {
        return active;
    }

    public List<UserCollection> getUserCollections() {
        return userCollections;
    }


    public static final class Builder {
        private int id;
        private String name;
        private Boolean active;
        private List<UserCollection> userCollections;

        public Builder() {
        }

        public Builder withId(int val) {
            id = val;
            return this;
        }

        public Builder withName(String val) {
            name = val;
            return this;
        }

        public Builder withActive(Boolean val) {
            active = val;
            return this;
        }

        public Builder withUserCollections(List<UserCollection> val) {
            userCollections = val;
            return this;
        }

        public Role build() {
            return new Role(this);
        }
    }
}
