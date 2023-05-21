package com.javadreamteam.shelteranimalbot.repository;

import com.javadreamteam.shelteranimalbot.model.ReportCat;
import com.javadreamteam.shelteranimalbot.model.ReportDog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ReportCatRepository extends JpaRepository<ReportCat, Long> {
    ReportCat findByChatId(Long chatId);
}
