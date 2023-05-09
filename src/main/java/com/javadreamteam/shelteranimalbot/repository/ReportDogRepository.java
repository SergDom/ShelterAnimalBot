package com.javadreamteam.shelteranimalbot.repository;

import com.javadreamteam.shelteranimalbot.model.ReportDog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportDogRepository extends JpaRepository<ReportDog, Long> {
    ReportDog findByChatId(Long chatId);
}
