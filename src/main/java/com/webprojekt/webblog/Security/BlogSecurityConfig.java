package com.webprojekt.webblog.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static com.webprojekt.webblog.Security.UserRoles.*;
import static org.springframework.security.config.Customizer.withDefaults;

//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// Normelerweise wäre es einfacher mit WebSecurityConfiguratorAdapter zu extenden und die methoden zu überschreiben
//aber es funktioniert nicht bei mir
@Configuration // anotation das sagt, hey du bist die config file für security
@EnableWebSecurity
public class BlogSecurityConfig extends WebSecurityConfiguration implements WebSecurityConfigurer<WebSecurity> {
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public BlogSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void init(WebSecurity builder) throws Exception {

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // Konfiguration der statischen Ressourcen, z. B. CSS-Dateien und Bilder
        web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails proAdmin =User.builder ()
                .username ("")
                .password (passwordEncoder.encode ("Password")) //debug evaluate expretion um, die encoding zu sehen
                .roles (ADMIN.name()).
                build ();
        return new InMemoryUserDetailsManager (
                proAdmin
        );
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeRequests((authorizeRequests) ->
                        authorizeRequests
                                .antMatchers ().
                )
                .formLogin(withDefaults());
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers ("/images/**", "/js/**", "/webjars/**");
    }

}


