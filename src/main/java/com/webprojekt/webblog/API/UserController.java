package com.webprojekt.webblog.API;

import com.webprojekt.webblog.BussinesLayer.AuthenticationService;
import com.webprojekt.webblog.DAO.UserEntity;
import com.webprojekt.webblog.BussinesLayer.WebBlogServices;
import com.webprojekt.webblog.Security.AuthenticationRequest;
import com.webprojekt.webblog.Security.AuthenticationResponse;
import com.webprojekt.webblog.Security.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/WebBlog/v1/auth")
@Controller
public class UserController {

    private final WebBlogServices webBlogServices;
    private final AuthenticationService authenticationService;
    @GetMapping("/registration")
    public String userRegistration(
            Model model,
            @ModelAttribute RegisterRequest request
    ) {
        model.addAttribute("user", new RegisterRequest (
                request.getName (),
                request.getUsername (),
                request.getEmail (),
                request.getPassword ()));
        return "registration";
    }
    @PostMapping("/registration")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestParam RegisterRequest request
    ){
        return ResponseEntity.ok (authenticationService.register(request));

    }





    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestParam AuthenticationRequest request
    ){

        return ResponseEntity.ok (authenticationService.authenticate(request));
    }
}
