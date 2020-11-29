package be.jimmyd.cm.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cm_role")
@Data
public class Role {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "active")
    private boolean active;

    @OneToMany
    @JoinColumn(name = "roleId")
    private List<UserCollection> userCollections;
}
