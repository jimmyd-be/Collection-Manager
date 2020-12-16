package be.jimmyd.cm.controllers;

import be.jimmyd.cm.domain.exceptions.PasswordIncorrectException;
import be.jimmyd.cm.domain.exceptions.UserAlreadyExists;
import be.jimmyd.cm.domain.logic.UserLogic;
import be.jimmyd.cm.dto.UserDto;
import be.jimmyd.cm.dto.UserEditDto;
import be.jimmyd.cm.dto.UserEditPasswordDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserLogic userLogic;

    public UserController(UserLogic userLogic) {
        this.userLogic = userLogic;
    }

    @GetMapping("")
    public UserDto getCurrentUser(UsernamePasswordAuthenticationToken user) {
        return userLogic.getUserByMail(user.getPrincipal().toString());
    }

    @DeleteMapping("")
    public void deleteUser(UsernamePasswordAuthenticationToken user) {
        userLogic.deleteUser(user.getPrincipal().toString());
    }

    @PatchMapping("/edit")
    public void editUser(@Valid @RequestBody UserEditDto userEditDto, UsernamePasswordAuthenticationToken user) throws Exception {
        userLogic.editUser(userEditDto, user.getPrincipal().toString());
    }

    @PatchMapping("/edit/password")
    public void editPassword(@Valid @RequestBody UserEditPasswordDto userEditPasswordDto, UsernamePasswordAuthenticationToken user) throws PasswordIncorrectException {
        userLogic.editPassword(userEditPasswordDto, user.getPrincipal().toString());
    }

    @PostMapping("/logout")
    public void logoutUser(Principal user) throws UserAlreadyExists {

        //TODO invalidate current token
    }
}
