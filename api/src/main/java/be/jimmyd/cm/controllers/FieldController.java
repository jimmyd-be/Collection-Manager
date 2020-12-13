package be.jimmyd.cm.controllers;

import be.jimmyd.cm.domain.exceptions.UserPermissionException;
import be.jimmyd.cm.domain.logic.FieldLogic;
import be.jimmyd.cm.domain.utils.SecurityUtil;
import be.jimmyd.cm.dto.FieldDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/field")
public class FieldController {

    private final SecurityUtil securityUtil;
    private final FieldLogic fieldLogic;

    public FieldController(SecurityUtil securityUtil, FieldLogic fieldLogic) {
        this.securityUtil = securityUtil;
        this.fieldLogic = fieldLogic;
    }

    @GetMapping("/collection/{id}")
    public List<FieldDto> getByCollection(@PathVariable("id") long id) throws UserPermissionException {
        List<FieldDto> result = new ArrayList<>();

        if (securityUtil.hasUserReadAccessToCollection(id)) {
            result.addAll(fieldLogic.getBasicFieldsByCollection(id));
            result.addAll(fieldLogic.getCustomFieldsByCollection(id));
        }
        return result;
    }

    @GetMapping("/basic/collection/{id}")
    public List<FieldDto> getBasicByCollection(@PathVariable("id") long id) throws UserPermissionException {

        List<FieldDto> result = new ArrayList<>();

        if (securityUtil.hasUserReadAccessToCollection(id)) {
            result.addAll(fieldLogic.getBasicFieldsByCollection(id));
        }

        return result;
    }

    @GetMapping("/custom/collection/{id}")
    public List<FieldDto> getCustomByCollection(@PathVariable("id") long id) throws UserPermissionException {
        List<FieldDto> result = new ArrayList<>();

        if (securityUtil.hasUserReadAccessToCollection(id)) {
            result.addAll(fieldLogic.getCustomFieldsByCollection(id));
        }

        return result;
    }
}
