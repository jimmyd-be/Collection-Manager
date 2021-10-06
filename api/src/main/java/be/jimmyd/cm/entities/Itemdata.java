package be.jimmyd.cm.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "cm_itemdata")
@Data
public class Itemdata {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    @Column(name = "valuecount")
    private long valueCount;

    @Basic
    @Column(name = "fieldvalue")
    private String fieldValue;

    @ManyToOne
    @JoinColumn(name = "itemid", referencedColumnName = "id", nullable = false)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "fieldid", referencedColumnName = "id", nullable = false)
    private Field field;
}
