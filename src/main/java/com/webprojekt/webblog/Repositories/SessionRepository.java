package com.webprojekt.webblog.Repositories;

import com.webprojekt.webblog.DAO.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, String> {
    Optional<Session> findByIdAndExpiresAtAfter(String id, Instant expiresAt);

}
