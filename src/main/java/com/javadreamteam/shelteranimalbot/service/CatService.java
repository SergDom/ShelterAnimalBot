package com.javadreamteam.shelteranimalbot.service;

import com.javadreamteam.shelteranimalbot.exceptions.CatException;
import com.javadreamteam.shelteranimalbot.model.Cat;
import com.javadreamteam.shelteranimalbot.repository.CatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Сервис CatService
 * Сервис используется для создания, редактирования, удаления и получения кошки/списка кошек из БД
 */
@Service
public class CatService {
    private final CatRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(CatService.class);

    public CatService(CatRepository catRepository) {
        this.repository = catRepository;
    }

    /**
     * Создание кошки и сохранение ее в БД
     * <br>
     * Используется метод репозитория {@link CatRepository#save(Object)}
     *
     * @param cat создается объект кошки
     * @return созданная кошки
     */
    public Cat create(Cat cat) {
        logger.info("Was invoked method to create a cat");
        return repository.save(cat);
    }

    /**
     * Поиск кошки в БД по id
     * <br>
     * Используется метод репозитория {@link CatRepository#findById(Object)}
     *
     * @param id идентификатор кошки
     * @return найденная кошка
     * @throws CatException, если кошка с указанным id не найденна в БД
     */
    public Cat getById(Long id) {
        logger.info("Was invoked method to get a cat by id={}", id);
        return repository.findById(id).orElseThrow(CatException::new);
    }

    /**
     * Изменение данных кошки в БД
     * <br>
     * Используется метод репозитория {@link CatRepository#save(Object)}
     *
     * @param cat изменяемая кошка
     * @return измененная кошка
     * @throws CatException, если кошка не найденна в БД
     */

    public Cat update(Cat cat) {
        logger.info("Was invoked method to update a cat");
        if (cat.getId() != null) {
            if (getById(cat.getId()) != null) {
                return repository.save(cat);
            }
        }
        throw new CatException();
    }

    /**
     * Удаление кошки из БД по id
     * <br>
     * Используется метод репозитория {@link CatRepository#delete(Object)}
     *
     * @param id идентификатор кошки
     */

    public void removeById(Long id) {
        logger.info("Was invoked method to remove a cat by id={}", id);
        repository.deleteById(id);
    }

    /**
     * Получение списка кошек из БД
     * <br>
     * Используется метод репозитория {@link CatRepository#findAll()}
     *
     * @return список кошек
     */

    public Collection<Cat> getAll() {
        logger.info("Was invoked method to get all cats");
        return repository.findAll();
    }
}
