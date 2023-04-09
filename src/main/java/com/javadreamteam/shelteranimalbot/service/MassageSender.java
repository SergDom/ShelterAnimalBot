package com.javadreamteam.shelteranimalbot.service;


import com.javadreamteam.shelteranimalbot.repository.InfoMassageRepository;
import com.pengrad.telegrambot.TelegramBot;
import org.springframework.stereotype.Service;

@Service
public class MassageSender {


    private final InfoMassageRepository infoMassageRepository;
    private static TelegramBot telegramBot;

    public MassageSender( InfoMassageRepository infoMassageRepository) {
        this.infoMassageRepository = infoMassageRepository;
    }

    public static void setTelegramBot(TelegramBot telegramBot) {
        MassageSender.telegramBot = telegramBot;
    }
}
