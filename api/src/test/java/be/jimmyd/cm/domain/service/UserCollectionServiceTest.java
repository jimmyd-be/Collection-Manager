package be.jimmyd.cm.domain.service;

import be.jimmyd.cm.domain.exceptions.OneActiveAdminNeededException;
import be.jimmyd.cm.domain.exceptions.UserPermissionException;
import be.jimmyd.cm.domain.mappers.CollectionUserMapper;
import be.jimmyd.cm.dto.CollectionShareDto;
import be.jimmyd.cm.dto.UserCollectionDto;
import be.jimmyd.cm.entities.UserCollection;
import be.jimmyd.cm.repositories.CollectionRepository;
import be.jimmyd.cm.repositories.CollectionUserRepository;
import be.jimmyd.cm.repositories.RoleRepository;
import be.jimmyd.cm.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static be.jimmyd.cm.constants.CollectionDtoTestConstants.userCollectionDto;
import static be.jimmyd.cm.constants.CollectionTestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserCollectionServiceTest {

    @Mock
    private CollectionUserMapper collectionUserMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private CollectionUserRepository collectionUserRepository;
    @Mock
    private CollectionRepository collectionRepository;
    @InjectMocks
    private UserCollectionService userCollectionService;

    @Test
    void getUsersByCollection() {
        when(collectionUserRepository.getByCollectionId(COLLECTION_ID)).thenReturn(List.of(userCollection()));
        when(collectionUserMapper.map(List.of(userCollection()))).thenReturn(List.of(userCollectionDto()));

        List<UserCollectionDto> result = userCollectionService.getUsersByCollection(COLLECTION_ID);

        assertThat(result).containsExactly(userCollectionDto());
    }

    @Test
    void deleteUserFromCollection() throws UserPermissionException, OneActiveAdminNeededException {
        when(collectionUserRepository.getByCollectionAndUser(COLLECTION_ID, USER_ID)).thenReturn(userCollection());
        when(collectionUserRepository.getByCollectionId(COLLECTION_ID)).thenReturn(userCollections());

        userCollectionService.deleteUserFromCollection(COLLECTION_ID, USER_ID, false);

        verify(collectionUserRepository, times(1)).delete(userCollection());
    }

    @Test
    void deleteLastAdminUserFromCollection() throws UserPermissionException, OneActiveAdminNeededException {

        List<UserCollection> userCollections = new ArrayList<>();
        userCollections.add(userCollection());

        when(collectionUserRepository.getByCollectionAndUser(COLLECTION_ID, USER_ID)).thenReturn(userCollection());
        when(collectionUserRepository.getByCollectionId(COLLECTION_ID)).thenReturn(userCollections);

        assertThrows(OneActiveAdminNeededException.class, () ->
                userCollectionService.deleteUserFromCollection(COLLECTION_ID, USER_ID, false)
        );

        verify(collectionUserRepository, times(0)).delete(userCollection());
    }

    @Test
    void shareCollection() throws UserPermissionException {
        when(userRepository.findByMailOrUserName(USER_NAME)).thenReturn(user());
        when(roleRepository.getByName(ROLE_NAME)).thenReturn(role());
        when(collectionRepository.findById(COLLECTION_ID)).thenReturn(Optional.of(collection()));

        userCollectionService.shareCollection(COLLECTION_ID, new CollectionShareDto.Builder().withRole(ROLE_NAME).withUserName(USER_NAME).build());

        verify(collectionUserRepository, times(1)).save(userCollectionWithCollection());
    }

    @Test
    void shareCollection_edit() throws UserPermissionException {
        when(userRepository.findByMailOrUserName(USER_NAME)).thenReturn(user());
        when(roleRepository.getByName(ROLE_NAME)).thenReturn(role());
        when(collectionUserRepository.getByCollectionAndUser(COLLECTION_ID, USER_ID)).thenReturn(userCollection());

        userCollectionService.shareCollection(COLLECTION_ID, new CollectionShareDto.Builder().withRole(ROLE_NAME).withUserName(USER_NAME).build());

        verify(collectionUserRepository, times(1)).save(userCollection());
    }

    @Test
    void addUserToCollection() {
        when(userRepository.findByMail(USER_MAIL)).thenReturn(user());
        when(roleRepository.getByName(ROLE_NAME)).thenReturn(role());

        userCollectionService.addUserToCollection(USER_MAIL, ROLE_NAME, collection());

        verify(collectionUserRepository, times(1)).save(userCollectionWithCollection());
    }
}
