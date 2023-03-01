package com.webprojekt.webblog.API;

import com.webprojekt.webblog.BussinesLayer.AuthenticationService;
import com.webprojekt.webblog.BussinesLayer.WebBlogServices;
import com.webprojekt.webblog.DAO.Comment;
import com.webprojekt.webblog.DAO.Entry;
import com.webprojekt.webblog.DTO.AuthenticationRequest;
import com.webprojekt.webblog.DTO.AuthenticationResponse;
import com.webprojekt.webblog.DTO.RegisterRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AnonController {
    private final WebBlogServices webBlogServices;
    private final AuthenticationService authenticationService;

    @Autowired
    public AnonController(WebBlogServices webBlogServices, AuthenticationService authenticationService) {
        this.webBlogServices = webBlogServices;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/index")
    public String index(Model model){
        //  webBlogServices.addAdmin (new User ("admin",""));

        model.addAttribute("entries", webBlogServices.getEntriesByCreationDate());
        model.addAttribute("comments", webBlogServices.getCommentsByCreationDate());
        model.addAttribute("comment", new Comment());
        model.addAttribute ("entry", new Entry());

        return "index";
    }


    @GetMapping("/registration")
    public String userRegistration(
            Model model,
            @ModelAttribute RegisterRequest request
    ) {
        model.addAttribute("user", new RegisterRequest (
                request.getName (),
                request.getUsername (),
                request.getEmail (),
                request.getPassword ())
        );




        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("user") RegisterRequest request, Model model) {
        try {
            authenticationService.register(request);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "registration";
        }

    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("login", new AuthenticationRequest());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute AuthenticationRequest request, Model model) {
        try {
            AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);
            model.addAttribute("token", authenticationResponse.getToken());
            return "redirect:/index"; // or any other URL you want to redirect to after login
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            model.addAttribute("errorMessage", "Invalid username or password");
            return "login";
        }
    }


    @GetMapping("/dummies")
    public String getDummies(){
        authenticationService.registerAdmin (new RegisterRequest ("admin","admin","admin1234","admin@admin.com"));
        webBlogServices.addUser ("Dummy Dummyson2","dummy","dummy1234","dummy@dummy.com");
        webBlogServices.addUser ("Dummy Dummyson3","dummy2","dummy1234","dummy@dummy.com");


        webBlogServices.addEntry("Scary Movie","\"Scary Movie\" ist eine Horror-Komödie aus dem Jahr 2000, " +
                "die eine Parodie auf viele bekannte Horrorfilme und -figuren enthält, wie beispielsweise \"Scream\", " +
                "\"The Sixth Sense\" und \"The Blair Witch Project\".Die Handlung folgt einer Gruppe von High-School-Schülern, " +
                "die von einem maskierten Mörder terrorisiert werden. Der Film nimmt bekannte Klischees des Horror-Genres aufs " +
                "Korn und persifliert sie auf humorvolle und oft absurde Weise. Dabei werden auch sexuelle Themen und Anspielungen " +
                "auf Popkultur aufgegriffen.\"Scary Movie\" ist bekannt für seine exzessive Verwendung von Gags und Anspielungen, " +
                "die sich oft auf die Grenzen des guten Geschmacks bewegen. Der Film war ein großer Erfolg an der Kinokasse und " +
                "führte zu mehreren Fortsetzungen, die ähnliche Themen und Stilmittel aufgriffen.", webBlogServices.findIdByUsername("admin"));

        webBlogServices.addEntry("Fight Club","\"Fight Club\" ist ein Film aus dem Jahr 1999, der auf dem gleichnamigen " +
                "Roman von Chuck Palahniuk basiert. Der Film erzählt die Geschichte eines namenlosen Protagonisten (gespielt von Edward Norton), " +
                "der an Schlaflosigkeit leidet und sich in einer existenziellen Krise befindet.Auf der Suche nach Sinn und Erfüllung " +
                "in seinem Leben trifft er auf Tyler Durden (gespielt von Brad Pitt), einen charismatischen und rebellischen Mann, der " +
                "ihn in einen Untergrund-Club einführt, in dem Männer in illegalen Faustkämpfen ihre Aggressionen ausleben. Der Club " +
                "entwickelt sich schnell zu einer anarchistischen Bewegung, die die Konsumgesellschaft und die Normen der Gesellschaft " +
                "in Frage stellt.Während der Film scheinbar über Gewalt, Chaos und Anarchie handelt, enthüllt er im Verlauf der Handlung " +
                "eine tiefere psychologische Dimension. Der Protagonist und Tyler Durden sind zwei Seiten derselben Persönlichkeit, die " +
                "durch den Kampfclub und ihre anarchistische Bewegung versuchen, aus dem engen Korsett der modernen Gesellschaft auszubrechen.",
                webBlogServices.findIdByUsername("admin"));
        webBlogServices.addEntry("Fear and loathing in Las Vegas","\"Fear and Loathing\" ist ein Film aus dem Jahr 1998, der" +
                " auf dem gleichnamigen Roman von Hunter S. Thompson basiert. Der Film erzählt die Geschichte von Raoul Duke (gespielt " +
                "von Johnny Depp) und seinem Anwalt Dr. Gonzo (gespielt von Benicio Del Toro), die nach Las Vegas reisen, um über ein " +
                "Motorradrennen zu berichten und sich dabei in einen wilden Drogenrausch stürzen.Der Film zeigt die surrealen und oft " +
                "verstörenden Abenteuer der beiden Protagonisten, die durch ihre Verwendung von LSD und anderen Drogen eine Abwärtsspirale " +
                "erleben. Der Film setzt sich auch kritisch mit der amerikanischen Gesellschaft der 60er Jahre auseinander und zeigt die " +
                "Auswirkungen von Drogenmissbrauch und Exzessen auf das menschliche Leben.", webBlogServices.findIdByUsername("admin"));

        webBlogServices.addEntry("Scarface","\"Scarface\" ist ein Film aus dem Jahr 1983 von Regisseur Brian De Palma. Der Film " +
                "erzählt die Geschichte von Tony Montana (gespielt von Al Pacino), einem kubanischen Einwanderer, der in Miami in den " +
                "1980er Jahren zum mächtigsten Drogenboss aufsteigt.Der Film zeigt den Aufstieg von Tony Montana von einem einfachen " +
                "Einwanderer zu einem skrupellosen Drogenhändler, der sich durch Gewalt, Betrug und Bestechung an die Spitze kämpft. " +
                "Während er immer mehr Macht und Reichtum erlangt, zerfällt seine Beziehung zu seiner Frau Elvira (gespielt von Michelle " +
                "Pfeiffer) und er gerät in einen blutigen Krieg mit rivalisierenden Gangs.", webBlogServices.findIdByUsername("admin"));
        return "redirect:/index";
    }


    @PostMapping("/users/{id}/upgrade")
    public String upgradeUser(@PathVariable("id") String id, Model model) {
        webBlogServices.upgrade(id);
        model.addAttribute("users",  webBlogServices.getAllUsers ());
        return "users";
    }

    @PostMapping ("/users/{id}/downgrade")
    public String downgradeUser(@PathVariable("id") String id, Model model) {
        webBlogServices.downgrade(id);
        model.addAttribute("users", webBlogServices.getAllUsers ());
        return "users";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {

        model.addAttribute("users", webBlogServices.getAllUsers ());
        return "users";
    }

    @PostMapping("/index")
    public String addEntryOrComment(@ModelAttribute Entry entry, @ModelAttribute Comment comment, Model model, HttpServletRequest request) {
        if (entry.getTitle() != null) {
            this.webBlogServices.addEntry(entry.getTitle (), entry.getText(), webBlogServices.findIdByUsername("Admin"));
        } else if (comment.getText() != null) {
            Long entryId = Long.parseLong(request.getParameter("entryId"));
            this.webBlogServices.addComment(comment.getText(), webBlogServices.findIdByUsername("admin"), entryId);
        }
        model.addAttribute("entry", new Entry());
        model.addAttribute("comment", new Comment());
        return "redirect:/index";
    }

    @PostMapping("/deletecomment/{id}")
    public String deleteComment(@PathVariable("id") Long id, @RequestParam(value = "deleteComment", required = false) String deleteComment) {
        if (deleteComment != null) {
            webBlogServices.deleteComment(id);
        }
        return "redirect:/index";
    }
    @PostMapping("/deleteEntry/{entryId}")
    public String deleteEntry(@PathVariable("entryId") Long entryId) {
        webBlogServices.deleteEntry(entryId);
        return "redirect:/index";
    }
    @GetMapping("/editEntry/{id}")
    public String editEntry(@PathVariable Long id, Model model) {
        Entry entry = webBlogServices.getEntryById(id);
        model.addAttribute("entry", entry);
        model.addAttribute("title", entry.getTitle());
        model.addAttribute("text", entry.getText());
        return "editentry";
    }
    @PostMapping("/updateEntry/{id}")
    public String updateEntry(@PathVariable Long id, @ModelAttribute Entry entry) {
        webBlogServices.updateEntry(id, entry.getTitle(), entry.getText());
        return "redirect:/index";
    }



}



