package com.javadreamteam.shelteranimalbot.service;

import com.javadreamteam.shelteranimalbot.exceptions.ClientException;
import com.javadreamteam.shelteranimalbot.model.Client;
import com.javadreamteam.shelteranimalbot.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(DogService.class);

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }



    public Client create(Client client) {
        logger.info("Was invoked method to create a personDog");
        return repository.save(client);
    }

    public Client getById(Long id) {
        logger.info("Was invoked method to get a Client by id={}", id);
        return repository.findById(id).orElseThrow(ClientException::new);
    }

    public Client update(Client client) {
        logger.info("Was invoked method to update a personDog");
        if (client.getId() != null) {
            if (getById(client.getId()) != null) {
                return repository.save(client);
            }
        }
        throw new ClientException();
    }

    public void removeById(Long id) {
        logger.info("Was invoked method to remove a Client by id={}", id);
        repository.deleteById(id);
    }

    public Collection<Client> getAll() {
        logger.info("Was invoked method to get all Clients with dogs");
        return repository.findAll();
    }

    public Client getByChatId(Long chatId) {
        logger.info("Was invoked method to get a Client with dog by chatId={}", chatId);
        return repository.findByChatId(chatId);
    }
}
