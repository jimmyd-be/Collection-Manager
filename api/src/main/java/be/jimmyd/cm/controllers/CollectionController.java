package be.jimmyd.cm.controllers;

import be.jimmyd.cm.domain.enums.Permission;
import be.jimmyd.cm.domain.exceptions.OneActiveAdminNeededException;
import be.jimmyd.cm.domain.exceptions.UserPermissionException;
import be.jimmyd.cm.domain.service.CollectionService;
import be.jimmyd.cm.domain.service.CollectionTypeService;
import be.jimmyd.cm.domain.service.UserCollectionService;
import be.jimmyd.cm.domain.utils.SecurityUtil;
import be.jimmyd.cm.dto.CollectionDto;
import be.jimmyd.cm.dto.CollectionShareDto;
import be.jimmyd.cm.dto.UserCollectionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/collection")
public class CollectionController {

    private final CollectionService collectionLogic;
    private final CollectionTypeService collectionTypeService;
    private final UserCollectionService userCollectionService;
    private final SecurityUtil securityUtil;

    public CollectionController(final CollectionService collectionLogic, final CollectionTypeService collectionTypeService,
                                final UserCollectionService userCollectionService, final SecurityUtil securityUtil) {
        this.collectionLogic = collectionLogic;
        this.collectionTypeService = collectionTypeService;
        this.userCollectionService = userCollectionService;
        this.securityUtil = securityUtil;
    }

    @GetMapping("/user")
    public List<CollectionDto> getByUser(UsernamePasswordAuthenticationToken user) {
        return collectionLogic.getByUser(user.getPrincipal().toString());
    }

    @GetMapping("/types")
    public List<String> getCollectionTypes() {
        return collectionTypeService.getAllTypes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollectionDto> getById(@PathVariable("id") long collectionId) {
        try {
            securityUtil.hasUserAccessToCollection(collectionId, Permission.READ);
            return ResponseEntity.ok(collectionLogic.getById(collectionId).orElseThrow());
        } catch (UserPermissionException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/{id}/users")
    public List<UserCollectionDto> getAllCollectionUsers(@PathVariable("id") long collectionId) {
        return userCollectionService.getUsersByCollection(collectionId);
    }

    @PatchMapping("/edit")
    public ResponseEntity editCollection(@RequestBody CollectionDto collectionDto) {

        try {
            securityUtil.hasUserAccessToCollection(collectionDto.getId(), Permission.READ);
            collectionLogic.editCollection(collectionDto);
            return ResponseEntity.ok().build();
        } catch (UserPermissionException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping("/add")
    public void addCollection(@Valid @RequestBody CollectionDto collectionDto, UsernamePasswordAuthenticationToken token) {
        collectionLogic.createCollection(collectionDto, token.getName().toString());

    }

    @PostMapping("/{id}/share")
    public ResponseEntity share(@PathVariable("id") long collectionId, @RequestBody CollectionShareDto collectionShareDto) {
        try {
            securityUtil.hasUserAccessToCollection(collectionId, Permission.ADMIN);
            userCollectionService.shareCollection(collectionId, collectionShareDto);
            return ResponseEntity.ok().build();
        } catch (UserPermissionException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") long collectionId) {
        try {
            securityUtil.hasUserAccessToCollection(collectionId, Permission.ADMIN);
            collectionLogic.deleteById(collectionId);
            return ResponseEntity.ok().build();
        } catch (UserPermissionException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @DeleteMapping("/{id}/user/{userId}")
    public ResponseEntity deleteUserFromCollection(@PathVariable("id") long collectionId, @PathVariable("userId") long userId) {

        try {
            securityUtil.hasUserAccessToCollection(collectionId, Permission.ADMIN);
            userCollectionService.deleteUserFromCollection(collectionId, userId);
        } catch (UserPermissionException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (OneActiveAdminNeededException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok().build();
    }

}
