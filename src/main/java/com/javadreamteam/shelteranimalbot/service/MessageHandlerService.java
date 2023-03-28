package com.javadreamteam.shelteranimalbot.service;

import com.pengrad.telegrambot.model.Message;

public interface MessageHandlerService {
    void handleMessage(Message message, long extractChatId);
}
