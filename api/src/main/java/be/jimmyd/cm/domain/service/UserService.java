package be.jimmyd.cm.domain.service;

import be.jimmyd.cm.domain.exceptions.*;
import be.jimmyd.cm.domain.mappers.UserMapper;
import be.jimmyd.cm.dto.UserDto;
import be.jimmyd.cm.dto.UserEditDto;
import be.jimmyd.cm.dto.UserEditPasswordDto;
import be.jimmyd.cm.dto.UserRegisterDto;
import be.jimmyd.cm.entities.User;
import be.jimmyd.cm.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserCollectionService userCollectionService;
    private final CollectionService collectionLogic;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, final UserCollectionService userCollectionService,
                       final CollectionService collectionLogic, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userCollectionService = userCollectionService;
        this.collectionLogic = collectionLogic;
        this.userMapper = userMapper;
    }

    public void registerUser(UserRegisterDto userDto) throws UserAlreadyExists {

        final User userCheck = userRepository.findByMail(userDto.getEmail());

        if (userCheck != null) {
            throw new UserAlreadyExists("User with mail " + userDto.getEmail() + " already exists");
        }

        final User user = userMapper.map(userDto);

        user.setUserPassword(passwordEncoder.encode(userDto.getPassword()));

        if (userRepository.count() == 0) {
            user.setAdmin(true);
        }

        userRepository.save(user);
    }

    public UserDto getUserByMail(String mail) {
        final User user = userRepository.findByMail(mail);

        return userMapper.map(user);
    }

    @Transactional
    public void deleteUser(User user) {
        user.getUserCollections().forEach(uc -> {
            try {
                userCollectionService.deleteUserFromCollection(uc.getCollection().getId(), user.getId());
            } catch (UserPermissionException e) {
                e.printStackTrace();
            }
        });

        userRepository.deleteNative(user.getId());

        collectionLogic.deleteWithoutLink();
    }

    @Transactional
    public void deleteUser(String mail) throws OneActiveAdminNeededException {
        final User user = userRepository.findByMail(mail);
        checkIfAdminStillActive(user.getId());
        deleteUser(user);
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
        } else if (!userEditPasswordDto.getPassword().equals(userEditPasswordDto.getPasswordRepeat())) {
            throw new PasswordIncorrectException("Password of user " + mail + " cannot be update because password and repeat password are not the same");
        } else {
            user.setUserPassword(passwordEncoder.encode(userEditPasswordDto.getPassword()));

            userRepository.save(user);
        }
    }

    public List<UserDto> getAllUsers() {

        final List<User> users = userRepository.findAll();

        return userMapper.map(users);
    }

    public void editUser(long userId, boolean active) throws UserNotExistsException, OneActiveAdminNeededException {

        if (!active) {
            checkIfAdminStillActive(userId);
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotExistsException());
        user.setActive(active);
        userRepository.save(user);
    }

    private void checkIfAdminStillActive(long userId) throws OneActiveAdminNeededException {
        List<User> allAdmins = userRepository.findAllAdmins();

        allAdmins.removeIf(u -> u.getId() == userId);

        if (allAdmins.isEmpty()) {
            throw new OneActiveAdminNeededException();
        }
    }

    public void changeAdmin(long userId, boolean isAdmin) throws UserNotExistsException, OneActiveAdminNeededException {

        checkIfAdminStillActive(userId);

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotExistsException());

        user.setAdmin(isAdmin);
        userRepository.save(user);

    }

    @Transactional
    public void deleteUser(long userId) throws OneActiveAdminNeededException, UserNotExistsException {
        checkIfAdminStillActive(userId);

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotExistsException());
        deleteUser(user);
    }
}
