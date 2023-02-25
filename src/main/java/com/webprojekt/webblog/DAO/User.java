package com.webprojekt.webblog.DAO;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Entity(name = "user")
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
            nullable = true,
            columnDefinition = "TEXT"

    )
    private String name;
    @Column(
            name = "is_user_admin"

    )
    boolean isAdmin;
    @Column(
            name = "username"
    )
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Only letters, digits and underscores allowed")
    private String username;
    @Column(
       name ="password"
    )
    @Size(min = 5, message = "your password must have at least 5 characters")
    private String password;
    @Transient
    private String password2;
    @OneToMany(mappedBy = "user")
    private List<Entry> entry;

    @OneToMany(mappedBy = "user")
    private List<Comment> comment;

    public User(String name) {
        this.name = name;
        this.isAdmin=false;
    }
//DTO Login
    public User(String username, String password1) {
        this.username = username;
        this.password = password1;
    }
//DTO registration

    public User( String username, String password1, String password2) {
        this.username = username;
        this.password = password1;
        this.password2 = password2;
    }

    public User(String name, String username, String password, String password2) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.password2 = password2;
    }
}
