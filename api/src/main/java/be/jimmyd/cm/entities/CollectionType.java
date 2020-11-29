package be.jimmyd.cm.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cm_collectiontype")
@Data
public class CollectionType {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "type")
    private String type;

    @Column(name = "active")
    private boolean active;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Field> fields;

    @OneToMany
    @JoinColumn(name = "collectionId")
    private List<Collection> collections;
}
