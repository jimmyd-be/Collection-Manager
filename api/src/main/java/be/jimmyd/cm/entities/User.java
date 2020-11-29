package be.jimmyd.cm.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cm_user")
@Data
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "mail")
    private String mail;

    @Column(name = "userPassword")
    private String userPassword;

    @Column(name = "creationDate")
    private LocalDateTime creationDate;

    @Column(name = "isAdmin")
    private boolean isAdmin;

    @Column(name = "theme")
    private String theme;

    @Column(name = "active")
    private boolean active;

    @OneToMany
    @JoinColumn(name = "userId")
    private List<UserCollection> userCollections;

}
