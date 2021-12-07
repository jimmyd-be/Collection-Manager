package be.jimmyd.cm.domain.service;

import be.jimmyd.cm.domain.exceptions.UserPermissionException;
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
public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final UserRepository userRepository;
    private final CollectionMapper collectionMapper;
    private final FieldRepository fieldRepository;
    private final UserCollectionService userCollectionService;
    private final CollectionTypeRepository collectionTypeRepository;
    private final FieldMapper fieldMapper;
    private final FieldTypeRepository fieldTypeRepository;
    private final ItemService itemService;
    private final FieldService fieldService;

    public CollectionService(CollectionRepository collectionRepository, UserRepository userRepository, FieldRepository fieldRepository,
                             final UserCollectionService userCollectionService, final CollectionTypeRepository collectionTypeRepository,
                             final FieldTypeRepository fieldTypeRepository, final ItemService itemService, final FieldService fieldService) {
        this.collectionRepository = collectionRepository;
        this.userRepository = userRepository;
        this.collectionMapper = CollectionMapper.INSTANCE;
        this.fieldRepository = fieldRepository;
        this.userCollectionService = userCollectionService;
        this.collectionTypeRepository = collectionTypeRepository;
        this.fieldMapper = FieldMapper.INSTANCE;
        this.fieldTypeRepository = fieldTypeRepository;
        this.itemService = itemService;
        this.fieldService = fieldService;
    }

    public List<CollectionDto> getByUser(String mail) {
        final User user = userRepository.findByMail(mail);

        final List<Collection> collections = collectionRepository.getByUser(user.getId());

        return collectionMapper.collectionToDto(collections);
    }

    public Optional<CollectionDto> getById(long collectionId) throws UserPermissionException {

        return collectionRepository.findById(collectionId)
                .map(collectionMapper::collectionToDto);

    }

    @Transactional
    public void deleteById(long collectionId) throws UserPermissionException {

        collectionRepository.findById(collectionId).ifPresent(collection -> {
            collection.getUserCollections().forEach(userCollection -> {
                try {
                    userCollectionService.deleteUserFromCollection(collectionId, userCollection.getUser().getId());
                } catch (UserPermissionException e) {
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

        Collection collection = new Collection();
        collection.setName(collectionDto.getName());
        collection.setActive(true);
        collection.setType(type);

        collection.setFields(new ArrayList<>());

        for (FieldDto dto : collectionDto.getFields()) {
            addFieldToCollection(dto, collection);
        }

        Collection savedCollection = collectionRepository.save(collection);

        userCollectionService.addUserToCollection(mail, "Admin", savedCollection);

    }

    private void addFieldToCollection(FieldDto dto, Collection collection) {
        final Field field = fieldMapper.dtoToField(dto);
        final FieldType fieldType = fieldTypeRepository.findByName(dto.getType());
        field.setType(fieldType);
        field.setActive(true);

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
            collectionDto.getFields().stream().filter(field -> field.getId() == 0).forEach(fieldDto -> {
                addFieldToCollection(fieldDto, collection);
            });

            final List<Long> fieldIds = collectionDto.getFields().stream().map(fields -> fields.getId()).collect(Collectors.toList());

            //Edit existent fields
            collectionDto.getFields()
                    .stream()
                    .filter(field -> field.getId() != 0)
                    .forEach(fieldDto -> {

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
                    });

            //Delete deleted fields
            collection.getFields()
                    .stream()
                    .filter(field -> fieldIds.contains(field.getId()))
                    .filter(field -> field.getCollectiontype() != null)
                    .forEach(field -> fieldRepository.delete(field));

            collectionRepository.save(collection);
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
