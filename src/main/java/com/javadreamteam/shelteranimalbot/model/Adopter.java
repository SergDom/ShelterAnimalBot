package com.javadreamteam.shelteranimalbot.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Adopter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long chatId;
    private String userName;
    private String name;
    private String address;
    private int age;
    private String phoneNumber;

    @OneToMany
    private List<Animal> animals;

    @OneToMany
    private List<Probation> probations;

    private Long currentReportId;

    public Adopter() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNomber) {
        this.phoneNumber = phoneNomber;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }

    public List<Probation> getProbations() {
        return probations;
    }

    public void setProbations(List<Probation> probations) {
        this.probations = probations;
    }

    public Long getCurrentReportId() {
        return currentReportId;
    }

    public void setCurrentReportId(Long currentReportId) {
        this.currentReportId = currentReportId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adopter adopter = (Adopter) o;
        return age == adopter.age && Objects.equals(id, adopter.id) && Objects.equals(chatId, adopter.chatId) && Objects.equals(userName, adopter.userName) && Objects.equals(name, adopter.name) && Objects.equals(address, adopter.address) && Objects.equals(phoneNumber, adopter.phoneNumber) && Objects.equals(animals, adopter.animals) && Objects.equals(probations, adopter.probations) && Objects.equals(currentReportId, adopter.currentReportId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, userName, name, address, age, phoneNumber, animals, probations, currentReportId);
    }
}
