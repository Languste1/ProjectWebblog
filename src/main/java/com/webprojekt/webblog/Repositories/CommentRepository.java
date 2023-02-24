package com.webprojekt.webblog.Repositories;

import com.webprojekt.webblog.DAO.Comment;
import com.webprojekt.webblog.DAO.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {


}
