package com.webprojekt.webblog.BussinesLayer;

import com.webprojekt.webblog.DAO.User;
import com.webprojekt.webblog.Repositories.UserRepository;
import com.webprojekt.webblog.DTO.AuthenticationRequest;
import com.webprojekt.webblog.DTO.AuthenticationResponse;
import com.webprojekt.webblog.DTO.RegisterRequest;
import com.webprojekt.webblog.DAO.UserRoles;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public AuthenticationResponse registerAdmin(RegisterRequest request) {
        var user = User.builder ()
                .name (request.getName ())
                .username (request.getUsername ())
                .password (passwordEncoder.encode (request.getPassword ()))
                .email (request.getEmail ())
                .userRoles (UserRoles.ADMIN)
                .build ();
        userRepository.save (user);
        var jwtToken = jwtService.generateToken (user);
        return AuthenticationResponse.builder()
                .token (jwtToken)
                .build();
    }

    /* public AuthenticationResponse authenticate(AuthenticationRequest request) {
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
     }*/
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate (
                    new UsernamePasswordAuthenticationToken (
                            request.getUsername (),
                            request.getPassword ()
                    )
            );
        } catch (org.springframework.security.core.AuthenticationException e) {
            throw new BadCredentialsException ("Invalid username or password", e);
        }

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException ("User not found"));

        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token (jwtToken)
                .build();
    }
}