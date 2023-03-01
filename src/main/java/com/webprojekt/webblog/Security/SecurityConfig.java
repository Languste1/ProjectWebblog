package com.webprojekt.webblog.Security;

import com.webprojekt.webblog.DAO.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAthFilter, AuthenticationProvider authenticationProvider, LogoutHandler logoutHandler) {
        this.jwtAthFilter = jwtAthFilter;
        this.authenticationProvider = authenticationProvider;
        this.logoutHandler = logoutHandler;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf() // disable CSRF protection
                .disable()
                .authorizeHttpRequests ()
                // allow access to static resources (CSS, JS, etc.) at common locations
                .requestMatchers(PathRequest.toStaticResources ().atCommonLocations ()).permitAll()
                // allow access to certain pages without authentication
                .requestMatchers ("/**","/index**","/registration**","/login**","/resources**","/css/**", "delete**", "editentry**", "").permitAll()
                // allow access to user-related pages for users, moderators, and admins
                .requestMatchers ("/dummies**").hasAnyRole(
                        UserRoles.USER.name (),
                        UserRoles.MODERATOR.name (),
                        UserRoles.ADMIN.name ()
                )
                // allow access to moderator-related pages for moderators and admins
                .requestMatchers ("/users").hasAnyRole(
                        UserRoles.MODERATOR.name (),
                        UserRoles.ADMIN.name ()
                )
                // allow access to admin-related pages for admins only
                .requestMatchers ("/users**").hasAnyRole(UserRoles.ADMIN.name ())
                // require authentication for all other requests
                .anyRequest()
                .authenticated()
                .and ()
                .formLogin ()
                .loginPage ("/login")
                .successForwardUrl ("/index")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());
        return http.build();
    }


}


