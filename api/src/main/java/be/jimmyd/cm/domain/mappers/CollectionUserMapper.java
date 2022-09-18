package be.jimmyd.cm.domain.mappers;

import be.jimmyd.cm.dto.UserCollectionDto;
import be.jimmyd.cm.entities.UserCollection;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CollectionUserMapper {
    
    public UserCollectionDto map(UserCollection userCollection) {
        return new UserCollectionDto.Builder()
                .withUserId(userCollection.getUser().getId())
                .withUserName(userCollection.getUser().getUsername())
                .withRoleName(userCollection.getRole().getName())
                .withRoleId(userCollection.getRole().getId())
                .build();
    }

    public List<UserCollectionDto> map(List<UserCollection> userCollections) {
        return userCollections.stream().map(userCollection -> map(userCollection)).collect(Collectors.toList());
    }
}
