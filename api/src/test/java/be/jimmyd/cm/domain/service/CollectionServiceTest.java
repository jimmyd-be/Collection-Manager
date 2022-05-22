package be.jimmyd.cm.domain.service;

import be.jimmyd.cm.domain.exceptions.OneActiveAdminNeededException;
import be.jimmyd.cm.domain.exceptions.UserPermissionException;
import be.jimmyd.cm.domain.mappers.CollectionMapper;
import be.jimmyd.cm.domain.mappers.FieldMapper;
import be.jimmyd.cm.dto.CollectionDto;
import be.jimmyd.cm.entities.Collection;
import be.jimmyd.cm.repositories.CollectionRepository;
import be.jimmyd.cm.repositories.CollectionTypeRepository;
import be.jimmyd.cm.repositories.FieldRepository;
import be.jimmyd.cm.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static be.jimmyd.cm.constants.CollectionDtoTestConstants.collectionDto;
import static be.jimmyd.cm.constants.CollectionDtoTestConstants.fieldDto;
import static be.jimmyd.cm.constants.CollectionTestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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

    @Captor
    ArgumentCaptor<Collection> collectionCaptor;

    @Test
    void getByUser() {
        when(userRepository.findByMail(USER_MAIL)).thenReturn(user());
        when(collectionRepository.getByUser(USER_ID)).thenReturn(List.of(collection()));
        when(collectionMapper.map(List.of(collection()))).thenReturn(List.of(collectionDto()));

        List<CollectionDto> result = collectionService.getByUser(USER_MAIL);

        assertThat(result).containsExactly(collectionDto());
    }

    @Test
    void getById() throws UserPermissionException {
        when(collectionRepository.findById(COLLECTION_ID)).thenReturn(Optional.of(collection()));
        when(collectionMapper.map(collection())).thenReturn(collectionDto());

        Optional<CollectionDto> result = collectionService.getById(COLLECTION_ID);

        assertThat(result.get()).isEqualTo(collectionDto());
    }

    @Test
    void deleteById() throws UserPermissionException, OneActiveAdminNeededException {
        when(collectionRepository.findById(COLLECTION_ID)).thenReturn(Optional.of(collection()));

        collectionService.deleteById(COLLECTION_ID);

        verify(userCollectionService, times(1)).deleteUserFromCollection(COLLECTION_ID, USER_ID);
        verify(collectionRepository, times(1)).deleteNative(COLLECTION_ID);
        verify(itemService, times(1)).deleteItemsWithoutCollection();
        verify(fieldService, times(1)).deleteFieldsWithoutCollection();

    }

    @Test
    void createCollection() {
        when(collectionTypeRepository.getByName(COLLECTION_TYPE_NAME)).thenReturn(collectionType());
        when(collectionRepository.save(collectionWithFields(0))).thenReturn(collectionWithFields(COLLECTION_ID));
        when(fieldMapper.map(fieldDto())).thenReturn(field());
        doNothing().when(userCollectionService).addUserToCollection(USER_MAIL, "Admin", collectionWithFields(COLLECTION_ID));

        collectionService.createCollection(collectionDto(), USER_MAIL);

        verify(fieldRepository, times(1)).save(field());
    }

    @Test
    void editCollection() throws UserPermissionException {
        when(collectionRepository.findById(COLLECTION_ID)).thenReturn(Optional.of(collection()));

        CollectionDto dto = new CollectionDto.Builder()
                .withId(COLLECTION_ID)
                .withName("New Collection 2")
                .withFields(List.of(fieldDto(FIELD_ID, "New Field 2"), fieldDto(11l, "Field 3")))
                .build();

        collectionService.editCollection(dto);

        verify(fieldRepository, times(0)).delete(any());
        verify(collectionRepository, times(1)).save(collectionCaptor.capture());
        verify(fieldRepository, times(2)).save(any());

        assertThat(collectionCaptor.getValue().getName()).isEqualTo("New Collection 2");
        assertThat(collectionCaptor.getValue().getType()).isEqualTo(collectionType());
    }

    @Test
    void editCollection_deleteField() throws UserPermissionException {
        when(collectionRepository.findById(COLLECTION_ID)).thenReturn(Optional.of(collectionWithFields(COLLECTION_ID)));

        CollectionDto dto = new CollectionDto.Builder()
                .withId(COLLECTION_ID)
                .withName("New Collection 2")
                .withFields(new ArrayList<>())
                .build();

        Collection collection = new Collection.Builder()
                .withActive(false)
                .withName(COLLECTION_NAME)
                .withId(COLLECTION_ID)
                .withType(collectionType())
                .withUserCollections(List.of(userCollection()))
                .build();
        ;

        collectionService.editCollection(dto);

        verify(fieldRepository, times(1)).delete(field());
        verify(collectionRepository, times(1)).save(collection);
        verify(fieldRepository, times(0)).save(any());
    }

    @Test
    void deleteWithoutLink() throws UserPermissionException, OneActiveAdminNeededException {
        when(collectionRepository.findById(COLLECTION_ID)).thenReturn(Optional.of(collection()));
        when(collectionRepository.getWithoutLink()).thenReturn(List.of(collection()));

        collectionService.deleteWithoutLink();

        verify(userCollectionService, times(1)).deleteUserFromCollection(COLLECTION_ID, USER_ID);
        verify(collectionRepository, times(1)).deleteNative(COLLECTION_ID);
        verify(itemService, times(1)).deleteItemsWithoutCollection();
        verify(fieldService, times(1)).deleteFieldsWithoutCollection();
    }
}