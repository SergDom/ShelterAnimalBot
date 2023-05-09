package com.javadreamteam.shelteranimalbot.service;

import static org.junit.jupiter.api.Assertions.*;

import com.javadreamteam.shelteranimalbot.exceptions.VolunteerException;
import com.javadreamteam.shelteranimalbot.model.Volunteer;
import com.javadreamteam.shelteranimalbot.repository.VolunteerRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest

class VolunteerServiceTest {

    @Mock
    private VolunteerRepository volunteerRepository;

    @InjectMocks
    private VolunteerService volunteerService;
    @Test
    public void testCreateVolunteer() {
        // Создание объекта Volunteer
        Volunteer volunteer = new Volunteer();
        volunteer.setName("John Doe");
        volunteer.setUsername("johndoe");
        volunteer.setPhone("1234567890");
        volunteer.setChatId(123456789L);

        // Задание поведения зависимости volunteerRepository
        when(volunteerRepository.save(any(Volunteer.class))).thenReturn(volunteer);

        // Вызов метода createVolunteer
        Volunteer result = volunteerService.createVolunteer(volunteer);

        // Проверка, что метод save был вызван один раз с переданным объектом Volunteer
        ArgumentCaptor<Volunteer> argumentCaptor = ArgumentCaptor.forClass(Volunteer.class);
        verify(volunteerRepository, times(1)).save(argumentCaptor.capture());
        Volunteer savedVolunteer = argumentCaptor.getValue();
        assertEquals("John Doe", savedVolunteer.getName());
        assertEquals("johndoe", savedVolunteer.getUsername());
        assertEquals("1234567890", savedVolunteer.getPhone());
        assertEquals(123456789L, savedVolunteer.getChatId());

        // Проверка, что результат равен созданному объекту Volunteer
        assertEquals(volunteer, result);
    }


        @Test
        public void testGetVolunteerById() {
            // Создание объекта Volunteer
            Volunteer volunteer = new Volunteer();
            volunteer.setId(1L);
            volunteer.setName("John Doe");
            volunteer.setUsername("johndoe");
            volunteer.setPhone("1234567890");
            volunteer.setChatId(123456789L);

            // Задание поведения зависимости volunteerRepository
            when(volunteerRepository.findById(1L)).thenReturn(Optional.of(volunteer));

            // Вызов метода getById
            Volunteer result = volunteerService.getById(1L);

            // Проверка, что метод findById был вызван один раз с переданным id
            verify(volunteerRepository, times(1)).findById(1L);

            // Проверка, что результат равен созданному объекту Volunteer
            assertEquals(volunteer, result);

            // Проверка, что метод findById был вызван с правильным аргументом
            ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
            verify(volunteerRepository).findById(argumentCaptor.capture());
            assertEquals(1L, argumentCaptor.getValue().longValue());

            // Проверка, что метод findById выбросил VolunteerException при отсутствии записи с таким id
            when(volunteerRepository.findById(2L)).thenReturn(Optional.empty());
            assertThrows(VolunteerException.class, () -> volunteerService.getById(2L));
        }


          @Test
    public void testUpdateVolunteer() {
              Volunteer volunteer = new Volunteer();
              volunteer.setId(1L);
              volunteer.setName("John");
              volunteer.setUsername("john123");
              volunteer.setPhone("1234567890");
              volunteer.setChatId(12345L);


              Volunteer updatedVolunteer = new Volunteer();
              updatedVolunteer.setId(1L);
              updatedVolunteer.setName("John Doe");
              updatedVolunteer.setUsername("johndoe123");
              updatedVolunteer.setPhone("0987654321");
              updatedVolunteer.setChatId(54321L);


              Mockito.when(volunteerRepository.findById(1L)).thenReturn(Optional.of(volunteer));
              Mockito.when(volunteerRepository.save(updatedVolunteer)).thenReturn(updatedVolunteer);

              Volunteer returnedVolunteer = volunteerService.updateVolunteer(1L, updatedVolunteer);

              assertNotNull(returnedVolunteer);
              assertEquals(updatedVolunteer.getId(), returnedVolunteer.getId());
              assertEquals(updatedVolunteer.getName(), returnedVolunteer.getName());
              assertEquals(updatedVolunteer.getUsername(), returnedVolunteer.getUsername());
              assertEquals(updatedVolunteer.getPhone(), returnedVolunteer.getPhone());
              assertEquals(updatedVolunteer.getChatId(), returnedVolunteer.getChatId());


              Mockito.verify(volunteerRepository, Mockito.times(1)).findById(1L);
              Mockito.verify(volunteerRepository, Mockito.times(1)).save(updatedVolunteer);
    }


    @Test
    public void testGetRandomVolunteer() {
        Volunteer volunteer = new Volunteer();
        volunteer.setId(1L);
        volunteer.setName("John");
        volunteer.setUsername("john123");
        volunteer.setPhone("1234567890");
        volunteer.setChatId(123456L);

        Mockito.when(volunteerRepository.getVolunteerById()).thenReturn(Optional.of(volunteer));

        Volunteer result = volunteerService.getRandomVolunteer();

        assertNotNull(result);
        assertEquals(volunteer.getId(), result.getId());
        assertEquals(volunteer.getName(), result.getName());
        assertEquals(volunteer.getUsername(), result.getUsername());
        assertEquals(volunteer.getPhone(), result.getPhone());
        assertEquals(volunteer.getChatId(), result.getChatId());
    }
}