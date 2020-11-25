package be.jimmyd.cm.domain.logic;

import be.jimmyd.cm.domain.mappers.RoleMapper;
import be.jimmyd.cm.dto.RoleDto;
import be.jimmyd.cm.entities.Role;
import be.jimmyd.cm.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleLogic {

    private final RoleRepository roleRepository;

    public RoleLogic(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<RoleDto> getActiveRoles() {

        List<Role> activeRoles = roleRepository.getActiveRoles();

        return RoleMapper.roleToDto(activeRoles);
    }
}
