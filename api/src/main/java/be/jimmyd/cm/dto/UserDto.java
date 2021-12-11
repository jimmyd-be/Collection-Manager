package be.jimmyd.cm.dto;

import java.time.LocalDate;

public class UserDto {

    private long id;
    private String mail;
    private String username;
    private boolean admin;
    private LocalDate creationDate;
    private String theme;
    private boolean active;

    private UserDto(){

    }

    private UserDto(Builder builder) {
        id = builder.id;
        mail = builder.mail;
        username = builder.username;
        admin = builder.isAdmin;
        creationDate = builder.creationDate;
        theme = builder.theme;
        active = builder.active;
    }

    public long getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAdmin() {
        return admin;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public String getTheme() {
        return theme;
    }

    public boolean isActive() {
        return active;
    }

    public static final class Builder {
        private long id;
        private String mail;
        private String username;
        private boolean isAdmin;
        private LocalDate creationDate;
        private String theme;
        private boolean active;

        public Builder() {
        }

        public Builder withId(long val) {
            id = val;
            return this;
        }

        public Builder withMail(String val) {
            mail = val;
            return this;
        }

        public Builder withUsername(String val) {
            username = val;
            return this;
        }

        public Builder withIsAdmin(boolean val) {
            isAdmin = val;
            return this;
        }

        public Builder withCreationDate(LocalDate val) {
            creationDate = val;
            return this;
        }

        public Builder withTheme(String val) {
            theme = val;
            return this;
        }

        public Builder withActive(boolean val) {
            active = val;
            return this;
        }

        public UserDto build() {
            return new UserDto(this);
        }
    }
}
