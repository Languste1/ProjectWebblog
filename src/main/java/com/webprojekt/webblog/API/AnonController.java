package com.webprojekt.webblog.API;

import com.webprojekt.webblog.BussinesLayer.AuthenticationService;
import com.webprojekt.webblog.BussinesLayer.WebBlogServices;
import com.webprojekt.webblog.Security.AuthenticationRequest;
import com.webprojekt.webblog.Security.AuthenticationResponse;
import com.webprojekt.webblog.Security.RegisterRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
public class AnonController {
    private final WebBlogServices webBlogServices;
    private final AuthenticationService authenticationService;

    @Autowired
    public AnonController(WebBlogServices webBlogServices, AuthenticationService authenticationService) {
        this.webBlogServices = webBlogServices;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/")
    public String index(){

        authenticationService.registerAdmin (new RegisterRequest ("ADMIN","admin","admin1234","admin@admin"));
        return "index";
    }

    @GetMapping("/registration")
    public String userRegistration(
            Model model,
            @ModelAttribute RegisterRequest request
    ) {
        model.addAttribute("user", new RegisterRequest (
                request.getName (),
                request.getUsername (),
                request.getEmail (),
                request.getPassword ())
        );




        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("user") RegisterRequest request, Model model) {
        try {
            authenticationService.register(request);
            model.addAttribute("message", "User registered successfully!");
            return "login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "registration";
        }

    }

    @GetMapping("/login")
    public String login(@ModelAttribute AuthenticationRequest request, Model model){
        model.addAttribute ("login",new AuthenticationRequest ());
        return "/login";
    }

    @PostMapping("/login")
    public String loginPost(
            @ModelAttribute AuthenticationRequest request,
            Model model,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse
    ) {
        try {
            AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);
            String token = authenticationResponse.getToken();
            Cookie cookie = new Cookie("jwtToken", token);
            cookie.setMaxAge(60 * 60 * 24); // set cookie expiration to 1 day
            cookie.setPath("/");
            httpResponse.addCookie(cookie);
            return "redirect:/"; // redirect to home page after successful authentication
        } catch (AuthenticationException e) {
            model.addAttribute("error", "Invalid username or password");
            model.addAttribute("login", new AuthenticationRequest()); // add the login object to the model to prepopulate the form
            return "login";
        }
    }

/*
    @PostMapping("/login")
    public String loginUser(Model model, @ModelAttribute("authenticationRequest") AuthenticationRequest authenticationRequest) {

        AuthenticationResponse response = authenticationService.authenticate(authenticationRequest);
        model.addAttribute("authenticationResponse", response);

        return "/"; // Hier geben wir den Namen der HTML-View zurück, die das Ergebnis darstellt
    }
*/

    @GetMapping("/entries")
    public  String getEntries(){

        return "entries";
    }

}



