package be.jimmyd.cm.dto;

public class UserLoginDto {

    private String email;
    private String password;
    private boolean rememberMe;

    private UserLoginDto() {
    }

    private UserLoginDto(Builder builder) {
        email = builder.email;
        password = builder.password;
        rememberMe = builder.rememberMe;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public static final class Builder {
        private String email;
        private String password;
        private boolean rememberMe;

        public Builder() {
        }

        public Builder withEmail(String val) {
            email = val;
            return this;
        }

        public Builder withPassword(String val) {
            password = val;
            return this;
        }

        public Builder withRememberMe(boolean val) {
            rememberMe = val;
            return this;
        }

        public UserLoginDto build() {
            return new UserLoginDto(this);
        }
    }
}
