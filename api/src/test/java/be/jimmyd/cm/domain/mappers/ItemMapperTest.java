package be.jimmyd.cm.domain.mappers;

import be.jimmyd.cm.dto.ItemDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static be.jimmyd.cm.constants.ItemDtoTestConstant.itemDto;
import static be.jimmyd.cm.constants.ItemTestConstant.item;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ItemMapperTest {

    @InjectMocks
    private ItemMapper itemMapper;

    @Test
    public void map() {
        ItemDto result = itemMapper.map(item());

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(itemDto());
    }

    @Test
    public void mapList() {
        List<ItemDto> result = itemMapper.map(List.of(item()));

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0))
                .usingRecursiveComparison()
                .isEqualTo(itemDto());
    }

}