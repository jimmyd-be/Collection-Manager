package be.jimmyd.cm.dto;

public class CollectionShareDto {

    private String userName;
    private String role;

    private CollectionShareDto(){

    }

    private CollectionShareDto(Builder builder) {
        userName = builder.userName;
        role = builder.role;
    }

    public String getUserName() {
        return userName;
    }

    public String getRole() {
        return role;
    }


    public static final class Builder {
        private String userName;
        private String role;

        public Builder() {
        }

        public Builder withUserName(String val) {
            userName = val;
            return this;
        }

        public Builder withRole(String val) {
            role = val;
            return this;
        }

        public CollectionShareDto build() {
            return new CollectionShareDto(this);
        }
    }
}
