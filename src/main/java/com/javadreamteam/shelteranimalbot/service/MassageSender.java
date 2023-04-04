package com.javadreamteam.shelteranimalbot.service;


import com.javadreamteam.shelteranimalbot.repository.AnimalRepository;
import com.javadreamteam.shelteranimalbot.repository.InfoMassageRepository;
import com.pengrad.telegrambot.TelegramBot;
import org.springframework.stereotype.Service;

@Service
public class MassageSender {

    private final AnimalRepository animalRepository;
    private final InfoMassageRepository infoMassageRepository;
    private static TelegramBot telegramBot;

    public MassageSender(AnimalRepository animalRepository, InfoMassageRepository infoMassageRepository) {
        this.animalRepository = animalRepository;
        this.infoMassageRepository = infoMassageRepository;
    }

    public static void setTelegramBot(TelegramBot telegramBot) {
        MassageSender.telegramBot = telegramBot;
    }
}
