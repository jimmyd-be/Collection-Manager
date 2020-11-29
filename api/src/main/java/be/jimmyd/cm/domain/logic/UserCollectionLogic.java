package be.jimmyd.cm.domain.logic;

import be.jimmyd.cm.domain.mappers.CollectionUserMapper;
import be.jimmyd.cm.dto.UserCollectionDto;
import be.jimmyd.cm.entities.UserCollection;
import be.jimmyd.cm.repositories.CollectionUserRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserCollectionLogic {

    private final CollectionUserMapper collectionUserMapper;
    private CollectionUserRepository collectionUserRepository;

    public UserCollectionLogic(final CollectionUserRepository collectionUserRepository) {
        this.collectionUserRepository = collectionUserRepository;
        this.collectionUserMapper = CollectionUserMapper.INSTANCE;
    }

    public List<UserCollectionDto> getUsersByCollection(long collectionId) {

        final List<UserCollection> users = collectionUserRepository.getByCollectionId(collectionId);

        return collectionUserMapper.userCollectionToDto(users);
    }

    public void deleteUserFromCollection(long collectionId, long userId) {
        final List<UserCollection> userCollections = collectionUserRepository.getByCollectionAndUser(collectionId, userId);

        userCollections.forEach(userCollection -> collectionUserRepository.delete(userCollection));
    }
}
