package be.jimmyd.cm.controllers;


import be.jimmyd.cm.domain.exceptions.ItemNotExistException;
import be.jimmyd.cm.domain.logic.ItemLogic;
import be.jimmyd.cm.dto.ItemDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public void addItemToCollection(@PathVariable("id") long collectionid, @RequestBody Map<String, String> itemData, UsernamePasswordAuthenticationToken user) {
        itemLogic.addItemToCollection(collectionid, itemData, user.getPrincipal().toString());
    }

    @PatchMapping("/edit/{id}/{collectionId}")
    public void editItem(@PathVariable("id") long itemId, @PathVariable("collectionId") long collectionId,
                                    @RequestBody Map<String, String> itemData, UsernamePasswordAuthenticationToken user) {
        //TODO add logic
        itemLogic.editItem(collectionId, itemId, itemData, user.getPrincipal().toString());
    }

    @DeleteMapping("/{itemId}/collection/{collectionId}")
    public void deleteItem(@PathVariable("itemId") long itemId, @PathVariable("collectionId") long collectionId) {
        //TODO add logic
    }


    @GetMapping("/get/collection/{id}/{page}/{itemsOnPage}")
    public List<ItemDto> getItemFromCollection(@PathVariable("id") long collectionId, @PathVariable("page") int page,
    @PathVariable("itemsOnPage") int itemsOnPage) {
        return itemLogic.getItemsByCollection(collectionId, PageRequest.of(page, itemsOnPage));
    }

    @GetMapping("/get/{id}")
    public ItemDto getItemById(@PathVariable("id") long itemId) throws ItemNotExistException {
        return itemLogic.getById(itemId);
    }

    @GetMapping("/external/{type}")
    public ItemDto searchItemExternally(@PathVariable("type") String type, @RequestParam String search) {
        //TODO add logic

        return null;
    }

    @PostMapping("/external/add/collection/{collectionId}/{source}/{externalId}")
    public void addItemToCollection(@PathVariable("collectionId") long collectionId, @PathVariable("source") String source,
                                    @PathVariable("externalId") String externalId) {
        //TODO add logic
    }
}
