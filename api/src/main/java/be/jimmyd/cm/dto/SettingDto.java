package be.jimmyd.cm.dto;

import java.util.Objects;

public class SettingDto {

    private String key;
    private String value;

    private SettingDto(){

    }

    private SettingDto(Builder builder) {
        key = builder.key;
        value = builder.value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SettingDto that = (SettingDto) o;
        return key.equals(that.key) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    public static final class Builder {
        private String key;
        private String value;

        public Builder() {
        }

        public Builder withKey(String val) {
            key = val;
            return this;
        }

        public Builder withValue(String val) {
            value = val;
            return this;
        }

        public SettingDto build() {
            return new SettingDto(this);
        }
    }
}
