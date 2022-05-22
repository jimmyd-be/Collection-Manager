package be.jimmyd.cm.domain.service;

import be.jimmyd.cm.domain.exceptions.OneActiveAdminNeededException;
import be.jimmyd.cm.domain.exceptions.UserPermissionException;
import be.jimmyd.cm.domain.mappers.CollectionMapper;
import be.jimmyd.cm.domain.mappers.FieldMapper;
import be.jimmyd.cm.dto.CollectionDto;
import be.jimmyd.cm.dto.FieldDto;
import be.jimmyd.cm.entities.Collection;
import be.jimmyd.cm.entities.CollectionType;
import be.jimmyd.cm.entities.Field;
import be.jimmyd.cm.entities.User;
import be.jimmyd.cm.repositories.CollectionRepository;
import be.jimmyd.cm.repositories.CollectionTypeRepository;
import be.jimmyd.cm.repositories.FieldRepository;
import be.jimmyd.cm.repositories.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final UserRepository userRepository;
    private final CollectionMapper collectionMapper;
    private final FieldRepository fieldRepository;
    private final UserCollectionService userCollectionService;
    private final CollectionTypeRepository collectionTypeRepository;
    private final FieldMapper fieldMapper;
    private final ItemService itemService;
    private final FieldService fieldService;

    public CollectionService(CollectionRepository collectionRepository,
                             UserRepository userRepository,
                             FieldRepository fieldRepository,
                             UserCollectionService userCollectionService,
                             CollectionTypeRepository collectionTypeRepository,
                             ItemService itemService,
                             FieldService fieldService,
                             CollectionMapper collectionMapper,
                             FieldMapper fieldMapper) {
        this.collectionRepository = collectionRepository;
        this.userRepository = userRepository;
        this.collectionMapper = collectionMapper;
        this.fieldRepository = fieldRepository;
        this.userCollectionService = userCollectionService;
        this.collectionTypeRepository = collectionTypeRepository;
        this.fieldMapper = fieldMapper;
        this.itemService = itemService;
        this.fieldService = fieldService;
    }

    public List<CollectionDto> getByUser(String mail) {
        final User user = userRepository.findByMail(mail);

        final List<Collection> collections = collectionRepository.getByUser(user.getId());

        return collectionMapper.map(collections);
    }

    public Optional<CollectionDto> getById(long collectionId) throws UserPermissionException {

        return collectionRepository.findById(collectionId)
                .map(collectionMapper::map);

    }

    @Transactional
    public void deleteById(long collectionId) throws UserPermissionException {

        collectionRepository.findById(collectionId).ifPresent(collection -> {
            collection.getUserCollections().forEach(userCollection -> {
                try {
                    userCollectionService.deleteUserFromCollection(collectionId, userCollection.getUser().getId());
                } catch (UserPermissionException e) {
                    e.printStackTrace();
                } catch (OneActiveAdminNeededException e) {
                    e.printStackTrace();
                }
            });

            collectionRepository.deleteNative(collection.getId());

            itemService.deleteItemsWithoutCollection();
            fieldService.deleteFieldsWithoutCollection();
        });
    }

    @Transactional
    public void createCollection(CollectionDto collectionDto, String mail) {

        final CollectionType type = collectionTypeRepository.getByName(collectionDto.getType());

        Collection collection = new Collection.Builder()
                .withName(collectionDto.getName())
                .withActive(true)
                .withType(type)
                .withFields(new ArrayList<>())
                .build();

        for (FieldDto dto : collectionDto.getFields()) {
            addFieldToCollection(dto, collection);
        }

        Collection savedCollection = collectionRepository.save(collection);

        userCollectionService.addUserToCollection(mail, "Admin", savedCollection);

    }

    private void addFieldToCollection(FieldDto dto, Collection collection) {
        final Field field = fieldMapper.map(dto);

        switch (field.getType().getType()) {
            case "url":
                field.setWidget("url");
                break;
            case "image":
                field.setWidget("image");
                break;
            case "rate":
                field.setWidget("rate");
                break;
            case "email":
                field.setWidget("email");
                break;
            default:
                field.setWidget("default");
        }

        collection.getFields().add(fieldRepository.save(field));
    }

    public void editCollection(CollectionDto collectionDto) throws UserPermissionException {

        final Optional<Collection> collectionOptional = collectionRepository.findById(collectionDto.getId());

        collectionOptional.ifPresent(collection -> {
            collection.setName(collectionDto.getName());

            //Add new fields to collection
            collectionDto.getFields()
                    .stream()
                    .filter(field -> field.getId() == 0)
                    .forEach(fieldDto -> addFieldToCollection(fieldDto, collection));

            final List<Long> fieldIds = collectionDto.getFields()
                    .stream()
                    .map(FieldDto::getId)
                    .collect(Collectors.toList());

            //Edit existent fields
            collectionDto.getFields()
                    .stream()
                    .filter(fieldDto -> fieldDto.getId() != 0)
                    .forEach(fieldDto -> editCollectionField(collection, fieldDto));

            //Delete deleted fields
            collection.getFields()
                    .stream()
                    .filter(field -> !fieldIds.contains(field.getId()))
                    .forEach(fieldRepository::delete);

            collectionRepository.save(collection);
        });
    }

    private void editCollectionField(Collection collection, FieldDto fieldDto) {
        collection.getFields().stream().filter(field -> field.getId() == fieldDto.getId()).findFirst().ifPresent(field -> {
            field.setName(fieldDto.getName());
            field.setLabel(fieldDto.getLabel());

            field.setPlaceHolder(fieldDto.getPlaceholder());
            field.setRequired(fieldDto.isRequired());
            field.setMultiValues(fieldDto.isMultivalues());
            field.setLabelPosition(fieldDto.getLabelPosition());
            field.setPlace(fieldDto.getPlace());
            field.setFieldOrder(fieldDto.getFieldOrder());
            field.setOtherOptions(fieldDto.getOptions());

            fieldRepository.save(field);
        });
    }

    public void deleteWithoutLink() {
        collectionRepository.getWithoutLink().forEach(col -> {
            try {
                deleteById(col.getId());
            } catch (UserPermissionException e) {
                e.printStackTrace();
            }
        });
    }
}
