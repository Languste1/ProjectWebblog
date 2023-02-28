package com.webprojekt.webblog.API;

import com.webprojekt.webblog.BussinesLayer.AuthenticationService;
import com.webprojekt.webblog.BussinesLayer.LogoutService;
import com.webprojekt.webblog.BussinesLayer.WebBlogServices;
import com.webprojekt.webblog.DAO.Comment;
import com.webprojekt.webblog.DAO.Entry;
import com.webprojekt.webblog.DTO.AuthenticationRequest;
import com.webprojekt.webblog.DTO.AuthenticationResponse;
import com.webprojekt.webblog.DTO.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AnonController {
    private final WebBlogServices webBlogServices;
    private final AuthenticationService authenticationService;
    private final LogoutService logoutService;
    @Autowired
    public AnonController(WebBlogServices webBlogServices, AuthenticationService authenticationService, LogoutService logoutService) {
        this.webBlogServices = webBlogServices;
        this.authenticationService = authenticationService;
        this.logoutService = logoutService;
    }

    @GetMapping("/index")
    public String index(Model model){
        //  webBlogServices.addAdmin (new User ("admin",""));

        model.addAttribute("entries", webBlogServices.getEntriesByCreationDate());
        model.addAttribute("comments", webBlogServices.getCommentsByCreationDate());
        model.addAttribute("comment", new Comment());
        model.addAttribute ("entry", new Entry());

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
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "registration";
        }

    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("login", new AuthenticationRequest());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute AuthenticationRequest request, Model model) {
        try {
            AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);
            model.addAttribute("token", authenticationResponse.getToken());
            return "redirect:/index"; // or any other URL you want to redirect to after login
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            model.addAttribute("errorMessage", "Invalid username or password");
            return "login";
        }
    }


    @GetMapping("/dummies")
    public String getDummies(){
        authenticationService.registerAdmin (new RegisterRequest ("admin","admin","admin1234","admin@admin.com"));
        webBlogServices.addUser ("Dummy Dummyson2","dummy","dummy1234","dummy@dummy.com");
        webBlogServices.addUser ("Dummy Dummyson3","dummy2","dummy1234","dummy@dummy.com");



        webBlogServices.addEntry("Placeholder","Hier ist ein Text1", webBlogServices.findIdByUsername("admin"));
        webBlogServices.addEntry("Placeholder4","Hier ist ein Text2", webBlogServices.findIdByUsername("admin"));
        webBlogServices.addEntry("Placeholder2","Hier ist ein Text3", webBlogServices.findIdByUsername("admin"));
        webBlogServices.addEntry("Placeholder3","Hier ist ein Text4", webBlogServices.findIdByUsername("admin"));
        return "redirect:/index";
    }


    @PostMapping("/users/{id}/upgrade")
    public String upgradeUser(@PathVariable("id") String id, Model model) {
        webBlogServices.upgrade(id);
        model.addAttribute("users",  webBlogServices.getAllUsers ());
        return "users";
    }

    @PostMapping ("/users/{id}/downgrade")
    public String downgradeUser(@PathVariable("id") String id, Model model) {
        webBlogServices.downgrade(id);
        model.addAttribute("users", webBlogServices.getAllUsers ());
        return "users";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {

        model.addAttribute("users", webBlogServices.getAllUsers ());
        return "users";
    }

    @PostMapping("/index")
    public String addEntryOrComment(@ModelAttribute Entry entry, @ModelAttribute Comment comment, Model model, HttpServletRequest request) {
        if (entry.getTitle() != null) {
            this.webBlogServices.addEntry(entry.getTitle (), entry.getText(), webBlogServices.findIdByUsername("Admin"));
        } else if (comment.getText() != null) {
            Long entryId = Long.parseLong(request.getParameter("entryId"));
            this.webBlogServices.addComment(comment.getText(), webBlogServices.findIdByUsername("admin"), entryId);
        }
        model.addAttribute("entry", new Entry());
        model.addAttribute("comment", new Comment());
        return "redirect:/index";
    }


    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            logoutService.logout(request, response, auth);
        }
        return "redirect:/index";
    }


}



