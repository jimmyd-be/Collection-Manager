package be.jimmyd.cm.domain.mappers;

import be.jimmyd.cm.dto.FieldDto;
import be.jimmyd.cm.entities.Field;
import be.jimmyd.cm.repositories.FieldTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static be.jimmyd.cm.constants.CollectionDtoTestConstants.fieldDto;
import static be.jimmyd.cm.constants.CollectionTestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class FieldMapperTest {

    @Mock
    private FieldTypeRepository fieldTypeRepository;

    @InjectMocks
    private FieldMapper mapper;

    @Test
    public void fieldToDto() {

        FieldDto result = mapper.map(field());

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(fieldDto());
    }

    @Test
    public void mapMultiFieldToDto() {
        List<FieldDto> result = mapper.mapMultiFieldToDto(List.of(field()));

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0))
                .usingRecursiveComparison()
                .isEqualTo(fieldDto());
    }

    @Test
    public void dtoToField() {

        Mockito.when(fieldTypeRepository.findByName(FIELD_TYPE)).thenReturn(fieldType());

        Field result = mapper.map(fieldDto());

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(field());
    }

}