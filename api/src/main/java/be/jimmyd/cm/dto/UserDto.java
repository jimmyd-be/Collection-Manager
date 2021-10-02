package be.jimmyd.cm.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UserDto {

    private long id;
    private String mail;
    private String username;
    private boolean isAdmin;
    private LocalDate creationDate;
    private String theme;
    private boolean active;
}
