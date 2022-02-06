package be.jimmyd.cm.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cm_user")
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
    private boolean isAdmin;

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

    private User(){

    }

    private User(Builder builder) {
        id = builder.id;
        username = builder.username;
        mail = builder.mail;
        userPassword = builder.userPassword;
        creationDate = builder.creationDate;
        isAdmin = builder.isAdmin != null && builder.isAdmin;
        theme = builder.theme;
        active = builder.active;
        userCollections = builder.userCollections;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getMail() {
        return mail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public boolean getAdmin() {
        return isAdmin;
    }

    public String getTheme() {
        return theme;
    }

    public Boolean getActive() {
        return active;
    }

    public List<UserCollection> getUserCollections() {
        return userCollections;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setUserCollections(List<UserCollection> userCollections) {
        this.userCollections = userCollections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static final class Builder {
        private long id;
        private String username;
        private String mail;
        private String userPassword;
        private LocalDateTime creationDate;
        private Boolean isAdmin;
        private String theme;
        private Boolean active;
        private List<UserCollection> userCollections;

        public Builder() {
        }

        public Builder withId(long val) {
            id = val;
            return this;
        }

        public Builder withUsername(String val) {
            username = val;
            return this;
        }

        public Builder withMail(String val) {
            mail = val;
            return this;
        }

        public Builder withUserPassword(String val) {
            userPassword = val;
            return this;
        }

        public Builder withCreationDate(LocalDateTime val) {
            creationDate = val;
            return this;
        }

        public Builder withIsAdmin(Boolean val) {
            isAdmin = val;
            return this;
        }

        public Builder withTheme(String val) {
            theme = val;
            return this;
        }

        public Builder withActive(Boolean val) {
            active = val;
            return this;
        }

        public Builder withUserCollections(List<UserCollection> val) {
            userCollections = val;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
