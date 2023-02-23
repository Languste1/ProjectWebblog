package com.webprojekt.webblog.Services;

import com.webprojekt.webblog.DAO.Entry;
import com.webprojekt.webblog.DAO.User;
import com.webprojekt.webblog.Repositories.EntryRepository;
import com.webprojekt.webblog.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Service
public class WebBlogServices {
    private UserRepository userRepository;
    private EntryRepository entryRepository;

    @Autowired
    public WebBlogServices(UserRepository userRepository, EntryRepository entryRepository) {
        this.userRepository = userRepository;
        this.entryRepository = entryRepository;
    }
// Wird eine Liste von Entries nach Datum sortiert
    public List<Entry> getEntriesByCreationDate() {
        Sort sort = Sort.by (Sort.Direction.DESC, "date"); // Sort wird benutz, um Listen von Objekten in DatenBanken zu sortieren
        return entryRepository.findAll (sort);
    }

    public List<Entry> getEntries() {
            return entryRepository.findAll ();
    }
    // Methode, um eine Entry zu addieren
    public void addEntry(String text, Long customerId){
    Optional<User> user= userRepository.findById (customerId);
    Entry entry=new Entry (text);
    entry.setText (text);
    entry.setUser (user.get ());
    entryRepository.save (entry);
    }

    public void addUser(String name) {
        User user = new User (name);
        user.setName (name);
        userRepository.save (user);
    }
}
