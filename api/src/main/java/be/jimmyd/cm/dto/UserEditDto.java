package be.jimmyd.cm.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserEditDto {

    @Size(min = 8)
    @NotNull
    private String password;

    @NotNull
    private String newUser;

    @NotNull
    @Email
    private String newMail;

    @NotNull
    private String theme;

    private UserEditDto(){

    }

    private UserEditDto(Builder builder) {
        password = builder.password;
        newUser = builder.newUser;
        newMail = builder.newMail;
        theme = builder.theme;
    }

    public String getPassword() {
        return password;
    }

    public String getNewUser() {
        return newUser;
    }

    public String getNewMail() {
        return newMail;
    }

    public String getTheme() {
        return theme;
    }

    public static final class Builder {
        private @Size(min = 8) @NotNull String password;
        private @NotNull String newUser;
        private @NotNull @Email String newMail;
        private @NotNull String theme;

        public Builder() {
        }

        public Builder withPassword(@Size(min = 8) @NotNull String val) {
            password = val;
            return this;
        }

        public Builder withNewUser(@NotNull String val) {
            newUser = val;
            return this;
        }

        public Builder withNewMail(@NotNull @Email String val) {
            newMail = val;
            return this;
        }

        public Builder withTheme(@NotNull String val) {
            theme = val;
            return this;
        }

        public UserEditDto build() {
            return new UserEditDto(this);
        }
    }
}
