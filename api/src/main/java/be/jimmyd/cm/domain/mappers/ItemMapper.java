package be.jimmyd.cm.domain.mappers;

import be.jimmyd.cm.dto.ItemDataDto;
import be.jimmyd.cm.dto.ItemDto;
import be.jimmyd.cm.entities.Item;
import be.jimmyd.cm.entities.Itemdata;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface ItemMapper {

    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "image", target = "image"),
            @Mapping(source = "itemdata", target = "data", qualifiedByName = "itemDataMapper")
    })
    ItemDto itemToDto(Item item);

    @Named("itemDataMapper")
    default List<ItemDataDto> itemDataMapper(List<Itemdata> itemDataList) {

        return itemDataList.stream().map(data -> {
            ItemDataDto dto = new ItemDataDto();
            dto.setFieldId(data.getField().getId());
            dto.setValue(data.getFieldValue());
            return dto;
        }).collect(Collectors.toList());
    }

    default List<ItemDto> itemToDto(List<Item> items) {
        return items.stream().map(item -> itemToDto(item)).collect(Collectors.toList());
    }
}
