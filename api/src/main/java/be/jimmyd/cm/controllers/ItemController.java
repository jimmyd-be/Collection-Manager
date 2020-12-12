package be.jimmyd.cm.controllers;


import be.jimmyd.cm.domain.exceptions.ItemNotExistException;
import be.jimmyd.cm.domain.logic.ItemLogic;
import be.jimmyd.cm.dto.ItemDto;
import be.jimmyd.cm.dto.ItemSearchDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    private final ItemLogic itemLogic;

    public ItemController(final ItemLogic itemLogic) {
        this.itemLogic = itemLogic;
    }

    @PostMapping("/add/collection/{id}")
    public void addItemToCollection(@PathVariable("id") long collectionId, @RequestBody Map<String, String> itemData, UsernamePasswordAuthenticationToken user) {
        itemLogic.addItemToCollection(collectionId, itemData, user.getPrincipal().toString());
    }

    @PatchMapping("/edit/{id}/{collectionId}")
    public void editItem(@PathVariable("id") long itemId, @PathVariable("collectionId") long collectionId,
                         @RequestBody Map<String, String> itemData, UsernamePasswordAuthenticationToken user) {
        itemLogic.editItem(itemId, itemData, user.getPrincipal().toString());
    }

    @DeleteMapping("/{itemId}/collection/{collectionId}")
    public void deleteItem(@PathVariable("itemId") long itemId, @PathVariable("collectionId") long collectionId) {

        itemLogic.deleteItemFromCollection(itemId, collectionId);
    }


    @GetMapping("/get/collection/{id}/{page}/{itemsOnPage}")
    public List<ItemDto> getItemFromCollection(@PathVariable("id") long collectionId, @PathVariable("page") int page,
                                               @PathVariable("itemsOnPage") int itemsOnPage) {
        return itemLogic.getItemsByCollection(collectionId, PageRequest.of(page, itemsOnPage));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ItemDto> getItemById(@PathVariable("id") long itemId) {
        try {
            return ResponseEntity.ok(itemLogic.getById(itemId));
        } catch (ItemNotExistException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/external/{type}")
    public List<ItemSearchDto> searchItemExternally(@PathVariable("type") String type, @RequestParam String search) {

        return itemLogic.searchItemExternally(type, search);
    }

    @PostMapping("/external/add/collection/{collectionId}/{source}/{externalId}")
    public void addItemToCollection(@PathVariable("collectionId") long collectionId, @PathVariable("source") String source,
                                    @PathVariable("externalId") String externalId, UsernamePasswordAuthenticationToken user) {
        itemLogic.addItemToCollection(collectionId, source, externalId, user.getPrincipal().toString());
    }
}
