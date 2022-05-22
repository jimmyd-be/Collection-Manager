package be.jimmyd.cm.controllers;

import be.jimmyd.cm.domain.exceptions.OneActiveAdminNeededException;
import be.jimmyd.cm.domain.exceptions.UserPermissionException;
import be.jimmyd.cm.domain.service.CollectionService;
import be.jimmyd.cm.domain.service.CollectionTypeService;
import be.jimmyd.cm.domain.service.UserCollectionService;
import be.jimmyd.cm.domain.utils.SecurityUtil;
import be.jimmyd.cm.dto.CollectionDto;
import be.jimmyd.cm.dto.CollectionShareDto;
import be.jimmyd.cm.dto.UserCollectionDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CollectionControllerTest {

    public final String userMail = "test@test.be";

    @Mock
    private CollectionService collectionService;

    @Mock
    private SecurityUtil securityUtil;

    @Mock
    private UserCollectionService userCollectionService;

    @InjectMocks
    private CollectionController controller;

    @Mock
    private CollectionTypeService collectionTypeService;

    @Test
    public void getByIdHappyFlow() throws UserPermissionException {

        CollectionDto dto = new CollectionDto.Builder()
                .withId(1)
                .withName("Collection 1")
                .build();

        when(collectionService.getById(anyLong())).thenReturn(Optional.of(dto));

        ResponseEntity<CollectionDto> response = controller.getById(1);

        assertEquals(1, response.getBody().getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getByIdForbidden() throws UserPermissionException {

        final UsernamePasswordAuthenticationToken token = mock(UsernamePasswordAuthenticationToken.class);

        when(collectionService.getById(1)).thenThrow(UserPermissionException.class);

        ResponseEntity<CollectionDto> response = controller.getById(1);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void editCollectionHappyFlow() throws UserPermissionException {

        CollectionDto dto = new CollectionDto.Builder()
                .withId(1)
                .withName("Collection 1")
                .build();

        final UsernamePasswordAuthenticationToken token = mock(UsernamePasswordAuthenticationToken.class);
        doNothing().when(collectionService).editCollection(dto);

        ResponseEntity<CollectionDto> response = controller.editCollection(dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void editCollectionForbidden() throws UserPermissionException {

        CollectionDto dto = new CollectionDto.Builder()
                .withId(1)
                .withName("Collection 1")
                .build();

        doThrow(UserPermissionException.class).when(collectionService).editCollection(dto);

        ResponseEntity<CollectionDto> response = controller.editCollection(dto);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void shareForbidden() throws UserPermissionException {

        final CollectionShareDto mock = mock(CollectionShareDto.class);

        doThrow(UserPermissionException.class).when(userCollectionService).shareCollection(1, mock);

        ResponseEntity response = controller.share(1, mock);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void deleteForbidden() throws UserPermissionException {

        doThrow(UserPermissionException.class).when(securityUtil).hasUserAccessToCollection(anyLong(), any());

        ResponseEntity response = controller.delete(1);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void deleteUserFromCollectionForbidden() throws UserPermissionException {

        doThrow(UserPermissionException.class).when(securityUtil).hasUserAccessToCollection(anyLong(), any());

        ResponseEntity response = controller.deleteUserFromCollection(1, 2);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void deleteHappyFlow() throws UserPermissionException {

        doNothing().when(collectionService).deleteById(1);

        ResponseEntity response = controller.delete(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deleteUserFromCollectionHappyFlow() throws UserPermissionException, OneActiveAdminNeededException {

        doNothing().when(userCollectionService).deleteUserFromCollection(1, 2);

        ResponseEntity response = controller.deleteUserFromCollection(1, 2);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void shareHappyFlow() throws UserPermissionException {

        final CollectionShareDto mock = mock(CollectionShareDto.class);

        doNothing().when(userCollectionService).shareCollection(1, mock);

        ResponseEntity response = controller.share(1, mock);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getByUserHappyFlow() {
        CollectionDto dto1 = Mockito.mock(CollectionDto.class);
        CollectionDto dto2 = Mockito.mock(CollectionDto.class);

        List<CollectionDto> userCollections = new ArrayList<>();
        userCollections.add(dto1);
        userCollections.add(dto2);
        final UsernamePasswordAuthenticationToken token = mock(UsernamePasswordAuthenticationToken.class);

        when(token.getPrincipal()).thenReturn(userMail);
        doReturn(userCollections).when(collectionService).getByUser(userMail);

        List<CollectionDto> response = controller.getByUser(token);
        assertEquals(userCollections, response);
    }

    @Test
    public void getCollectionTypesHappyFlow() {

        List<String> types = List.of("Movies", "Games");

        when(collectionTypeService.getAllTypes()).thenReturn(types);

        List<String> response = controller.getCollectionTypes();
        assertEquals(types, response);
    }

    @Test
    public void addCollectionHappyFlow() {
        final UsernamePasswordAuthenticationToken token = mock(UsernamePasswordAuthenticationToken.class);
        CollectionDto dto1 = Mockito.mock(CollectionDto.class);
        when(token.getName()).thenReturn(userMail);

        controller.addCollection(dto1, token);

        verify(collectionService, times(1)).createCollection(dto1, userMail);
    }


    @Test
    public void getAllCollectionUsersHappyFlow() {

        long id = 1;

        UserCollectionDto user1 = mock(UserCollectionDto.class);
        UserCollectionDto user2 = mock(UserCollectionDto.class);

        List<UserCollectionDto> userCollections = List.of(user1, user2);

        when(userCollectionService.getUsersByCollection(id)).thenReturn(userCollections);

        List<UserCollectionDto> response = controller.getAllCollectionUsers(id);
        assertEquals(userCollections, response);
    }
}
