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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collectionId")
    private Collection collectionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId")
    private Role roleId;
}
