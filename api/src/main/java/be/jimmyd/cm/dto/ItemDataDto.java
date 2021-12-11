package be.jimmyd.cm.dto;

public class ItemDataDto {

    private long fieldId;
    private String value;

    private ItemDataDto(){

    }

    private ItemDataDto(Builder builder) {
        fieldId = builder.fieldId;
        value = builder.value;
    }

    public long getFieldId() {
        return fieldId;
    }

    public String getValue() {
        return value;
    }


    public static final class Builder {
        private long fieldId;
        private String value;

        public Builder() {
        }

        public Builder withFieldId(long val) {
            fieldId = val;
            return this;
        }

        public Builder withValue(String val) {
            value = val;
            return this;
        }

        public ItemDataDto build() {
            return new ItemDataDto(this);
        }
    }
}
