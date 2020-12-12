package be.jimmyd.cm.controllers;

import be.jimmyd.cm.domain.exceptions.UserPermissionException;
import be.jimmyd.cm.domain.logic.CollectionLogic;
import be.jimmyd.cm.domain.logic.CollectionTypeLogic;
import be.jimmyd.cm.domain.logic.UserCollectionLogic;
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

    private final CollectionLogic collectionLogic;
    private final CollectionTypeLogic collectionTypeLogic;
    private final UserCollectionLogic userCollectionLogic;

    public CollectionController(final CollectionLogic collectionLogic, final CollectionTypeLogic collectionTypeLogic,
                                final UserCollectionLogic userCollectionLogic) {
        this.collectionLogic = collectionLogic;
        this.collectionTypeLogic = collectionTypeLogic;
        this.userCollectionLogic = userCollectionLogic;
    }

    @GetMapping("/user")
    public List<CollectionDto> getByUser(UsernamePasswordAuthenticationToken user) {
        return collectionLogic.getByUser(user.getPrincipal().toString());
    }

    @GetMapping("/types")
    public List<String> getCollectionTypes() {
        return collectionTypeLogic.getAllTypes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollectionDto> getById(@PathVariable("id") long collectionId) {
        try {
            return ResponseEntity.ok(collectionLogic.getById(collectionId));
        } catch (UserPermissionException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/{id}/users")
    public List<UserCollectionDto> getAllCollectionUsers(@PathVariable("id") long collectionId) {
        return userCollectionLogic.getUsersByCollection(collectionId);
    }

    @PatchMapping("/edit")
    public ResponseEntity editCollection(@RequestBody CollectionDto collectionDto) {

        try {
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
            userCollectionLogic.shareCollection(collectionId, collectionShareDto);
            return ResponseEntity.ok().build();
        } catch (UserPermissionException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") long collectionId) {
        try {
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
            userCollectionLogic.deleteUserFromCollection(collectionId, userId);
        } catch (UserPermissionException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok().build();
    }

}
