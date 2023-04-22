package com.javadreamteam.shelteranimalbot.repository;

import com.javadreamteam.shelteranimalbot.model.ClientCat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientCatRepository extends JpaRepository<ClientCat, Long> {
    ClientCat findByChatId(Long chatId);
}
