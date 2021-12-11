package be.jimmyd.cm.domain.service;

import be.jimmyd.cm.domain.mappers.FieldMapper;
import be.jimmyd.cm.dto.FieldDto;
import be.jimmyd.cm.repositories.FieldRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static be.jimmyd.cm.constants.CollectionDtoTestConstants.fieldDto;
import static be.jimmyd.cm.constants.CollectionTestConstants.field;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FieldServiceTest {

    @Mock
    private FieldRepository fieldRepository;

    @Mock
    private FieldMapper fieldMapper;

    @InjectMocks
    private FieldService fieldService;

    @Test
    public void getBasicFieldsByCollection() {
        when(fieldRepository.findBasicFieldByCollectionId(1)).thenReturn(List.of(field()));
        when(fieldMapper.mapMultiFieldToDto(List.of(field()))).thenReturn(List.of(fieldDto()));

        List<FieldDto> fields = fieldService.getBasicFieldsByCollection(1);

        assertThat(fields).containsExactly(fieldDto());
    }

    @Test
    public void getCustomFieldsByCollection() {
        when(fieldRepository.findCustomFieldByCollectionId(1)).thenReturn(List.of(field()));
        when(fieldMapper.mapMultiFieldToDto(List.of(field()))).thenReturn(List.of(fieldDto()));

        List<FieldDto> fields = fieldService.getCustomFieldsByCollection(1);

        assertThat(fields).containsExactly(fieldDto());
    }

    @Test
    public void deleteFieldsWithoutCollection() {
        when(fieldRepository.findFieldsWithoutCollection()).thenReturn(List.of(field()));

        fieldService.deleteFieldsWithoutCollection();

        verify(fieldRepository, times(1)).deleteAll(any());
    }

}