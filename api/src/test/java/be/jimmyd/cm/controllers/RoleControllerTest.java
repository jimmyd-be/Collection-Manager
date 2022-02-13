package be.jimmyd.cm.controllers;

import be.jimmyd.cm.domain.exceptions.UserPermissionException;
import be.jimmyd.cm.domain.service.RoleService;
import be.jimmyd.cm.dto.RoleDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleControllerTest {

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController controller;

    @Test
    public void getActiveRoles() throws UserPermissionException {

        RoleDto adminRole = new RoleDto.Builder().withId(1)
                .withName("Admin")
                .withActive(true)
                .build();
        RoleDto userRole = new RoleDto.Builder().withId(2)
                .withName("User")
                .withActive(true)
                .build();
        when(roleService.getActiveRoles()).thenReturn(List.of(adminRole, userRole));

        List<RoleDto> response = controller.getActiveRoles();

        assertEquals(2, response.size());
        assertEquals(adminRole, response.get(0));
        assertEquals(userRole, response.get(1));
    }

}
