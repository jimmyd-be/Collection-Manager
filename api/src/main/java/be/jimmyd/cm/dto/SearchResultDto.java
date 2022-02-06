package be.jimmyd.cm.dto;

import java.util.List;
import java.util.Objects;

public class SearchResultDto {

    private long collectionId;
    private String collectionName;
    private List<ItemDto> items;

    private SearchResultDto(){

    }

    private SearchResultDto(Builder builder) {
        collectionId = builder.collectionId;
        collectionName = builder.collectionName;
        items = builder.items;
    }

    public long getCollectionId() {
        return collectionId;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public List<ItemDto> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchResultDto that = (SearchResultDto) o;
        return collectionId == that.collectionId && Objects.equals(collectionName, that.collectionName) && Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collectionId, collectionName, items);
    }

    public static final class Builder {
        private long collectionId;
        private String collectionName;
        private List<ItemDto> items;

        public Builder() {
        }

        public Builder withCollectionId(long val) {
            collectionId = val;
            return this;
        }

        public Builder withCollectionName(String val) {
            collectionName = val;
            return this;
        }

        public Builder withItems(List<ItemDto> val) {
            items = val;
            return this;
        }

        public SearchResultDto build() {
            return new SearchResultDto(this);
        }
    }
}
