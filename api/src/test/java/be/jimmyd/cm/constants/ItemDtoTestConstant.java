package be.jimmyd.cm.constants;

import be.jimmyd.cm.dto.ItemDataDto;
import be.jimmyd.cm.dto.ItemDto;
import be.jimmyd.cm.dto.SearchResultDto;

import java.util.List;

import static be.jimmyd.cm.constants.CollectionTestConstants.*;
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

    public static SearchResultDto searchResultDto() {
        return new SearchResultDto.Builder()
                .withCollectionId(COLLECTION_ID)
                .withCollectionName(COLLECTION_NAME)
                .withItems(List.of(itemDto()))
                .build();
    }
}
