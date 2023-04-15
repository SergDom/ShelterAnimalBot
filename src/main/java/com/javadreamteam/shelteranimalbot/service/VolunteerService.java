package com.javadreamteam.shelteranimalbot.service;

import com.javadreamteam.shelteranimalbot.exceptions.DogException;
import com.javadreamteam.shelteranimalbot.exceptions.VolunteerException;
import com.javadreamteam.shelteranimalbot.model.Volunteer;
import com.javadreamteam.shelteranimalbot.repository.DogRepository;
import com.javadreamteam.shelteranimalbot.repository.VolunteerRepository;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VolunteerService {
    private final VolunteerRepository volunteerRepository;

    private final Logger logger = LoggerFactory.getLogger(VolunteerService.class);

    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    /**
     * Создание волонтера и сохранение в БД
     * <br>
     * Используется метод репозитория {@link VolunteerRepository#save(Object)}
     * @param volunteer создается объект волонтер
     * @return создан волонтер
     */
    public Volunteer createVolunteer(Volunteer volunteer) {

        return volunteerRepository.save(volunteer);
    }

    /**
     * Поиск волонтера в БД по id
     * <br>
     * Используется метод репозитория {@link VolunteerRepository#findById(Object)}
     * @param id идентификатор волонтера
     * @throws VolunteerException, если волонтер с указанным id не найден в БД
     * @return найден волонтер
     */
    public Volunteer getById (long id) {
        logger.debug("Was invoked method to get a Volunteer by (id = {})", id);
        return volunteerRepository.findById(id).orElseThrow(VolunteerException::new);
    }

    /**
     * Изменение данных волонтера в БД
     * <br>
     * Используется метод репозитория {@link VolunteerRepository#save(Object)}
     * @param volunteer изменяемый волонтер
     * @throws VolunteerException, если волонтер не найден в БД
     * @return измененный волонтер
     */

    public Volunteer updateVolunteer(long id, Volunteer volunteer) {
        logger.debug("Was invoked method to update Volunteer (Id = {})", volunteer.getId());
        if (volunteer.getId() != null) {
            if (getById(volunteer.getId()) != null) {

                return volunteerRepository.save(volunteer);
            }
        }
        throw new VolunteerException();
    }

    /**
     * Получение произвольного волонтера из БД
     * <br>
     * Используется метод репозитория {@link VolunteerRepository#getRandomVolunteer()}
     * @throws VolunteerException, если волонтер не найден в БД
     * @return найден волонтер
     */
    public List<Volunteer> getRandomVolunteer() {
        return volunteerRepository
                .getRandomVolunteer();
    }
}
