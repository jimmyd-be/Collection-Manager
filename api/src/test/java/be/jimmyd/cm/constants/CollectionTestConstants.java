package be.jimmyd.cm.constants;

import be.jimmyd.cm.entities.*;

import java.time.LocalDate;
import java.util.List;

public class CollectionTestConstants {

    public static final String COLLECTION_TYPE_NAME = "Movis";
    public static final int COLLECTION_TYPE_ID = 10;
    public static final long FIELD_ID = 11L;
    public static final String FIELD_NAME = "Custom Field";
    public static final String FIELD_OPTIONS = "option 1";
    public static final Boolean FIELD_REQUIRED = false;
    public static final String FIELD_PLACE_HOLDER = "Placeholder";
    public static final int FIELD_ORDER = 0;
    public static final String FIELD_PLACE = "main";
    public static final String FIELD_WIDGET = "default";
    public static final Boolean FIELD_MULTI_VALUE = true;
    public static final String FIELD_LABEL_POSITION = "ABOVE";
    public static final String FIELD_LABEL = "Label";
    public static final String FIELD_TYPE = "Checkbox";
    public static final long USER_ID = 12L;
    public static final long USER_COLLECTION_ID = 13L;
    public static final String THEME = "default";
    public static final String USER_MAIL = "test@test.com";
    public static final String USER_NAME = "user 1";
    public static final String USER_PASSWORD = "SecurePassword";
    public static final LocalDate USER_CREATION_DATE = LocalDate.now();
    public static final int ROLE_ID = 25;
    public static final String ROLE_NAME = "Editor";
    public static final int FIELD_TYPE_ID = 111;
    public static final String COLLECTION_NAME = "New collection";
    public static final long COLLECTION_ID = 5L;

    private CollectionTestConstants() {
    }

    public static CollectionType collectionType() {
        return new CollectionType.Builder()
                .withType(COLLECTION_TYPE_NAME)
                .withId(COLLECTION_TYPE_ID)
                .withActive(true)
                .build();
    }

    public static Collection collection() {
        return new Collection.Builder()
                .withActive(false)
                .withName(COLLECTION_NAME)
                .withId(COLLECTION_ID)
                .withType(collectionType())
                .withFields(fields())
                .withUserCollections(List.of(userCollection()))
                .build();
    }

    public static List<Field> fields() {
        return List.of(field());
    }

    public static UserCollection userCollection() {
        return new UserCollection.Builder()
                .withUser(user())
                .withRole(role())
                .withId(USER_COLLECTION_ID)
                .build();
    }

    public static Role role() {
        return new Role.Builder()
                .withActive(true)
                .withId(ROLE_ID)
                .withName(ROLE_NAME)
                .build();
    }

    public static User user() {
        return new User.Builder()
                .withId(USER_ID)
                .withIsAdmin(false)
                .withTheme(THEME)
                .withActive(true)
                .withMail(USER_MAIL)
                .withUsername(USER_NAME)
                .withUserPassword(USER_PASSWORD)
                .build();
    }

    public static Field field() {
        return new Field.Builder()
                .withId(FIELD_ID)
                .withName(FIELD_NAME)
                .withOtherOptions(FIELD_OPTIONS)
                .withRequired(FIELD_REQUIRED)
                .withPlaceHolder(FIELD_PLACE_HOLDER)
                .withFieldOrder(FIELD_ORDER)
                .withPlace(FIELD_PLACE)
                .withMultiValues(FIELD_MULTI_VALUE)
                .withLabelPosition(FIELD_LABEL_POSITION)
                .withLabel(FIELD_LABEL)
                .withWidget(FIELD_WIDGET)
                .withType(fieldType())
                .build();
    }

    public static FieldType fieldType() {
        return new FieldType.Builder()
                .withActive(true)
                .withId(FIELD_TYPE_ID)
                .withType(FIELD_TYPE)
                .build();
    }
}
