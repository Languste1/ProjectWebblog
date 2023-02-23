package com.webprojekt.webblog.DAO;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "user")
@Table
public class User {
    @Id
    @SequenceGenerator (
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_sequence"
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
            name = "is_user_admin",
            nullable = false
    )
    boolean isAdmin;


}
