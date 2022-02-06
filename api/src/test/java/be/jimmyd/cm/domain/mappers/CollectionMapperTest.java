package be.jimmyd.cm.domain.mappers;

import be.jimmyd.cm.dto.CollectionDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static be.jimmyd.cm.constants.CollectionDtoTestConstants.collectionDto;
import static be.jimmyd.cm.constants.CollectionDtoTestConstants.fieldDtos;
import static be.jimmyd.cm.constants.CollectionTestConstants.collection;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CollectionMapperTest {

    @Mock
    private FieldMapper fieldMapper;

    @InjectMocks
    private CollectionMapper collectionMapper;

    @Test
    public void map() {

        when(fieldMapper.mapMultiFieldToDto(any())).thenReturn(fieldDtos());

        CollectionDto collectionDto = collectionMapper.map(collection());

        assertThat(collectionDto)
                .usingRecursiveComparison()
                .isEqualTo(collectionDto());
    }

    @Test
    public void mapList() {

        when(fieldMapper.mapMultiFieldToDto(any())).thenReturn(fieldDtos());

        List<CollectionDto> collectionDto = collectionMapper.map(List.of(collection()));

        assertThat(collectionDto.get(0))
                .usingRecursiveComparison()
                .isEqualTo(collectionDto());
    }


}