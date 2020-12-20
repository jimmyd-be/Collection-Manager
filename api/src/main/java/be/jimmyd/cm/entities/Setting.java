package be.jimmyd.cm.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cm_setting")
@Data
public class Setting {

    @Id
    @Column(name = "key")
    private String key;

    @Basic
    @Column(name = "value")
    private String value;
}
