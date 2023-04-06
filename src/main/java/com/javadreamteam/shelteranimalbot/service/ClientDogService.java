package com.javadreamteam.shelteranimalbot.service;

import com.javadreamteam.shelteranimalbot.exceptions.ClientException;
import com.javadreamteam.shelteranimalbot.model.ClientDog;
import com.javadreamteam.shelteranimalbot.repository.ClientDogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Сервис ClientDogService
 * Сервис используется для создания, редактирования, удаления и получения владельца собаки/списка владельцев собак из БД
 */

@Service
public class ClientDogService {

    private final ClientDogRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(DogService.class);

    public ClientDogService(ClientDogRepository repository) {
        this.repository = repository;
    }

    /**
     * Создание владельца собаки и сохранение его в БД
     * <br>
     * Используется метод репозитория {@link ClientDogRepository#save(Object)}
     * @param clientDog создается объект владелец собаки
     * @return созданный владелец
     */

    public ClientDog create(ClientDog clientDog) {
        logger.info("Was invoked method to create a personDog");
        return repository.save(clientDog);
    }

    /**
     * Поиск владельца собаки в БД по id
     * <br>
     * Используется метод репозитория {@link ClientDogRepository#findById(Object)}
     * @param id идентификатор владельца
     * @throws ClientException, если владелец с указанным id не найден
     * @return найденный владелец
     */
    public ClientDog getById(Long id) {
        logger.info("Was invoked method to get a Client by id={}", id);
        return repository.findById(id).orElseThrow(ClientException::new);
    }

    /**
     * Изменение данных владельца собаки в БД
     * <br>
     * Используется метод репозитория {@link ClientDogRepository#save(Object)}
     * @param clientDog изменяемый владелец
     * @throws ClientException, если указанный владелец собаки не найден
     * @return измененный владелец собаки
     */
    public ClientDog update(ClientDog clientDog) {
        logger.info("Was invoked method to update a personDog");
        if (clientDog.getId() != null) {
            if (getById(clientDog.getId()) != null) {
                return repository.save(clientDog);
            }
        }
        throw new ClientException();
    }
    /**
     * Удаление владельца собаки из БД по id
     * <br>
     * Используется метод репозитория {@link ClientDogRepository#delete(Object)}
     * @param id идентификатор владельца собаки
     */
    public void removeById(Long id) {
        logger.info("Was invoked method to remove a Client by id={}", id);
        repository.deleteById(id);
    }
    /**
     * Получение списка владельцев собак из БД
     * <br>
     * Используется метод репозитория {@link ClientDogRepository#findAll()}
     * @return список владельцев собак
     */

    public Collection<ClientDog> getAll() {
        logger.info("Was invoked method to get all Clients with dogs");
        return repository.findAll();
    }
    /**
     * Получение списка владельцев собак из БД по id
     * <br>
     * Используется метод репозитория {@link ClientDogRepository Set<ClientDog>#findByChatId()}
     * @return список владельцев собак
     */

    public Collection<ClientDog> getByChatId(Long chatId) {
        logger.info("Was invoked method to get a Client with dog by chatId={}", chatId);
        return repository.findByChatId(chatId);
    }
}
