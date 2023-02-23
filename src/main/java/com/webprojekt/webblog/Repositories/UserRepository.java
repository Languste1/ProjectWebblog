package com.webprojekt.webblog.Repositories;

import com.webprojekt.webblog.DAO.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
