package be.jimmyd.cm.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cm_collection")
public class Collection {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "active")
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "typeid", referencedColumnName = "id", nullable = false)
    private CollectionType type;

    @ManyToMany
    @JoinTable(
            name = "cm_collectionfield",
            joinColumns = @JoinColumn(name = "collectionid"),
            inverseJoinColumns = @JoinColumn(name = "fieldid"))
    private List<Field> fields;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "cm_collectionitem",
            joinColumns = {@JoinColumn(name = "collectionid")},
            inverseJoinColumns = {@JoinColumn(name = "itemid")}
    )
    private List<Item> items;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "collectionid")
    private List<UserCollection> userCollections;

    private Collection(){

    }

    private Collection(Builder builder) {
        id = builder.id;
        name = builder.name;
        active = builder.active;
        type = builder.type;
        fields = builder.fields;
        items = builder.items;
        userCollections = builder.userCollections;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Boolean getActive() {
        return active;
    }

    public CollectionType getType() {
        return type;
    }

    public List<Field> getFields() {
        return fields;
    }

    public List<Item> getItems() {
        return items;
    }

    public List<UserCollection> getUserCollections() {
        return userCollections;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Collection that = (Collection) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static final class Builder {
        private long id;
        private String name;
        private Boolean active;
        private CollectionType type;
        private List<Field> fields;
        private List<Item> items;
        private List<UserCollection> userCollections;

        public Builder() {
        }

        public Builder withId(long val) {
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

        public Builder withType(CollectionType val) {
            type = val;
            return this;
        }

        public Builder withFields(List<Field> val) {
            fields = val;
            return this;
        }

        public Builder withItems(List<Item> val) {
            items = val;
            return this;
        }

        public Builder withUserCollections(List<UserCollection> val) {
            userCollections = val;
            return this;
        }

        public Collection build() {
            return new Collection(this);
        }
    }
}
