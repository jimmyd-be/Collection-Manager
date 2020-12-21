package be.jimmyd.cm.controllers;

import be.jimmyd.cm.domain.logic.SettingLogic;
import be.jimmyd.cm.domain.logic.UserLogic;
import be.jimmyd.cm.dto.SettingDto;
import be.jimmyd.cm.dto.UserDto;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/settings")
    public List<SettingDto> getAllSettings() {
        return settingLogic.getAllSettings();
    }

    @PatchMapping("/settings")
    public void saveSettings(@RequestBody List<SettingDto> settings) {
        settingLogic.saveSettings(settings);
    }
}
