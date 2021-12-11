package be.jimmyd.cm.constants;

import be.jimmyd.cm.dto.ItemDataDto;
import be.jimmyd.cm.dto.ItemDto;

import java.util.List;

import static be.jimmyd.cm.constants.CollectionTestConstants.FIELD_ID;
import static be.jimmyd.cm.constants.ItemTestConstant.*;

public class ItemDtoTestConstant {

    private ItemDtoTestConstant(){}

    public static ItemDto itemDto() {
        return new ItemDto.Builder()
                .withId(ITEM_ID)
                .withName(ITEM_NAME)
                .withImage(ITEM_IMAGE)
                .withData(List.of(itemDateDto()))
                .build();
    }

    public static ItemDataDto itemDateDto() {
        return new ItemDataDto.Builder()
                .withFieldId(FIELD_ID)
                .withValue(FIELD_VALUE)
                .build();
    }
}
