package be.jimmyd.cm.controllers;

import be.jimmyd.cm.domain.logic.RoleLogic;
import be.jimmyd.cm.dto.RoleDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    private RoleLogic roleLogic;

    public RoleController(RoleLogic roleLogic) {
        this.roleLogic = roleLogic;
    }

    @GetMapping("/active")
    public List<RoleDto> getActiveRoles() {
        return roleLogic.getActiveRoles();
    }
}
