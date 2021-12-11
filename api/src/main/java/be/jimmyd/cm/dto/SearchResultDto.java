package be.jimmyd.cm.dto;

import java.util.List;

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
