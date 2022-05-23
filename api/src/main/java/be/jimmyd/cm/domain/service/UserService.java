package be.jimmyd.cm.domain.service;

import be.jimmyd.cm.domain.exceptions.*;
import be.jimmyd.cm.domain.mappers.UserMapper;
import be.jimmyd.cm.dto.UserDto;
import be.jimmyd.cm.dto.UserEditDto;
import be.jimmyd.cm.dto.UserEditPasswordDto;
import be.jimmyd.cm.dto.UserRegisterDto;
import be.jimmyd.cm.entities.InvalidToken;
import be.jimmyd.cm.entities.User;
import be.jimmyd.cm.repositories.InvalidTokenRepository;
import be.jimmyd.cm.repositories.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserCollectionService userCollectionService;
    private final CollectionService collectionLogic;
    private final UserMapper userMapper;
    private final InvalidTokenRepository invalidTokenRepository;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       UserCollectionService userCollectionService,
                       CollectionService collectionLogic,
                       UserMapper userMapper,
                       InvalidTokenRepository invalidTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userCollectionService = userCollectionService;
        this.collectionLogic = collectionLogic;
        this.userMapper = userMapper;
        this.invalidTokenRepository = invalidTokenRepository;
    }

    public void registerUser(UserRegisterDto userDto) throws UserAlreadyExists, PasswordIncorrectException {

        final User userCheck = userRepository.findByMail(userDto.getEmail());

        if (userCheck != null) {
            throw new UserAlreadyExists("User with mail " + userDto.getEmail() + " already exists");
        } else if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            throw new PasswordIncorrectException("Password is not equals to confirm password");
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
                userCollectionService.deleteUserFromCollection(uc.getCollection().getId(), user.getId(), false);
            } catch (UserPermissionException e) {
                e.printStackTrace();
            } catch (OneActiveAdminNeededException e) {
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

    public void logout(UsernamePasswordAuthenticationToken user, String token) {
        String bearerToken = token.replace("Bearer ", "");

        DecodedJWT decode = JWT.decode(bearerToken);

        if (decode.getSubject().equals(user.getName())) {
            invalidTokenRepository.save(new InvalidToken.Builder()
                    .withInvalidFrom(LocalDateTime.now())
                    .withToken(bearerToken)
                    .build());
        }

        deleteOldTokens();
    }

    private void deleteOldTokens() {
        List<String> tokens = invalidTokenRepository.findOldTokens(LocalDateTime.now().minus(7, ChronoUnit.DAYS))
                .stream()
                .map(InvalidToken::getToken)
                .collect(Collectors.toList());

        invalidTokenRepository.deleteAllById(tokens);
    }
}
