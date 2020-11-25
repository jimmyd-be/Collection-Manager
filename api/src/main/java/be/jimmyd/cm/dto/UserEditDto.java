package be.jimmyd.cm.dto;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserEditDto {

    @Size(min=8)
    @NotNull
    private String password;

    @NotNull
    private String newUser;

    @NotNull
    @Email
    private String newMail;

    @NotNull
    private String theme;
}
