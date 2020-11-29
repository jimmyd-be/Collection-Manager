package be.jimmyd.cm.domain.mappers;

import be.jimmyd.cm.dto.UserCollectionDto;
import be.jimmyd.cm.entities.UserCollection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface CollectionUserMapper {

    CollectionUserMapper INSTANCE = Mappers.getMapper(CollectionUserMapper.class);

    @Mappings({
        @Mapping(source = "user.id", target = "userId"),
        @Mapping(source = "user.username", target = "userName"),
        @Mapping(source = "roleId.id", target = "roleId"),
        @Mapping(source = "roleId.name", target = "roleName")
    })
    UserCollectionDto userCollectionToDto(UserCollection userCollection);

    default List<UserCollectionDto> userCollectionToDto(List<UserCollection> userCollections) {
        return userCollections.stream().map(userCollection -> userCollectionToDto(userCollection)).collect(Collectors.toList());
    }
}
