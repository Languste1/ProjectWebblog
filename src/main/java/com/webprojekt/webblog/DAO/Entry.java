package com.webprojekt.webblog.DAO;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity(name = "entry")
@Table
public class Entry {

    @Id
    @SequenceGenerator(
            name= "entry_sequence",
            sequenceName = "entry_sequence",
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
            name = "entry_date",
            nullable = false
    )
    private LocalDate date;

    @Column(
            name = "text",
            nullable = false
    )
    private String text;

    @ManyToOne
    @JoinColumn(name = "idUser", nullable = false)
    private User user;


}
