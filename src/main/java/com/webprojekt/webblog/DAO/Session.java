package com.webprojekt.webblog.DAO;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
/*import java.util.UUID;

@Table
@Data
@Entity
public class Session {
    @Id
    private String id = UUID.randomUUID().toString();

    @ManyToOne
    private User user;

    @Column(name = "expireAt",
    nullable = false)
    private Instant expiresAt;

    public Session () {

    }

    public Session(User user, Instant expiresAt) {
        this.user = user;
        this.expiresAt = expiresAt;
    }
}
*/