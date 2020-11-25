package be.jimmyd.cm.dto;

import lombok.Data;

import java.util.List;

@Data
public class CollectionDto {

    public long id;
    public String name;
    public String type;
    public List<String> members;
    public List<FieldDto> fields;
}
