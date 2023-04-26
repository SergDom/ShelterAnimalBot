package com.javadreamteam.shelteranimalbot.keyboard;

import com.javadreamteam.shelteranimalbot.listener.ShelterAnimalBotUpdatesListener;
import com.javadreamteam.shelteranimalbot.model.Volunteer;
import com.javadreamteam.shelteranimalbot.repository.VolunteerRepository;
import com.javadreamteam.shelteranimalbot.service.VolunteerService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.ForwardMessage;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static com.javadreamteam.shelteranimalbot.keyboard.KeyboardConstant.*;


@Service
public class KeyboardShelter {
    private static final Logger logger = LoggerFactory.getLogger(ShelterAnimalBotUpdatesListener.class);

    // константы для кнопок
    public static final String ABOUT_BOT = "Информация о боте";
    public static final String ABOUT_SHELTER = "Информация о приюте";
    public static final String HOW_ADOPT = "Как взять питомца";
    public static final String SEND_REPORT = "Прислать отчет о питомце";
    public static final String REPORT_FORM = "Форма отчета";
    public static final String REPORT_STORE = "Отправить отчет";



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

    private static final long telegramChatVolunteer = 426343815L; //SergeyD

    private final TelegramBot telegramBot;
    private final VolunteerRepository volunteerRepository;
    private final VolunteerService volunteerService;




    public KeyboardShelter(TelegramBot telegramBot,
                           VolunteerRepository volunteerRepository, VolunteerService volunteerService) {
        this.telegramBot = telegramBot;

        this.volunteerRepository = volunteerRepository;
        this.volunteerService = volunteerService;

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

    public void menuReport(long chatId) {
        logger.info("Method menuReport has been run: {}, {}", chatId, "вызвали Меню отчета");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton(REPORT_FORM),
                new KeyboardButton(REPORT_STORE));
        replyKeyboardMarkup.addRow(
                new KeyboardButton(REQUEST_VOLUNTEER));
        replyKeyboardMarkup.addRow(new KeyboardButton(MAIN_MENU));
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, SEND_REPORT);
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

    public void sendDocument(Update update, String name, String text) throws IOException{
        String path = "src/main/resources/static/";
        File doc = new File(path + name);
        SendDocument sendDocument = new SendDocument(update.message().chat().id(), doc);
        sendDocument.caption(text);
        telegramBot.execute(sendDocument);
    }


//    public void sendForwardMessage(Long chatId, Integer messageId) {
//        ForwardMessage forwardMessage = new ForwardMessage(chatId ,chatId, messageId);
//        volunteerService.getById();
//        volunteerRepository.getRandomVolunteer();
//        telegramBot.execute(forwardMessage);
//    }


    /**
     * Генерирует и отправляет сообщение волонтеру из БД
     * Если {@code @username} посетитель вызывает его по имени {@code @username}.
     * Иначе указан {@code chat_id}.
     * Если БД волонтера пуста отправляется сообщение {@code NO_VOLUNTEERS_TEXT}.
     *
     * @param update
     */


    public void callVolunteer(Update update) {
        String userId = ""; // guest's chat_id or username
        long chatId = 0; // volunteer's chat_id
        userId += update.message().from().id();
        logger.info("UserId = {}", userId);
//        Random random = new Random();
//        Pageable pageable = PageRequest.of(random.nextLong(( volunteerRepository.count()), 1);
//        Volunteer randomVolunteer = volunteerRepository.findAll(pageable).getContent().get(0);
        List <Volunteer> volunteer = volunteerService.getRandomVolunteer();
        chatId = Long.parseLong(userId);
        if (volunteer == null) {
            // Guest chat_id. Send message to the guest.
            SendMessage message = new SendMessage(chatId, NO_VOLUNTEERS_TEXT);
            telegramBot.execute(message);
        } else {
            // Volunteer chat_id. Send message to volunteer
//            chatId = volunteer.stream().map(Volunteer::getChatId).mapToLong();
//            if (update.message().from().username() != null) {
                userId = "@" + update.message().from().username();
                SendMessage message = new SendMessage(chatId, String.format(CONTACT_TELEGRAM_USERNAME_TEXT, userId));
                telegramBot.execute(message);


        }
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
