package com.webprojekt.webblog.DAO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    public Session(User user, Instant expiresAt) {
        this.user = user;
        this.expiresAt = expiresAt;
    }
}
