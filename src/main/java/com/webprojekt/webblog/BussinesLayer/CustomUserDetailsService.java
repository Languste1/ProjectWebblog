package com.webprojekt.webblog.BussinesLayer;

import com.webprojekt.webblog.DAO.UserEntity;
import com.webprojekt.webblog.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserEntity user = userRepository.findByUsername (username);
        if (user == null ){
            throw new UsernameNotFoundException (username);
        }
        UserDetails userDetails = User.withUsername(user.getUsername ())
                .password (user.getPassword ())
                .authorities ("USER").build ();

        return userDetails;
    }
}
