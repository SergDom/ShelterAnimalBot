package com.javadreamteam.shelteranimalbot.repository;

import com.javadreamteam.shelteranimalbot.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
