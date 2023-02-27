package com.webprojekt.webblog.Security;

import com.webprojekt.webblog.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BlogConfig {
    private final UserRepository userRepository;
@Autowired
    public BlogConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Ein Benutzerdetailsdienst, der den Benutzer anhand des Benutzernamens aus der Datenbank sucht
    @Bean
    protected UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername (username)
                .orElseThrow (() -> new UsernameNotFoundException ("User not found"));
    }

    // Ein DAO-Authentifizierungsanbieter, der den Benutzerdetailsdienst und den Passwort-Encoder verwendet
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider ();
        authProvider.setUserDetailsService (userDetailsService ());
        authProvider.setPasswordEncoder (passwordEncoder());
        return authProvider;
    }

    // Ein Passwort-Encoder, der das Passwort des Benutzers verschlüsselt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder (10);
    }

    // Ein Authentifizierungs-Manager, der von Spring Security verwendet wird
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager ();
    }


}
