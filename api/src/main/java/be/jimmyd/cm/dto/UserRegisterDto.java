package be.jimmyd.cm.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegisterDto {

    @NotNull
    private String fullName;

    @NotNull
    @Email
    private String email;

    @Size(min = 8)
    @NotNull
    private String password;

    private UserRegisterDto(){

    }

    private UserRegisterDto(Builder builder) {
        fullName = builder.fullName;
        email = builder.email;
        password = builder.password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public static final class Builder {
        private @NotNull String fullName;
        private @NotNull @Email String email;
        private @Size(min = 8) @NotNull String password;

        public Builder() {
        }

        public Builder withFullName(@NotNull String val) {
            fullName = val;
            return this;
        }

        public Builder withEmail(@NotNull @Email String val) {
            email = val;
            return this;
        }

        public Builder withPassword(@Size(min = 8) @NotNull String val) {
            password = val;
            return this;
        }

        public UserRegisterDto build() {
            return new UserRegisterDto(this);
        }
    }
}
