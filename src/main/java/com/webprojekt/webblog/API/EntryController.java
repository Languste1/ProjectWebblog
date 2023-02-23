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
    @GetMapping("/readentry")
    public @ResponseBody String getEntry (Model model ) {
        model.addAttribute("Entries", webBlogServices.getEntries());



    return "Tamayo ist der Beste!!!!!!!!!!!!";
    }


}
