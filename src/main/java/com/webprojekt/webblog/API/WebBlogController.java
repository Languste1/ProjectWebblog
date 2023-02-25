package com.webprojekt.webblog.API;

import com.webprojekt.webblog.BussinesLayer.WebBlogServices;
import com.webprojekt.webblog.DAO.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class WebBlogController {

private WebBlogServices webBlogServices;
@Autowired
    public WebBlogController(WebBlogServices webBlogServices) {
        this.webBlogServices = webBlogServices;
    }

    @GetMapping("/")
public String home(@ModelAttribute("sessionUser") User sessionUser, Model model) {
model.addAttribute ("sessionUser",sessionUser);
        webBlogServices.addUser("Peter","AW123456");
        webBlogServices.addUser("Hanz","AW123456");
        webBlogServices.addUser("Kevin","AW123456");
        webBlogServices.addEntry("Hier ist ein Text1", 1L);
        webBlogServices.addEntry("Hier ist ein Text2", 2L);
        webBlogServices.addEntry("Hier ist ein Text3", 3L);
        webBlogServices.addEntry("Hier ist ein Text4", 1L);

        return "redirect:/entries";
}



}
