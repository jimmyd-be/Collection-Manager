package be.jimmyd.cm.domain.service;

import be.jimmyd.cm.domain.exceptions.OneActiveAdminNeededException;
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

@Component
public class UserCollectionService {

    private final CollectionUserMapper collectionUserMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CollectionUserRepository collectionUserRepository;
    private final CollectionRepository collectionRepository;

    public UserCollectionService(CollectionUserRepository collectionUserRepository,
                                 UserRepository userRepository,
                                 RoleRepository roleRepository,
                                 CollectionRepository collectionRepository,
                                 CollectionUserMapper collectionUserMapper) {
        this.collectionUserRepository = collectionUserRepository;
        this.collectionUserMapper = collectionUserMapper;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.collectionRepository = collectionRepository;
    }

    public List<UserCollectionDto> getUsersByCollection(long collectionId) {

        final List<UserCollection> users = collectionUserRepository.getByCollectionId(collectionId);

        return collectionUserMapper.map(users);
    }

    public void deleteUserFromCollection(long collectionId, long userId) throws UserPermissionException, OneActiveAdminNeededException {

        List<UserCollection> allUsers = collectionUserRepository.getByCollectionId(collectionId);
        final UserCollection userCollection = collectionUserRepository.getByCollectionAndUser(collectionId, userId);

        allUsers.remove(userCollection);

        if(allUsers.stream().anyMatch(n -> n.getRole().getName().equalsIgnoreCase("admin"))) {
            collectionUserRepository.delete(userCollection);
        } else {
            throw new OneActiveAdminNeededException();
        }
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
            collectionRepository.findById(collectionId).ifPresent(collection ->
                collectionUserRepository.save(new UserCollection.Builder()
                        .withUser(user)
                        .withCollection(collection)
                        .withRole(role)
                        .build())
            );
        }
    }

    public void addUserToCollection(String mail, String roleName, Collection collection) {

        final User user = userRepository.findByMail(mail);
        final Role role = roleRepository.getByName(roleName);

        UserCollection userCollection = new UserCollection.Builder()
                .withUser(user)
                .withCollection(collection)
                .withRole(role)
                .build();

        collectionUserRepository.save(userCollection);
    }
}
