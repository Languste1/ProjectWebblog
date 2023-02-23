package com.webprojekt.webblog.Services;

import com.webprojekt.webblog.Repositories.EntryRepository;
import com.webprojekt.webblog.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebBlogServices {
    private UserRepository userRepository;
    private EntryRepository entryRepository;

    @Autowired
    public WebBlogServices(UserRepository userRepository, EntryRepository entryRepository) {
        this.userRepository = userRepository;
        this.entryRepository = entryRepository;
    }
}
