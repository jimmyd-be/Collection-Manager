package be.jimmyd.cm.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cm_fieldtype")
@Data
public class FieldType {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "type")
    private String type;

    @Basic
    @Column(name = "active")
    private Boolean active;

    @OneToMany
    private List<Field> fields;

}
