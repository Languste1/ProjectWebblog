package com.webprojekt.webblog.Security;

import com.webprojekt.webblog.BussinesLayer.LogoutService;
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
    private final AccessDeniedHandlerImpl accessDeniedHandler;
    private final JwtAuthenticationFilter jwtAthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    // Konstruktor, um die erforderlichen Abhängigkeiten zu injizieren
    @Autowired
    public SecurityConfig(
            AccessDeniedHandlerImpl accessDeniedHandler,
            JwtAuthenticationFilter jwtAthFilter,
            AuthenticationProvider authenticationProvider,
            LogoutHandler logoutHandler) {
        this.accessDeniedHandler = accessDeniedHandler;
        this.jwtAthFilter = jwtAthFilter;
        this.authenticationProvider = authenticationProvider;
        this.logoutHandler = logoutHandler;

    }



    // Konfigurieren der Sicherheitsfilterkette
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // deaktivieren des CSRF-Schutzes
                .csrf().disable()
                // Autorisierung von Http-Anfragen
                .authorizeHttpRequests()
                // Zugriff auf statische Ressourcen (CSS, JS, etc.) an gemeinsamen Orten erlauben
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                // Zugriff auf bestimmte Seiten ohne Authentifizierung erlauben
                .requestMatchers(
                        "/index**","/registration**",
                        "/login**","/resources**","/css/**"
                )
                .permitAll()
                // Zugriff auf benutzerbezogene Seiten für Benutzer, Moderatoren und Admins erlauben
                .requestMatchers("/dummies**","/logout").hasAnyRole(
                        UserRoles.USER.name(),
                        UserRoles.MODERATOR.name(),
                        UserRoles.ADMIN.name()
                )
                // Zugriff auf Moderator-bezogene Seiten für Moderatoren und Admins erlauben
                .requestMatchers("/users").hasAnyRole(
                        UserRoles.MODERATOR.name(),
                        UserRoles.ADMIN.name()
                )
                // Zugriff auf Admin-bezogene Seiten nur für Admins erlauben
                .requestMatchers("/users**").hasAnyRole(UserRoles.ADMIN.name())
                // Authentifizierung für alle anderen Anfragen erforderlich machen
                .anyRequest()
                .authenticated()
                // Zugriff verweigert Handler festlegen
                .and().exceptionHandling().accessDeniedPage ("/index")
                // Formular-basierte Authentifizierung aktivieren
                .and().formLogin()
                .loginPage("/login") // Login-Seite festlegen
                .successForwardUrl("/index") // URL für die erfolgreiche Weiterleitung nach dem Login festlegen
                .and()
                // Session-Management auf STATELESS festlegen, um CSRF-Schutz zu vermeiden
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // AuthenticationProvider für die Authentifizierung konfigurieren
                .authenticationProvider(authenticationProvider)
                // JwtAuthenticationFilter hinzufügen, bevor der Standard-Filter hinzugefügt wird
                .addFilterBefore(jwtAthFilter, UsernamePasswordAuthenticationFilter.class)
                // Logout-Konfiguration
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl ("/index")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());

        return http.build();
    }


}


