package be.jimmyd.cm.domain.mappers;

import be.jimmyd.cm.dto.UserDto;
import be.jimmyd.cm.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static be.jimmyd.cm.constants.CollectionTestConstants.user;
import static be.jimmyd.cm.constants.UserDtoTestConstant.userDto;
import static be.jimmyd.cm.constants.UserDtoTestConstant.userRegisterDto;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    @InjectMocks
    private UserMapper mapper;

    @Test
    public void mapDtoToUser() {

        User result = mapper.map(userRegisterDto());

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id", "creationDate")
                .isEqualTo(user());
    }

    @Test
    public void mapUserToDto(){
        UserDto result = mapper.map(user());

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(userDto());
    }

    @Test
    public void mapUserToDtoList(){
        List<UserDto> result = mapper.map(List.of(user()));

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0))
                .usingRecursiveComparison()
                .isEqualTo(userDto());
    }

}