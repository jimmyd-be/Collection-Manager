package be.jimmyd.cm.domain.utils;

import be.jimmyd.cm.domain.enums.Permission;
import be.jimmyd.cm.domain.exceptions.UserPermissionException;
import be.jimmyd.cm.entities.User;
import be.jimmyd.cm.repositories.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    private final UserRepository userRepository;

    public SecurityUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean hasUserReadAccessToCollection(String mail, long collectionId) throws UserPermissionException {

        return hasUserAccessToCollection(mail, collectionId, Permission.READ);
    }

    public boolean hasUserAccessToCollection(String mail, long collectionId, Permission permission) throws UserPermissionException {

        //TODo add logic
        final User user = userRepository.findByMail(mail);

        if(false) {
            throw new UserPermissionException("User " + user.getId() + " hasn't acces to collection " + collectionId);
        }

        return true;
    }
}
