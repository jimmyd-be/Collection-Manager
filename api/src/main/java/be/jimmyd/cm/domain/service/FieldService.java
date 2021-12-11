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
    private final FieldMapper fieldMapper;

    public FieldService(FieldRepository fieldRepository, FieldMapper fieldMapper) {
        this.fieldRepository = fieldRepository;
        this.fieldMapper = fieldMapper;
    }

    public List<FieldDto> getBasicFieldsByCollection(long collectionId) {

        List<Field> basicFields = fieldRepository.findBasicFieldByCollectionId(collectionId);

        return fieldMapper.mapMultiFieldToDto(basicFields);
    }

    public List<FieldDto> getCustomFieldsByCollection(long collectionId) {

        List<Field> customFields = fieldRepository.findCustomFieldByCollectionId(collectionId);

        return fieldMapper.mapMultiFieldToDto(customFields);
    }

    public void deleteFieldsWithoutCollection() {

        List<Field> fields = fieldRepository.findFieldsWithoutCollection();

        fieldRepository.deleteAll(fields);
    }
}
