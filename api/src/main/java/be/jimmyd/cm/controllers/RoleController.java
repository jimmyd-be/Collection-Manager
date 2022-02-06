package be.jimmyd.cm.controllers;

import be.jimmyd.cm.domain.service.RoleService;
import be.jimmyd.cm.dto.RoleDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/active")
    public List<RoleDto> getActiveRoles() {
        return roleService.getActiveRoles();
    }
}
