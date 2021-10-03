package be.jimmyd.cm.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cm_collection")
@Data
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
}
