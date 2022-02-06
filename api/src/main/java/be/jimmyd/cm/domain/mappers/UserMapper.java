package be.jimmyd.cm.domain.mappers;

import be.jimmyd.cm.dto.UserDto;
import be.jimmyd.cm.dto.UserRegisterDto;
import be.jimmyd.cm.entities.User;

import javax.inject.Named;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Named
public class UserMapper {

    public User map(UserRegisterDto user) {
        return new User.Builder()
                .withCreationDate(LocalDateTime.now())
                .withUsername(user.getFullName())
                .withMail(user.getEmail())
                .withUserPassword(user.getPassword())
                .withActive(true)
                .withTheme("default")
                .withIsAdmin(false)
                .build();
    }

    public UserDto map(User user) {
        return new UserDto.Builder()
                .withId(user.getId())
                .withCreationDate(LocalDate.now())
                .withUsername(user.getUsername())
                .withMail(user.getMail())
                .withActive(user.getActive())
                .withTheme(user.getTheme())
                .withIsAdmin(user.getAdmin())
                .build();
    }

    public List<UserDto> map(List<User> users) {
        return users.parallelStream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
