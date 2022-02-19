package be.jimmyd.cm.domain.service;

import be.jimmyd.cm.entities.CollectionType;
import be.jimmyd.cm.repositories.CollectionTypeRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CollectionTypeService {

    private final CollectionTypeRepository collectionTypeRepository;

    public CollectionTypeService(CollectionTypeRepository collectionTypeRepository) {
        this.collectionTypeRepository = collectionTypeRepository;
    }

    public List<String> getAllTypes() {
        return collectionTypeRepository.getAllTypes()
                .stream()
                .map(CollectionType::getType)
                .sorted()
                .collect(Collectors.toList());
    }
}
