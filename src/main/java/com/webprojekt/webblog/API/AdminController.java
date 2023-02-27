package com.webprojekt.webblog.API;

import com.webprojekt.webblog.BussinesLayer.AuthenticationService;
import com.webprojekt.webblog.BussinesLayer.WebBlogServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/WebBlog/User/isModerator/isAdmin")
@Controller
public class AdminController {

    private final WebBlogServices webBlogServices;
    @Autowired
    public AdminController(WebBlogServices webBlogServices) {
        this.webBlogServices = webBlogServices;
    }

    @PostMapping("/users/{id}/upgrade")
    public String upgradeUser(@PathVariable("id") String id, Model model) {
        webBlogServices.upgrade(id);
        model.addAttribute("users",  webBlogServices.getAllUsers ());
        return "users";
    }

    @PutMapping("/users/{id}/downgrade")
    public String downgradeUser(@PathVariable("id") String id, Model model) {
        webBlogServices.downgrade(id);
        model.addAttribute("users", webBlogServices.getAllUsers ());
        return "users";
    }

}
