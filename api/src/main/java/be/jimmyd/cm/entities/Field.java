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

}
