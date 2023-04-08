package com.javadreamteam.shelteranimalbot.listener;

import static com.javadreamteam.shelteranimalbot.keyboard.KeyboardConstant.*;
import static com.javadreamteam.shelteranimalbot.keyboard.KeyboardShelter.*;

import com.javadreamteam.shelteranimalbot.keyboard.KeyboardShelter;
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


@Service
public class ShelterAnimalBotUpdatesListener implements UpdatesListener {
    private static final Logger logger = LoggerFactory.getLogger(ShelterAnimalBotUpdatesListener.class);

    private final TelegramBot telegramBot;

    private final KeyboardShelter keyboardShelter;


    public ShelterAnimalBotUpdatesListener(TelegramBot telegramBot, KeyboardShelter keyboardShelter) {
        this.telegramBot = telegramBot;
        this.keyboardShelter = keyboardShelter;
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
                            keyboardShelter.sendPhoto(chatId, "/static/SHELTER_LOGO.png", SHELTER_ABOUT);
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
                            keyboardShelter.sendPhoto(chatId, "/static/SHELTER_RULES.png", SHELTER_RULES);
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
                        sendMessage(chatId, TRANSPORT);
                        break;
                    case CYNOLOGIST_INFO:
                        try {
                            keyboardShelter.sendDocument(update, "CYNOLOGIST_INFO.pdf", CYNOLOGIST);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;

                    case CYNOLOGIST_LIST_INFO:
                        sendMessage(chatId, CYNOLOGIST_HOME);
                        break;
                    case PUPPY_INFO:
                        sendMessage(chatId, PUPPY);
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
            }
            catch (Exception e) {
                logger.error((e.getMessage()), e);
            }
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }


        public void sendReplyMessage (Long chatId, String messageText, Integer messageId){
            SendMessage sendMessage = new SendMessage(chatId, messageText);
            sendMessage.replyToMessageId(messageId);
            telegramBot.execute(sendMessage);
        }

        public void sendMessage ( long chatId, String text){
            SendMessage message = new SendMessage(chatId, text);
            telegramBot.execute(message);
        }
    }
