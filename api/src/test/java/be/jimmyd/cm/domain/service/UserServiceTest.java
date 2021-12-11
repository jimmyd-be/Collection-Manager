package be.jimmyd.cm.domain.service;

import be.jimmyd.cm.domain.exceptions.UserAlreadyExists;
import be.jimmyd.cm.domain.mappers.UserMapper;
import be.jimmyd.cm.dto.UserDto;
import be.jimmyd.cm.entities.User;
import be.jimmyd.cm.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static be.jimmyd.cm.constants.CollectionTestConstants.*;
import static be.jimmyd.cm.constants.UserDtoTestConstant.userDto;
import static be.jimmyd.cm.constants.UserDtoTestConstant.userRegisterDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserCollectionService userCollectionService;
    @Mock
    private CollectionService collectionLogic;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Test
    void registerUser_firstUser() throws UserAlreadyExists {
        when(userRepository.findByMail(USER_MAIL)).thenReturn(null);
        when(userMapper.map(userRegisterDto())).thenReturn(user());
        when(passwordEncoder.encode(USER_PASSWORD)).thenReturn("Encrypted Password");

        userService.registerUser(userRegisterDto());

        Mockito.verify(userRepository).save(userCaptor.capture());

        assertThat(userCaptor.getValue()).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(new User.Builder()
                        .withActive(true)
                        .withIsAdmin(true)
                        .withMail(USER_MAIL)
                        .withTheme(THEME)
                        .withUserPassword("Encrypted Password")
                        .withUsername(USER_NAME)
                        .build());
    }

    @Test
    void registerUserAlreadyExist() {
        when(userRepository.findByMail(USER_MAIL)).thenReturn(user());

        assertThrows(UserAlreadyExists.class, () -> {
            userService.registerUser(userRegisterDto());
        });
    }

    @Test
    void getUserByMail() {
        when(userRepository.findByMail(USER_MAIL)).thenReturn(user());
        when(userMapper.map(user())).thenReturn(userDto());

        UserDto result = userService.getUserByMail(USER_MAIL);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(userDto());
    }

    @Disabled
    @Test
    void deleteUser() {
    }

    @Disabled
    @Test
    void testDeleteUser() {
    }

    @Disabled
    @Test
    void editUser() {
    }

    @Disabled
    @Test
    void editPassword() {
    }

    @Disabled
    @Test
    void getAllUsers() {
    }

    @Disabled
    @Test
    void testEditUser() {
    }

    @Disabled
    @Test
    void changeAdmin() {
    }

    @Disabled
    @Test
    void testDeleteUser1() {
    }
}