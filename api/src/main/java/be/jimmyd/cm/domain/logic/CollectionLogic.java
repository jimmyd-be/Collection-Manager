package be.jimmyd.cm.domain.logic;

import be.jimmyd.cm.domain.mappers.CollectionMapper;
import be.jimmyd.cm.dto.CollectionDto;
import be.jimmyd.cm.entities.Collection;
import be.jimmyd.cm.entities.User;
import be.jimmyd.cm.repositories.CollectionRepository;
import be.jimmyd.cm.repositories.FieldRepository;
import be.jimmyd.cm.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CollectionLogic {

    private final CollectionRepository collectionRepository;
    private final UserRepository userRepository;
    private final CollectionMapper collectionMapper;
    private final FieldRepository fieldRepository;

    public CollectionLogic(CollectionRepository collectionRepository,  UserRepository userRepository, FieldRepository fieldRepository) {
        this.collectionRepository = collectionRepository;
        this.userRepository = userRepository;
        this.collectionMapper = CollectionMapper.INSTANCE;
        this.fieldRepository = fieldRepository;
    }

    public List<CollectionDto> getByUser(String mail) {
        final User user = userRepository.findByMail(mail);

        final List<Collection> collections = collectionRepository.getByUser(user.getId());

        return collectionMapper.collectionToDto(collections);
    }

    public CollectionDto getById(long collectionId) {

        //TODO add check for user permission
        final Optional<Collection> collection = collectionRepository.findById(collectionId);

        if(collection.isPresent()) {
            return collectionMapper.collectionToDto(collection.get());
        }

        return null;

    }

    public void deleteById(long collectionId) {
        collectionRepository.findById(collectionId).ifPresent(collection -> {
            collectionRepository.delete(collection);
        });
    }
}
