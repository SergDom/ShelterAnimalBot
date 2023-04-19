package com.javadreamteam.shelteranimalbot.model;

import com.javadreamteam.shelteranimalbot.model.photo.DogPhoto;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "dog")
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "breed")
    private String breed;

    @Column(name = "age")
    private Integer age;

    @Column(name = "info")
    private String info;

    @OneToMany(mappedBy = "dog")
    private Collection<DogPhoto> dogPhoto;

    @OneToMany(mappedBy = "dog")
    private Collection<Report> report;

    public Dog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Collection<DogPhoto> getDogPhoto() {
        return dogPhoto;
    }

    public void setDogPhoto(Collection<DogPhoto> dogPhoto) {
        this.dogPhoto = dogPhoto;
    }

    public Collection<Report> getReport() {
        return report;
    }

    public void setReport(Collection<Report> report) {
        this.report = report;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dog dog = (Dog) o;
        return Objects.equals(id, dog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Dog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", breed='" + breed + '\'' +
                ", age=" + age +
                ", info='" + info + '\'' +
                '}';
    }
}
