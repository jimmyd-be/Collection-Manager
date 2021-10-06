package be.jimmyd.cm.domain.service;

import be.jimmyd.cm.domain.mappers.FieldMapper;
import be.jimmyd.cm.dto.FieldDto;
import be.jimmyd.cm.entities.Field;
import be.jimmyd.cm.repositories.FieldRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FieldService {

    private final FieldRepository fieldRepository;

    public FieldService(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    public List<FieldDto> getBasicFieldsByCollection(long collectionId) {

        List<Field> basicFields = fieldRepository.findBasicFieldByCollectionId(collectionId);

        return FieldMapper.INSTANCE.mapMultiFieldToDto(basicFields);
    }

    public List<FieldDto> getCustomFieldsByCollection(long collectionId) {

        List<Field> customFields = fieldRepository.findCustomFieldByCollectionId(collectionId);

        return FieldMapper.INSTANCE.mapMultiFieldToDto(customFields);
    }

    public void deleteFieldsWithoutCollection() {

        List<Field> fields = fieldRepository.findFieldsWithoutCollection();

        fieldRepository.deleteAll(fields);
    }
}
