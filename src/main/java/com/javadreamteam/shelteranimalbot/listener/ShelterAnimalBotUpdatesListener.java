package com.javadreamteam.shelteranimalbot.listener;

import static com.javadreamteam.shelteranimalbot.keyboard.KeyboardConstant.*;
import static com.javadreamteam.shelteranimalbot.keyboard.KeyboardShelter.*;

import com.javadreamteam.shelteranimalbot.keyboard.KeyboardShelter;
import com.javadreamteam.shelteranimalbot.keyboard.SendReport;
import com.javadreamteam.shelteranimalbot.model.ClientCat;
import com.javadreamteam.shelteranimalbot.model.ClientDog;
import com.javadreamteam.shelteranimalbot.model.User;
import com.javadreamteam.shelteranimalbot.repository.ClientCatRepository;
import com.javadreamteam.shelteranimalbot.repository.ClientDogRepository;
import com.javadreamteam.shelteranimalbot.repository.UserRepository;
import com.javadreamteam.shelteranimalbot.repository.VolunteerRepository;
import com.javadreamteam.shelteranimalbot.service.ClientCatService;
import com.javadreamteam.shelteranimalbot.service.ClientDogService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.ForwardMessage;
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

    private final ClientCatRepository clientCatRepository;
    private final ClientDogRepository clientDogRepository;

    private final ClientDogService clientDogService;
    private final ClientCatService clientCatService;
    private final UserRepository userRepository;


//    private boolean isCat = false;
    private static final long telegramChatVolunteer = 426343815L; //SergeyD

    public ShelterAnimalBotUpdatesListener(TelegramBot telegramBot, KeyboardShelter keyboardShelter, VolunteerRepository volunteerRepository, SendReport sendReport, ClientCatRepository clientCatRepository, ClientDogRepository clientDogRepository, ClientDogService clientDogService, ClientCatService clientCatService, UserRepository userRepository) {
        this.telegramBot = telegramBot;
        this.keyboardShelter = keyboardShelter;
        this.sendReport = sendReport;
        this.clientCatRepository = clientCatRepository;
        this.clientDogRepository = clientDogRepository;
        this.clientDogService = clientDogService;
        this.clientCatService = clientCatService;
        this.userRepository = userRepository;
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
                        if (userRepository.findUserByChatId(update.message().chat().id()) == null) {
                            userRepository.save(new User(update.message().chat().id(),
                                    update.message().chat().firstName(),
                                    false));
                        } else {
                            User user = userRepository.findUserByChatId(update.message().chat().id());
                            user.setIsDog(false);
                            userRepository.save(user);
                        }
                        keyboardShelter.sendMenu(chatId);
                        break;

                    case DOG:
                        if (userRepository.findUserByChatId(update.message().chat().id()) == null) {
                            userRepository.save(new User(update.message().chat().id(),
                                    update.message().chat().firstName(),
                                    true));
                        } else {
                            User user = userRepository.findUserByChatId(update.message().chat().id());
                            user.setIsDog(true);
                            userRepository.save(user);
                        }
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
                        if (update.message() != null) {
                            if (update.message().photo() != null || update.message().caption() != null) {
                                sendReport.downloadReport(update);
                            } else {
                                shareMessage(update);
                            }
                        }
                        break;
// Меню о приюте
                    case CONTACTS:
                        try {
                            keyboardShelter.sendPhoto(chatId, "/static/shelter/MAP_SHELTER.png", SHELTER_CONTACTS);
                        } catch (URISyntaxException | IOException e) {
                            throw new RuntimeException(e);
                        }
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
                        if (userRepository.findUserByChatId(update.message().chat().id()).isDog()) {
                            sendMessage(chatId, nameUser + WELCOME);
                            keyboardShelter.menuAdviseAnimal(chatId);
                        } else {
                            sendMessage(chatId, nameUser + WELCOME);
                            keyboardShelter.menuAdviseAnimalCat(chatId);
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
                        if (userRepository.findUserByChatId(update.message().chat().id()).isDog()) {
                            try {
                                keyboardShelter.sendDocument(update, "/advice/" + "PUPPY_INFO.pdf", PUPPY);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            try {
                                keyboardShelter.sendDocument(update, "/advice/" + "PUPPY_CAT_INFO.pdf", PUPPY);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        break;

                    case ADULT_INFO:
                        if (userRepository.findUserByChatId(update.message().chat().id()).isDog()) {
                            try {
                                keyboardShelter.sendDocument(update, "/advice/" + "ADULT_INFO.pdf", ADULT);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            try {
                                keyboardShelter.sendDocument(update, "/advice/" + "ADULT_CAT.pdf", ADULT);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        break;

                    case DISABLED_INFO:
                        if (userRepository.findUserByChatId(update.message().chat().id()).isDog()) {
                            try {
                                keyboardShelter.sendDocument(update, "/advice/" + "DISABLED_DOG.pdf", DISABLE_PET);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            try {
                                keyboardShelter.sendDocument(update, "/advice/" + "DISABLED_CAT.pdf", DISABLE_PET);
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

                    case SEND_CONTACTS:
                        if (update.message() != null && update.message().contact() != null) {
                        shareContactInDB(update);}
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

    public void sendForwardMessage(Long chatId, Integer messageId) {
        ForwardMessage forwardMessage = new ForwardMessage(telegramChatVolunteer, chatId, messageId);
        telegramBot.execute(forwardMessage);
    }


//    public void shareContact(Update update) {
//        if (update.message().contact() != null) {
//            String firstName = update.message().contact().firstName();
//            String phone = update.message().contact().phoneNumber();
////            String username = update.message().chat().username();
//            long finalChatId = update.message().chat().id();
//            List <ClientDog> sortChatId = clientDogRepository.findAll().stream().filter(i -> i.getChatId() == finalChatId).toList();
//            List <ClientCat> sortChatIdCat = clientCatRepository.findAll().stream().filter(i -> i.getChatId() == finalChatId).toList();
//
//            if (!sortChatId.isEmpty() || !sortChatIdCat.isEmpty()) {
//                SendMessage message = new SendMessage(finalChatId, "Вы уже в базе");
//                telegramBot.execute(message);
//
//            }
//            if (isCat) {
//                clientCatRepository.save(new ClientCat(firstName, phone, finalChatId));
//            } else {
//                clientDogRepository.save(new ClientDog(firstName, phone, finalChatId));
//            }
//            SendMessage message = new SendMessage(finalChatId, "Вас успешно добавили в базу. Скоро вам перезвонят.");
//            telegramBot.execute(message);
//            // Сообщение в чат волонтерам
//            SendMessage messageToVolunteer = new SendMessage(telegramChatVolunteer, phone + " " + firstName + " Добавил(а) свой номер в базу");
//            telegramBot.execute(messageToVolunteer);
//
//        }
//    }

    public void shareMessage(Update update) {
        if (update.message().contact() != null) {
            shareContactInDB(update);
            return;
        } else if (update.message().text() == null) {
            return;
        }
    }

    private void shareContactInDB(Update update){
        logger.info("Created owner in database: " +
                update.message().chat().id());

        Long chatId = update.message().chat().id();

        if (userRepository.findUserByChatId(update.message().chat().id()).isDog() &&
                clientDogRepository.findByChatId(chatId) == null){
            clientDogService.create(new ClientDog(chatId,
                            update.message().contact().firstName(),
                            update.message().contact().phoneNumber()));}
        else if(clientCatRepository.findByChatId(chatId) == null){
            clientCatService.create(new ClientCat(chatId,
                            update.message().contact().firstName(),
                            update.message().contact().phoneNumber()));
        }
        telegramBot.execute(new SendMessage(update.message().chat().id(),
                "Мы с вами перезовним!"));
        // Сообщение в чат волонтерам
        SendMessage messageToVolunteer = new SendMessage(telegramChatVolunteer, " Добавил(а) свой номер в базу");
        telegramBot.execute(messageToVolunteer);
    }
}
