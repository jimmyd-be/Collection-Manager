package be.jimmyd.cm.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

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
    private boolean active;

    @ManyToOne
    private CollectionType type;

    @ManyToMany(mappedBy = "collections", cascade = CascadeType.REMOVE)
    private List<Item> items;

    @ManyToMany(mappedBy = "collections")
    private List<Field> fields;

    @OneToMany(cascade=CascadeType.REMOVE)
    @JoinColumn(name = "collectionId")
    private List<UserCollection> userCollections;
}
