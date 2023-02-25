package com.webprojekt.webblog.API;

import com.webprojekt.webblog.DAO.Comment;
import com.webprojekt.webblog.BussinesLayer.WebBlogServices;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EntryController {

    @Autowired
    WebBlogServices webBlogServices;
/*
    @GetMapping("/")
    public String index(){

        webBlogServices.addUser("Peter","AW123456");
        webBlogServices.addUser("Hanz","AW123456");
        webBlogServices.addUser("Kevin","AW123456");
        webBlogServices.addEntry("Hier ist ein Text1", 1L);
        webBlogServices.addEntry("Hier ist ein Text2", 2L);
        webBlogServices.addEntry("Hier ist ein Text3", 3L);
        webBlogServices.addEntry("Hier ist ein Text4", 1L);

        return "redirect:/Entry";
    }
*/

    @GetMapping("/Entry")
    public  String getEntry (Model model ) {



        model.addAttribute("entries", webBlogServices.getEntriesByCreationDate());
        model.addAttribute("comments", webBlogServices.getCommentsByCreationDate());
        model.addAttribute("comment", new Comment());

        return "entries";
    }
    @PostMapping("/Entry")
    public String addComment(@ModelAttribute Comment comment, Model model, HttpServletRequest request) {

        Long entryId = Long.parseLong(request.getParameter("entryId"));

        this.webBlogServices.addComment(comment.getText(), 1L, entryId );
        model.addAttribute("comment", new Comment());



        return "redirect:/Entry";
    }

}
