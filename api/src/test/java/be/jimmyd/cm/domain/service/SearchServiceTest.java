package be.jimmyd.cm.domain.service;

import be.jimmyd.cm.domain.mappers.ItemMapper;
import be.jimmyd.cm.repositories.CollectionRepository;
import be.jimmyd.cm.repositories.ItemRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private ItemRepository itemRepository;
    @Mock
    private ItemMapper itemMapper;
    @Mock
    private CollectionRepository collectionRepository;
    @InjectMocks
    private SearchService searchService;

    @Disabled
    @Test
    void globalSearch() {
    }
}