package be.jimmyd.cm.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cm_itemdata")
public class Itemdata {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    @Column(name = "valuecount")
    private long valueCount;

    @Basic
    @Column(name = "fieldvalue")
    private String fieldValue;

    @ManyToOne
    @JoinColumn(name = "itemid", referencedColumnName = "id", nullable = false)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "fieldid", referencedColumnName = "id", nullable = false)
    private Field field;

    private Itemdata(){

    }

    private Itemdata(Builder builder) {
        id = builder.id;
        valueCount = builder.valueCount;
        fieldValue = builder.fieldValue;
        item = builder.item;
        field = builder.field;
    }

    public long getId() {
        return id;
    }

    public long getValueCount() {
        return valueCount;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public Item getItem() {
        return item;
    }

    public Field getField() {
        return field;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Itemdata itemdata = (Itemdata) o;
        return id == itemdata.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static final class Builder {
        private long id;
        private long valueCount;
        private String fieldValue;
        private Item item;
        private Field field;

        public Builder() {
        }

        public Builder withId(long val) {
            id = val;
            return this;
        }

        public Builder withValueCount(long val) {
            valueCount = val;
            return this;
        }

        public Builder withFieldValue(String val) {
            fieldValue = val;
            return this;
        }

        public Builder withItem(Item val) {
            item = val;
            return this;
        }

        public Builder withField(Field val) {
            field = val;
            return this;
        }

        public Itemdata build() {
            return new Itemdata(this);
        }
    }
}
