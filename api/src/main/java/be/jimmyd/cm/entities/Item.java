package be.jimmyd.cm.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cm_item")
@Data
public class Item {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "image")
    private String image;

    @Basic
    @Column(name = "creationDate")
    private LocalDateTime creationDate;

    @Basic
    @Column(name = "lastModified")
    private LocalDateTime lastModified;

    @Basic
    @Column(name = "modifiedBy")
    private Long modifiedBy;

    @Basic
    @Column(name = "active")
    private Boolean active;

    @ManyToMany
    @JoinTable(
            name="cm_collectionitem",
            joinColumns = {@JoinColumn(name="collectionId")},
            inverseJoinColumns = {@JoinColumn(name="itemId")}
    )
    private List<Collection> collections;

    @ManyToOne
    @JoinColumn(name = "author", referencedColumnName = "id", nullable = false)
    private User author;

    @OneToMany
    private List<Itemdata> itemdata;
}
