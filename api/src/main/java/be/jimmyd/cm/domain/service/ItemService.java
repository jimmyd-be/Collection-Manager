package be.jimmyd.cm.domain.service;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class ItemService {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final FieldRepository fieldRepository;
    private final CollectionRepository collectionRepository;
    private final ItemDataRepository itemDataRepository;
    private final ExternalSystem externalSystemService;
    private final ItemMapper itemMapper;

    public ItemService(final UserRepository userRepository, final ItemRepository itemRepository, final FieldRepository fieldRepository,
                       final CollectionRepository collectionRepository, final ItemDataRepository itemDataRepository, final ExternalSystem externalSystemService,
                       ItemMapper itemMapper) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.fieldRepository = fieldRepository;
        this.collectionRepository = collectionRepository;
        this.itemDataRepository = itemDataRepository;
        this.externalSystemService = externalSystemService;
        this.itemMapper = itemMapper;
    }

    @Transactional
    public long addItemToCollection(long collectionId, Map<String, String> itemData, String userMail) {

        AtomicLong itemId = new AtomicLong();
        final User user = userRepository.findByMail(userMail);
        collectionRepository.findById(collectionId).ifPresent(collection -> {

            final List<Field> fields = fieldRepository.findBasicFieldByCollectionId(collectionId);
            fields.addAll(fieldRepository.findCustomFieldByCollectionId(collectionId));

            long titleFieldId = fields.stream().filter(n -> n.getName().equalsIgnoreCase("Title")).map(n -> n.getId()).findFirst().get();
            long coverFieldId = fields.stream().filter(n -> n.getName().equalsIgnoreCase("Cover")).map(n -> n.getId()).findFirst().get();

            String title = itemData.remove(titleFieldId + "_0");
            String cover = itemData.remove(coverFieldId + "_0");

            Item newItem = new Item.Builder()
                    .withActive(true)
                    .withName(title)
                    .withAuthor(user)
                    .withCreationDate(LocalDateTime.now())
                    .withImage(cover)
                    .withLastModified(LocalDateTime.now())
                    .build();


            final Item finalNewItem = itemRepository.save(newItem);

            itemId.set(finalNewItem.getId());

            List<Itemdata> itemdataList = new ArrayList<>();

            AtomicReference<String> tempLabel = new AtomicReference<>("");

            itemData.forEach((key, value) -> {

                long fieldId = getFieldIDFromKey(key);

                fields.stream().filter(field -> field.getId() == fieldId).findFirst().ifPresent(field -> {

                            String newValue = value;

                            if (key.endsWith("_label")) {
                                tempLabel.set(value);
                            } else if (field.getType().getType().equals("url")) {

                                String label = tempLabel.getAndSet("");
                                newValue = value + (label.equals("") ? "" : "||" + label);
                            }

                            if (!key.endsWith("_label")) {
                                Itemdata itemdata = new Itemdata.Builder()
                                        .withField(field)
                                        .withFieldValue(newValue)
                                        .withItem(finalNewItem)
                                        .build();
                                //TODO add validation on field level (required fields, ...)

                                itemdataList.add(itemdata);
                            }
                        }
                );
            });

            itemDataRepository.saveAll(itemdataList);


            finalNewItem.getCollections().add(collection);
            itemRepository.save(finalNewItem);
            //collection.getItems().add(newItem);
            //collectionRepository.save(collection);
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
        final Optional<Item> itemOptional = itemRepository.findById(itemId);

        if (itemOptional.isPresent()) {
            return itemMapper.map(itemOptional.get());
        } else {
            throw new ItemNotExistException("Item with id " + itemId + " does not exist");
        }
    }

    public List<ItemDto> getItemsByCollection(long collectionId, PageRequest page, String query) {
        final List<Item> items = new ArrayList<>();

        if (query == null || query.isBlank()) {
            items.addAll(itemRepository.getByCollectionId(collectionId, page));
        } else {
            items.addAll(itemRepository.getByCollectionIdAndQuery(collectionId, query.toUpperCase(), page));
        }

        return itemMapper.map(items);
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
