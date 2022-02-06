package be.jimmyd.cm.dto;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDataDto that = (ItemDataDto) o;
        return fieldId == that.fieldId && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldId, value);
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
