package be.jimmyd.cm.domain.logic;

import be.jimmyd.cm.domain.enums.Permission;
import be.jimmyd.cm.domain.exceptions.UserPermissionException;
import be.jimmyd.cm.domain.mappers.CollectionMapper;
import be.jimmyd.cm.domain.mappers.FieldMapper;
import be.jimmyd.cm.domain.utils.SecurityUtil;
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
    private final ItemLogic itemLogic;
    private final FieldLogic fieldLogic;
    private final SecurityUtil securityUtil;

    public CollectionLogic(CollectionRepository collectionRepository, UserRepository userRepository, FieldRepository fieldRepository,
                           final UserCollectionLogic userCollectionLogic, final CollectionTypeRepository collectionTypeRepository,
                           final FieldTypeRepository fieldTypeRepository, final ItemLogic itemLogic, final FieldLogic fieldLogic, final SecurityUtil securityUtil) {
        this.collectionRepository = collectionRepository;
        this.userRepository = userRepository;
        this.collectionMapper = CollectionMapper.INSTANCE;
        this.fieldRepository = fieldRepository;
        this.userCollectionLogic = userCollectionLogic;
        this.collectionTypeRepository = collectionTypeRepository;
        this.fieldMapper = FieldMapper.INSTANCE;
        this.fieldTypeRepository = fieldTypeRepository;
        this.itemLogic = itemLogic;
        this.fieldLogic = fieldLogic;
        this.securityUtil = securityUtil;
    }

    public List<CollectionDto> getByUser(String mail) {
        final User user = userRepository.findByMail(mail);

        final List<Collection> collections = collectionRepository.getByUser(user.getId());

        return collectionMapper.collectionToDto(collections);
    }

    public CollectionDto getById(long collectionId, String userMail) throws UserPermissionException {

        //TODO add check for user permission
        securityUtil.hasUserAccessToCollection(userMail, collectionId, Permission.READ);


        final Optional<Collection> collection = collectionRepository.findById(collectionId);

        if (collection.isPresent()) {
            return collectionMapper.collectionToDto(collection.get());
        }

        return null;

    }

    @Transactional
    public void deleteById(long collectionId) throws UserPermissionException {
        collectionRepository.findById(collectionId).ifPresent(collection -> {
            collection.getUserCollections().forEach(userCollection -> {
                try {
                    userCollectionLogic.deleteUserFromCollection(collectionId, userCollection.getUser().getId());
                } catch (UserPermissionException e) {
                    e.printStackTrace();
                }
            });

            collectionRepository.deleteNative(collection.getId());

            itemLogic.deleteItemsWithoutCollection();
            fieldLogic.deleteFieldsWithoutCollection();
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

        for (FieldDto dto : collectionDto.getFields()) {
            final Field field = fieldMapper.dtoToField(dto);
            final FieldType fieldType = fieldTypeRepository.findByName(dto.getType());
            field.setType(fieldType);

            collection.getFields().add(fieldRepository.save(field));
        }

        Collection savedCollection = collectionRepository.save(collection);

        userCollectionLogic.addUserToCollection(mail, "Owner", savedCollection);

    }

    public void editCollection(CollectionDto collectionDto, String userMail) throws UserPermissionException {

        //TODO check permissions of user to collection
        final Optional<Collection> collectionOptional = collectionRepository.findById(collectionDto.getId());

        collectionOptional.ifPresent(collection -> {
            collection.setName(collectionDto.getName());

            //Add new fields to collection
            collectionDto.getFields().stream().filter(field -> field.getId() == 0).forEach(fieldDto -> {
                Field field = fieldMapper.dtoToField(fieldDto);
                final FieldType fieldType = fieldTypeRepository.findByName(fieldDto.getType());
                field.setType(fieldType);

                field = fieldRepository.save(field);
                collection.getFields().add(field);
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
