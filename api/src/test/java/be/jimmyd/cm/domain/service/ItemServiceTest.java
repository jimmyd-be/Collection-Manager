package be.jimmyd.cm.domain.service;

import be.jimmyd.cm.domain.external.ExternalSystem;
import be.jimmyd.cm.domain.mappers.ItemMapper;
import be.jimmyd.cm.repositories.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private FieldRepository fieldRepository;
    @Mock
    private CollectionRepository collectionRepository;
    @Mock
    private ItemDataRepository itemDataRepository;
    @Mock
    private ExternalSystem externalSystemService;
    @Mock
    private ItemMapper itemMapper;
    @InjectMocks
    private ItemService itemService;

    @Disabled
    @Test
    void addItemToCollection() {
    }

    @Disabled
    @Test
    void saveItem() {
    }

    @Disabled
    @Test
    void testAddItemToCollection() {
    }

    @Disabled
    @Test
    void editItem() {
    }

    @Disabled
    @Test
    void getById() {
    }

    @Disabled
    @Test
    void getItemsByCollection() {
    }

    @Disabled
    @Test
    void deleteItemsWithoutCollection() {
    }

    @Disabled
    @Test
    void deleteItemFromCollection() {
    }

    @Disabled
    @Test
    void searchItemExternally() {
    }
}