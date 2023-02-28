package com.webprojekt.webblog.API;

import com.webprojekt.webblog.BussinesLayer.AuthenticationService;
import com.webprojekt.webblog.BussinesLayer.WebBlogServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/WebBlog/User/isModerator")
@Controller
public class ModeratorController {

    private final WebBlogServices webBlogServices;
    private final AuthenticationService authenticationService;
    @Autowired
    public ModeratorController(WebBlogServices webBlogServices, AuthenticationService authenticationService) {
        this.webBlogServices = webBlogServices;
        this.authenticationService = authenticationService;
    }








}
