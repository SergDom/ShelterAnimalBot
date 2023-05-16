package com.javadreamteam.shelteranimalbot.service;

import com.javadreamteam.shelteranimalbot.exceptions.ClientException;
import com.javadreamteam.shelteranimalbot.exceptions.StatusException;
import com.javadreamteam.shelteranimalbot.keyboard.ClientStatus;
import com.javadreamteam.shelteranimalbot.model.ClientCat;
import com.javadreamteam.shelteranimalbot.model.ClientDog;
import com.javadreamteam.shelteranimalbot.repository.ClientDogRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collection;

@Service
public class ClientDogService {

    private final ClientDogRepository repository;

    private final TelegramBot telegramBot;

    private static final Logger logger = LoggerFactory.getLogger(DogService.class);

    public ClientDogService(ClientDogRepository repository, TelegramBot telegramBot) {
        this.repository = repository;
        this.telegramBot = telegramBot;
    }

    /**
     *Создание нового клиента
     *
     * @return созданный клиент
     */

    public ClientDog create(ClientDog clientDog, ClientStatus status) {
        logger.info("Was invoked method to create a clientDog");
        clientDog.setStatus(status);
        return repository.save(clientDog);
    }

    /**
     * получение клиента по его id
     * Используется метод репозитория {@link ClientDogRepository#findById(Object)}
     *
     * @param id идентификатор клиента
     * @return найденный клиент
     * @throws ClientException, если указаный владелец собаки не найден
     */
    public ClientDog getById(Long id) {
        logger.info("Was invoked method to get a Client by id={}", id);
        return repository.findById(id).orElseThrow(ClientException::new);
    }
    /**
     * Изменение данных владельца собаки
     * Используется метод репозитория {@link ClientDogRepository#save(Object)}
     * @param clientDog идентификатор клиента
     * @throws ClientException, если указаный владелец собаки не найден
     * @return измененный клиент
     */
    public ClientDog update(ClientDog clientDog) {
        logger.info("Was invoked method to update a clientDog");
        if (clientDog.getId() != null) {
            if (getById(clientDog.getId()) != null) {
                return repository.save(clientDog);
            }
        }
        throw new ClientException();
    }

    /**
     * удаление клиента собаки по id
     * Используется метод репозитория {@link ClientDogRepository#deleteById(Object)}
     * @param id идентификатор клиента
     */
    public void removeById(Long id) {
        logger.info("Was invoked method to remove a Client by id={}", id);
        repository.deleteById(id);
    }
    /**
     * получение списка клиентов собак
     * Используется метод репозитория {@link ClientDogRepository#findAll()}
     * @return список клиентов
     */
    public Collection<ClientDog> getAll() {
        logger.info("Was invoked method to get all Clients with dogs");
        return repository.findAll();
    }

    /**
     * получение клиента по его chatId
     * Используется метод репозитория {@link ClientDogRepository#findByChatId(Long)}
     * @param chatId идентификатор клиента
     * @return найденный клиент
     */

    public ClientDog getByChatId(Long chatId) {
        logger.info("Was invoked method to get a Client with dog by chatId={}", chatId);
        return repository.findByChatId(chatId);
    }

    /**
     * Изменение количества дней испытательного срока
     * @param id идентификатор клиента
     * @param option количество дней, на которое изменяется (1 - 14, 2 - 30)
     * @return владелец с измененным испытательным сроком
     */
    public ClientDog changeNumberOfReportDays(Long id, Long option) {
        ClientDog clientDog = repository.findById(id).orElseThrow(() -> {
            logger.error("There is not owner dog with id = {}", id);
            return new ClientException();
        });
        if (clientDog.getReportDays() == null) {
            logger.error("Owner has no probation, id = {}", id);
            throw new StatusException();
        }
        if (option == 1) {
            logger.info("The trial period has been extended by 14 days, id = {}", id);
            clientDog.setReportDays(clientDog.getReportDays() + 14);
            telegramBot.execute(new SendMessage(clientDog.getChatId(), "Вам продлили период испытательного срока на 14 дней"));

        } else if (option == 2) {
            logger.info("The trial period has been extended by 30 days, id = {}", id);
            clientDog.setReportDays(clientDog.getReportDays() + 30);
            telegramBot.execute(new SendMessage(clientDog.getChatId(), "Вам продлили период испытательного срока на 30 дней"));
        }
        return repository.save(clientDog);
    }

    /**
     * Изменение статуса клиента
     * @param id идентификатор владельца
     * @param status выбираемый статус
     * @return владелец с измененным статусом
     */
    public ClientDog updateStatus(Long id, ClientStatus status) {
        logger.info("Request to update owner dog status {}", status);
        ClientDog clientDog = repository.findById(id).orElseThrow(() -> {
            logger.error("There is not owner dog with id = {}", id);
            return new ClientException();
        });
        clientDog.setStatus(status);
        clientDog.setReportDays(30L);
        telegramBot.execute(new SendMessage(clientDog.getChatId(), "Вам присвоен статус " + clientDog.getStatus().getDescription()));
        return repository.save(clientDog);
    }

    /**
     * Уведомление владельцу от волонтера
     * @param id идентификатор владельца
     * @param option номер команды
     * @return измененные данные владельца и уведомление
     */
    public ClientDog noticeToClient (Long id, Long option) {
        ClientDog clientDog = repository.findById(id).orElseThrow(() -> {
            logger.error("There is not owner dog with id = {}", id);
            return new ClientException();
        });
        if (option == 1) {
            logger.info("Notification owner dog about bad report, id = {}", id);
            telegramBot.execute(new SendMessage(clientDog.getChatId(), "«Дорогой усыновитель, мы заметили, что ты заполняешь отчет не так подробно, как необходимо. " +
                    "Пожалуйста, подойди ответственнее к этому занятию. " +
                    "В противном случае волонтеры приюта будут обязаны самолично проверять условия содержания животного».")
            );
        } else if (option == 2) {
            logger.info("Successful completion of the probationary period, id = {}", id);
            clientDog.setStatus(ClientStatus.APPROVED);
            telegramBot.execute(new SendMessage(clientDog.getChatId(), "Вы прошли испытательный срок."));
        } else if (option == 3) {
            logger.info("The probationary period has not passed, id = {}", id);
            clientDog.setStatus(ClientStatus.IN_BLACK_LIST);
            telegramBot.execute(new SendMessage(clientDog.getChatId(), "Вы не прошли испытательный срок."));

            File doc = new File("src/main/resources/static/advice/DOCUMENTS_INFO.pdf");
            SendDocument sendDocument = new SendDocument(clientDog.getChatId(), doc);
            sendDocument.caption("Ознакомьтесь с приложеной инструкцией");
            telegramBot.execute(sendDocument);

        } else {
            throw new NullPointerException();
        }
        return repository.save(clientDog);
    }

}
