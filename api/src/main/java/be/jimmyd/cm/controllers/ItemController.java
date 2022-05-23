package be.jimmyd.cm.controllers;


import be.jimmyd.cm.domain.enums.Permission;
import be.jimmyd.cm.domain.exceptions.ItemNotExistException;
import be.jimmyd.cm.domain.exceptions.UserPermissionException;
import be.jimmyd.cm.domain.service.ItemService;
import be.jimmyd.cm.domain.utils.SecurityUtil;
import be.jimmyd.cm.dto.ItemDto;
import be.jimmyd.cm.dto.ItemSearchDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    private final ItemService itemService;
    private final SecurityUtil securityUtil;

    public ItemController(final ItemService itemService,
                          final SecurityUtil securityUtil) {
        this.itemService = itemService;
        this.securityUtil = securityUtil;
    }

    @PostMapping("/add/collection/{id}")
    public ResponseEntity addItemToCollection(@PathVariable("id") long collectionId, @RequestBody Map<String, String> itemData, UsernamePasswordAuthenticationToken user) {

        try {
            securityUtil.hasUserAccessToCollection(collectionId, Permission.EDIT);
            itemService.addItemToCollection(collectionId, itemData, user.getPrincipal().toString());
            return ResponseEntity.ok().build();
        } catch (UserPermissionException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

    }

    @PatchMapping("/edit/{id}/{collectionId}")
    public ResponseEntity editItem(@PathVariable("id") long itemId, @PathVariable("collectionId") long collectionId,
                                   @RequestBody Map<String, String> itemData, UsernamePasswordAuthenticationToken user) {

        try {
            securityUtil.hasUserAccessToCollection(collectionId, Permission.EDIT);
            itemService.editItem(itemId, itemData, user.getPrincipal().toString());
            return ResponseEntity.ok().build();
        } catch (UserPermissionException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @DeleteMapping("/{itemId}/collection/{collectionId}")
    public ResponseEntity deleteItem(@PathVariable("itemId") long itemId, @PathVariable("collectionId") long collectionId) {
        try {
            securityUtil.hasUserAccessToCollection(collectionId, Permission.EDIT);
            itemService.deleteItemFromCollection(itemId, collectionId);
            return ResponseEntity.ok().build();
        } catch (UserPermissionException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }


    @GetMapping("/get/collection/{id}/{page}/{itemsOnPage}")
    public ResponseEntity<List<ItemDto>> getItemFromCollection(@PathVariable("id") long collectionId, @PathVariable("page") int page,
                                                               @PathVariable("itemsOnPage") int itemsOnPage,
                                                               @RequestParam(value = "query", required = false) String query) {
        try {
            securityUtil.hasUserAccessToCollection(collectionId, Permission.READ);
            return ResponseEntity.ok(itemService.getItemsByCollection(collectionId, PageRequest.of(page, itemsOnPage), query));
        } catch (UserPermissionException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/count/collection/{id}")
    public ResponseEntity<Integer> countItemFromCollection(@PathVariable("id") long collectionId,
                                                           @RequestParam(value = "query", required = false) String query) {
        try {
            securityUtil.hasUserAccessToCollection(collectionId, Permission.READ);
            return ResponseEntity.ok(itemService.countItemsByCollection(collectionId, query));
        } catch (UserPermissionException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ItemDto> getItemById(@PathVariable("id") long itemId) {

        try {
            securityUtil.hasUserAccessToItem(itemId, Permission.READ);

            return ResponseEntity.ok(itemService.getById(itemId));
        } catch (ItemNotExistException ex) {
            return ResponseEntity.notFound().build();
        } catch (UserPermissionException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/external/{type}")
    public List<ItemSearchDto> searchItemExternally(@PathVariable("type") String type, @RequestParam String search) {

        return itemService.searchItemExternally(type, search);
    }

    @PostMapping("/external/add/collection/{collectionId}/{source}/{externalId}")
    public ResponseEntity addItemToCollection(@PathVariable("collectionId") long collectionId, @PathVariable("source") String source,
                                              @PathVariable("externalId") String externalId, UsernamePasswordAuthenticationToken user) {
        try {
            securityUtil.hasUserAccessToCollection(collectionId, Permission.EDIT);
            itemService.addItemToCollection(collectionId, source, externalId, user.getPrincipal().toString());
            return ResponseEntity.ok().build();
        } catch (UserPermissionException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
