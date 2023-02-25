package com.webprojekt.webblog.API;

import com.webprojekt.webblog.BussinesLayer.WebBlogServices;
import com.webprojekt.webblog.DAO.Session;
import com.webprojekt.webblog.DAO.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.Optional;
@Controller
public class SessionController {
   private WebBlogServices webBlogServices;

    @Autowired
    public SessionController(WebBlogServices webBlogServices) {
        this.webBlogServices = webBlogServices;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("login", new User ("", ""));
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("login") User user, BindingResult bindingResult, HttpServletResponse response) {
        Optional<User> optionalUser = webBlogServices.findByUsernameAndPassword(user.getUsername(), user.getPassword ());

        if (optionalUser.isPresent()) {
            Session session = new Session(optionalUser.get(), Instant.now().plusSeconds(7*24*60*60)); //expires one week from now
            webBlogServices.addSession(session);

           //store the session ID in a cookie to keep the username secret
            Cookie cookie = new Cookie("sessionId", session.getId());
            response.addCookie(cookie);

            // Login successful
            return "redirect:/";
        }

        bindingResult.addError(new FieldError ("login", "password", "Login not successful."));

        return "login";
    }

    @PostMapping("/logout")
    public String logout(@CookieValue(value = "sessionId", defaultValue = "") String sessionId, HttpServletResponse response) {
        Optional<Session> optionalSession = webBlogServices.findByIdAndExpiresAtAfter(sessionId, Instant.now());
        optionalSession.ifPresent(session -> webBlogServices.delete(session));

        Cookie cookie = new Cookie("sessionId", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/";
    }
}
