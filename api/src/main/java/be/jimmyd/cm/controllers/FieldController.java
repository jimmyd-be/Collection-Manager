package be.jimmyd.cm.controllers;

import be.jimmyd.cm.domain.enums.Permission;
import be.jimmyd.cm.domain.exceptions.UserPermissionException;
import be.jimmyd.cm.domain.logic.FieldLogic;
import be.jimmyd.cm.domain.utils.SecurityUtil;
import be.jimmyd.cm.dto.FieldDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<FieldDto>> getByCollection(@PathVariable("id") long id) {

        try {
            securityUtil.hasUserAccessToCollection(id, Permission.READ);

            List<FieldDto> result = new ArrayList<>();

            result.addAll(fieldLogic.getBasicFieldsByCollection(id));
            result.addAll(fieldLogic.getCustomFieldsByCollection(id));

            return ResponseEntity.ok(result);
        } catch (UserPermissionException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

    }

    @GetMapping("/basic/collection/{id}")
    public ResponseEntity<List<FieldDto>> getBasicByCollection(@PathVariable("id") long id) {

        try {
            securityUtil.hasUserAccessToCollection(id, Permission.READ);

            List<FieldDto> result = new ArrayList<>();

            result.addAll(fieldLogic.getBasicFieldsByCollection(id));

            return ResponseEntity.ok(result);
        } catch (UserPermissionException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/custom/collection/{id}")
    public ResponseEntity<List<FieldDto>> getCustomByCollection(@PathVariable("id") long id) {

        try {
            securityUtil.hasUserAccessToCollection(id, Permission.READ);
            List<FieldDto> result = new ArrayList<>();

            result.addAll(fieldLogic.getCustomFieldsByCollection(id));

            return ResponseEntity.ok(result);
        } catch (UserPermissionException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
