package com.javadreamteam.shelteranimalbot.model;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

import com.javadreamteam.shelteranimalbot.model.photo.*;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private LocalDate date;
    private String entry;
    private String photoId;

    @OneToMany(mappedBy = "report")
    private Collection<ReportPhoto> reportPhoto;

    @ManyToOne
    @JoinColumn(name = "client_dog_id")
    private ClientDog clientDog;

}
