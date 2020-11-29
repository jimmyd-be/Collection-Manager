package be.jimmyd.cm.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cm_item")
@Data
public class Item {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "creationDate")
    private LocalDateTime creationDate;

    @Column(name = "lastModified")
    private LocalDateTime lastModified;

    @Column(name = "modifiedBy")
    private Long modifiedBy;

    @Column(name = "active")
    private boolean active;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "cm_collectionItem",
            joinColumns = { @JoinColumn(name = "collectionId") },
            inverseJoinColumns = { @JoinColumn(name = "itemId") }
    )
    private List<Collection> collections;

    @OneToMany
    private List<Item> ItemDatas;
}
