package com.webprojekt.webblog.DAO;

import com.webprojekt.webblog.Security.UserRoles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class User implements UserDetails {
    @Id
    @GeneratedValue(
            strategy = GenerationType.UUID
    )
    @Column(
            nullable = false
    )
    private String id;

    @Column(
            name = "name",
            nullable = false,
            columnDefinition = "TEXT"

    )
    private String name;

    @OneToMany(mappedBy = "user")
    private List<Entry> entry;

    public User(String name) {
        this.name = name;
    }
    @Column(name = "username",
    nullable = false, unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Only letters, digits and underscores allowed")
    private String username;

    @Column(name = "passwort",
    nullable = false)
    @Size(min = 5, message = "your password must have at least 5 characters") //Theoretisch auch max implementierbar
    private String password;
    @Column(
            name = "email",
            columnDefinition = "TEXT"
    )
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRoles userRoles;

    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of (new SimpleGrantedAuthority (userRoles.name ()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
