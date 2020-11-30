package be.jimmyd.cm.domain.logic;

import be.jimmyd.cm.domain.mappers.CollectionMapper;
import be.jimmyd.cm.domain.mappers.FieldMapper;
import be.jimmyd.cm.dto.CollectionDto;
import be.jimmyd.cm.dto.FieldDto;
import be.jimmyd.cm.entities.*;
import be.jimmyd.cm.repositories.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CollectionLogic {

    private final CollectionRepository collectionRepository;
    private final UserRepository userRepository;
    private final CollectionMapper collectionMapper;
    private final FieldRepository fieldRepository;
    private final UserCollectionLogic userCollectionLogic;
    private final CollectionTypeRepository collectionTypeRepository;
    private final FieldMapper fieldMapper;
    private final FieldTypeRepository fieldTypeRepository;

    public CollectionLogic(CollectionRepository collectionRepository,  UserRepository userRepository, FieldRepository fieldRepository,
                           final UserCollectionLogic userCollectionLogic, final CollectionTypeRepository collectionTypeRepository,
                           final FieldTypeRepository fieldTypeRepository) {
        this.collectionRepository = collectionRepository;
        this.userRepository = userRepository;
        this.collectionMapper = CollectionMapper.INSTANCE;
        this.fieldRepository = fieldRepository;
        this.userCollectionLogic = userCollectionLogic;
        this.collectionTypeRepository = collectionTypeRepository;
        this.fieldMapper = FieldMapper.INSTANCE;
        this.fieldTypeRepository = fieldTypeRepository;
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

    @Transactional
    public void createCollection(CollectionDto collectionDto, String mail) {

        final CollectionType type = collectionTypeRepository.getByName(collectionDto.type);

        Collection collection = new Collection();
        collection.setName(collectionDto.getName());
        collection.setActive(true);
        collection.setType(type);

        collection.setFields(new ArrayList<>());

        for(FieldDto dto : collectionDto.getFields()) {
            final Field field = fieldMapper.dtoToField(dto);
            final FieldType fieldType = fieldTypeRepository.findByName(dto.getType());
            field.setType(fieldType);

            collection.getFields().add(fieldRepository.save(field));
        }

        Collection savedCollection = collectionRepository.save(collection);

        userCollectionLogic.addUserToCollection(mail, "Owner", savedCollection);

    }

    public void editCollection(CollectionDto collectionDto) {

        //TODO check permissions of user to collection
        final Optional<Collection> collectionOptional = collectionRepository.findById(collectionDto.getId());

        collectionOptional.ifPresent(collection -> {
            collection.setName(collectionDto.getName());

            //Add new fields to collection
            collectionDto.getFields().stream().filter(field -> field.getId() == 0).forEach(fieldDto -> {
                final Field field = fieldMapper.dtoToField(fieldDto);
                final FieldType fieldType = fieldTypeRepository.findByName(fieldDto.getType());
                field.setType(fieldType);

                collection.getFields().add(field);
            });

            final List<Long> fieldIds = collectionDto.getFields().stream().map(fields -> fields.getId()).collect(Collectors.toList());

            //Edit existent fields
            collectionDto.getFields()
                    .stream()
                    .filter(field -> field.getId() != 0)
                    .forEach(fieldDto -> {
            //TODO check what can be changed in a field of a collection
            });

            //Delete deleted fields
            collection.getFields()
                    .stream()
                    .filter(field -> fieldIds.contains(field.getId()))
                    .filter(field -> field.getCollectiontype() != null)
                    .forEach(field -> fieldRepository.delete(field));

        });


    }
}
