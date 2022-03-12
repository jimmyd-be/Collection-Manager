package be.jimmyd.cm.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class UserRegisterDto {

    @NotNull
    private String fullName;

    @NotNull
    @Email
    private String email;

    @Size(min = 8)
    @NotNull
    private String password;

    @Size(min = 8)
    @NotNull
    private String confirmPassword;

    private UserRegisterDto(){

    }

    private UserRegisterDto(Builder builder) {
        fullName = builder.fullName;
        email = builder.email;
        password = builder.password;
        confirmPassword = builder.confirmPassword;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRegisterDto that = (UserRegisterDto) o;
        return Objects.equals(fullName, that.fullName) && Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, email, password);
    }

    public static final class Builder {
        private @NotNull String fullName;
        private @NotNull @Email String email;
        private @Size(min = 8) @NotNull String password;
        private @Size(min = 8) @NotNull String confirmPassword;

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

        public Builder withConfirmPassword(@Size(min = 8) @NotNull String val) {
            confirmPassword = val;
            return this;
        }

        public UserRegisterDto build() {
            return new UserRegisterDto(this);
        }
    }
}
