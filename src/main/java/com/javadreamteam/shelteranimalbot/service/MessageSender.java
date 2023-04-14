package com.javadreamteam.shelteranimalbot.service;


import com.javadreamteam.shelteranimalbot.repository.InfoMessageRepository;
import com.pengrad.telegrambot.TelegramBot;
import org.springframework.stereotype.Service;

@Service
public class MessageSender {
    
    private final InfoMessageRepository infoMessageRepository;
    private static TelegramBot telegramBot;

    public MessageSender(InfoMessageRepository infoMessageRepository) {
        this.infoMessageRepository = infoMessageRepository;
    }

    public static void setTelegramBot(TelegramBot telegramBot) {
        MessageSender.telegramBot = telegramBot;
    }
}
