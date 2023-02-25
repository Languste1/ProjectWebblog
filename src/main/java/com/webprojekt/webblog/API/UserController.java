package com.webprojekt.webblog.API;


import com.webprojekt.webblog.BussinesLayer.WebBlogServices;
import com.webprojekt.webblog.DAO.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
private WebBlogServices webBlogServices;
@Autowired
    public UserController(WebBlogServices webBlogServices) {
        this.webBlogServices = webBlogServices;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("registration", new User ("", "", ""));
        return "register";
    }


    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registration") User userRefistration, BindingResult bindingResult) {

        if(!userRefistration.getPassword ().equals(userRefistration.getPassword2())) {
            bindingResult.addError(new FieldError ("registration", "password", "passwords are not matching"));
        }

        if(webBlogServices.existsByUsername(userRefistration.getUsername())) {
            bindingResult.addError(new FieldError("registration", "username", "username already in use"));
        }

        if(bindingResult.hasErrors()) {
            return "register";

        }

        User user = new User(userRefistration.getName (), userRefistration.getUsername (), userRefistration.getPassword (), userRefistration.getPassword ());
        webBlogServices.addUser (user);

        return "redirect:/login";
    }

}
