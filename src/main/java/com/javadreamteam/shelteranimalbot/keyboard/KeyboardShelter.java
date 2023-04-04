package com.javadreamteam.shelteranimalbot.keyboard;

import com.javadreamteam.shelteranimalbot.listener.ShelterAnimalBotUpdatesListener;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.*;
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
     * @param chatId
     */
    public void sendMenu(long chatId) {
        logger.info("Method sendMessage has been run: {}, {}", chatId, "Вызвано основное меню");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton("Информация о боте"),
                new KeyboardButton("Информация о приюте"));
        replyKeyboardMarkup.addRow(new KeyboardButton("Как взять питомца"),
                new KeyboardButton("Прислать отчет о питомце"));
        replyKeyboardMarkup.addRow(new KeyboardButton("Позвать волонтера"));

        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "Основное меню");
    }


    /**
     * Меню о приюте
     * @param chatId
     */
    public void menuInfoShelter(long chatId) {
        logger.info("Method MenuInfoShelter has been run: {}, {}", chatId, "Вызвано Информация о приюте");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton("Контакты"),
                new KeyboardButton("Правила поведения"));
        replyKeyboardMarkup.addRow(
                new KeyboardButton("Оставить контакты").requestContact(true),
                new KeyboardButton("Позвать волонтера"));
        replyKeyboardMarkup.addRow(new KeyboardButton("Вернуться в меню"));
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "Информация о приюте");
    }

    /**
     * Меню как взять питомца
     * @param chatId
     */
    public void menuTakeAnimal(long chatId) {
        logger.info("Method menuTakeAnimal has been run: {}, {}", chatId, "вызвали Как взять питомца из приюта");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton("Советы и рекомендации"));
        replyKeyboardMarkup.addRow(
                new KeyboardButton("Оставить контакты").requestContact(true),
                new KeyboardButton("Позвать волонтера"));
        replyKeyboardMarkup.addRow(new KeyboardButton("Вернуться в меню"));
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "Как взять питомца");
    }

    /**
     * Меню с рекомендациями
     * @param chatId
     */
    public void menuAdviseAnimal(long chatId) {
        logger.info("Method menuAdviseAnimal has been run: {}, {}", chatId, "вызвали Советы и рекомендации");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton("Документы"),
                new KeyboardButton("Причины отказа"));
        replyKeyboardMarkup.addRow(
                new KeyboardButton("Транспортировка"),
                new KeyboardButton("Кинологи"),
                new KeyboardButton("Кинологи дома"));
        replyKeyboardMarkup.addRow(
                new KeyboardButton("Щенок"),
                new KeyboardButton("Взрослый"),
                new KeyboardButton("С ограничениями"));
        replyKeyboardMarkup.addRow(
                new KeyboardButton("Оставить контакты").requestContact(true),
                new KeyboardButton("Позвать волонтера"));

        replyKeyboardMarkup.addRow(new KeyboardButton("Вернуться в меню"));
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "Советы и рекомендации");
    }

    /**
     * Метод обработки фото и отправки в чат
     * @param chatId
     * @param name
     * @param text
     */
    public void sendPhoto(long chatId, String name, String text) throws URISyntaxException, IOException {
        byte[] image = Files.readAllBytes(
                Paths.get(ShelterAnimalBotUpdatesListener.class.getResource(name).toURI())
        );
        SendPhoto sendPhoto = new SendPhoto(chatId, image);
        sendPhoto.caption(text);
        telegramBot.execute(sendPhoto);
    }

    /**
     * Метод возврата в верхнее меню
     * @param replyKeyboardMarkup
     * @param chatId
     * @param text
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
