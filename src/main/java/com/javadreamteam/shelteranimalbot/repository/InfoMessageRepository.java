package com.javadreamteam.shelteranimalbot.repository;

import com.javadreamteam.shelteranimalbot.model.InfoMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfoMessageRepository extends JpaRepository<InfoMessage, String> {
}
