package be.jimmyd.cm.domain.service;

import be.jimmyd.cm.domain.exceptions.UserPermissionException;
import be.jimmyd.cm.domain.mappers.CollectionUserMapper;
import be.jimmyd.cm.dto.CollectionShareDto;
import be.jimmyd.cm.dto.UserCollectionDto;
import be.jimmyd.cm.entities.Collection;
import be.jimmyd.cm.entities.Role;
import be.jimmyd.cm.entities.User;
import be.jimmyd.cm.entities.UserCollection;
import be.jimmyd.cm.repositories.CollectionRepository;
import be.jimmyd.cm.repositories.CollectionUserRepository;
import be.jimmyd.cm.repositories.RoleRepository;
import be.jimmyd.cm.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserCollectionService {

    private final CollectionUserMapper collectionUserMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CollectionUserRepository collectionUserRepository;
    private final CollectionRepository collectionRepository;

    public UserCollectionService(final CollectionUserRepository collectionUserRepository, final UserRepository userRepository,
                                 final RoleRepository roleRepository, final CollectionRepository collectionRepository) {
        this.collectionUserRepository = collectionUserRepository;
        this.collectionUserMapper = CollectionUserMapper.INSTANCE;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.collectionRepository = collectionRepository;
    }

    public List<UserCollectionDto> getUsersByCollection(long collectionId) {

        final List<UserCollection> users = collectionUserRepository.getByCollectionId(collectionId);

        return collectionUserMapper.userCollectionToDto(users);
    }

    public void deleteUserFromCollection(long collectionId, long userId) throws UserPermissionException {

        //TODO check if owner still exist after deletion

        final UserCollection userCollection = collectionUserRepository.getByCollectionAndUser(collectionId, userId);

        collectionUserRepository.delete(userCollection);
    }

    public void shareCollection(long collectionId, CollectionShareDto collectionShareDto) throws UserPermissionException {

        //TODO add validation
        final User user = userRepository.findByMailOrUserName(collectionShareDto.getUserName());
        final Role role = roleRepository.getByName(collectionShareDto.getRole());

        UserCollection userCollection = collectionUserRepository.getByCollectionAndUser(collectionId, user.getId());

        if (userCollection != null) {
            userCollection.setRole(role);
            collectionUserRepository.save(userCollection);
        } else {
            final Optional<Collection> collectionOptional = collectionRepository.findById(collectionId);

            if (collectionOptional.isPresent()) {
                userCollection = new UserCollection();
                userCollection.setRole(role);
                userCollection.setUser(user);
                userCollection.setCollection(collectionOptional.get());
                collectionUserRepository.save(userCollection);
            }
        }

    }

    public void addUserToCollection(String mail, String roleName, Collection collection) {

        final User user = userRepository.findByMail(mail);
        final Role role = roleRepository.getByName(roleName);

        UserCollection userCollection = new UserCollection();
        userCollection.setCollection(collection);
        userCollection.setRole(role);
        userCollection.setUser(user);

        collectionUserRepository.save(userCollection);
    }
}
