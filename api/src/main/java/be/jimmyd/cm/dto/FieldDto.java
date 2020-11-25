package be.jimmyd.cm.dto;

import lombok.Data;

@Data
public class FieldDto {

    public long id;
    public String name;
    public String type;
    public String options;
    public boolean required;
    public String placeholder;
    public int fieldOrder;
    public String place;
    public boolean multivalues;
    public String labelPosition;
    public String label;
    public String value;
    public String widget;
}
