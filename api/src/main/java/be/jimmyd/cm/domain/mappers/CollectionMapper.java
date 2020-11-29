package be.jimmyd.cm.domain.mappers;

import be.jimmyd.cm.dto.CollectionDto;
import be.jimmyd.cm.dto.FieldDto;
import be.jimmyd.cm.entities.Collection;
import be.jimmyd.cm.entities.Field;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface CollectionMapper {

    CollectionMapper INSTANCE = Mappers.getMapper(CollectionMapper.class);

    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "name", target = "name"),
        @Mapping(source = "type.type", target = "type"),
        @Mapping(source = "fields", target = "fields", qualifiedByName = "fieldMapping")
    })
    CollectionDto collectionToDto(Collection collection);

    default List<CollectionDto> collectionToDto(List<Collection> collections) {
        return collections.stream().map(collection -> collectionToDto(collection)).collect(Collectors.toList());
    }

    @Named("fieldMapping")
    default List<FieldDto> mapFieldToCollection(List<Field> fields) {

        return FieldMapper.INSTANCE.mapMultiFieldToDto(fields);
    }

}
