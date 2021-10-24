package be.jimmyd.cm.domain.utils;

import be.jimmyd.cm.domain.enums.Permission;
import be.jimmyd.cm.domain.exceptions.UserPermissionException;
import be.jimmyd.cm.entities.Role;
import be.jimmyd.cm.entities.User;
import be.jimmyd.cm.entities.UserCollection;
import be.jimmyd.cm.repositories.CollectionUserRepository;
import be.jimmyd.cm.repositories.ItemRepository;
import be.jimmyd.cm.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SecurityUtilTest {

    private UserRepository userRepository;

    @Mock
    private CollectionUserRepository collectionUserRepository;

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private SecurityUtil securityUtil;

    public SecurityUtilTest() {

        userRepository = Mockito.mock(UserRepository.class);

        Authentication authentication = Mockito.mock(Authentication.class);

        when(authentication.getName()).thenReturn("test@test.be");

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        final User userMock = Mockito.mock(User.class);
        when(userRepository.findByMail(anyString())).thenReturn(userMock);
    }

    @Test
    public void testUserHasAccessToCollection2() throws UserPermissionException {

        final UserCollection userCollectionMock = Mockito.mock(UserCollection.class);
        final Role roleMock = Mockito.mock(Role.class);

        when(collectionUserRepository.getByCollectionAndUser(anyLong(), anyLong())).thenReturn(userCollectionMock);
        when(userCollectionMock.getRole()).thenReturn(roleMock);
        when(roleMock.getActive()).thenReturn(true);
        when(roleMock.getName()).thenReturn("Admin");

        securityUtil.hasUserAccessToCollection(1, Permission.READ);
    }

    @Test
    public void testUserHasAccessToCollection1() throws UserPermissionException {

        final UserCollection userCollectionMock = Mockito.mock(UserCollection.class);
        final Role roleMock = Mockito.mock(Role.class);

        when(collectionUserRepository.getByCollectionAndUser(anyLong(), anyLong())).thenReturn(userCollectionMock);
        when(userCollectionMock.getRole()).thenReturn(roleMock);
        when(roleMock.getActive()).thenReturn(true);
        when(roleMock.getName()).thenReturn("Admin");

        securityUtil.hasUserAccessToCollection(1, Permission.ADMIN);
    }

    @Test
    public void testUserHasAccessToCollection3() throws UserPermissionException {

        final UserCollection userCollectionMock = Mockito.mock(UserCollection.class);
        final Role roleMock = Mockito.mock(Role.class);

        when(collectionUserRepository.getByCollectionAndUser(anyLong(), anyLong())).thenReturn(userCollectionMock);
        when(userCollectionMock.getRole()).thenReturn(roleMock);
        when(roleMock.getActive()).thenReturn(true);
        when(roleMock.getName()).thenReturn("Viewer");

        securityUtil.hasUserAccessToCollection(1, Permission.READ);
    }

    @Test
    public void testUserHasNoEditPermission() {

        final UserCollection userCollectionMock = Mockito.mock(UserCollection.class);
        final Role roleMock = Mockito.mock(Role.class);

        when(collectionUserRepository.getByCollectionAndUser(anyLong(), anyLong())).thenReturn(userCollectionMock);
        when(userCollectionMock.getRole()).thenReturn(roleMock);
        when(roleMock.getActive()).thenReturn(true);
        when(roleMock.getName()).thenReturn("Viewer");

        assertThrows(UserPermissionException.class, () -> {
            securityUtil.hasUserAccessToCollection(1, Permission.EDIT);
        });
    }

    @Test
    public void testUserHasNoAdminPermission() {

        final UserCollection userCollectionMock = Mockito.mock(UserCollection.class);
        final Role roleMock = Mockito.mock(Role.class);

        when(collectionUserRepository.getByCollectionAndUser(anyLong(), anyLong())).thenReturn(userCollectionMock);
        when(userCollectionMock.getRole()).thenReturn(roleMock);
        when(roleMock.getActive()).thenReturn(true);
        when(roleMock.getName()).thenReturn("Viewer");

        assertThrows(UserPermissionException.class, () -> {
            securityUtil.hasUserAccessToCollection(1, Permission.ADMIN);
        });
    }
}
