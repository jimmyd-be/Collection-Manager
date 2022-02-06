package be.jimmyd.cm.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class FieldDto {

    private long id;

    @NotNull
    private String name;

    @NotNull
    private String type;
    private String options;
    private boolean required;
    private String placeholder;
    private int fieldOrder;

    @NotNull
    private String place;
    private boolean multivalues;

    @NotNull
    private String labelPosition;

    @NotNull
    private String label;
    private String value;
    private String widget;

    private FieldDto() {

    }

    private FieldDto(Builder builder) {
        id = builder.id;
        name = builder.name;
        type = builder.type;
        options = builder.options;
        required = builder.required;
        placeholder = builder.placeholder;
        fieldOrder = builder.fieldOrder;
        place = builder.place;
        multivalues = builder.multivalues;
        labelPosition = builder.labelPosition;
        label = builder.label;
        value = builder.value;
        widget = builder.widget;
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

    public String getOptions() {
        return options;
    }

    public boolean isRequired() {
        return required;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public int getFieldOrder() {
        return fieldOrder;
    }

    public String getPlace() {
        return place;
    }

    public boolean isMultivalues() {
        return multivalues;
    }

    public String getLabelPosition() {
        return labelPosition;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    public String getWidget() {
        return widget;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldDto fieldDto = (FieldDto) o;
        return id == fieldDto.id && required == fieldDto.required && fieldOrder == fieldDto.fieldOrder && multivalues == fieldDto.multivalues && Objects.equals(name, fieldDto.name) && Objects.equals(type, fieldDto.type) && Objects.equals(options, fieldDto.options) && Objects.equals(placeholder, fieldDto.placeholder) && Objects.equals(place, fieldDto.place) && Objects.equals(labelPosition, fieldDto.labelPosition) && Objects.equals(label, fieldDto.label) && Objects.equals(value, fieldDto.value) && Objects.equals(widget, fieldDto.widget);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, options, required, placeholder, fieldOrder, place, multivalues, labelPosition, label, value, widget);
    }

    public static final class Builder {
        private long id;
        private @NotNull String name;
        private @NotNull String type;
        private String options;
        private boolean required;
        private String placeholder;
        private int fieldOrder;
        private @NotNull String place;
        private boolean multivalues;
        private @NotNull String labelPosition;
        private @NotNull String label;
        private String value;
        private String widget;

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

        public Builder withOptions(String val) {
            options = val;
            return this;
        }

        public Builder withRequired(boolean val) {
            required = val;
            return this;
        }

        public Builder withPlaceholder(String val) {
            placeholder = val;
            return this;
        }

        public Builder withFieldOrder(int val) {
            fieldOrder = val;
            return this;
        }

        public Builder withPlace(@NotNull String val) {
            place = val;
            return this;
        }

        public Builder withMultivalues(boolean val) {
            multivalues = val;
            return this;
        }

        public Builder withLabelPosition(@NotNull String val) {
            labelPosition = val;
            return this;
        }

        public Builder withLabel(@NotNull String val) {
            label = val;
            return this;
        }

        public Builder withValue(String val) {
            value = val;
            return this;
        }

        public Builder withWidget(String val) {
            widget = val;
            return this;
        }

        public FieldDto build() {
            return new FieldDto(this);
        }
    }
}
