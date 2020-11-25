package be.jimmyd.cm.controllers;

import be.jimmyd.cm.dto.CollectionDto;
import be.jimmyd.cm.dto.CollectionShareDto;
import be.jimmyd.cm.dto.CollectionTypeDto;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/collection")
public class CollectionController {

    @GetMapping("/user")
    public List<CollectionDto> getByUser(Principal user) {
        //TODO add action

        return new ArrayList<>();
    }

    @GetMapping("/types")
    public List<CollectionTypeDto> getCollectionTypes() {
        //TODO add action

        return new ArrayList<>();
    }

    @GetMapping("/{id}")
    public CollectionDto getById(@PathVariable("id") long collectionid) {
        //TODO add action

        return null;
    }

    @GetMapping("/{id}/users")
    public List<CollectionDto> getAllCollectionUsers(@PathVariable("id") long collectionid) {
        //TODO add action

        return new ArrayList<>();
    }

    @PatchMapping("/edit")
    public void editCollection(@RequestBody CollectionDto collectionDto) {
        //TODO add action

    }

    @PostMapping("/add")
    public void addCollection(@RequestBody CollectionDto collectionDto) {
        //TODO add action

    }

    @PostMapping("/{id}/share")
    public void share(@PathVariable("id") long collectionId, @RequestBody CollectionShareDto collectionShareDto) {
        //TODO add action
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long collectionId) {
        //TODO add action
    }

    @DeleteMapping("/{id}/user/{userId}")
    public void deleteUserFromCollection(@PathVariable("id") long collectionId, @PathVariable("userId") long userId) {
        //TODo add action
    }

}
