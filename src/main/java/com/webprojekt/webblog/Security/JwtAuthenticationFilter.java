package com.webprojekt.webblog.Security;

import com.webprojekt.webblog.BussinesLayer.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//Component,Service
// Ein Komponent, der von Spring verwaltet wird und in anderen Komponenten verwendet werden kann
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // JwtService wird zur Überprüfung des JWT-Tokens verwendet
    private final JwtService jwtService;

    // UserDetailsService wird verwendet, um Benutzerinformationen aus der Datenbank abzurufen
    private final UserDetailsService userDetailsService;

    // Konstruktor, der JwtService und UserDetailsService über Autowired injiziert
    @Autowired
    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    // Überschreiben der doFilterInternal-Methode von OncePerRequestFilter
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException{

        // Holt den Authorization-Header
        final String authHeader = request.getHeader ("Authorization");
        final String jwt;
        final String username;

        // Prüft, ob Authorization-Header vorhanden und mit "Bearer " beginnt
        if(authHeader == null || !authHeader.startsWith ("Bearer ")){
            filterChain.doFilter ( request, response);
            return;
        }

        // Extrahiert den JWT-Token aus dem Authorization-Header
        jwt = authHeader.substring (7);

        // Extrahiert den Benutzernamen aus dem JWT-Token
        username= jwtService.extractUsername(jwt);

        // Prüft, ob der Benutzername gültig ist und keine Authentifizierung bereits besteht
        if (username != null && SecurityContextHolder.getContext ().getAuthentication () == null){

            // Holt die Benutzerdetails aus der Datenbank
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // Prüft, ob der JWT-Token gültig ist
            if (jwtService.isTokenValid (jwt,userDetails)){

                // Erstellt eine neue Authentifizierungsanforderung
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken (
                        userDetails,
                        null,
                        userDetails.getAuthorities ()
                );

                // Setzt die Details der Authentifizierungsanforderung
                authenticationToken.setDetails (
                        new WebAuthenticationDetailsSource ().buildDetails (request)
                );

                // Setzt die Authentifizierung im SecurityContext
                SecurityContextHolder.getContext ().setAuthentication (authenticationToken);
            }
        }

        // Führt den Filterchain fort
        filterChain.doFilter (request,response);
    }
}
