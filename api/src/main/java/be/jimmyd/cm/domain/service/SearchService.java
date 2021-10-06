package be.jimmyd.cm.domain.service;

import be.jimmyd.cm.domain.mappers.ItemMapper;
import be.jimmyd.cm.dto.ItemDto;
import be.jimmyd.cm.dto.SearchResultDto;
import be.jimmyd.cm.entities.Collection;
import be.jimmyd.cm.entities.Item;
import be.jimmyd.cm.repositories.CollectionRepository;
import be.jimmyd.cm.repositories.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private ItemRepository itemRepository;
    private ItemMapper itemMapper = ItemMapper.INSTANCE;
    private CollectionRepository collectionRepository;

    public SearchService(ItemRepository itemRepository,
                         CollectionRepository collectionRepository) {
        this.itemRepository = itemRepository;
        this.collectionRepository = collectionRepository;
    }

    public List<SearchResultDto> globalSearch(String searchTerm, String userMail) {

        List<SearchResultDto> searchResult = new ArrayList<>();
        List<Item> items = itemRepository.findBySearch(searchTerm, userMail);
        List<Collection> collections = collectionRepository.getByUser(userMail);

        collections.forEach(collection -> {
            List<ItemDto> itemResult = items.parallelStream()
                    .filter(item -> item.getCollections().contains(collection))
                    .map(itemMapper::itemToDto)
                    .collect(Collectors.toList());

            if (!itemResult.isEmpty()) {
                SearchResultDto result = new SearchResultDto();
                result.setCollectionId(collection.getId());
                result.setCollectionName(collection.getName());
                result.setItems(itemResult);

                searchResult.add(result);
            }
        });

        return searchResult;
    }
}
