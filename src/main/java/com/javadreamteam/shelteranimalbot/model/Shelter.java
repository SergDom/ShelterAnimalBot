package com.javadreamteam.shelteranimalbot.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class Shelter {

    @Id
    private String number;

    private String name;
    private String address;
    private String phoneNumber;
    private String timeTable;

    private ShelterType shelterType;

    @OneToMany
    private Set<Animal> animals;

    public Shelter() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String namber) {
        this.number = namber;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTimeTable() {
        return timeTable;
    }

    public void setTimeTable(String timeTable) {
        this.timeTable = timeTable;
    }

    public ShelterType getShelterType() {
        return shelterType;
    }

    public void setShelterType(ShelterType shelterType) {
        this.shelterType = shelterType;
    }

    public Set<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(Set<Animal> animals) {
        this.animals = animals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelter shelter = (Shelter) o;
        return Objects.equals(number, shelter.number) && Objects.equals(name, shelter.name) && Objects.equals(address, shelter.address) && Objects.equals(phoneNumber, shelter.phoneNumber) && Objects.equals(timeTable, shelter.timeTable) && Objects.equals(shelterType, shelter.shelterType) && Objects.equals(animals, shelter.animals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, name, address, phoneNumber, timeTable, shelterType, animals);
    }
}
