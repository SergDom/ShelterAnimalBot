package com.javadreamteam.shelteranimalbot.service;

import com.javadreamteam.shelteranimalbot.exceptions.ClientException;
import com.javadreamteam.shelteranimalbot.keyboard.ClientStatus;
import com.javadreamteam.shelteranimalbot.model.ClientCat;
import com.javadreamteam.shelteranimalbot.repository.ClientCatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ClientCatService {

    private final ClientCatRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(CatService.class);

    public ClientCatService(ClientCatRepository repository) {
        this.repository = repository;
    }

    /**
     *Создание нового клиента
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
     * @param id идентификатор клиента
     * @return найденный клиент
     */
    public ClientCat getById(Long id) {
        logger.info("Was invoked method to get a Client by id={}", id);
        return repository.findById(id).orElseThrow(ClientException::new);
    }

    public ClientCat update(ClientCat clientCat) {
        logger.info("Was invoked method to update a personCat");
        if (clientCat.getId() != null) {
            if (getById(clientCat.getId()) != null) {
                return repository.save(clientCat);
            }
        }
        throw new ClientException();
    }

    public void removeById(Long id) {
        logger.info("Was invoked method to remove a Client by id={}", id);
        repository.deleteById(id);
    }

    public Collection<ClientCat> getAll() {
        logger.info("Was invoked method to get all Clients with cats");
        return repository.findAll();
    }

    public ClientCat getByChatId(Long chatId) {
        logger.info("Was invoked method to get a Client with cat by chatId={}", chatId);
        return repository.findByChatId(chatId);
    }

}
