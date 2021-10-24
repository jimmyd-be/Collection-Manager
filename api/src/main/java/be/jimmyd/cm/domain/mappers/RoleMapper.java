package be.jimmyd.cm.domain.mappers;

import be.jimmyd.cm.dto.RoleDto;
import be.jimmyd.cm.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    static List<RoleDto> roleToDto(List<Role> activeRoles) {
        return activeRoles.stream().map(role -> INSTANCE.roleToDto(role)).collect(Collectors.toList());
    }

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "active", source = "active")
    })
    RoleDto roleToDto(Role role);
}
