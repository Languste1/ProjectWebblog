package com.webprojekt.webblog.BussinesLayer;


import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwt;

@Service
public class JwtService {
    public String extractUsername(String token) {
        return null;
    }

    private Claims extractAllclaims(String token){
        return Jwts.parserBuilder ()
    }
}