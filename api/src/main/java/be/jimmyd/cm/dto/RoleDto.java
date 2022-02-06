package be.jimmyd.cm.dto;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleDto roleDto = (RoleDto) o;
        return id == roleDto.id && active == roleDto.active && Objects.equals(name, roleDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, active);
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
