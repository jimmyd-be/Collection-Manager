package be.jimmyd.cm.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cm_field")
@Data
public class Field {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "choises")
    private String choises;

    @Column(name = "required")
    private boolean required;

    @Column(name = "placeHolder")
    private String placeHolder;

    @Column(name = "label")
    private String label;

    @Column(name = "otherOptions")
    private String otherOptions;

    @Column(name = "fieldOrder")
    private int fieldOrder;

    @Column(name = "place")
    private String place;

    @Column(name = "multiValues")
    private boolean multiValues;

    @Column(name = "active")
    private boolean active;

    @Column(name = "labelPosition")
    private String labelPosition;

    @Column(name = "widget")
    private String widget;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "cm_collectionfield",
            joinColumns = { @JoinColumn(name = "collectionId") },
            inverseJoinColumns = { @JoinColumn(name = "fieldId") }
    )
    private List<Collection> collections;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collectionBaseType")
    private CollectionType collectionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type")
    private FieldType type;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Item> ItemDatas;
}
