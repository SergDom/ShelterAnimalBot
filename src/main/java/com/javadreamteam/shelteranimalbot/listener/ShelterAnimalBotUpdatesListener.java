package com.javadreamteam.shelteranimalbot.listener;

import static com.javadreamteam.shelteranimalbot.keyboard.KeyboardConstant.*;
import static com.javadreamteam.shelteranimalbot.keyboard.KeyboardShelter.*;

import com.javadreamteam.shelteranimalbot.keyboard.KeyboardShelter;
import com.javadreamteam.shelteranimalbot.model.Volunteer;
import com.javadreamteam.shelteranimalbot.service.VolunteerService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ShelterAnimalBotUpdatesListener implements UpdatesListener {
    private static final Logger logger = LoggerFactory.getLogger(ShelterAnimalBotUpdatesListener.class);

    private final TelegramBot telegramBot;

    private final KeyboardShelter keyboardShelter;

    private final VolunteerService volunteerService;


    public ShelterAnimalBotUpdatesListener(TelegramBot telegramBot, KeyboardShelter keyboardShelter, VolunteerService volunteerService) {
        this.telegramBot = telegramBot;
        this.keyboardShelter = keyboardShelter;
        this.volunteerService = volunteerService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            String nameUser = update.message().chat().firstName();
            String textUpdate = update.message().text();
            Integer messageId = update.message().messageId();
            long chatId = update.message().chat().id();
            try {

                switch (textUpdate) {

                    case START:
                        sendMessage(chatId, nameUser + WELCOME);
                        keyboardShelter.sendMenu(chatId);
                        break;
// Главное меню
                    case ABOUT_BOT:
                        sendMessage(chatId, INFO_ABOUT_BOT);
                        break;

                    case ABOUT_SHELTER:
                        keyboardShelter.menuInfoShelter(chatId);
                        try {
                            keyboardShelter.sendPhoto(chatId, "/static/shelter/SHELTER_LOGO.png", SHELTER_ABOUT);
                        } catch (URISyntaxException | IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
// Меню о приюте
                    case CONTACTS:
                        sendMessage(chatId, SHELTER_CONTACTS);
                        break;

                    case RULES_SHELTER:
                        try {
                            keyboardShelter.sendPhoto(chatId, "/static/shelter/SHELTER_RULES.png", SHELTER_RULES);
                        } catch (URISyntaxException | IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
// Меню как взять питомца
                    case HOW_ADOPT:
                        keyboardShelter.menuTakeAnimal(chatId);
                        break;

                    case ADVISE:
                        sendMessage(chatId, nameUser + WELCOME);
                        keyboardShelter.menuAdviseAnimal(chatId);
                        break;

                    case DOCUMENT_LIST:
                        sendMessage(chatId, DOCUMENTS);
                        break;
                    case REFUSE_REASONS:
                        sendMessage(chatId, REFUSE);
                        break;
                    case TRANSPORTATION:
                        try {
                            keyboardShelter.sendDocument(update, "/advice/" +"TRANSPORTATION_INFO.pdf", TRANSPORT);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;

                    case CYNOLOGIST_INFO:
                        try {
                            keyboardShelter.sendDocument(update, "/advice/" +"CYNOLOGIST_INFO.pdf", CYNOLOGIST);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;

                    case CYNOLOGIST_LIST_INFO:
                        try {
                            keyboardShelter.sendDocument(update, "/advice/" +"CYNOLOGIST_LIST_INFO.pdf", CYNOLOGIST_LIST);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;

                    case PUPPY_INFO:
                        try {
                            keyboardShelter.sendDocument(update, "/advice/" +"PUPPY_INFO.pdf", PUPPY);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        break;
                    case ADULT_INFO:
                        sendMessage(chatId, ADULT);
                        break;
                    case DISABLED_INFO:
                        sendMessage(chatId, DISABLE_PET);
                        break;
// Общие кнопки
                    case REQUEST_VOLUNTEER:
                        sendMessage(chatId, CALL_VOLUNTEERS);
                        callVolunteer(update);
                        break;

                    case MAIN_MENU:
                        keyboardShelter.sendMenu(chatId);
                        break;

                    case "":
                        System.out.println("Нельзя");
                        sendMessage(chatId, "Пустое сообщение");
                        break;

                    default:
                        sendReplyMessage(chatId, "Я не знаю такой команды", messageId);
                        break;
                }
            } catch (Exception e) {
                logger.error((e.getMessage()), e);
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }


    public void sendReplyMessage(Long chatId, String messageText, Integer messageId) {
        SendMessage sendMessage = new SendMessage(chatId, messageText);
        sendMessage.replyToMessageId(messageId);
        telegramBot.execute(sendMessage);
    }

    public void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);
        telegramBot.execute(message);
    }

    /**
     * Генерирует и отправляет сообщение волонтеру из БД
     * Если {@code @username} посетитель вызывает его по имени {@code @username}.
     * Иначе указан {@code chat_id}.
     * Если БД волонтера пуста отправляется сообщение {@code NO_VOLUNTEERS_TEXT}.
     *
     * @param update
     */
    private void callVolunteer(Update update) {
        String userId = ""; // guest's chat_id or username
        long chatId = 0; // volunteer's chat_id
        userId += update.message().from().id();
        logger.info("UserId = {}", userId);
        List <Volunteer> volunteer = volunteerService.getRandomVolunteer();
        chatId = Long.parseLong(userId);
        if (volunteer == null) {
            // Guest chat_id. Send message to the guest.
            SendMessage message = new SendMessage(chatId, NO_VOLUNTEERS_TEXT);
            telegramBot.execute(message);
        } else {
            // Volunteer chat_id. Send message to volunteer.
            if (update.message().from().username() != null) {
                userId = "@" + update.message().from().username();
                SendMessage message = new SendMessage(chatId, String.format(CONTACT_TELEGRAM_USERNAME_TEXT, userId));
                telegramBot.execute(message);
            } else {
                SendMessage message = new SendMessage(chatId, String.format(CONTACT_TELEGRAM_ID_TEXT, userId));
                telegramBot.execute(message);
            }
        }
    }
}
