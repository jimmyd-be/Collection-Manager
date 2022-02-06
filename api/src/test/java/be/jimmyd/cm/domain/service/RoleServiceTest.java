package be.jimmyd.cm.domain.service;

import be.jimmyd.cm.domain.mappers.RoleMapper;
import be.jimmyd.cm.dto.RoleDto;
import be.jimmyd.cm.repositories.RoleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static be.jimmyd.cm.constants.CollectionDtoTestConstants.roleDto;
import static be.jimmyd.cm.constants.CollectionTestConstants.role;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private RoleService roleService;

    @Test
    public void getActiveRoles() {
        when(roleRepository.getActiveRoles()).thenReturn(List.of(role()));
        when(roleMapper.map(List.of(role()))).thenReturn(List.of(roleDto()));

        List<RoleDto> result = roleService.getActiveRoles();

        Assertions.assertThat(result).containsExactly(roleDto());
    }

}