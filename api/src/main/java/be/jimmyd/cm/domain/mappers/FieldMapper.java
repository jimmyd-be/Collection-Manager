package be.jimmyd.cm.domain.mappers;

import be.jimmyd.cm.dto.FieldDto;
import be.jimmyd.cm.entities.Field;
import be.jimmyd.cm.entities.FieldType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface FieldMapper {

    FieldMapper INSTANCE = Mappers.getMapper(FieldMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "type.type", target = "type"),
            @Mapping(source = "otherOptions", target = "options"),
            @Mapping(source = "required", target = "required"),
            @Mapping(source = "placeHolder", target = "placeholder"),
            @Mapping(source = "fieldOrder", target = "fieldOrder"),
            @Mapping(source = "place", target = "place"),
            @Mapping(source = "multiValues", target = "multivalues"),
            @Mapping(source = "labelPosition", target = "labelPosition"),
            @Mapping(source = "label", target = "label"),
            @Mapping(source = "widget", target = "widget")
    })
    FieldDto fieldToDto(Field field);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "options", target = "otherOptions"),
            @Mapping(source = "required", target = "required"),
            @Mapping(source = "placeholder", target = "placeHolder"),
            @Mapping(source = "fieldOrder", target = "fieldOrder"),
            @Mapping(source = "place", target = "place"),
            @Mapping(source = "multivalues", target = "multiValues"),
            @Mapping(source = "labelPosition", target = "labelPosition"),
            @Mapping(source = "label", target = "label"),
            @Mapping(source = "widget", target = "widget"),
            @Mapping(target = "type", ignore = true)
    })
    Field dtoToField(FieldDto field);

    default List<FieldDto> mapMultiFieldToDto(List<Field> fields) {

        return fields.stream().map(field -> fieldToDto(field)).collect(Collectors.toList());
    }
}
