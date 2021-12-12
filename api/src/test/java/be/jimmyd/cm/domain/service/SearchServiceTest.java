package be.jimmyd.cm.domain.service;

import be.jimmyd.cm.domain.mappers.ItemMapper;
import be.jimmyd.cm.dto.SearchResultDto;
import be.jimmyd.cm.repositories.CollectionRepository;
import be.jimmyd.cm.repositories.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static be.jimmyd.cm.constants.CollectionTestConstants.USER_MAIL;
import static be.jimmyd.cm.constants.CollectionTestConstants.collection;
import static be.jimmyd.cm.constants.ItemDtoTestConstant.itemDto;
import static be.jimmyd.cm.constants.ItemDtoTestConstant.searchResultDto;
import static be.jimmyd.cm.constants.ItemTestConstant.itemWithCollection;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

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

    @Test
    void globalSearch() {
        when(itemRepository.findBySearch("The Matrix", USER_MAIL)).thenReturn(List.of(itemWithCollection()));
        when(collectionRepository.getByUser(USER_MAIL)).thenReturn(List.of(collection()));
        when(itemMapper.map(itemWithCollection())).thenReturn(itemDto());

        List<SearchResultDto> result = searchService.globalSearch("The Matrix", USER_MAIL);

        assertThat(result)
                .containsExactly(searchResultDto());
    }
}