package be.jimmyd.cm.controllers;

import be.jimmyd.cm.domain.exceptions.OneActiveAdminNeededException;
import be.jimmyd.cm.domain.exceptions.UserNotExistsException;
import be.jimmyd.cm.domain.logic.SettingLogic;
import be.jimmyd.cm.domain.logic.UserLogic;
import be.jimmyd.cm.dto.SettingDto;
import be.jimmyd.cm.dto.UserDto;
import be.jimmyd.cm.dto.UserEditDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserLogic userLogic;
    private final SettingLogic settingLogic;

    public AdminController(final UserLogic userLogic, final SettingLogic settingLogic) {
        this.userLogic = userLogic;
        this.settingLogic = settingLogic;
    }

    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        return userLogic.getAllUsers();
    }

    @PatchMapping("/user/disable/{userId}")
    public ResponseEntity disableUser(@PathVariable("userId") long userId) {
        try {
            userLogic.editUser(userId, false);
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
            userLogic.editUser(userId, true);
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
            userLogic.changeAdmin(userId);
            return ResponseEntity.ok().build();
        } catch (UserNotExistsException e) {
            return ResponseEntity.notFound().build();
        } catch (OneActiveAdminNeededException e) {
            return ResponseEntity.status(FORBIDDEN).build();
        }
    }

    @GetMapping("/settings")
    public List<SettingDto> getAllSettings() {
        return settingLogic.getAllSettings();
    }

    @PatchMapping("/settings")
    public void saveSettings(@RequestBody List<SettingDto> settings) {
        settingLogic.saveSettings(settings);
    }
}
