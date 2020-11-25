package be.jimmyd.cm.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {

    public long id;
    public String mail;
    public String username;
    public boolean isAdmin;
    public LocalDate creationDate;
    public String theme;
}
