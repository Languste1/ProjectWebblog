package com.webprojekt.webblog.API;

import com.webprojekt.webblog.DAO.User;
import com.webprojekt.webblog.BussinesLayer.WebBlogServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {


    private final WebBlogServices webBlogServices;

    @Autowired
    public UserController(WebBlogServices webBlogServices) {
        this.webBlogServices = webBlogServices;
    }

    @GetMapping("/registration")
    public String userRegistration(Model model, @ModelAttribute User user) {
        model.addAttribute("users", webBlogServices.getUsers());

        model.addAttribute("user", new User("","","", ""));
        return "registration";
    }

    @PostMapping("/registration")
    public String userRegistrationPost(Model model, @ModelAttribute User user) {
        User userRegistration = new User(user.getName(), user.getUsername(), user.getPasswort(), user.getPasswort2());
        webBlogServices.addUser(userRegistration.getName(), userRegistration.getUsername(), userRegistration.getPasswort(), userRegistration.getPasswort2());

        return "registration";
    }
}
