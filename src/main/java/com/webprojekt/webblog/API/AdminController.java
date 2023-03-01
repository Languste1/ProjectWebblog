package com.webprojekt.webblog.API;

import com.webprojekt.webblog.BussinesLayer.AuthenticationService;
import com.webprojekt.webblog.BussinesLayer.WebBlogServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class AdminController {

    private final WebBlogServices webBlogServices;
    @Autowired
    public AdminController(WebBlogServices webBlogServices) {
        this.webBlogServices = webBlogServices;
    }



}
