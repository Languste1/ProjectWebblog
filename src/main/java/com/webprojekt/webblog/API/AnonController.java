package com.webprojekt.webblog.API;

import com.webprojekt.webblog.BussinesLayer.AuthenticationService;
import com.webprojekt.webblog.BussinesLayer.WebBlogServices;
import com.webprojekt.webblog.DAO.Comment;
import com.webprojekt.webblog.DTO.AuthenticationRequest;
import com.webprojekt.webblog.DTO.AuthenticationResponse;
import com.webprojekt.webblog.DTO.RegisterRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String index(Model model){
      //  webBlogServices.addAdmin (new User ("admin",""));

        model.addAttribute("entries", webBlogServices.getEntriesByCreationDate());
        model.addAttribute("comments", webBlogServices.getCommentsByCreationDate());
        model.addAttribute("comment", new Comment());

        return "index";
    }

    @PostMapping("/")
    public String addComment(@ModelAttribute Comment comment, Model model, HttpServletRequest request) {

        Long entryId = Long.parseLong(request.getParameter("entryId"));

        this.webBlogServices.addComment(comment.getText(), webBlogServices.findIdByUsername("admin"), entryId );
        model.addAttribute("comment", new Comment());



        return "redirect:/";
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
            return "redirect:/";
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



    @GetMapping("/entries")
    public  String getEntries(){

        return "entries";
    }

    @GetMapping("/dummies")
    public String getDummies(){
        authenticationService.registerAdmin (new RegisterRequest ("admin","admin","admin1234","admin@admin.com"));
        webBlogServices.addUser ("Dummy Dummyson2","dummy","dummy1234","dummy@dummy.com");
        webBlogServices.addUser ("Dummy Dummyson3","dummy2","dummy1234","dummy@dummy.com");

    //dummy


        webBlogServices.addEntry("Placeholder","Hier ist ein Text1", webBlogServices.findIdByUsername("admin"));
        webBlogServices.addEntry("Placeholder4","Hier ist ein Text2", webBlogServices.findIdByUsername("admin"));
        webBlogServices.addEntry("Placeholder2","Hier ist ein Text3", webBlogServices.findIdByUsername("admin"));
        webBlogServices.addEntry("Placeholder3","Hier ist ein Text4", webBlogServices.findIdByUsername("admin"));
        return "redirect:/";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {

        model.addAttribute("users", webBlogServices.getAllUsers ());
        return "users";
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
}



