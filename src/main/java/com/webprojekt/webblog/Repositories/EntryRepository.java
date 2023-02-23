package com.webprojekt.webblog.Repositories;

import com.webprojekt.webblog.DAO.Entry;
import com.webprojekt.webblog.DAO.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntryRepository extends JpaRepository<Entry,Long> {

}