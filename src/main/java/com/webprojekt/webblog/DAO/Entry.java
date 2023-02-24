package com.webprojekt.webblog.DAO;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

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
            generator = "entry_sequence"
    )
    @Column(
            nullable = false
    )
    private long id;

    @Column(
            name = "entry_date",
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

    @OneToMany(mappedBy = "entry")
    private List<Comment> comment;

    public Entry( String text) {
        this.date= LocalDateTime.now ();
        this.text = text;
    }

    public Entry() {
    }
}
