package be.jimmyd.cm.controllers;

import be.jimmyd.cm.domain.exceptions.OneActiveAdminNeededException;
import be.jimmyd.cm.domain.exceptions.PasswordIncorrectException;
import be.jimmyd.cm.domain.exceptions.UserAlreadyExists;
import be.jimmyd.cm.domain.service.UserService;
import be.jimmyd.cm.dto.UserDto;
import be.jimmyd.cm.dto.UserEditDto;
import be.jimmyd.cm.dto.UserEditPasswordDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public UserDto getCurrentUser(UsernamePasswordAuthenticationToken user) {
        return userService.getUserByMail(user.getPrincipal().toString());
    }

    @DeleteMapping("")
    public ResponseEntity deleteUser(UsernamePasswordAuthenticationToken user) {
        try {
            userService.deleteUser(user.getPrincipal().toString());
            return ResponseEntity.ok().build();
        } catch (OneActiveAdminNeededException e) {
            return ResponseEntity.status(FORBIDDEN).build();
        }
    }

    @PatchMapping("/edit")
    public void editUser(@Valid @RequestBody UserEditDto userEditDto, UsernamePasswordAuthenticationToken user) throws Exception {
        userService.editUser(userEditDto, user.getPrincipal().toString());
    }

    @PatchMapping("/edit/password")
    public void editPassword(@Valid @RequestBody UserEditPasswordDto userEditPasswordDto, UsernamePasswordAuthenticationToken user) throws PasswordIncorrectException {
        userService.editPassword(userEditPasswordDto, user.getPrincipal().toString());
    }

    @PostMapping("/logout")
    public void logoutUser(Principal user) throws UserAlreadyExists {

        //TODO invalidate current token
    }
}
