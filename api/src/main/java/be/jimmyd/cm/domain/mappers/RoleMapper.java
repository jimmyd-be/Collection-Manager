package be.jimmyd.cm.domain.mappers;

import be.jimmyd.cm.dto.RoleDto;
import be.jimmyd.cm.entities.Role;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleMapper {

    public List<RoleDto> map(List<Role> activeRoles) {
        return activeRoles.stream().map(role -> map(role)).collect(Collectors.toList());
    }

    public RoleDto map(Role role) {
        return new RoleDto.Builder()
                .withId(role.getId())
                .withName(role.getName())
                .withActive(role.getActive())
                .build();
    }
}
