package com.webprojekt.webblog.DAO;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity(name = "entry")
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    @Nullable
    @Column(
            name = "title"
    )
    private String title;
    @Column(
            name = "entry_date",
            nullable = false,
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime date;

    @Column(
            name = "text",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String text;

    @ManyToOne
    @JoinColumn(
            name = "idUser",
            nullable = false
    )
    private User user;
    

    public Entry( String text) {
        this.date= LocalDateTime.now ();
        this.text = text;
    }


}
