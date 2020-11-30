package be.jimmyd.cm.config;

import be.jimmyd.cm.dto.UserDetailsImpl;
import be.jimmyd.cm.entities.User;
import be.jimmyd.cm.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {

        User user = userRepository.findByMail(mail);

        if(user != null) {
            return new UserDetailsImpl(user);
        }
        else
        {
            throw new UsernameNotFoundException("Username: " + mail + " does not exist");
        }
    }


}
