package com.webprojekt.webblog.BussinesLayer;

import com.webprojekt.webblog.DAO.Entry;
import com.webprojekt.webblog.DAO.UserEntity;
import com.webprojekt.webblog.Repositories.EntryRepository;
/*import com.webprojekt.webblog.Repositories.SessionRepository;*/
import com.webprojekt.webblog.Repositories.SessionRepository;
import com.webprojekt.webblog.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@Service
public class WebBlogServices  {
    private UserRepository userRepository;
    private EntryRepository entryRepository;
    private SessionRepository sessionRepository;


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
        Optional<UserEntity> user = userRepository.findById(customerId);
        Entry entry = new Entry(text);
        entry.setText(text);
        entry.setUser(user.get());
        entryRepository.save(entry);
    }

    public List<UserEntity> getUsers() {
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
