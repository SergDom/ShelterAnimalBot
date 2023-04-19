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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateReport() {
        return dateReport;
    }

    public void setDateReport(LocalDateTime dateReport) {
        this.dateReport = dateReport;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getTextReport() {
        return textReport;
    }

    public void setTextReport(String textReport) {
        this.textReport = textReport;
    }

    public Collection<ReportPhoto> getReportPhoto() {
        return reportPhoto;
    }

    public void setReportPhoto(Collection<ReportPhoto> reportPhoto) {
        this.reportPhoto = reportPhoto;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }
}
