package be.jimmyd.cm.controllers;

import be.jimmyd.cm.dto.UserDto;
import be.jimmyd.cm.dto.UserEditDto;
import be.jimmyd.cm.dto.UserEditPasswordDto;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("")
    public UserDto getCurrentUser() {
        //TODO return current user;
        return null;
    }

    @DeleteMapping("")
    public void deleteUser(Principal user) {
        //TODO delete user action
    }

    @PatchMapping("/edit")
    public void editUser(@RequestBody UserEditDto userEditDto) {
        //TODO edit user
    }

    @PatchMapping("/edit/password")
    public void editPassword(@RequestBody UserEditPasswordDto userEditPasswordDto) {
        //TODO edit password
    }
}
