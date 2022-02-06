package be.jimmyd.cm.dto;

public class ItemSearchDto {

    private String externalId;
    private String name;
    private String image;
    private int releaseDate;
    private String source;
    private String url;

    private ItemSearchDto(){

    }

    private ItemSearchDto(Builder builder) {
        externalId = builder.externalId;
        name = builder.name;
        image = builder.image;
        releaseDate = builder.releaseDate;
        source = builder.source;
        url = builder.url;
    }

    public String getExternalId() {
        return externalId;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getReleaseDate() {
        return releaseDate;
    }

    public String getSource() {
        return source;
    }

    public String getUrl() {
        return url;
    }

    public static final class Builder {
        private String externalId;
        private String name;
        private String image;
        private int releaseDate;
        private String source;
        private String url;

        public Builder() {
        }

        public Builder withExternalId(String val) {
            externalId = val;
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

        public Builder withReleaseDate(int val) {
            releaseDate = val;
            return this;
        }

        public Builder withSource(String val) {
            source = val;
            return this;
        }

        public Builder withUrl(String val) {
            url = val;
            return this;
        }

        public ItemSearchDto build() {
            return new ItemSearchDto(this);
        }
    }
}
