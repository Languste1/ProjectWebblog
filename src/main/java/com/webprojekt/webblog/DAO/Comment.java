package com.webprojekt.webblog.DAO;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity(name = "comment")
@Table
public class Comment {

    @Id
    @SequenceGenerator(
            name= "comment_sequence",
            sequenceName = "comment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "comment_sequence"
    )
    @Column(
            nullable = false
    )
    private long id;

    @Column(
            name = "comment_date",
            nullable = false,
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime date;

    @Column(
            name = "text",
            nullable = false
    )
    private String text;

    @ManyToOne
    @JoinColumn(name = "idUser", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "idEntry", nullable = false)
    private Entry entry;

    public Comment( String text) {
        this.date= LocalDateTime.now ();
        this.text = text;
    }

    public Comment() {
    }
}
