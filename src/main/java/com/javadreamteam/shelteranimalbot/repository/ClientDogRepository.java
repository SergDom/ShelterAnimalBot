package com.javadreamteam.shelteranimalbot.repository;

import com.javadreamteam.shelteranimalbot.model.Client;
import com.javadreamteam.shelteranimalbot.model.ClientDog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientDogRepository extends JpaRepository<ClientDog, Long> {

    ClientDog findByChatId(Long chatId);

}
