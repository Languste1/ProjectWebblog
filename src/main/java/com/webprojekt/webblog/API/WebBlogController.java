package com.webprojekt.webblog.API;

public class WebBlogController {
@GetMapping("/")
    public String index(){

    return "index";
}

}
