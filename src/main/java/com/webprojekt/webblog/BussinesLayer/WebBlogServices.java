package com.webprojekt.webblog.BussinesLayer;

import com.webprojekt.webblog.DAO.Comment;
import com.webprojekt.webblog.Repositories.CommentRepository;
import com.webprojekt.webblog.DAO.Entry;
import com.webprojekt.webblog.DAO.User;
import com.webprojekt.webblog.Repositories.EntryRepository;
/*import com.webprojekt.webblog.Repositories.SessionRepository;*/
import com.webprojekt.webblog.Repositories.UserRepository;
import com.webprojekt.webblog.DAO.UserRoles;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WebBlogServices  {
    private UserRepository userRepository;
    private EntryRepository entryRepository;
    private CommentRepository commentRepository;

    @Autowired
    public WebBlogServices(UserRepository userRepository, EntryRepository entryRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.entryRepository = entryRepository;
        this.commentRepository = commentRepository;
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
    public void addEntry(String title, String text, String customerId) {
        Optional<User> user = userRepository.findById(customerId);
        Entry entry = new Entry(text);
        entry.setText(text);
        entry.setUser(user.get ());
        entry.setTitle (title);
        entryRepository.save(entry);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void addUser(String name, String username, String password, String email) {
        User user= new User (name,username,password);
        user.setEmail (email);
        user.setUserRoles (UserRoles.USER);
        userRepository.save (user);
    }

    public void addAdmin(String name, String username, String password) {
        User user= new User (name,username,password,"admin", UserRoles.ADMIN);
        userRepository.save (user);
    }

    public void addComment(String text, String userId, Long entryID){
        Optional<User> user = userRepository.findById(userId);
        Optional<Entry> entry = entryRepository.findById(entryID);
        Comment comment = new Comment (text);
        comment.setText(text);
        comment.setUser(user.get());
        comment.setEntry(entry.get());
        commentRepository.save(comment);
    }

    public List<Comment> getCommentsByCreationDate(){
        Sort sort = Sort.by(Sort.Direction.ASC, "date");
        return commentRepository.findAll(sort);
    }

    public String findIdByUsername(String name) {
        Optional<User> user = userRepository.findByUsername(name);
        return user.map(User::getId).orElse("");
    }


    public void upgrade (String id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            User foundUser = user.get();
            foundUser.setUserRoles (UserRoles.MODERATOR); // Beispielhaft wird hier die Admin-Eigenschaft auf "true" gesetzt
            userRepository.save(foundUser);
        } else {
            // Fehlerbehandlung, falls der Benutzer nicht gefunden wurde
        }
    }

    public void downgrade (String id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            User foundUser = user.get();
            foundUser.setUserRoles (UserRoles.USER); // Beispielhaft wird hier die Admin-Eigenschaft auf "true" gesetzt
            userRepository.save(foundUser);
        } else {
            // Fehlerbehandlung, falls der Benutzer nicht gefunden wurde
        }
    }


    public List<User> getAllUsers() {
        return userRepository.findAll ();
    }
    public void deleteComment(Long commentId){
        commentRepository.deleteById(commentId);
    }
    @Transactional
    public void deleteEntry(Long id) {
        Entry entry = entryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid entry id"));
        commentRepository.deleteAllByEntry(entry);
        entryRepository.delete(entry);
    }
    public void updateEntry(Long id, String title, String text) {
        Optional<Entry> optionalEntry = entryRepository.findById(id);
        if (optionalEntry.isPresent()) {
            Entry entry = optionalEntry.get();
            entry.setTitle(title);
            entry.setText(text);
            entryRepository.save(entry);
        }
    }

    public Entry getEntryById(Long id) {
        Optional<Entry> optionalEntry = entryRepository.findById(id);
        return optionalEntry.orElse(null);
    }

}
