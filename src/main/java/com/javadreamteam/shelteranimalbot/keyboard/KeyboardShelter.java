package com.javadreamteam.shelteranimalbot.keyboard;

import com.javadreamteam.shelteranimalbot.listener.ShelterAnimalBotUpdatesListener;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;


@Service
public class KeyboardShelter {
    private static final Logger logger = LoggerFactory.getLogger(ShelterAnimalBotUpdatesListener.class);

    // константы для кнопок
    public static final String ABOUT_BOT = "Информация о боте";
    public static final String ABOUT_SHELTER = "Информация о приюте";
    public static final String HOW_ADOPT = "Как взять питомца";
    public static final String SEND_REPORT = "Прислать отчет о питомце";


    public static final String REQUEST_VOLUNTEER = "Позвать волонтера";
    public static final String MAIN_MENU = "Вернуться в меню";
    public static final String CONTACTS = "Контакты";
    public static final String SEND_CONTACTS = "Оставить контакты";


    public static final String RULES_SHELTER = "Правила поведения";

    public static final String ADVISE = "Советы и рекомендации";
    public static final String DOCUMENT_LIST = "Документы";
    public static final String REFUSE_REASONS = "Причины отказа";

    public static final String TRANSPORTATION = "Транспортировка";
    public static final String CYNOLOGIST_INFO = "Кинологи";
    public static final String CYNOLOGIST_LIST_INFO = "Кинологи список";

    public static final String PUPPY_INFO = "Щенок";
    public static final String ADULT_INFO = "Взрослый";
    public static final String DISABLED_INFO = "С ограничениями";


    private final TelegramBot telegramBot;


    public KeyboardShelter(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;

    }

    /**
     * Основное Меню
     *
     * @param chatId
     */
    public void sendMenu(long chatId) {
        logger.info("Method sendMessage has been run: {}, {}", chatId, "Вызвано основное меню");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton(ABOUT_BOT),
                new KeyboardButton(ABOUT_SHELTER));
        replyKeyboardMarkup.addRow(new KeyboardButton(HOW_ADOPT),
                new KeyboardButton(SEND_REPORT));
        replyKeyboardMarkup.addRow(new KeyboardButton(REQUEST_VOLUNTEER));

        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "Основное меню");
    }


    /**
     * Меню о приюте
     *
     * @param chatId
     */
    public void menuInfoShelter(long chatId) {
        logger.info("Method MenuInfoShelter has been run: {}, {}", chatId, "Вызвано Информация о приюте");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton(CONTACTS),
                new KeyboardButton(RULES_SHELTER));
        replyKeyboardMarkup.addRow(
                new KeyboardButton(SEND_CONTACTS).requestContact(true),
                new KeyboardButton(REQUEST_VOLUNTEER));
        replyKeyboardMarkup.addRow(new KeyboardButton(MAIN_MENU));
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, ABOUT_SHELTER);
    }

    /**
     * Меню как взять питомца
     *
     * @param chatId
     */
    public void menuTakeAnimal(long chatId) {
        logger.info("Method menuTakeAnimal has been run: {}, {}", chatId, "вызвали Как взять питомца из приюта");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton(ADVISE));
        replyKeyboardMarkup.addRow(
                new KeyboardButton(DOCUMENT_LIST),
                new KeyboardButton(REFUSE_REASONS));
        replyKeyboardMarkup.addRow(
                new KeyboardButton(SEND_CONTACTS).requestContact(true),
                new KeyboardButton(REQUEST_VOLUNTEER));
        replyKeyboardMarkup.addRow(new KeyboardButton(MAIN_MENU));
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, HOW_ADOPT);
    }

    /**
     * Меню с рекомендациями
     *
     * @param chatId
     */
    public void menuAdviseAnimal(long chatId) {
        logger.info("Method menuAdviseAnimal has been run: {}, {}", chatId, "вызвали Советы и рекомендации");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton(TRANSPORTATION),
                new KeyboardButton(CYNOLOGIST_INFO),
                new KeyboardButton(CYNOLOGIST_LIST_INFO));
        replyKeyboardMarkup.addRow(
                new KeyboardButton(PUPPY_INFO),
                new KeyboardButton(ADULT_INFO),
                new KeyboardButton(DISABLED_INFO));
        replyKeyboardMarkup.addRow(
                new KeyboardButton(SEND_CONTACTS).requestContact(true),
                new KeyboardButton(REQUEST_VOLUNTEER));

        replyKeyboardMarkup.addRow(new KeyboardButton(MAIN_MENU));
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, ADVISE);
    }

    /**
     * Метод обработки фото и отправки в чат
     *
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

    public void sendDocument(Update update, String name, String text) throws IOException, URISyntaxException {
//        byte[] document = Files.readAllBytes(Paths.get(ShelterAnimalBotUpdatesListener.class.getResource(name).getPath()));
        String path = "/static/";
        File doc = new File(path + name);
        SendDocument sendDocument = new SendDocument(update.message().chat().id(), doc);
        sendDocument.caption(text);
        telegramBot.execute(sendDocument);
    }

    /**
     * Метод возврата в верхнее меню
     *
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
