package be.jimmyd.cm.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cm_itemdata")
@Data
public class ItemData {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Item item;

    @ManyToOne
    private Field field;

    @Column(name = "valueCount")
    private long valueCount;

    @Column(name = "fieldValue")
    private String fieldValue;

}
