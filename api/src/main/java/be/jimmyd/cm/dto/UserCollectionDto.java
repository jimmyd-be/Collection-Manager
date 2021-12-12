package be.jimmyd.cm.dto;

import java.util.Objects;

public class UserCollectionDto {

    private long userId;
    private String userName;
    private long roleId;
    private String roleName;

    private UserCollectionDto() {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCollectionDto that = (UserCollectionDto) o;
        return userId == that.userId && roleId == that.roleId && Objects.equals(userName, that.userName) && Objects.equals(roleName, that.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, roleId, roleName);
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
