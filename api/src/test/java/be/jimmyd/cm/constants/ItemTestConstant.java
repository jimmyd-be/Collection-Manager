package be.jimmyd.cm.constants;

import be.jimmyd.cm.entities.Item;
import be.jimmyd.cm.entities.Itemdata;

import java.time.LocalDateTime;
import java.util.List;

import static be.jimmyd.cm.constants.CollectionTestConstants.field;
import static be.jimmyd.cm.constants.CollectionTestConstants.user;

public class ItemTestConstant {

    public static final long ITEM_ID = 123L;
    public static final String ITEM_NAME = "The Matrix";
    public static final String ITEM_IMAGE = "http://test.org/image.jpg";
    public static final LocalDateTime ITEM_CREATION_DATE = LocalDateTime.now();
    public static final String FIELD_VALUE = "Ok";
    private static final long ITEM_DATE_ID = 456L;
    private static final long FIELD_VALUE_COUNT = 0;

    private ItemTestConstant(){}

    public static Item item() {
        return new Item.Builder()
                .withId(ITEM_ID)
                .withName(ITEM_NAME)
                .withImage(ITEM_IMAGE)
                .withActive(true)
                .withAuthor(user())
                .withCreationDate(ITEM_CREATION_DATE)
                .withLastModified(ITEM_CREATION_DATE)
                .withItemdata(List.of(itemDate()))
                .build();
    }

    public static Itemdata itemDate() {
        return new Itemdata.Builder()
                .withId(ITEM_DATE_ID)
                .withField(field())
                .withFieldValue(FIELD_VALUE)
                .withValueCount(FIELD_VALUE_COUNT)
                .build();
    }
}
