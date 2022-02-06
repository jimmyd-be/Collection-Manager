package be.jimmyd.cm.domain.utils;

import be.jimmyd.cm.domain.enums.Permission;
import be.jimmyd.cm.domain.exceptions.ItemNotExistException;
import be.jimmyd.cm.domain.exceptions.UserPermissionException;
import be.jimmyd.cm.entities.Item;
import be.jimmyd.cm.entities.Role;
import be.jimmyd.cm.entities.User;
import be.jimmyd.cm.entities.UserCollection;
import be.jimmyd.cm.repositories.CollectionUserRepository;
import be.jimmyd.cm.repositories.ItemRepository;
import be.jimmyd.cm.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SecurityUtil {

    private final UserRepository userRepository;
    private final CollectionUserRepository collectionUserRepository;
    private final ItemRepository itemRepository;

    public SecurityUtil(UserRepository userRepository,
                        final CollectionUserRepository collectionUserRepository,
                        final ItemRepository itemRepository) {
        this.userRepository = userRepository;
        this.collectionUserRepository = collectionUserRepository;
        this.itemRepository = itemRepository;
    }

    public void hasUserAccessToCollection(long collectionId, Permission permission) throws UserPermissionException {

        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        final User user = userRepository.findByMail(userMail);

        final UserCollection userCollection = collectionUserRepository.getByCollectionAndUser(collectionId, user.getId());

        if (userCollection != null && checkPermission(userCollection.getRole(), permission)) {
            return;
        }

        throw new UserPermissionException("User " + user.getId() + " hasn't access to collection " + collectionId);
    }

    private boolean checkPermission(Role role, Permission permission) {
        if (role.getActive()) {
            Permission userPermission = permissionToEnum(role.getName());

            if (permission == Permission.READ) {
                return userPermission == Permission.READ || userPermission == Permission.EDIT || userPermission == Permission.ADMIN;
            } else if (permission == Permission.EDIT) {
                return userPermission == Permission.EDIT || userPermission == Permission.ADMIN;
            } else if (permission == Permission.ADMIN) {
                return userPermission == Permission.ADMIN;
            }
        }

        return false;
    }

    private Permission permissionToEnum(String name) {

        if (name.equalsIgnoreCase("admin")) {
            return Permission.ADMIN;
        } else if (name.equalsIgnoreCase("editor")) {
            return Permission.EDIT;
        } else if (name.equalsIgnoreCase("viewer")) {
            return Permission.READ;
        }
        return null;
    }

    public boolean hasUserAccessToItem(long itemId, Permission permission) throws UserPermissionException, ItemNotExistException {
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        final User user = userRepository.findByMail(userMail);

        final Optional<Item> item = itemRepository.findById(itemId);

        if (item.isPresent()) {

            final Optional<UserCollection> userCollection = item.get().getCollections()
                    .stream()
                    .map(n -> n.getUserCollections())
                    .flatMap(List::stream)
                    .filter(n -> n.getUser().equals(user))
                    .findFirst();

            if (userCollection.isPresent() && checkPermission(userCollection.get().getRole(), permission)) {
                return true;
            }
        } else {
            throw new ItemNotExistException("Item with id " + itemId + " does not exist");
        }

        throw new UserPermissionException("User " + user.getId() + " hasn't access to item " + itemId);
    }
}
