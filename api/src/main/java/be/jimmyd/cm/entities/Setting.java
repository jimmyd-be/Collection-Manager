package be.jimmyd.cm.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cm_setting")
public class Setting {

    @Id
    @Column(name = "id")
    private String id;

    @Basic
    @Column(name = "setting")
    private String value;

    private Setting(){

    }

    private Setting(Builder builder) {
        id = builder.id;
        value = builder.value;
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Setting setting = (Setting) o;
        return Objects.equals(id, setting.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static final class Builder {
        private String id;
        private String value;

        public Builder() {
        }

        public Builder withId(String val) {
            id = val;
            return this;
        }

        public Builder withValue(String val) {
            value = val;
            return this;
        }

        public Setting build() {
            return new Setting(this);
        }
    }
}
