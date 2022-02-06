package be.jimmyd.cm.domain.mappers;

import be.jimmyd.cm.dto.FieldDto;
import be.jimmyd.cm.entities.Field;
import be.jimmyd.cm.repositories.FieldTypeRepository;

import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

@Named
public class FieldMapper {

    private final FieldTypeRepository fieldTypeRepository;

    public FieldMapper(FieldTypeRepository fieldTypeRepository) {
        this.fieldTypeRepository = fieldTypeRepository;
    }

    public FieldDto map(Field field) {
        return new FieldDto.Builder()
                .withId(field.getId())
                .withName(field.getName())
                .withType(field.getType().getType())
                .withOptions(field.getOtherOptions())
                .withRequired(field.getRequired())
                .withPlaceholder(field.getPlaceHolder())
                .withFieldOrder(field.getFieldOrder())
                .withPlace(field.getPlace())
                .withMultivalues(field.getMultiValues())
                .withLabelPosition(field.getLabelPosition())
                .withLabel(field.getLabel())
                .withWidget(field.getWidget())
                .build();
    }

    public Field map(FieldDto field) {
        return new Field.Builder()
                .withId(field.getId())
                .withName(field.getName())
                .withOtherOptions(field.getOptions())
                .withRequired(field.isRequired())
                .withPlaceHolder(field.getPlaceholder())
                .withFieldOrder(field.getFieldOrder())
                .withPlace(field.getPlace())
                .withMultiValues(field.isMultivalues())
                .withLabelPosition(field.getLabelPosition())
                .withLabel(field.getLabel())
                .withWidget(field.getWidget())
                .withType(fieldTypeRepository.findByName(field.getType()))
                .build();
    }

    public List<FieldDto> mapMultiFieldToDto(List<Field> fields) {
        return fields.stream().map(field -> map(field)).collect(Collectors.toList());
    }
}
