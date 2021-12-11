package be.jimmyd.cm.dto;

public class UserCollectionDto {

    private long userId;
    private String userName;
    private long roleId;
    private String roleName;

    private UserCollectionDto(){

    }

    private UserCollectionDto(Builder builder) {
        userId = builder.userId;
        userName = builder.userName;
        roleId = builder.roleId;
        roleName = builder.roleName;
    }

    public long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public long getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public static final class Builder {
        private long userId;
        private String userName;
        private long roleId;
        private String roleName;

        public Builder() {
        }

        public Builder withUserId(long val) {
            userId = val;
            return this;
        }

        public Builder withUserName(String val) {
            userName = val;
            return this;
        }

        public Builder withRoleId(long val) {
            roleId = val;
            return this;
        }

        public Builder withRoleName(String val) {
            roleName = val;
            return this;
        }

        public UserCollectionDto build() {
            return new UserCollectionDto(this);
        }
    }
}
