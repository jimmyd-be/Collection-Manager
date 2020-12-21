package be.jimmyd.cm.domain.external;

import be.jimmyd.cm.dto.ItemSearchDto;
import be.jimmyd.cm.entities.Field;

import java.util.List;
import java.util.Map;

public interface ExternalApi {

    CollectionTypeEnum getType();

    String getUniqueKey();

    List<String> getProperties();

    boolean isReadyToUse();

    List<ItemSearchDto> searchItems(String search) throws Throwable;

    Map<String, String> getById(String id, List<Field> basicFields) throws Throwable;
}
