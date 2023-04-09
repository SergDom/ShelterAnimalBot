package com.javadreamteam.shelteranimalbot.model;


import javax.persistence.*;
import java.util.Objects;

@Entity
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String color;
    private String species;
    private int age;
    private String uniqueCharacteristics;
    private String specialNeeds;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Shelter shelter;

    @OneToOne
    private Probation probation;

    public Animal() {
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUniqueCharacteristics() {
        return uniqueCharacteristics;
    }

    public void setUniqueCharacteristics(String uniqueCharacteristics) {
        this.uniqueCharacteristics = uniqueCharacteristics;
    }

    public String getSpecialNeeds() {
        return specialNeeds;
    }

    public void setSpecialNeeds(String specialNeeds) {
        this.specialNeeds = specialNeeds;
    }

    public Client getAdopter() {
        return client;
    }

    public void setAdopter(Client client) {
        this.client = client;
    }

    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }

    public Probation getProbation() {
        return probation;
    }

    public void setProbation(Probation probation) {
        this.probation = probation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return age == animal.age && Objects.equals(id, animal.id) && Objects.equals(name, animal.name) && Objects.equals(color, animal.color) && Objects.equals(species, animal.species) && Objects.equals(uniqueCharacteristics, animal.uniqueCharacteristics) && Objects.equals(specialNeeds, animal.specialNeeds) && Objects.equals(client, animal.client) && Objects.equals(shelter, animal.shelter) && Objects.equals(probation, animal.probation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, color, species, age, uniqueCharacteristics, specialNeeds, client, shelter, probation);
    }
}
