package be.jimmyd.cm.controllers;

import be.jimmyd.cm.domain.logic.CollectionLogic;
import be.jimmyd.cm.domain.logic.CollectionTypeLogic;
import be.jimmyd.cm.domain.logic.UserCollectionLogic;
import be.jimmyd.cm.domain.logic.UserLogic;
import be.jimmyd.cm.dto.CollectionDto;
import be.jimmyd.cm.dto.CollectionShareDto;
import be.jimmyd.cm.dto.CollectionTypeDto;
import be.jimmyd.cm.dto.UserCollectionDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public CollectionDto getById(@PathVariable("id") long collectionId) {
        return collectionLogic.getById(collectionId);
    }

    @GetMapping("/{id}/users")
    public List<UserCollectionDto> getAllCollectionUsers(@PathVariable("id") long collectionId) {
        return userCollectionLogic.getUsersByCollection(collectionId);
    }

    @PatchMapping("/edit")
    public void editCollection(@RequestBody CollectionDto collectionDto) {
        collectionLogic.editCollection(collectionDto);

    }

    @PostMapping("/add")
    public void addCollection(@RequestBody CollectionDto collectionDto, UsernamePasswordAuthenticationToken user) {
        collectionLogic.createCollection(collectionDto, user.getPrincipal().toString());

    }

    @PostMapping("/{id}/share")
    public void share(@PathVariable("id") long collectionId, @RequestBody CollectionShareDto collectionShareDto) {
        userCollectionLogic.shareCollection(collectionId, collectionShareDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long collectionId) {
        collectionLogic.deleteById(collectionId);
    }

    @DeleteMapping("/{id}/user/{userId}")
    public void deleteUserFromCollection(@PathVariable("id") long collectionId, @PathVariable("userId") long userId) {
        userCollectionLogic.deleteUserFromCollection(collectionId, userId);
    }

}
