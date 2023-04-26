package com.javadreamteam.shelteranimalbot.listener;

import static com.javadreamteam.shelteranimalbot.keyboard.KeyboardConstant.*;
import static com.javadreamteam.shelteranimalbot.keyboard.KeyboardShelter.*;

import com.javadreamteam.shelteranimalbot.keyboard.KeyboardShelter;
import com.javadreamteam.shelteranimalbot.keyboard.SendReport;
import com.javadreamteam.shelteranimalbot.repository.VolunteerRepository;
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

    private final SendReport sendReport;


    private boolean isCat = false;

    public ShelterAnimalBotUpdatesListener(TelegramBot telegramBot, KeyboardShelter keyboardShelter, VolunteerRepository volunteerRepository, SendReport sendReport) {
        this.telegramBot = telegramBot;
        this.keyboardShelter = keyboardShelter;
        this.sendReport = sendReport;
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

// Главное меню (выбор приюта)
                    case START:
                        sendMessage(chatId, nameUser + WELCOME);
                        keyboardShelter.mainMenu(chatId);
                        break;

                    case CAT:
                        isCat = true;
                        keyboardShelter.sendMenu(chatId);
                        break;

                    case DOG:
                        isCat = false;
                        keyboardShelter.sendMenu(chatId);
                        break;
// Основное меню
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
// Меню отчета
                    case SEND_REPORT:
                        keyboardShelter.menuReport(chatId);
                        break;
                    case REPORT_FORM:
                        sendMessage(chatId, REPORT_LIST);
                        break;
                    case REPORT_STORE:
                        sendReport.storeReport(update);
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
                        if (isCat) {
                            sendMessage(chatId, nameUser + WELCOME);
                            keyboardShelter.menuAdviseAnimalCat(chatId);
                        } else {
                            sendMessage(chatId, nameUser + WELCOME);
                            keyboardShelter.menuAdviseAnimal(chatId);
                        }
                        break;

                    case DOCUMENT_LIST:
                        try {
                            keyboardShelter.sendDocument(update, "/advice/" + "DOCUMENTS_INFO.pdf", DOCUMENTS);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;

                    case REFUSE_REASONS:
                        try {
                            keyboardShelter.sendDocument(update, "/advice/" + "REFUSE_REASONS.pdf", REFUSE);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;

                    case TRANSPORTATION:
                        try {
                            keyboardShelter.sendDocument(update, "/advice/" + "TRANSPORTATION_INFO.pdf", TRANSPORT);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;

                    case CYNOLOGIST_INFO:
                        try {
                            keyboardShelter.sendDocument(update, "/advice/" + "CYNOLOGIST_INFO.pdf", CYNOLOGIST);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;

                    case CYNOLOGIST_LIST_INFO:
                        try {
                            keyboardShelter.sendDocument(update, "/advice/" + "CYNOLOGIST_LIST_INFO.pdf", CYNOLOGIST_LIST);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;

                    case PUPPY_INFO:
                        if (isCat) {
                            try {
                                keyboardShelter.sendDocument(update, "/advice/" + "PUPPY_INFO.pdf", PUPPY);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            try {
                                keyboardShelter.sendDocument(update, "/advice/" + "PUPPY_INFO.pdf", PUPPY);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        break;

                    case ADULT_INFO:
                        if (isCat) {
                            try {
                                keyboardShelter.sendDocument(update, "/advice/" + "ADULT_INFO.pdf", ADULT);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            try {
                                keyboardShelter.sendDocument(update, "/advice/" + "ADULT_INFO.pdf", ADULT);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        break;

                    case DISABLED_INFO:
                        if (isCat) {
                            try {
                                keyboardShelter.sendDocument(update, "/advice/" + "DISABLED_DOG.pdf", DISABLE_PET);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            try {
                                keyboardShelter.sendDocument(update, "/advice/" + "DISABLED_DOG.pdf", DISABLE_PET);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        break;
// Общие кнопки
                    case REQUEST_VOLUNTEER:
                        sendMessage(chatId, CALL_VOLUNTEERS);
                        keyboardShelter.callVolunteer(update);
                        break;

                    case MAIN_MENU:
                        keyboardShelter.sendMenu(chatId);
                        break;

                    case TOP_MENU:
                        keyboardShelter.mainMenu(chatId);
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

}
