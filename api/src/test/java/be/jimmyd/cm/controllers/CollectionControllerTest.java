package be.jimmyd.cm.controllers;

import be.jimmyd.cm.domain.exceptions.UserPermissionException;
import be.jimmyd.cm.domain.logic.CollectionLogic;
import be.jimmyd.cm.domain.logic.CollectionTypeLogic;
import be.jimmyd.cm.domain.logic.UserCollectionLogic;
import be.jimmyd.cm.domain.utils.SecurityUtil;
import be.jimmyd.cm.dto.CollectionDto;
import be.jimmyd.cm.dto.CollectionShareDto;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CollectionControllerTest {

    public final String userMail = "test@test.be";

    @Mock
    private CollectionLogic collectionLogic;

    @Mock
    private SecurityUtil securityUtil;

    @Mock
    private UserCollectionLogic userCollectionLogic;

    @InjectMocks
    private CollectionController controller;

    @Test
    public void getByIdHappyFlow() throws UserPermissionException {

        CollectionDto dto = new CollectionDto();
        dto.setId(1);
        dto.setName("Collection 1");

        when(collectionLogic.getById(anyLong())).thenReturn(dto);

        ResponseEntity<CollectionDto> response = controller.getById(1);

        assertEquals(1, response.getBody().getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getByIdForbidden() throws UserPermissionException {

        final UsernamePasswordAuthenticationToken token = mock(UsernamePasswordAuthenticationToken.class);

        when(collectionLogic.getById(1)).thenThrow(UserPermissionException.class);

        ResponseEntity<CollectionDto> response = controller.getById(1);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void editCollectionHappyFlow() throws UserPermissionException {

        CollectionDto dto = new CollectionDto();
        dto.setId(1);
        dto.setName("Collection 1");

        final UsernamePasswordAuthenticationToken token = mock(UsernamePasswordAuthenticationToken.class);
        doNothing().when(collectionLogic).editCollection(dto);

        ResponseEntity<CollectionDto> response = controller.editCollection(dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void editCollectionForbidden() throws UserPermissionException {

        CollectionDto dto = new CollectionDto();
        dto.setId(1);
        dto.setName("Collection 1");

        doThrow(UserPermissionException.class).when(collectionLogic).editCollection(dto);

        ResponseEntity<CollectionDto> response = controller.editCollection(dto);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void shareForbidden() throws UserPermissionException {

        final CollectionShareDto mock = mock(CollectionShareDto.class);

        doThrow(UserPermissionException.class).when(userCollectionLogic).shareCollection(1, mock);

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

        doNothing().when(collectionLogic).deleteById(1);

        ResponseEntity response = controller.delete(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deleteUserFromCollectionHappyFlow() throws UserPermissionException {

        doNothing().when(userCollectionLogic).deleteUserFromCollection(1, 2);

        ResponseEntity response = controller.deleteUserFromCollection(1, 2);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void shareHappyFlow() throws UserPermissionException {

        final CollectionShareDto mock = mock(CollectionShareDto.class);

        doNothing().when(userCollectionLogic).shareCollection(1, mock);

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
        doReturn(userCollections).when(collectionLogic).getByUser(userMail);

        List<CollectionDto> response = controller.getByUser(token);
        assertEquals(userCollections, response);
    }

    @Test
    public void getCollectionTypesHappyFlow() {
        CollectionDto dto1 = Mockito.mock(CollectionDto.class);
        CollectionDto dto2 = Mockito.mock(CollectionDto.class);

        List<CollectionDto> userCollections = new ArrayList<>();
        userCollections.add(dto1);
        userCollections.add(dto2);
        final UsernamePasswordAuthenticationToken token = mock(UsernamePasswordAuthenticationToken.class);

        when(token.getPrincipal()).thenReturn(userMail);
        doReturn(userCollections).when(collectionLogic).getByUser(userMail);

        List<CollectionDto> response = controller.getByUser(token);
        assertEquals(userCollections, response);
    }

    //getAllCollectionUsers
    //addCollection

}
