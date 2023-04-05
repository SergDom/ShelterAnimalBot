package com.javadreamteam.shelteranimalbot.service;

import com.javadreamteam.shelteranimalbot.exceptions.DogException;
import com.javadreamteam.shelteranimalbot.model.Dog;
import com.javadreamteam.shelteranimalbot.repository.DogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Service

public class DogService {
private final DogRepository repository;
private static final Logger logger = LoggerFactory.getLogger(DogService.class);

    public DogService(DogRepository dogRepository) {
        this.repository = dogRepository;
    }

    public Dog create(Dog dog) {
        logger.info("Was invoked method to create a dog");
        return repository.save(dog);
    }
    public Dog getById(Long id) {
        logger.info("Was invoked method to get a dog by id={}", id);
        return repository.findById(id).orElseThrow(DogException::new);
    }
    public Dog update(Dog dog) {
        logger.info("Was invoked method to update a dog");
        if (dog.getId() != null) {
            if (getById(dog.getId()) != null) {
                return repository.save(dog);
            }
        }
        throw new DogException();
    }

    public void removeById(Long id) {
        logger.info("Was invoked method to remove a cat by id={}", id);
        repository.deleteById(id);
    }

    public Collection<Dog> getAll() {
        logger.info("Was invoked method to get all dogs");
        return repository.findAll();
    }
}
