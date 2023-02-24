package com.webprojekt.webblog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// Normelerweise wäre es einfacher mit WebSecurityConfiguratorAdapter zu extenden und die methoden zu überschreiben
//aber es funktioniert nicht bei mir
@Configuration // anotation das sagt, hey du bist die config file für security
@EnableWebSecurity
public class BlogSecurityConfig extends WebSecurityConfiguration {
private final PasswordEncoder passwordEncoder;
@Autowired
    public BlogSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
        protected UserDetailsService userDetailsService() {
            UserDetails proAdmin =User.builder ()
                    .username ("")
                    .password (passwordEncoder.encode ("Password")) //debug evaluate expretion um, die encoding zu sehen
                    .roles ("ADMIN").
                    build ();
            return new InMemoryUserDetailsManager (
                    proAdmin
            );
        }


        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

            http.authorizeRequests().antMatchers("/login").permitAll()
                    .antMatchers("/users/**", "/settings/**").hasAuthority("Admin")
                    .hasAnyAuthority("Admin", "Editor", "Salesperson")
                    .hasAnyAuthority("Admin", "Editor", "Salesperson", "Shipper")
                    .anyRequest().authenticated()
                    .and().formLogin()
                    .loginPage("/login")
                    .usernameParameter("email")
                    .permitAll()
                    .and()
                    .rememberMe().key("AbcdEfghIjklmNopQrsTuvXyz_0123456789")
                    .and()
                    .logout().permitAll();

            http.headers().frameOptions().sameOrigin();

            return http.build();
        }

        @Bean
        public WebSecurityCustomizer webSecurityCustomizer() {
            return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
        }

    }


