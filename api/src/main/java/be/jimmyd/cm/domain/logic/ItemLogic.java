package be.jimmyd.cm.domain.logic;

import be.jimmyd.cm.domain.exceptions.ItemNotExistException;
import be.jimmyd.cm.domain.external.ExternalSystem;
import be.jimmyd.cm.domain.mappers.ItemMapper;
import be.jimmyd.cm.dto.ItemDto;
import be.jimmyd.cm.dto.ItemSearchDto;
import be.jimmyd.cm.entities.*;
import be.jimmyd.cm.repositories.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class ItemLogic {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final FieldRepository fieldRepository;
    private final CollectionRepository collectionRepository;
    private final ItemDataRepository itemDataRepository;
    private final ExternalSystem externalSystemService;

    public ItemLogic(final UserRepository userRepository, final ItemRepository itemRepository, final FieldRepository fieldRepository,
                     final CollectionRepository collectionRepository, final ItemDataRepository itemDataRepository, final ExternalSystem externalSystemService) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.fieldRepository = fieldRepository;
        this.collectionRepository = collectionRepository;
        this.itemDataRepository = itemDataRepository;
        this.externalSystemService = externalSystemService;
    }

    @Transactional
    public long addItemToCollection(long collectionId, Map<String, String> itemData, String userMail) {

        AtomicLong itemId = new AtomicLong();
        final User user = userRepository.findByMail(userMail);
        collectionRepository.findById(collectionId).ifPresent(collection -> {

            //TODo check user permission on collection
            //TODO split this method
            final List<Field> fields = fieldRepository.findBasicFieldByCollectionId(collectionId);
            fields.addAll(fieldRepository.findCustomFieldByCollectionId(collectionId));

            long titleFieldId = fields.stream().filter(n -> n.getName().equalsIgnoreCase("Title")).map(n -> n.getId()).findFirst().get();
            long coverFieldId = fields.stream().filter(n -> n.getName().equalsIgnoreCase("Cover")).map(n -> n.getId()).findFirst().get();

            String title = itemData.remove(titleFieldId + "_0");
            String cover = itemData.remove(coverFieldId + "_0");

            Item newItem = new Item();
            newItem.setActive(true);
            newItem.setName(title);
            newItem.setAuthor(user);
            newItem.setCreationDate(LocalDateTime.now());
            newItem.setImage(cover);
            newItem.setLastModified(LocalDateTime.now());

            final Item finalNewItem = itemRepository.save(newItem);

            itemId.set(finalNewItem.getId());
            itemData.forEach((key, value) -> {

                long fieldId = getFieldIDFromKey(key);

                fields.stream().filter(field -> field.getId() == fieldId).findFirst().ifPresent(field -> {
                            Itemdata itemdata = new Itemdata();
                            itemdata.setField(field);
                            itemdata.setFieldValue(value);
                            itemdata.setItem(finalNewItem);
                            //TODO add validation on field level (required fields, ...)

                            itemDataRepository.save(itemdata);
                        }
                );
            });

            collection.getItems().add(newItem);
            collectionRepository.save(collection);
        });
        return itemId.get();
    }

    @Transactional
    public long addItemToCollection(long collectionId, String source, String itemId, String userId) {

        final List<Field> basicFields = fieldRepository.findBasicFieldByCollectionId(collectionId);

        final Map<String, String> itemData = externalSystemService.getItemById(source, itemId, basicFields);

        return addItemToCollection(collectionId, itemData, userId);
    }

    private long getFieldIDFromKey(String key) {
        return Long.parseLong(key.substring(0, key.indexOf("_")));
    }

    @Transactional
    public void editItem(long itemId, Map<String, String> itemData, String userMail) {
        //TODo check user permission

        itemRepository.findById(itemId).ifPresent(item -> {

            final List<Collection> collections = item.getCollections();

            collections.forEach(collection -> itemRepository.deleteItemFromCollection(item.getId(), collection.getId()));

            itemDataRepository.deleteByItemId(item.getId());
            itemRepository.delete(item);

            long newItemId = addItemToCollection(collections.get(0).getId(), itemData, userMail);

            itemRepository.findById(newItemId).ifPresent(newItem -> {
                for (int i = 1; i < collections.size(); i++) {
                    newItem.getCollections().add(collections.get(i));
                }
                itemRepository.save(newItem);
            });

        });
    }

    public ItemDto getById(long itemId) throws ItemNotExistException {
        //TODo check user permission

        final Optional<Item> itemOptional = itemRepository.findById(itemId);

        if (itemOptional.isPresent()) {
            return ItemMapper.INSTANCE.itemToDto(itemOptional.get());
        } else {
            throw new ItemNotExistException("Item with id " + itemId + " does not exist");
        }
    }

    public List<ItemDto> getItemsByCollection(long collectionId, PageRequest page) {
        //TODo check user permission

        final List<Item> items = itemRepository.getByCollectionId(collectionId, page);

        return ItemMapper.INSTANCE.itemToDto(items);
    }

    @Transactional
    public void deleteItemsWithoutCollection() {
        List<Item> items = itemRepository.findItemsWithoutCollection();

        items.forEach(item -> {
            itemDataRepository.deleteByItemId(item.getId());
            itemRepository.delete(item);
        });
    }

    public void deleteItemFromCollection(long itemId, long collectionId) {
        //TODO check user permission

        collectionRepository.findById(collectionId).ifPresent(collection -> {
            collection.getItems().stream().filter(n -> n.getId() == itemId).findFirst().ifPresent(field -> collection.getItems().remove(field));

            collectionRepository.save(collection);
        });

        deleteItemsWithoutCollection();
    }

    public List<ItemSearchDto> searchItemExternally(final String type, final String search) {
        return externalSystemService.searchItemsByType(type, search);
    }
}
