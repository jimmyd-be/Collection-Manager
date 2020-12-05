package be.jimmyd.cm.domain.logic;

import be.jimmyd.cm.domain.exceptions.PasswordIncorrectException;
import be.jimmyd.cm.domain.exceptions.UserAlreadyExists;
import be.jimmyd.cm.domain.exceptions.UserPermissionException;
import be.jimmyd.cm.domain.mappers.UserMapper;
import be.jimmyd.cm.dto.*;
import be.jimmyd.cm.entities.User;
import be.jimmyd.cm.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserLogic {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserCollectionLogic userCollectionLogic;
    private final CollectionLogic collectionLogic;

    public UserLogic(UserRepository userRepository, PasswordEncoder passwordEncoder,final UserCollectionLogic userCollectionLogic,
                     final CollectionLogic collectionLogic) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userCollectionLogic = userCollectionLogic;
        this.collectionLogic = collectionLogic;
    }

    public void registerUser(UserRegisterDto userDto) throws UserAlreadyExists {

        final User userCheck = userRepository.findByMail(userDto.getEmail());

        if (userCheck != null) {
            throw new UserAlreadyExists("User with mail " + userDto.getEmail() + " already exists");
        }

        final User user = UserMapper.INSTANCE.registrationToUser(userDto);

        user.setUserPassword(passwordEncoder.encode(userDto.getPassword()));

        if (userRepository.count() == 0) {
            user.setIsAdmin(true);
        }

        userRepository.save(user);
    }

    public UserDto getUserByMail(String mail) {
        final User user = userRepository.findByMail(mail);

        return UserMapper.INSTANCE.userToDto(user);
    }

    @Transactional
    public void deleteUser(String mail) {
        final User user = userRepository.findByMail(mail);

        user.getUserCollections().forEach(uc -> {
            try {
                userCollectionLogic.deleteUserFromCollection(uc.getCollection().getId(), user.getId());
            } catch (UserPermissionException e) {
                e.printStackTrace();
            }
        });

        userRepository.deleteNative(user.getId());

        collectionLogic.deleteWithoutLink();
    }

    public void editUser(UserEditDto userEditDto, String currentMail) throws PasswordIncorrectException {
        final User user = userRepository.findByMail(currentMail);

        if (passwordEncoder.matches(userEditDto.getPassword(), user.getUserPassword())) {
            user.setUsername(userEditDto.getNewUser());
            user.setMail(userEditDto.getNewMail());
            user.setTheme(userEditDto.getTheme());

            userRepository.save(user);
        } else {
            throw new PasswordIncorrectException("User " + currentMail + " cannot be update because password is incorrect");
        }

    }

    public void editPassword(UserEditPasswordDto userEditPasswordDto, String mail) throws PasswordIncorrectException {
        final User user = userRepository.findByMail(mail);

        if (!passwordEncoder.matches(userEditPasswordDto.getCurrentPassword(), user.getUserPassword())) {
            throw new PasswordIncorrectException("Password of user " + mail + " cannot be update because password is incorrect");
        } else if (userEditPasswordDto.getPassword().equals(userEditPasswordDto.getPasswordRepeat())) {
            throw new PasswordIncorrectException("Password of user " + mail + " cannot be update because password and repeat password are not the same");
        } else {
            user.setUserPassword(passwordEncoder.encode(userEditPasswordDto.getPassword()));

            userRepository.save(user);
        }
    }
}
