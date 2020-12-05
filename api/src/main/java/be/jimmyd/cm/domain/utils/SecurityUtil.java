package be.jimmyd.cm.domain.utils;

import be.jimmyd.cm.domain.enums.Permission;
import be.jimmyd.cm.domain.exceptions.UserPermissionException;
import be.jimmyd.cm.entities.User;
import be.jimmyd.cm.repositories.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class SecurityUtil {

    private final UserRepository userRepository;

    public SecurityUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean hasUserReadAccessToCollection(long collectionId) throws UserPermissionException {

        return hasUserAccessToCollection(collectionId, Permission.READ);
    }

    public boolean hasUserAccessToCollection(long collectionId, Permission permission) throws UserPermissionException {

        //TODo add logic

        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        final User user = userRepository.findByMail(userMail);

        if(false) {
            throw new UserPermissionException("User " + user.getId() + " hasn't acces to collection " + collectionId);
        }

        return true;
    }
}
