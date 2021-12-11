package be.jimmyd.cm.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cm_collectiontype")
public class CollectionType {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "type")
    private String type;

    @Basic
    @Column(name = "active")
    private Boolean active;

    @OneToMany
    @JoinColumn(name = "typeid")
    private List<Collection> collections;

    @OneToMany
    @JoinColumn(name = "collectionbasetype")
    private List<Field> fields;

    private CollectionType(){

    }

    private CollectionType(Builder builder) {
        id = builder.id;
        type = builder.type;
        active = builder.active;
        collections = builder.collections;
        fields = builder.fields;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Boolean getActive() {
        return active;
    }

    public List<Collection> getCollections() {
        return collections;
    }

    public List<Field> getFields() {
        return fields;
    }


    public static final class Builder {
        private int id;
        private String type;
        private Boolean active;
        private List<Collection> collections;
        private List<Field> fields;

        public Builder() {
        }

        public Builder withId(int val) {
            id = val;
            return this;
        }

        public Builder withType(String val) {
            type = val;
            return this;
        }

        public Builder withActive(Boolean val) {
            active = val;
            return this;
        }

        public Builder withCollections(List<Collection> val) {
            collections = val;
            return this;
        }

        public Builder withFields(List<Field> val) {
            fields = val;
            return this;
        }

        public CollectionType build() {
            return new CollectionType(this);
        }
    }
}
