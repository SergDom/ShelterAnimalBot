package com.javadreamteam.shelteranimalbot.model;


import com.javadreamteam.shelteranimalbot.model.photo.ReportPhoto;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**
     * Идентификатор записи, primary key
     */
    private Long id;

    /**
     * Дата отправки отчета
     */
    private LocalDateTime dateReport;
    /**
     * Идентификатор чата хозяина животного, отправившего отчет
     */
    private Long chatId;
    /**
     * Текст отчета
     */
    private String textReport;

    /**
     * Фото петомца
     */
    @OneToMany(mappedBy = "report")
    private Collection<ReportPhoto> reportPhoto;

    /**
     * id питомца
     */
    @ManyToOne
    @JoinColumn(name = "dog_id")
    private Dog dog;

    public Report() {
    }



}
