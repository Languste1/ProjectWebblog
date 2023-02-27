package com.webprojekt.webblog.BussinesLayer;

import com.webprojekt.webblog.DAO.User;
import com.webprojekt.webblog.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;
@Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<User> user = userRepository.findByUsername (username);
        if (user == null ){
            throw new UsernameNotFoundException (username);
        }
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.get ().getUsername ())
                .password (user.get ().getPassword ())
                .authorities ("USER").build ();

        return userDetails;
    }
}
