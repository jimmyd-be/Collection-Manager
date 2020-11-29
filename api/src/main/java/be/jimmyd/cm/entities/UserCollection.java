package be.jimmyd.cm.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "cm_usercollection")
@Data
public class UserCollection {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "collectionId", referencedColumnName = "id", nullable = false)
    private Collection collection;

    @ManyToOne
    @JoinColumn(name = "roleId", referencedColumnName = "id", nullable = false)
    private Role role;
}
