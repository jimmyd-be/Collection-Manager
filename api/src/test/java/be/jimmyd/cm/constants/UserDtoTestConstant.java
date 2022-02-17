package be.jimmyd.cm.constants;

import be.jimmyd.cm.dto.UserDto;
import be.jimmyd.cm.dto.UserRegisterDto;

import static be.jimmyd.cm.constants.CollectionTestConstants.*;

public class UserDtoTestConstant {

    private UserDtoTestConstant() {}

    public static UserRegisterDto userRegisterDto() {
        return new UserRegisterDto.Builder()
                .withFullName(USER_NAME)
                .withEmail(USER_MAIL)
                .withPassword(USER_PASSWORD)
                .withConfirmPassword(USER_PASSWORD)
                .build();
    }

    public static UserRegisterDto userRegisterWithFaultyConfirmPasswordDto() {
        return new UserRegisterDto.Builder()
                .withFullName(USER_NAME)
                .withEmail(USER_MAIL)
                .withPassword(USER_PASSWORD)
                .withConfirmPassword(ANOTHER_USER_PASSWORD)
                .build();
    }

    public static UserDto userDto() {
        return new UserDto.Builder()
                .withId(USER_ID)
                .withActive(true)
                .withIsAdmin(false)
                .withCreationDate(USER_CREATION_DATE)
                .withUsername(USER_NAME)
                .withMail(USER_MAIL)
                .withTheme(THEME)
                .build();
    }
}
