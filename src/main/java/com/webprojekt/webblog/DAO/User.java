package com.webprojekt.webblog.DAO;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity(name = "user")
@Table
public class User {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @Column(
            nullable = false
    )
    private long id;
    @Column(
            name = "name",
            nullable = false,
            columnDefinition = "TEXT"

    )
    private String name;
    @Column(
            name = "is_user_admin"

    )
    boolean isAdmin;

    @OneToMany(mappedBy = "user")
    private List<Entry> entry;

    @OneToMany(mappedBy = "user")
    private List<Comment> comment;

    public User(String name) {
        this.name = name;
        this.isAdmin=false;
    }

    public User() {
    }
}
