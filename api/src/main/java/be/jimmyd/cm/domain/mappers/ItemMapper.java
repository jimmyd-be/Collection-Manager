package be.jimmyd.cm.domain.mappers;

import be.jimmyd.cm.dto.ItemDataDto;
import be.jimmyd.cm.dto.ItemDto;
import be.jimmyd.cm.entities.Item;
import be.jimmyd.cm.entities.Itemdata;

import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

@Named
public class ItemMapper {


    public ItemDto map(Item item) {
        return new ItemDto.Builder()
                .withId(item.getId())
                .withName(item.getName())
                .withImage(item.getImage())
                .withData(itemDataMapper(item.getItemdata()))
                .build();
    }

    public List<ItemDataDto> itemDataMapper(List<Itemdata> itemDataList) {

        return itemDataList.stream().map(this::itemDataMapper).collect(Collectors.toList());
    }

    public ItemDataDto itemDataMapper(Itemdata itemData) {
        return new ItemDataDto.Builder()
                .withValue(itemData.getFieldValue())
                .withFieldId(itemData.getField().getId())
                .build();
    }

    public List<ItemDto> map(List<Item> items) {
        return items.stream().map(item -> map(item)).collect(Collectors.toList());
    }
}
