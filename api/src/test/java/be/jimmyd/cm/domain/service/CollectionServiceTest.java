package be.jimmyd.cm.domain.service;

import be.jimmyd.cm.domain.exceptions.UserPermissionException;
import be.jimmyd.cm.domain.mappers.CollectionMapper;
import be.jimmyd.cm.domain.mappers.FieldMapper;
import be.jimmyd.cm.dto.CollectionDto;
import be.jimmyd.cm.repositories.CollectionRepository;
import be.jimmyd.cm.repositories.CollectionTypeRepository;
import be.jimmyd.cm.repositories.FieldRepository;
import be.jimmyd.cm.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static be.jimmyd.cm.constants.CollectionDtoTestConstants.collectionDto;
import static be.jimmyd.cm.constants.CollectionTestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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