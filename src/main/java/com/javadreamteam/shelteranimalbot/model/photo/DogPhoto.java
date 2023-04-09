package com.javadreamteam.shelteranimalbot.model.photo;

import com.javadreamteam.shelteranimalbot.model.Dog;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "dog_photo")
public class DogPhoto extends Photo {

    @ManyToOne
    @JoinColumn(name = "dog_id")
    private Dog dog;
}
