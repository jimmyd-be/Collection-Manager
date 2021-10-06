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
    @Column(name = "userpassword")
    private String userPassword;

    @Basic
    @Column(name = "creationdate")
    private LocalDateTime creationDate;

    @Basic
    @Column(name = "admin")
    private Boolean isAdmin;

    @Basic
    @Column(name = "theme")
    private String theme;

    @Basic
    @Column(name = "active")
    private Boolean active;

  /*  @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private List<Item> items;*/

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "userid")
    private List<UserCollection> userCollections;

}
