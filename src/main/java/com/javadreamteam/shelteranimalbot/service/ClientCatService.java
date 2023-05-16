package com.javadreamteam.shelteranimalbot.service;

import com.javadreamteam.shelteranimalbot.exceptions.ClientException;
import com.javadreamteam.shelteranimalbot.exceptions.StatusException;
import com.javadreamteam.shelteranimalbot.keyboard.ClientStatus;
import com.javadreamteam.shelteranimalbot.keyboard.KeyboardShelter;
import com.javadreamteam.shelteranimalbot.model.ClientCat;
import com.javadreamteam.shelteranimalbot.repository.ClientCatRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collection;


@Service
public class ClientCatService {

    private final ClientCatRepository repository;
    private final TelegramBot telegramBot;
    private final KeyboardShelter keyboardShelter;

    private static final Logger logger = LoggerFactory.getLogger(CatService.class);

    public ClientCatService(ClientCatRepository repository, TelegramBot telegramBot, KeyboardShelter keyboardShelter) {
        this.repository = repository;
        this.telegramBot = telegramBot;
        this.keyboardShelter = keyboardShelter;
    }

    /**
     * Создание нового клиента
     *
     * @return созданный клиент
     */

    public ClientCat create(ClientCat clientCat, ClientStatus status) {
        logger.info("Was invoked method to create a personCat");
        clientCat.setStatus(status);
        return repository.save(clientCat);
    }

    /**
     * получение клиента по его id
     * Используется метод репозитория {@link ClientCatRepository#findById(Object)}
     *
     * @param id идентификатор клиента
     * @return найденный клиент
     * @throws ClientException, если указаный владелец кота не найден
     */
    public ClientCat getById(Long id) {
        logger.info("Was invoked method to get a Client by id={}", id);
        return repository.findById(id).orElseThrow(ClientException::new);
    }

    /**
     * Изменение данных владельца кота
     * Используется метод репозитория {@link ClientCatRepository#save(Object)}
     * @param clientCat идентификатор клиента
     * @throws ClientException, если указаный владелец кота не найден
     * @return измененный клиент
     */
    public ClientCat update(ClientCat clientCat) {
        logger.info("Was invoked method to update a personCat");
        if (clientCat.getId() != null) {
            if (getById(clientCat.getId()) != null) {
                return repository.save(clientCat);
            }
        }
        throw new ClientException();
    }

    /**
     * удаление клиента кота по id
     * Используется метод репозитория {@link ClientCatRepository#deleteById(Object)}
     * @param id идентификатор клиента
     */

    public void removeById(Long id) {
        logger.info("Was invoked method to remove a Client by id={}", id);
        repository.deleteById(id);
    }
    /**
     * получение списка клиентов котов
     * Используется метод репозитория {@link ClientCatRepository#findAll()}
     * @return список клиентов
     */

    public Collection<ClientCat> getAll() {
        logger.info("Was invoked method to get all Clients with cats");
        return repository.findAll();
    }

    /**
     * получение клиента по его chatId
     * Используется метод репозитория {@link ClientCatRepository#findByChatId(Long)}
     * @param chatId идентификатор клиента
     * @return найденный клиент
     */
    public ClientCat getByChatId(Long chatId) {
        logger.info("Was invoked method to get a Client with cat by chatId={}", chatId);
        return repository.findByChatId(chatId);
    }
    /**
     * Изменение количества дней испытательного срока
     * @param id идентификатор клиента
     * @param option количество дней, на которое изменяется (1 - 14, 2 - 30)
     * @return владелец с измененным испытательным сроком
     */
    public ClientCat changeNumberOfReportDays(Long id, Long option) {
        ClientCat clientCat = repository.findById(id).orElseThrow(() -> {
            logger.error("There is not owner cat with id = {}", id);
            return new ClientException();
        });
        if (clientCat.getReportDays() == null) {
            logger.error("Owner has no probation, id = {}", id);
            throw new StatusException();
        }
        if (option == 1) {
            logger.info("The trial period has been extended by 14 days, id = {}", id);
            clientCat.setReportDays(clientCat.getReportDays() + 14);
            telegramBot.execute(new SendMessage(clientCat.getChatId(), "Вам продлили период испытательного срока на 14 дней"));

        } else if (option == 2) {
            logger.info("The trial period has been extended by 30 days, id = {}", id);
            clientCat.setReportDays(clientCat.getReportDays() + 30);
            telegramBot.execute(new SendMessage(clientCat.getChatId(), "Вам продлили период испытательного срока на 30 дней"));
        }
        return repository.save(clientCat);
    }

    /**
     * Изменение статуса клиента
     * @param id идентификатор владельца
     * @param status выбираемый статус
     * @return владелец с измененным статусом
     */
    public ClientCat updateStatus(Long id, ClientStatus status) {
        logger.info("Request to update owner cat status {}", status);
        ClientCat clientCat = repository.findById(id).orElseThrow(() -> {
            logger.error("There is not owner cat with id = {}", id);
            return new ClientException();
        });
        clientCat.setStatus(status);
        clientCat.setReportDays(30L);
        telegramBot.execute(new SendMessage(clientCat.getChatId(), "Вам присвоен статус " + clientCat.getStatus().getDescription()));
        return repository.save(clientCat);
    }

    /**
     * Уведомление владельцу от волонтера
     * @param id идентификатор владельца
     * @param option номер команды
     * @return измененные данные владельца и уведомление
     */
    public ClientCat noticeToClient (Long id, Long option) {
        ClientCat clientCat = repository.findById(id).orElseThrow(() -> {
            logger.error("There is not owner cat with id = {}", id);
            return new ClientException();
        });
        if (option == 1) {
            logger.info("Notification owner cat about bad report, id = {}", id);
            telegramBot.execute(new SendMessage(clientCat.getChatId(), "«Дорогой усыновитель, мы заметили, что ты заполняешь отчет не так подробно, как необходимо. " +
                    "Пожалуйста, подойди ответственнее к этому занятию. " +
                    "В противном случае волонтеры приюта будут обязаны самолично проверять условия содержания животного».")
            );
        } else if (option == 2) {
            logger.info("Successful completion of the probationary period, id = {}", id);
            clientCat.setStatus(ClientStatus.APPROVED);
            telegramBot.execute(new SendMessage(clientCat.getChatId(), "Вы прошли испытательный срок."));
        } else if (option == 3) {
            logger.info("The probationary period has not passed, id = {}", id);
            clientCat.setStatus(ClientStatus.IN_BLACK_LIST);
            telegramBot.execute(new SendMessage(clientCat.getChatId(), "Вы не прошли испытательный срок."));

            File doc = new File("src/main/resources/static/advice/DOCUMENTS_INFO.pdf");
            SendDocument sendDocument = new SendDocument(clientCat.getChatId(), doc);
            sendDocument.caption("Ознакомьтесь с приложеной инструкцией");
            telegramBot.execute(sendDocument);

        } else {
            throw new NullPointerException();
        }
        return repository.save(clientCat);
    }
}
