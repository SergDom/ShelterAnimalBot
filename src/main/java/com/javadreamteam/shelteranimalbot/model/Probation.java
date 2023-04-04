package com.javadreamteam.shelteranimalbot.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
public class Probation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private LocalDate finish;
    private boolean Successful;
    private String volunteersComment;
    private boolean needToSentVolunteersComment;

    @OneToOne
    private Animal animal;

    @ManyToOne
    private Adopter adopter;

    @OneToMany
    private List<Report> reports;

    public Probation() {
    }

    public LocalDate getFinish() {
        return finish;
    }

    public void setFinish(LocalDate finish) {
        this.finish = finish;
    }

    public boolean isSuccessful() {
        return Successful;
    }

    public void setSuccessful(boolean successful) {
        Successful = successful;
    }

    public String getVolunteersComment() {
        return volunteersComment;
    }

    public void setVolunteersComment(String volunteersComment) {
        this.volunteersComment = volunteersComment;
    }

    public boolean isNeedToSentVolunteersComment() {
        return needToSentVolunteersComment;
    }

    public void setNeedToSentVolunteersComment(boolean needToSentVolunteersComment) {
        this.needToSentVolunteersComment = needToSentVolunteersComment;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Adopter getAdopter() {
        return adopter;
    }

    public void setAdopter(Adopter adopter) {
        this.adopter = adopter;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Probation probation = (Probation) o;
        return Successful == probation.Successful && needToSentVolunteersComment == probation.needToSentVolunteersComment
                && Objects.equals(finish, probation.finish) && Objects.equals(volunteersComment, probation.volunteersComment)
                && Objects.equals(animal, probation.animal) && Objects.equals(adopter, probation.adopter)
                && Objects.equals(reports, probation.reports);
    }

    @Override
    public int hashCode() {
        return Objects.hash(finish, Successful, volunteersComment, needToSentVolunteersComment, animal, adopter, reports);
    }
}
