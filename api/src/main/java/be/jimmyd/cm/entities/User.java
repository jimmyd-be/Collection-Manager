package be.jimmyd.cm.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cm_user")
@Data
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    @Column(name = "username")
    private String username;

    @Basic
    @Column(name = "mail")
    private String mail;

    @Basic
    @Column(name = "userPassword")
    private String userPassword;

    @Basic
    @Column(name = "creationDate")
    private LocalDateTime creationDate;

    @Basic
    @Column(name = "isAdmin")
    private Boolean isAdmin;

    @Basic
    @Column(name = "theme")
    private String theme;

    @Basic
    @Column(name = "active")
    private Boolean active;

    @OneToMany
    private List<Item> items;

    @OneToMany
    private List<UserCollection> userCollections;

}
