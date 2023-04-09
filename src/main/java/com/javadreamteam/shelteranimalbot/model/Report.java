package com.javadreamteam.shelteranimalbot.model;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;

import com.javadreamteam.shelteranimalbot.model.photo.*;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private String entry;

    @OneToMany(mappedBy = "report")
    private Collection<ReportPhoto> reportPhoto;

    @ManyToOne
    @JoinColumn(name = "client_dog_id")
    private ClientDog clientDog;

    public Report() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public Collection<ReportPhoto> getReportPhoto() {
        return reportPhoto;
    }

    public void setReportPhoto(Collection<ReportPhoto> reportPhoto) {
        this.reportPhoto = reportPhoto;
    }

    public ClientDog getClientDog() {
        return clientDog;
    }

    public void setClientDog(ClientDog clientDog) {
        this.clientDog = clientDog;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return Objects.equals(id, report.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
