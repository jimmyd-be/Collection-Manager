package be.jimmyd.cm.controllers;

import be.jimmyd.cm.dto.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        //TODO return all users
        return new ArrayList<>();
    }
}
