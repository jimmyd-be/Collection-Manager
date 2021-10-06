package be.jimmyd.cm.domain.service;

import be.jimmyd.cm.repositories.CollectionTypeRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CollectionTypeService {

    private CollectionTypeRepository collectionTypeRepository;

    public CollectionTypeService(final CollectionTypeRepository collectionTypeRepository) {
        this.collectionTypeRepository = collectionTypeRepository;
    }

    public List<String> getAllTypes() {
        return collectionTypeRepository.getAllTypes().stream().map(type -> type.getType()).collect(Collectors.toList());
    }
}
