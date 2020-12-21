package be.jimmyd.cm.domain.mappers;

import be.jimmyd.cm.dto.UserDto;
import be.jimmyd.cm.dto.UserRegisterDto;
import be.jimmyd.cm.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings({
            @Mapping(source = "fullName", target = "username"),
            @Mapping(target = "mail", source = "email"),
            @Mapping(target = "userPassword", source = "password"),
            @Mapping(target = "active", constant = "true"),
            @Mapping(target = "theme", constant = "default"),
            @Mapping(target = "creationDate", source = "fullName", qualifiedByName = "currentDateTime")
    })
    User registrationToUser(UserRegisterDto user);

    @Named("currentDateTime")
    static LocalDateTime currentDateTime(String fullName) {
        return LocalDateTime.now();
    }

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "mail", target = "mail"),
            @Mapping(source = "username", target = "username"),
            @Mapping(source = "isAdmin", target = "admin"),
            @Mapping(source = "creationDate", target = "creationDate"),
            @Mapping(source = "theme", target = "theme"),
    })
    UserDto userToDto(User user);

    default List<UserDto> userToDto(List<User> users) {
        return users.parallelStream()
                .map(user -> userToDto(user))
                .collect(Collectors.toList());
    }
}
