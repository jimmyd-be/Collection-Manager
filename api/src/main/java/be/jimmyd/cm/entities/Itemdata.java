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
    @Column(name = "valueCount")
    private long valueCount;

    @Basic
    @Column(name = "fieldValue")
    private String fieldValue;

    @ManyToOne
    @JoinColumn(name = "itemId", referencedColumnName = "id", nullable = false)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "fieldId", referencedColumnName = "id", nullable = false)
    private Field field;
}
