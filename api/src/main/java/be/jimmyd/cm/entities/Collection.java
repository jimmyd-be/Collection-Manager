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
    @JoinColumn(name = "typeId", referencedColumnName = "id", nullable = false)
    private CollectionType type;

    @ManyToMany
    @JoinTable(
            name = "cm_collectionfield",
            joinColumns = @JoinColumn(name = "collectionId"),
            inverseJoinColumns = @JoinColumn(name = "fieldId"))
    private List<Field> fields;

    @ManyToMany
    @JoinTable(
            name="cm_collectionItem",
            joinColumns = {@JoinColumn(name="itemId")},
            inverseJoinColumns = {@JoinColumn(name="collectionId")}
    )
    private List<Item> items;

    @OneToMany
    @JoinColumn(name = "collectionId")
    private List<UserCollection> userCollections;
}
