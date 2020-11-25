package be.jimmyd.cm.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserEditPasswordDto {

    @Size(min=8)
    @NotNull
    private String currentpassword;

    @Size(min=8)
    @NotNull
    private String password;

    @Size(min=8)
    @NotNull
    private String passwordRepeat;
}
