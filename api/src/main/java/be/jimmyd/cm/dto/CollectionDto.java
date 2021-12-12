package be.jimmyd.cm.dto;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

public class CollectionDto {

    private long id;

    @NotNull
    private String name;

    @NotNull
    private String type;
    private List<String> members;
    private List<FieldDto> fields;

    private CollectionDto(){

    }

    private CollectionDto(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.type = builder.type;
        this.members = builder.members;
        this.fields = builder.fields;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public List<String> getMembers() {
        return members;
    }

    public List<FieldDto> getFields() {
        return fields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollectionDto that = (CollectionDto) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(type, that.type) && Objects.equals(members, that.members) && Objects.equals(fields, that.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, members, fields);
    }

    public static final class Builder {
        private long id;
        private @NotNull String name;
        private @NotNull String type;
        private List<String> members;
        private List<FieldDto> fields;

        public Builder() {
        }

        public Builder withId(long val) {
            id = val;
            return this;
        }

        public Builder withName(@NotNull String val) {
            name = val;
            return this;
        }

        public Builder withType(@NotNull String val) {
            type = val;
            return this;
        }

        public Builder withMembers(List<String> val) {
            members = val;
            return this;
        }

        public Builder withFields(List<FieldDto> val) {
            fields = val;
            return this;
        }

        public CollectionDto build() {
            return new CollectionDto(this);
        }
    }
}
