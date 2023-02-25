package com.webprojekt.webblog.BussinesLayer;

import com.webprojekt.webblog.DAO.Comment;
import com.webprojekt.webblog.DAO.Entry;
import com.webprojekt.webblog.DAO.Session;
import com.webprojekt.webblog.DAO.User;
import com.webprojekt.webblog.Repositories.CommentRepository;
import com.webprojekt.webblog.Repositories.EntryRepository;
import com.webprojekt.webblog.Repositories.SessionRepository;
import com.webprojekt.webblog.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class WebBlogServices {
    private UserRepository userRepository;
    private EntryRepository entryRepository;

    private CommentRepository commentRepository;
    private SessionRepository sessionRepository;
    @Autowired
    public WebBlogServices(UserRepository userRepository, EntryRepository entryRepository, CommentRepository commentRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.entryRepository = entryRepository;
        this.commentRepository = commentRepository;
        this.sessionRepository = sessionRepository;
    }



    // Wird eine Liste von Entries nach Datum sortiert
    public List<Entry> getEntriesByCreationDate() {
        Sort sort = Sort.by (Sort.Direction.DESC, "date"); // Sort wird benutz, um Listen von Objekten in DatenBanken zu sortieren
        return entryRepository.findAll (sort);
    }

    public List<Comment> getCommentsByCreationDate(){
        Sort sort = Sort.by(Sort.Direction.ASC, "date");
        return commentRepository.findAll(sort);
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

    public void addComment(String text, Long userId, Long entryID){
        Optional<User> user = userRepository.findById(userId);
        Optional<Entry> entry = entryRepository.findById(entryID);
        Comment comment = new Comment(text);
        comment.setText(text);
        comment.setUser(user.get());
        comment.setEntry(entry.get());
        commentRepository.save(comment);
    }


    public void addUser(String name, String password) {
        User user = new User (name);
        user.setName (name);
        user.setPassword (password);
        userRepository.save (user);
    }


    public Optional<User> findByUsernameAndPassword(String username, String password1) {
        return userRepository.findByUsernameAndPassword (username,password1);
    }

    public Optional<Session> findByIdAndExpiresAtAfter(String sessionId, Instant now) {
        return sessionRepository.findByIdAndExpiresAtAfter (sessionId,now);
    }

    public void delete(Session session) {
        sessionRepository.delete (session);
    }

    public void addSession(Session session) {
        sessionRepository.save (session);
    }

    public boolean existsByUsername(String username) {
      return userRepository.existsByUsername (username);
    }

    public void addUser(User user) {
        userRepository.save (user);
    }
}