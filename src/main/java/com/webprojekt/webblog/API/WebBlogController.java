package com.webprojekt.webblog.API;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/WebBlog")
@Controller
public class WebBlogController {

@GetMapping("/")
    public String index(){

    return "index";
}

}
