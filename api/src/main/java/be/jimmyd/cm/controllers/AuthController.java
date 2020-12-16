package be.jimmyd.cm.controllers;

import be.jimmyd.cm.domain.exceptions.UserAlreadyExists;
import be.jimmyd.cm.domain.logic.UserLogic;
import be.jimmyd.cm.dto.UserRegisterDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserLogic userLogic;

    public AuthController(UserLogic userLogic) {
        this.userLogic = userLogic;
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody UserRegisterDto user) throws UserAlreadyExists {

        try {
            userLogic.registerUser(user);
            return ResponseEntity.ok().build();
        } catch (UserAlreadyExists ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }

}
