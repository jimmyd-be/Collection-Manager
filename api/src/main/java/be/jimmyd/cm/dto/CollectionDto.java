package be.jimmyd.cm.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

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
