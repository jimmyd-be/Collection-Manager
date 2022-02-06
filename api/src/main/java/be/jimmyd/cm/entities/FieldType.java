package be.jimmyd.cm.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cm_fieldtype")
public class FieldType {
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
    private List<Field> fields;

    private FieldType(){

    }

    private FieldType(Builder builder) {
        id = builder.id;
        type = builder.type;
        active = builder.active;
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

    public List<Field> getFields() {
        return fields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldType fieldType = (FieldType) o;
        return id == fieldType.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static final class Builder {
        private int id;
        private String type;
        private Boolean active;
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

        public Builder withFields(List<Field> val) {
            fields = val;
            return this;
        }

        public FieldType build() {
            return new FieldType(this);
        }
    }
}
