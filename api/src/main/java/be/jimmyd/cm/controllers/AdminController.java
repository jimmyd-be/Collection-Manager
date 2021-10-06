package be.jimmyd.cm.controllers;

import be.jimmyd.cm.domain.exceptions.OneActiveAdminNeededException;
import be.jimmyd.cm.domain.exceptions.UserNotExistsException;
import be.jimmyd.cm.domain.service.SettingService;
import be.jimmyd.cm.domain.service.UserService;
import be.jimmyd.cm.dto.SettingDto;
import be.jimmyd.cm.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;
    private final SettingService settingService;

    public AdminController(final UserService userService, final SettingService settingService) {
        this.userService = userService;
        this.settingService = settingService;
    }

    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PatchMapping("/user/disable/{userId}")
    public ResponseEntity disableUser(@PathVariable("userId") long userId) {
        try {
            userService.editUser(userId, false);
            return ResponseEntity.ok().build();
        } catch (UserNotExistsException e) {
            return ResponseEntity.notFound().build();
        } catch (OneActiveAdminNeededException e) {
            return ResponseEntity.status(FORBIDDEN).build();
        }
    }

    @PatchMapping("/user/enable/{userId}")
    public ResponseEntity enableUser(@PathVariable("userId") long userId) {
        try {
            userService.editUser(userId, true);
            return ResponseEntity.ok().build();
        } catch (UserNotExistsException e) {
            return ResponseEntity.notFound().build();
        } catch (OneActiveAdminNeededException e) {
            return ResponseEntity.status(FORBIDDEN).build();
        }
    }

    @PatchMapping("/user/set/admin/{userId}")
    public ResponseEntity changeAdmin(@PathVariable("userId") long userId) {
        try {
            userService.changeAdmin(userId);
            return ResponseEntity.ok().build();
        } catch (UserNotExistsException e) {
            return ResponseEntity.notFound().build();
        } catch (OneActiveAdminNeededException e) {
            return ResponseEntity.status(FORBIDDEN).build();
        }
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity deleteUser(@PathVariable("userId") long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok().build();
        } catch (OneActiveAdminNeededException e) {
            return ResponseEntity.status(FORBIDDEN).build();
        } catch (UserNotExistsException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/settings")
    public List<SettingDto> getAllSettings() {
        return settingService.getAllSettings();
    }

    @PatchMapping("/settings")
    public void saveSettings(@RequestBody List<SettingDto> settings) {
        settingService.saveSettings(settings);
    }
}
