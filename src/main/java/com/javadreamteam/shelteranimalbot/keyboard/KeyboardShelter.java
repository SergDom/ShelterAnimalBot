package com.javadreamteam.shelteranimalbot.keyboard;

import com.javadreamteam.shelteranimalbot.listener.ShelterAnimalBotUpdatesListener;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class KeyboardShelter {
    private static final Logger logger = LoggerFactory.getLogger(ShelterAnimalBotUpdatesListener.class);


    private final TelegramBot telegramBot;

    public KeyboardShelter(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    /**
     * Основное Меню
     */
    public void sendMenu(long chatId) {
        logger.info("Method sendMessage has been run: {}, {}", chatId, "Вызвано основное меню ");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton("Информация о боте"),
                new KeyboardButton("Информация о приюте"));
        replyKeyboardMarkup.addRow(new KeyboardButton("Как взять питомца из приюта"),
                new KeyboardButton("Прислать отчет о питомце"));
        replyKeyboardMarkup.addRow(new KeyboardButton("Позвать волонтера"));

        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "Основное меню");
    }
    /**
     * Меню о приюте
     */
    public void menuInfoShelter(long chatId) {
        logger.info("Method MenuInfoShelter has been run: {}, {}", chatId, "Вызвано Информация о приюте");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton("Контакты"),
                new KeyboardButton("Правила поведения"),
                new KeyboardButton("Оставить контактные данные").requestContact(true));
        replyKeyboardMarkup.addRow(new KeyboardButton("Позвать волонтера"));
        replyKeyboardMarkup.addRow(new KeyboardButton("Вернуться в меню"));
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "Информация о приюте");
    }

    /**
     * Меню как взять питомца
     */



    /**
     * Метод обработки фото и отправки в чат
     */
    public void sendPhoto (long chatId,String name,String text) throws URISyntaxException, IOException {
    byte [] image = Files.readAllBytes(
            Paths.get(ShelterAnimalBotUpdatesListener.class.getResource(name).toURI())
    );
    SendPhoto sendPhoto = new SendPhoto(chatId, image);
    sendPhoto.caption(text);
    telegramBot.execute(sendPhoto);
    }

    /**
     * Метод возврата в верхнее меню
     */
    public void returnResponseReplyKeyboardMarkup(ReplyKeyboardMarkup replyKeyboardMarkup, Long chatId, String text) {
        replyKeyboardMarkup.resizeKeyboard(true);
        replyKeyboardMarkup.oneTimeKeyboard(false);
        replyKeyboardMarkup.selective(false);
        SendMessage request = new SendMessage(chatId, text)
                .replyMarkup(replyKeyboardMarkup);
        SendResponse sendResponse = telegramBot.execute(request);
        if (!sendResponse.isOk()) {
            int codeError = sendResponse.errorCode();
            String description = sendResponse.description();
            logger.info("code of error: {}", codeError);
            logger.info("description -: {}", description);
        }
    }
}
