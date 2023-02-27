package com.webprojekt.webblog.Security;

import com.webprojekt.webblog.Repositories.UserRepository;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// Normelerweise wäre es einfacher mit WebSecurityConfiguratorAdapter zu extenden und die methoden zu überschreiben
//aber es funktioniert nicht bei mir
@Configuration // anotation das sagt, hey du bist die config file für security
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAthFilter;
    private final AuthenticationProvider authenticationProvider;


    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAthFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAthFilter = jwtAthFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf()
                .disable()
                .authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .requestMatchers ("/**","/registration**","/login**","/entries","/dummies**").permitAll() // allow access to login page
                .requestMatchers ("/WebBlog/User**").hasAnyRole("USER","ADMIN","MODERATOR")
                .requestMatchers ("/WebBlog/User/isModerator**").hasAnyRole("ADMIN","MODERATOR")
                .requestMatchers ("/WebBlog/User/isModerator/isAdmin**").hasAnyRole("ADMIN")
                .anyRequest().authenticated() // all other requests require authentication
                .and()
                .formLogin() // enable form-based login
                .loginPage("/login") // specify the login page URL
                .defaultSuccessUrl("/") // redirect to homepage after successful login
                .and ()
                .logout() // enable logout
                .logoutUrl("/logout") // specify the logout URL
                .logoutSuccessUrl("/login") // redirect to login page after successful logout
                .and()
                .sessionManagement ()
                .sessionCreationPolicy (SessionCreationPolicy.STATELESS)
                .and ()
                .authenticationProvider (authenticationProvider)
                .addFilterBefore (jwtAthFilter, UsernamePasswordAuthenticationFilter.class)
        ;
    return http.build();
    }
/*
.and ()
                .formLogin() // enable form-based login
                .loginPage("/login") // specify the login page URL
                .defaultSuccessUrl("/") // redirect to homepage after successful login
                .successHandler((request, response, authentication) -> {
                    String token = ((AuthenticationResponse) authentication.getPrincipal()).getToken();
                    Cookie cookie = new Cookie("jwtToken", token);
                    cookie.setPath("/");
                    cookie.setMaxAge(60 * 60 * 24); // set cookie expiration to 1 day
                    response.addCookie(cookie);
                })
 */



}


