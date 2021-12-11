package be.jimmyd.cm.domain.service;

import be.jimmyd.cm.domain.mappers.CollectionMapper;
import be.jimmyd.cm.domain.mappers.FieldMapper;
import be.jimmyd.cm.repositories.CollectionRepository;
import be.jimmyd.cm.repositories.CollectionTypeRepository;
import be.jimmyd.cm.repositories.FieldRepository;
import be.jimmyd.cm.repositories.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CollectionServiceTest {

    @Mock
    private CollectionRepository collectionRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CollectionMapper collectionMapper;
    @Mock
    private FieldRepository fieldRepository;
    @Mock
    private UserCollectionService userCollectionService;
    @Mock
    private CollectionTypeRepository collectionTypeRepository;
    @Mock
    private FieldMapper fieldMapper;
    @Mock
    private ItemService itemService;
    @Mock
    private FieldService fieldService;

    @InjectMocks
    private CollectionService collectionService;

    @Disabled
    @Test
    void getByUser() {
    }

    @Disabled
    @Test
    void getById() {
    }

    @Disabled
    @Test
    void deleteById() {
    }

    @Disabled
    @Test
    void createCollection() {
    }

    @Disabled
    @Test
    void editCollection() {
    }

    @Disabled
    @Test
    void deleteWithoutLink() {
    }
}