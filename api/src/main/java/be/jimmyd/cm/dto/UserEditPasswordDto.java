package be.jimmyd.cm.dto;

import lombok.Data;

@Data
public class UserEditPasswordDto {

    private String currentpassword;
    private String password;
    private String passwordRepeat;
}
