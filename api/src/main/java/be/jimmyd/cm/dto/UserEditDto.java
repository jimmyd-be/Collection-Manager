package be.jimmyd.cm.dto;

import lombok.Data;

@Data
public class UserEditDto {

    private String password;
    private String newUser;
    private String newMail;
    private String theme;
}
