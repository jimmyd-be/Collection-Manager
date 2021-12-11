package be.jimmyd.cm.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cm_item")
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
    @Column(name = "creationdate")
    private LocalDateTime creationDate;

    @Basic
    @Column(name = "lastmodified")
    private LocalDateTime lastModified;

    @Basic
    @Column(name = "modifiedby")
    private Long modifiedBy;

    @Basic
    @Column(name = "active")
    private Boolean active;

    @ManyToMany
    @JoinTable(
            name = "cm_collectionitem",
            joinColumns = {@JoinColumn(name = "itemid")},
            inverseJoinColumns = {@JoinColumn(name = "collectionid")}
    )
    private List<Collection> collections = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "author", referencedColumnName = "id", nullable = false)
    private User author;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "itemid")
    private List<Itemdata> itemdata;

    private Item(){

    }

    private Item(Builder builder) {
        id = builder.id;
        name = builder.name;
        image = builder.image;
        creationDate = builder.creationDate;
        lastModified = builder.lastModified;
        modifiedBy = builder.modifiedBy;
        active = builder.active;
        collections = builder.collections == null ? new ArrayList<>(): builder.collections;
        author = builder.author;
        itemdata = builder.itemdata;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public Boolean getActive() {
        return active;
    }

    public List<Collection> getCollections() {
        return collections;
    }

    public User getAuthor() {
        return author;
    }

    public List<Itemdata> getItemdata() {
        return itemdata;
    }


    public static final class Builder {
        private long id;
        private String name;
        private String image;
        private LocalDateTime creationDate;
        private LocalDateTime lastModified;
        private Long modifiedBy;
        private Boolean active;
        private List<Collection> collections;
        private User author;
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

        public Builder withImage(String val) {
            image = val;
            return this;
        }

        public Builder withCreationDate(LocalDateTime val) {
            creationDate = val;
            return this;
        }

        public Builder withLastModified(LocalDateTime val) {
            lastModified = val;
            return this;
        }

        public Builder withModifiedBy(Long val) {
            modifiedBy = val;
            return this;
        }

        public Builder withActive(Boolean val) {
            active = val;
            return this;
        }

        public Builder withCollections(List<Collection> val) {
            collections = val;
            return this;
        }

        public Builder withAuthor(User val) {
            author = val;
            return this;
        }

        public Builder withItemdata(List<Itemdata> val) {
            itemdata = val;
            return this;
        }

        public Item build() {
            return new Item(this);
        }
    }
}
