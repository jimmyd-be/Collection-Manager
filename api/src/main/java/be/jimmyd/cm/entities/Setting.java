package be.jimmyd.cm.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "cm_setting")
@Data
public class Setting {

    @Id
    @Column(name = "id")
    private String id;

    @Basic
    @Column(name = "value")
    private String value;
}
