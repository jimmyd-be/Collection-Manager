package be.jimmyd.cm.dto;

public class RoleDto {

    private long id;
    private String name;
    private boolean active;

    private RoleDto(){

    }

    private RoleDto(Builder builder) {
        id = builder.id;
        name = builder.name;
        active = builder.active;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }


    public static final class Builder {
        private long id;
        private String name;
        private boolean active;

        public Builder() {
        }

        public Builder withId(long val) {
            id = val;
            return this;
        }

        public Builder withName(String val) {
            name = val;
            return this;
        }

        public Builder withActive(boolean val) {
            active = val;
            return this;
        }

        public RoleDto build() {
            return new RoleDto(this);
        }
    }
}
