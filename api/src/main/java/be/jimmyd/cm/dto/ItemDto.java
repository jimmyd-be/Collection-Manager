package be.jimmyd.cm.dto;

import java.util.List;
import java.util.Objects;

public class ItemDto {

    private long id;
    private String name;
    private String image;
    private List<ItemDataDto> data;

    private ItemDto(){

    }

    private ItemDto(Builder builder) {
        id = builder.id;
        name = builder.name;
        image = builder.image;
        data = builder.data;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public List<ItemDataDto> getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDto itemDto = (ItemDto) o;
        return id == itemDto.id && Objects.equals(name, itemDto.name) && Objects.equals(image, itemDto.image) && Objects.equals(data, itemDto.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, image, data);
    }

    public static final class Builder {
        private long id;
        private String name;
        private String image;
        private List<ItemDataDto> data;

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

        public Builder withImage(String val) {
            image = val;
            return this;
        }

        public Builder withData(List<ItemDataDto> val) {
            data = val;
            return this;
        }

        public ItemDto build() {
            return new ItemDto(this);
        }
    }
}
