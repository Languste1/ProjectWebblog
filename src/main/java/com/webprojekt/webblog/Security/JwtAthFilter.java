package com.webprojekt.webblog.Security;

import com.webprojekt.webblog.BussinesLayer.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
//Component,Service
@RequiredArgsConstructor
@Component
public class JwtAthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    @Override
    protected void doFilterInternal(
           @NonNull HttpServletRequest request,
           @NonNull HttpServletResponse response,
           @NonNull FilterChain filterChain
    ) throws ServletException, IOException{
       final String authHeader = request.getHeader ("Authorization");
       final String jwt;
       final String username;
       if(authHeader == null ||!authHeader.startsWith ("Bearer ")){
           filterChain.doFilter (request, response);
           return;
       }
       jwt = authHeader.substring (7);
       username= jwtService.extractUsername(jwt);
    }

}
