package com.webprojekt.webblog.BussinesLayer;

import com.webprojekt.webblog.DAO.User;
import com.webprojekt.webblog.Repositories.UserRepository;
import com.webprojekt.webblog.Security.AuthenticationRequest;
import com.webprojekt.webblog.Security.AuthenticationResponse;
import com.webprojekt.webblog.Security.RegisterRequest;
import com.webprojekt.webblog.Security.UserRoles;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
private final UserRepository userRepository;
private  final PasswordEncoder passwordEncoder;
private final JwtService jwtService;
private  final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder ()
                .name (request.getName ())
                .username (request.getUsername ())
                .password (passwordEncoder.encode (request.getPassword ()))
                .email (request.getEmail ())
                .userRoles (UserRoles.USER)
                .build ();
        userRepository.save (user);
        var jwtToken = jwtService.generateToken (user);
        return AuthenticationResponse.builder()
                .token (jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate (
                new UsernamePasswordAuthenticationToken (
                        request.getUsername (),
                        request.getPassword ()
                )
        );
        var user = userRepository.findByUsername (request.getUsername ())
                .orElseThrow ();
        var jwtToken = jwtService.generateToken (user);
        return AuthenticationResponse.builder()
                .token (jwtToken).
                build();
    }
}
