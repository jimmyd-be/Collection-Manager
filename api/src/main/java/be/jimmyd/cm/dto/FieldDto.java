package be.jimmyd.cm.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
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
}
