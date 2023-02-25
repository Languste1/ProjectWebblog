package com.webprojekt.webblog.API;

import com.webprojekt.webblog.BussinesLayer.WebBlogServices;
import com.webprojekt.webblog.DAO.Session;
import com.webprojekt.webblog.DAO.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.Instant;
import java.util.Optional;
@ControllerAdvice
public class SessionControllerAdvice {
    private WebBlogServices webBlogServices;
    @Autowired
    public SessionControllerAdvice(WebBlogServices webBlogServices) {
        this.webBlogServices = webBlogServices;
    }



    @ModelAttribute("sessionUser")
    public User sessionUser(@CookieValue(value = "sessionId", defaultValue = "") String sessionId) {
        if (!sessionId.isEmpty()) {
            Optional<Session> optionalSession = webBlogServices.findByIdAndExpiresAtAfter(
                    sessionId, Instant.now());
            if (optionalSession.isPresent()) {
                Session session = optionalSession.get();

                // new expiresAt value for the current session
                session.setExpiresAt(Instant.now().plusSeconds(7 * 24 * 60 * 60));
                webBlogServices.addSession (session);

                return session.getUser();
            }
        }
        return null;
    }
}
