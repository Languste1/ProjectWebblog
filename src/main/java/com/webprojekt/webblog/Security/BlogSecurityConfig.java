package com.webprojekt.webblog.Security;

import com.webprojekt.webblog.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.webprojekt.webblog.Security.UserRoles.*;
import static org.springframework.security.config.Customizer.withDefaults;

//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// Normelerweise wäre es einfacher mit WebSecurityConfiguratorAdapter zu extenden und die methoden zu überschreiben
//aber es funktioniert nicht bei mir
@Configuration // anotation das sagt, hey du bist die config file für security
@EnableWebSecurity
@RequiredArgsConstructor
public class BlogSecurityConfig extends WebSecurityConfiguration implements WebSecurityConfigurer<WebSecurity> {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtAthFilter JwtAthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Override
    public void init(WebSecurity builder) throws Exception {

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // Konfiguration der statischen Ressourcen, z. B. CSS-Dateien und Bilder
        web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
// DAO userdetails und encoding
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider ();
        authProvider.setUserDetailsService (userDetailsService ());
        authProvider.setPasswordEncoder (passwordEncoder);
        return authProvider;
    }

    @Bean
    protected UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername (username)
                .orElseThrow (() -> new UsernameNotFoundException ("User not found"));
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf ()
                .disable ()
                .authorizeHttpRequests ()
                .requestMatchers ("/WebBlog/v1/auth**")
                .permitAll ()
                .anyRequest ()
                .authenticated ()
                .and ()
                .formLogin (
                        form -> form.defaultSuccessUrl ("/account/login")
                                .loginPage ("login")
                                .failureUrl ("login?error=true")
                )
                .sessionManagement ()
                .sessionCreationPolicy (SessionCreationPolicy.STATELESS)
                .and ()
                .authenticationProvider (authenticationProvider)
                .addFilterBefore (JwtAthFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager ();
    }

}


