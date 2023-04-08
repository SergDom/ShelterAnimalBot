package com.javadreamteam.shelteranimalbot.service;

import com.javadreamteam.shelteranimalbot.exceptions.DogException;
import com.javadreamteam.shelteranimalbot.model.Dog;
import com.javadreamteam.shelteranimalbot.repository.DogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Сервис DogService
 * Сервис используется для создания, редактирования, удаления и получения собаки/списка собак из БД
 */
@Service

public class DogService {
private final DogRepository repository;
private static final Logger logger = LoggerFactory.getLogger(DogService.class);

    public DogService(DogRepository dogRepository) {
        this.repository = dogRepository;
    }

    /**
     * Создание собаки и сохранение ее в БД
     * <br>
     * Используется метод репозитория {@link DogRepository#save(Object)}
     * @param dog создается объект собака
     * @return созданная собака
     */
    public Dog create(Dog dog) {
        logger.info("Was invoked method to create a dog");
        return repository.save(dog);
    }

    /**
     * Поиск собаки в БД по id
     * <br>
     * Используется метод репозитория {@link DogRepository#findById(Object)}
     * @param id идентификатор собаки
     * @throws DogException, если собака с указанным id не найденна в БД
     * @return найденная собака
     */
    public Dog getById(Long id) {
        logger.info("Was invoked method to get a dog by id={}", id);
        return repository.findById(id).orElseThrow(DogException::new);
    }

    /**
     * Изменение данных собаки в БД
     * <br>
     * Используется метод репозитория {@link DogRepository#save(Object)}
     * @param dog изменяемая собака
     * @throws DogException, если собака не найденна в БД
     * @return измененная собака
     */

    public Dog update(Dog dog) {
        logger.info("Was invoked method to update a dog");
        if (dog.getId() != null) {
            if (getById(dog.getId()) != null) {
                return repository.save(dog);
            }
        }
        throw new DogException();
    }
    /**
     * Удаление собаки из БД по id
     * <br>
     * Используется метод репозитория {@link DogRepository#delete(Object)}
     * @param id идентификатор собаки
     */

    public void removeById(Long id) {
        logger.info("Was invoked method to remove a cat by id={}", id);
        repository.deleteById(id);
    }
    /**
     * Получение списка собак из БД
     * <br>
     * Используется метод репозитория {@link DogRepository#findAll()}
     * @return список собак
     */

    public Collection<Dog> getAll() {
        logger.info("Was invoked method to get all dogs");
        return repository.findAll();
    }
}
