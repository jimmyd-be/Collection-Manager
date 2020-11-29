package be.jimmyd.cm.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cm_field")
@Data
public class Field {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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
    @Column(name = "placeHolder")
    private String placeHolder;

    @Basic
    @Column(name = "label")
    private String label;

    @Basic
    @Column(name = "otherOptions")
    private String otherOptions;

    @Basic
    @Column(name = "fieldOrder")
    private int fieldOrder;

    @Basic
    @Column(name = "place")
    private String place;

    @Basic
    @Column(name = "multiValues")
    private Boolean multiValues;

    @Basic
    @Column(name = "active")
    private Boolean active;

    @Basic
    @Column(name = "labelPosition")
    private String labelPosition;

    @Basic
    @Column(name = "widget")
    private String widget;

    @ManyToMany
    @JoinTable(
            name = "cm_collectionfield",
            joinColumns = @JoinColumn(name = "fieldId"),
            inverseJoinColumns = @JoinColumn(name = "collectionId"))
    private List<Collection> collections;

    @ManyToOne
    @JoinColumn(name = "type", referencedColumnName = "id", nullable = false)
    private FieldType type;

    @ManyToOne
    @JoinColumn(name = "collectionBaseType", referencedColumnName = "id")
    private CollectionType collectiontype;

    @OneToMany
    private List<Itemdata> itemdata;

}
