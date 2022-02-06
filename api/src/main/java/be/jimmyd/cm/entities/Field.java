package be.jimmyd.cm.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cm_field")
public class Field {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "choises")
    private String choises;

    @Basic
    @Column(name = "required")
    private Boolean required;

    @Basic
    @Column(name = "placeholder")
    private String placeHolder;

    @Basic
    @Column(name = "label")
    private String label;

    @Basic
    @Column(name = "otheroptions")
    private String otherOptions;

    @Basic
    @Column(name = "fieldorder")
    private int fieldOrder;

    @Basic
    @Column(name = "place")
    private String place;

    @Basic
    @Column(name = "multivalues")
    private Boolean multiValues;

    @Basic
    @Column(name = "active")
    private Boolean active;

    @Basic
    @Column(name = "basic")
    private Boolean basic;

    @Basic
    @Column(name = "labelposition")
    private String labelPosition;

    @Basic
    @Column(name = "widget")
    private String widget;

    @ManyToMany
    @JoinTable(
            name = "cm_collectionfield",
            joinColumns = @JoinColumn(name = "fieldid"),
            inverseJoinColumns = @JoinColumn(name = "collectionid"))
    private List<Collection> collections;

    @ManyToOne
    @JoinColumn(name = "type", referencedColumnName = "id", nullable = false)
    private FieldType type;

    @ManyToOne
    @JoinColumn(name = "collectionbasetype", referencedColumnName = "id")
    private CollectionType collectiontype;

    @OneToMany
    @JoinColumn(name = "fieldid")
    private List<Itemdata> itemdata;

    private Field(){

    }

    private Field(Builder builder) {
        id = builder.id;
        name = builder.name;
        choises = builder.choises;
        required = builder.required;
        placeHolder = builder.placeHolder;
        label = builder.label;
        otherOptions = builder.otherOptions;
        fieldOrder = builder.fieldOrder;
        place = builder.place;
        multiValues = builder.multiValues;
        active = builder.active;
        basic = builder.basic;
        labelPosition = builder.labelPosition;
        widget = builder.widget;
        collections = builder.collections;
        type = builder.type;
        collectiontype = builder.collectiontype;
        itemdata = builder.itemdata;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getChoises() {
        return choises;
    }

    public Boolean getRequired() {
        return required;
    }

    public String getPlaceHolder() {
        return placeHolder;
    }

    public String getLabel() {
        return label;
    }

    public String getOtherOptions() {
        return otherOptions;
    }

    public int getFieldOrder() {
        return fieldOrder;
    }

    public String getPlace() {
        return place;
    }

    public Boolean getMultiValues() {
        return multiValues;
    }

    public Boolean getActive() {
        return active;
    }

    public Boolean getBasic() {
        return basic;
    }

    public String getLabelPosition() {
        return labelPosition;
    }

    public String getWidget() {
        return widget;
    }

    public List<Collection> getCollections() {
        return collections;
    }

    public FieldType getType() {
        return type;
    }

    public CollectionType getCollectiontype() {
        return collectiontype;
    }

    public List<Itemdata> getItemdata() {
        return itemdata;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChoises(String choises) {
        this.choises = choises;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public void setPlaceHolder(String placeHolder) {
        this.placeHolder = placeHolder;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setOtherOptions(String otherOptions) {
        this.otherOptions = otherOptions;
    }

    public void setFieldOrder(int fieldOrder) {
        this.fieldOrder = fieldOrder;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setMultiValues(Boolean multiValues) {
        this.multiValues = multiValues;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setBasic(Boolean basic) {
        this.basic = basic;
    }

    public void setLabelPosition(String labelPosition) {
        this.labelPosition = labelPosition;
    }

    public void setWidget(String widget) {
        this.widget = widget;
    }

    public void setCollections(List<Collection> collections) {
        this.collections = collections;
    }

    public void setType(FieldType type) {
        this.type = type;
    }

    public void setCollectiontype(CollectionType collectiontype) {
        this.collectiontype = collectiontype;
    }

    public void setItemdata(List<Itemdata> itemdata) {
        this.itemdata = itemdata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return id == field.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static final class Builder {
        private long id;
        private String name;
        private String choises;
        private Boolean required;
        private String placeHolder;
        private String label;
        private String otherOptions;
        private int fieldOrder;
        private String place;
        private Boolean multiValues;
        private Boolean active;
        private Boolean basic;
        private String labelPosition;
        private String widget;
        private List<Collection> collections;
        private FieldType type;
        private CollectionType collectiontype;
        private List<Itemdata> itemdata;

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

        public Builder withChoises(String val) {
            choises = val;
            return this;
        }

        public Builder withRequired(Boolean val) {
            required = val;
            return this;
        }

        public Builder withPlaceHolder(String val) {
            placeHolder = val;
            return this;
        }

        public Builder withLabel(String val) {
            label = val;
            return this;
        }

        public Builder withOtherOptions(String val) {
            otherOptions = val;
            return this;
        }

        public Builder withFieldOrder(int val) {
            fieldOrder = val;
            return this;
        }

        public Builder withPlace(String val) {
            place = val;
            return this;
        }

        public Builder withMultiValues(Boolean val) {
            multiValues = val;
            return this;
        }

        public Builder withActive(Boolean val) {
            active = val;
            return this;
        }

        public Builder withBasic(Boolean val) {
            basic = val;
            return this;
        }

        public Builder withLabelPosition(String val) {
            labelPosition = val;
            return this;
        }

        public Builder withWidget(String val) {
            widget = val;
            return this;
        }

        public Builder withCollections(List<Collection> val) {
            collections = val;
            return this;
        }

        public Builder withType(FieldType val) {
            type = val;
            return this;
        }

        public Builder withCollectiontype(CollectionType val) {
            collectiontype = val;
            return this;
        }

        public Builder withItemdata(List<Itemdata> val) {
            itemdata = val;
            return this;
        }

        public Field build() {
            return new Field(this);
        }
    }
}
