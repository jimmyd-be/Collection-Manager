package be.jimmyd.cm.domain.logic;

import be.jimmyd.cm.repositories.CollectionTypeRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CollectionTypeLogic {

    private CollectionTypeRepository collectionTypeRepository;

    public CollectionTypeLogic(final CollectionTypeRepository collectionTypeRepository) {
        this.collectionTypeRepository = collectionTypeRepository;
    }

    public List<String> getAllTypes() {
        return collectionTypeRepository.getAllTypes().stream().map(type -> type.getType()).collect(Collectors.toList());
    }
}
