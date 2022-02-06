package be.jimmyd.cm.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserEditPasswordDto {

    @Size(min = 8)
    @NotNull
    private String currentPassword;

    @Size(min = 8)
    @NotNull
    private String password;

    @Size(min = 8)
    @NotNull
    private String passwordRepeat;

    private UserEditPasswordDto(){

    }

    private UserEditPasswordDto(Builder builder) {
        currentPassword = builder.currentPassword;
        password = builder.password;
        passwordRepeat = builder.passwordRepeat;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public static final class Builder {
        private @Size(min = 8) @NotNull String currentPassword;
        private @Size(min = 8) @NotNull String password;
        private @Size(min = 8) @NotNull String passwordRepeat;

        public Builder() {
        }

        public Builder withCurrentPassword(@Size(min = 8) @NotNull String val) {
            currentPassword = val;
            return this;
        }

        public Builder withPassword(@Size(min = 8) @NotNull String val) {
            password = val;
            return this;
        }

        public Builder withPasswordRepeat(@Size(min = 8) @NotNull String val) {
            passwordRepeat = val;
            return this;
        }

        public UserEditPasswordDto build() {
            return new UserEditPasswordDto(this);
        }
    }
}
