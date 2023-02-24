package com.webprojekt.webblog.API;

import com.webprojekt.webblog.DAO.Entry;
import com.webprojekt.webblog.Services.WebBlogServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EntryController {

    @Autowired
    WebBlogServices webBlogServices;



    @GetMapping("/Entry")
    public  String getEntry (Model model ) {


        webBlogServices.addUser ("Peter");
        webBlogServices.addUser ("Hanz");
        webBlogServices.addUser ("Kevin");
        webBlogServices.addEntry ("Hier ist ein Text1",1L);
        webBlogServices.addEntry ("Hier ist ein Text2",2L);
        webBlogServices.addEntry ("Hier ist ein Text3",3L);
        webBlogServices.addEntry ("Hier ist ein Text4",1L);

        webBlogServices.addComment("testkommentar1", 1L, 1L);







        model.addAttribute("entries", webBlogServices.getEntriesByCreationDate ());


        model.addAttribute("comments", webBlogServices.getCommentsByCreationDate());
  /*

         */



        return "entries";
    }


}
