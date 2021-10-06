package be.jimmyd.cm.domain.service;

import be.jimmyd.cm.domain.mappers.RoleMapper;
import be.jimmyd.cm.dto.RoleDto;
import be.jimmyd.cm.entities.Role;
import be.jimmyd.cm.repositories.RoleRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<RoleDto> getActiveRoles() {

        List<Role> activeRoles = roleRepository.getActiveRoles();

        return RoleMapper.roleToDto(activeRoles);
    }
}
