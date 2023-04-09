package com.javadreamteam.shelteranimalbot.model;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private LocalDate date;
    private String entry;
    private String photoId;


}
