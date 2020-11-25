package be.jimmyd.cm.controllers;

import be.jimmyd.cm.domain.exceptions.UserAlreadyExists;
import be.jimmyd.cm.domain.logic.UserLogic;
import be.jimmyd.cm.dto.UserLoginDto;
import be.jimmyd.cm.dto.UserRegisterDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserLogic userLogic;

    public AuthController(UserLogic userLogic) {
        this.userLogic = userLogic;
    }

    @PostMapping("/register")
    public void registerUser(@RequestBody UserRegisterDto user) throws UserAlreadyExists {

        userLogic.registerUser(user);

    }

}
