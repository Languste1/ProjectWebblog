package com.webprojekt.webblog.DAO;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    /*@OneToMany(mappedBy = "user")
    List<Session> sessions;*/
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

    public User(String name) {
        this.name = name;
        this.isAdmin=false;
    }
    @Column(name = "username",
    nullable = false, unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Only letters, digits and underscores allowed")
    private String username;

    @Column(name = "passwort",
    nullable = false)
    @Size(min = 5, message = "your password must have at least 5 characters") //Theoretisch auch max implementierbar
    private String passwort;


    @Transient //Passwort wird von Tabelle ignoriert
    private String passwort2;



    public User() {
    }

    public User(String name, String username, String passwort) {
        this.name = name;
        this.username = username;
        this.passwort = passwort;
    }

    //Das hier ist der DTO von Register

    public User(String name, String username, String passwort, String passwort2) {
        this.name = name;
        this.username = username;
        this.passwort = passwort;
        this.passwort2 = passwort2;
    }

    //Das hier ist der DTO von Login
    public User(String username, String passwort) {
        this.username = username;
        this.passwort = passwort;
    }

}
