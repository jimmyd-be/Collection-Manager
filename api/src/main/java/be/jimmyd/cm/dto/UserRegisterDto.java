package be.jimmyd.cm.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserRegisterDto {

    @NotNull
    private String fullName;

    @NotNull
    @Email
    private String email;

    @Size(min = 8)
    @NotNull
    private String password;
}
