package com.javadreamteam.shelteranimalbot.repository;

import com.javadreamteam.shelteranimalbot.model.ClientDog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ClientDogRepository extends JpaRepository <ClientDog, Long> {

    ClientDog findByChatId(Long chatId);

}
