package com.webprojekt.webblog.API;

import com.webprojekt.webblog.BussinesLayer.WebBlogServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EntryController {


    WebBlogServices webBlogServices;

    @Autowired
    public EntryController(WebBlogServices webBlogServices) {
        this.webBlogServices = webBlogServices;
    }

    @GetMapping("/Entry")
    public  String getEntry (Model model ) {

        webBlogServices.addUser ("Peter", "PeterPan", "PeterPan", "PeterPan");
        webBlogServices.addUser ("Hanz", "HanzPan", "HanzPan", "HanzPan");
        webBlogServices.addUser ("Kevin", "KevinPan", "KevinPan", "KevinPan");
        webBlogServices.addEntry ("Hier ist ein Text1",1L);
        webBlogServices.addEntry ("Hier ist ein Text2",2L);
        webBlogServices.addEntry ("Hier ist ein Text3",3L);
        webBlogServices.addEntry ("Hier ist ein Text4",1L);

        model.addAttribute("entries", webBlogServices.getEntriesByCreationDate ());

        return "entries";
    }


}
