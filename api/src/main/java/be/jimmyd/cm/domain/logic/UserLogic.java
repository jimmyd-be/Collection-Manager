package be.jimmyd.cm.domain.logic;

import be.jimmyd.cm.domain.exceptions.UserAlreadyExists;
import be.jimmyd.cm.domain.mappers.UserMapper;
import be.jimmyd.cm.dto.UserLoginDto;
import be.jimmyd.cm.dto.UserRegisterDto;
import be.jimmyd.cm.entities.User;
import be.jimmyd.cm.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserLogic {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserLogic(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(UserRegisterDto userDto) throws UserAlreadyExists {

        final User userCheck = userRepository.findByMail(userDto.getEmail());

        if(userCheck != null) {
            throw new UserAlreadyExists("User with mail " + userDto.getEmail() + " already exists");
        }

        final User user = UserMapper.INSTANCE.registrationToUser(userDto);

        user.setUserPassword(passwordEncoder.encode(userDto.getPassword()));

        if(userRepository.count() == 0) {
            user.setAdmin(true);
        }

        userRepository.save(user);
    }
}
