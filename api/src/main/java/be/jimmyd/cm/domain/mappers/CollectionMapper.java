package be.jimmyd.cm.domain.mappers;

import be.jimmyd.cm.dto.CollectionDto;
import be.jimmyd.cm.entities.Collection;
import be.jimmyd.cm.entities.UserCollection;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CollectionMapper {

    private final FieldMapper fieldMapper;

    public CollectionMapper(FieldMapper fieldMapper) {
        this.fieldMapper = fieldMapper;
    }


    public CollectionDto map(Collection collection) {
        return new CollectionDto.Builder()
                .withFields(fieldMapper.mapMultiFieldToDto(collection.getFields()))
                .withId(collection.getId())
                .withMembers(mapMembers(collection.getUserCollections()))
                .withName(collection.getName())
                .withType(collection.getType().getType())
                .build();
    }

    public List<CollectionDto> map(List<Collection> collections) {
        return collections.stream().map(collection -> map(collection)).collect(Collectors.toList());
    }

    private List<String> mapMembers(List<UserCollection> userCollections) {
        return userCollections.stream().map(uc -> uc.getUser().getUsername()).collect(Collectors.toList());
    }

}
