package be.jimmyd.cm.controllers;

import be.jimmyd.cm.domain.enums.Permission;
import be.jimmyd.cm.domain.exceptions.UserPermissionException;
import be.jimmyd.cm.domain.service.FieldService;
import be.jimmyd.cm.domain.utils.SecurityUtil;
import be.jimmyd.cm.dto.FieldDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FieldControllerTest {

    @Mock
    private FieldService fieldService;

    @Mock
    private SecurityUtil securityUtil;

    @InjectMocks
    private FieldController controller;

    private final FieldDto basicFieldDto = new FieldDto.Builder()
            .withId(1)
            .withName("Field 1")
            .build();

    private final FieldDto customFieldDto = new FieldDto.Builder()
            .withId(2)
            .withName("Custom Field 1")
            .build();

    @Test
    public void getByCollection() throws UserPermissionException {

        when(fieldService.getBasicFieldsByCollection(1)).thenReturn(List.of(basicFieldDto));
        when(fieldService.getCustomFieldsByCollection(1)).thenReturn(List.of(customFieldDto));

        ResponseEntity<List<FieldDto>> response = controller.getByCollection(1);

        assertEquals(2, response.getBody().size());
        assertEquals(basicFieldDto, response.getBody().get(0));
        assertEquals(customFieldDto, response.getBody().get(1));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getByCollection_forbidden() throws UserPermissionException {

        doThrow(UserPermissionException.class).when(securityUtil).hasUserAccessToCollection(1, Permission.READ);

        ResponseEntity<List<FieldDto>> response = controller.getByCollection(1);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void getBasicByCollection() throws UserPermissionException {

        when(fieldService.getBasicFieldsByCollection(1)).thenReturn(List.of(basicFieldDto));

        ResponseEntity<List<FieldDto>> response = controller.getBasicByCollection(1);

        assertEquals(1, response.getBody().size());
        assertEquals(basicFieldDto, response.getBody().get(0));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getBasicByCollection_forbidden() throws UserPermissionException {

        doThrow(UserPermissionException.class).when(securityUtil).hasUserAccessToCollection(1, Permission.READ);

        ResponseEntity<List<FieldDto>> response = controller.getBasicByCollection(1);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void getCustomByCollection() throws UserPermissionException {

        when(fieldService.getCustomFieldsByCollection(1)).thenReturn(List.of(customFieldDto));

        ResponseEntity<List<FieldDto>> response = controller.getCustomByCollection(1);

        assertEquals(1, response.getBody().size());
        assertEquals(customFieldDto, response.getBody().get(0));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getCustomByCollection_forbidden() throws UserPermissionException {

        doThrow(UserPermissionException.class).when(securityUtil).hasUserAccessToCollection(1, Permission.READ);

        ResponseEntity<List<FieldDto>> response = controller.getCustomByCollection(1);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
}
