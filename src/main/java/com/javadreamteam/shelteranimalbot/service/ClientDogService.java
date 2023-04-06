package com.javadreamteam.shelteranimalbot.service;

import com.javadreamteam.shelteranimalbot.exceptions.ClientException;
import com.javadreamteam.shelteranimalbot.model.ClientDog;
import com.javadreamteam.shelteranimalbot.repository.ClientDogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ClientDogService {

    private final ClientDogRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(DogService.class);

    public ClientDogService(ClientDogRepository repository) {
        this.repository = repository;
    }

    public ClientDog create(ClientDog clientDog) {
        logger.info("Was invoked method to create a personDog");
        return repository.save(clientDog);
    }

    public ClientDog getById(Long id) {
        logger.info("Was invoked method to get a Client by id={}", id);
        return repository.findById(id).orElseThrow(ClientException::new);
    }

    public ClientDog update(ClientDog clientDog) {
        logger.info("Was invoked method to update a personDog");
        if (clientDog.getId() != null) {
            if (getById(clientDog.getId()) != null) {
                return repository.save(clientDog);
            }
        }
        throw new ClientException();
    }

    public void removeById(Long id) {
        logger.info("Was invoked method to remove a Client by id={}", id);
        repository.deleteById(id);
    }

    public Collection<ClientDog> getAll() {
        logger.info("Was invoked method to get all Clients with dogs");
        return repository.findAll();
    }

    public ClientDog getByChatId(Long chatId) {
        logger.info("Was invoked method to get a Client with dog by chatId={}", chatId);
        return repository.findByChatId(chatId);
    }
}
