package be.jimmyd.cm.domain.service;

import be.jimmyd.cm.domain.mappers.CollectionUserMapper;
import be.jimmyd.cm.repositories.CollectionRepository;
import be.jimmyd.cm.repositories.CollectionUserRepository;
import be.jimmyd.cm.repositories.RoleRepository;
import be.jimmyd.cm.repositories.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

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

    @Disabled
    @Test
    void getUsersByCollection() {
    }

    @Disabled
    @Test
    void deleteUserFromCollection() {
    }

    @Disabled
    @Test
    void shareCollection() {
    }

    @Disabled
    @Test
    void addUserToCollection() {
    }
}
