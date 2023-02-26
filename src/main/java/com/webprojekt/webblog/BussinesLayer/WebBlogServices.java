package com.webprojekt.webblog.BussinesLayer;

import com.webprojekt.webblog.DAO.Entry;
import com.webprojekt.webblog.DAO.User;
import com.webprojekt.webblog.Repositories.EntryRepository;
/*import com.webprojekt.webblog.Repositories.SessionRepository;*/
import com.webprojekt.webblog.Repositories.SessionRepository;
import com.webprojekt.webblog.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WebBlogServices {
    private UserRepository userRepository;
    private EntryRepository entryRepository;
    private SessionRepository sessionRepository;

    @Autowired
    public WebBlogServices(UserRepository userRepository, EntryRepository entryRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.entryRepository = entryRepository;
        this.sessionRepository = sessionRepository;
    }


    // Wird eine Liste von Entries nach Datum sortiert
    public List<Entry> getEntriesByCreationDate() {
        Sort sort = Sort.by(Sort.Direction.DESC, "date"); // Sort wird benutz, um Listen von Objekten in DatenBanken zu sortieren
        return entryRepository.findAll(sort);
    }

    public List<Entry> getEntries() {
        return entryRepository.findAll();
    }

    // Methode, um eine Entry zu addieren
    public void addEntry(String text, Long customerId) {
        Optional<User> user = userRepository.findById(customerId);
        Entry entry = new Entry(text);
        entry.setText(text);
        entry.setUser(user.get());
        entryRepository.save(entry);
    }

    public void addUser(String name, String username, String passwort, String passwort2) {
        /*if (!passwort.equals(passwort2)) {
            System.out.println("Das ist falsch");
        }*/
        User user = new User(name);
        user.setName(name);
        user.setUsername(username);
        user.setPassword (passwort);
        user.setPasswort2(passwort2);
        userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

/*
    public void addSession(String name, String username, String password, String passwort2) {
        Optional<User> optionalUser = userRepository.findByUsernameAndPassword(username, password);
        if () {

            addUser(name, username, password, passwort2);
        }
    }
*/
}
