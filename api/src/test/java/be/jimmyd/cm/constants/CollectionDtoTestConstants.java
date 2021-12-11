package be.jimmyd.cm.constants;

import be.jimmyd.cm.dto.CollectionDto;
import be.jimmyd.cm.dto.FieldDto;
import be.jimmyd.cm.dto.RoleDto;
import be.jimmyd.cm.dto.UserCollectionDto;

import java.util.List;

import static be.jimmyd.cm.constants.CollectionTestConstants.*;

public class CollectionDtoTestConstants {

    private CollectionDtoTestConstants() {}

    public static CollectionDto collectionDto()  {
        return new CollectionDto.Builder()
                .withName("New collection")
                .withId(5)
                .withType(COLLECTION_TYPE_NAME)
                .withFields(fieldDtos())
                .withMembers(List.of(USER_NAME))
                .build();
    }

    public static List<FieldDto> fieldDtos() {
        return List.of(fieldDto());
    }

    public static FieldDto fieldDto() {
        return new FieldDto.Builder()
                .withId(FIELD_ID)
                .withName(FIELD_NAME)
                .withType(FIELD_TYPE)
                .withOptions(FIELD_OPTIONS)
                .withRequired(FIELD_REQUIRED)
                .withPlaceholder(FIELD_PLACE_HOLDER)
                .withFieldOrder(FIELD_ORDER)
                .withPlace(FIELD_PLACE)
                .withMultivalues(FIELD_MULTI_VALUE)
                .withLabelPosition(FIELD_LABEL_POSITION)
                .withLabel(FIELD_LABEL)
                .withWidget(FIELD_WIDGET)
                .build();
    }

    public static UserCollectionDto userCollectionDto() {
        return new UserCollectionDto.Builder()
                .withRoleId(ROLE_ID)
                .withUserId(USER_ID)
                .withRoleName(ROLE_NAME)
                .withUserName(USER_NAME)
                .build();
    }

    public static RoleDto roleDto() {
        return new RoleDto.Builder()
                .withId(ROLE_ID)
                .withName(ROLE_NAME)
                .withActive(true)
                .build();
    }
}
