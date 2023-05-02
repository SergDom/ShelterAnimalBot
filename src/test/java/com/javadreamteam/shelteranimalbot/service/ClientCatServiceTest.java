package com.javadreamteam.shelteranimalbot.service;

import com.javadreamteam.shelteranimalbot.keyboard.ReportStatus;
import com.javadreamteam.shelteranimalbot.model.Cat;
import com.javadreamteam.shelteranimalbot.model.ClientCat;
import com.javadreamteam.shelteranimalbot.repository.ClientCatRepository;
import com.javadreamteam.shelteranimalbot.repository.ClientDogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ClientCatServiceTest {
  @Mock
  private ClientCatRepository repository;
  @InjectMocks
  private ClientCatService service;


    @Test
    void create() {
       //создаем объекты
      ClientCat clientCat = new ClientCat();
      clientCat.setName("Alice");
      clientCat.setChatId(12345L);
      clientCat.setPhoneNumber("1234567890");
      clientCat.setEmail("alice@example.com");
      clientCat.setYearOfBirth(1990);
      clientCat.setStatus(ReportStatus.PENDING);

      Cat cat = new Cat();
      cat.setName("Fluffy");
      cat.setAge(3);
      cat.setBreed("Persian");


      clientCat.setCat(cat);

      // имитируем сохранение объекта в репозитории
      Mockito.when(repository.save(clientCat)).thenReturn(clientCat);

      // вызываем метод create() и проверяем результат
      ClientCat returnedClientCat = service.create(clientCat);

      assertNotNull(returnedClientCat);
      assertEquals(clientCat.getName(), returnedClientCat.getName());
      assertEquals(clientCat.getChatId(), returnedClientCat.getChatId());
      assertEquals(clientCat.getPhoneNumber(), returnedClientCat.getPhoneNumber());
      assertEquals(clientCat.getEmail(), returnedClientCat.getEmail());
      assertEquals(clientCat.getYearOfBirth(), returnedClientCat.getYearOfBirth());
      assertEquals(clientCat.getStatus(), returnedClientCat.getStatus());
      assertEquals(clientCat.getCat().getName(), returnedClientCat.getCat().getName());
      assertEquals(clientCat.getCat().getAge(), returnedClientCat.getCat().getAge());
      assertEquals(clientCat.getCat().getBreed(), returnedClientCat.getCat().getBreed());

      assertEquals(0, returnedClientCat.getVolunteerId());

      // проверяем, что метод save() был вызван один раз
      Mockito.verify(repository, Mockito.times(1)).save(clientCat);
    }

    @Test
    void getById() {
      // создаем объекты
      ClientCat clientCat = new ClientCat();
      clientCat.setId(1L);
      clientCat.setName("Alice");
      clientCat.setChatId(12345L);
      clientCat.setPhoneNumber("1234567890");
      clientCat.setEmail("alice@example.com");
      clientCat.setYearOfBirth(1990);
      clientCat.setStatus(ReportStatus.PENDING);

      Cat cat = new Cat();
      cat.setId(1L);
      cat.setName("Fluffy");
      cat.setAge(3);
      cat.setBreed("Persian");

      clientCat.setCat(cat);

      // имитируем получение объекта из репозитория
      Mockito.when(repository.findById(1L)).thenReturn(Optional.of(clientCat));

      // вызываем метод getById() и проверяем результат
      ClientCat returnedClientCat = service.getById(1L);

      assertNotNull(returnedClientCat);
      assertEquals(clientCat.getName(), returnedClientCat.getName());
      assertEquals(clientCat.getChatId(), returnedClientCat.getChatId());
      assertEquals(clientCat.getPhoneNumber(), returnedClientCat.getPhoneNumber());
      assertEquals(clientCat.getEmail(), returnedClientCat.getEmail());
      assertEquals(clientCat.getYearOfBirth(), returnedClientCat.getYearOfBirth());
      assertEquals(clientCat.getStatus(), returnedClientCat.getStatus());
      assertEquals(clientCat.getCat().getName(), returnedClientCat.getCat().getName());
      assertEquals(clientCat.getCat().getAge(), returnedClientCat.getCat().getAge());
      assertEquals(clientCat.getCat().getBreed(), returnedClientCat.getCat().getBreed());
      assertEquals(0, returnedClientCat.getVolunteerId());

      // проверяем, что метод findById() был вызван один раз с нужным аргументом
      Mockito.verify(repository, Mockito.times(1)).findById(1L);

      // проверяем, что метод findById() был вызван только один раз
      Mockito.verify(repository, Mockito.times(1)).findById(Mockito.anyLong());
    }

    @Test
    void update() {
      // создаем объекты
      ClientCat clientCat = new ClientCat();
      clientCat.setId(1L);
      clientCat.setName("Alice");
      clientCat.setChatId(12345L);
      clientCat.setPhoneNumber("1234567890");
      clientCat.setEmail("alice@example.com");
      clientCat.setYearOfBirth(1990);
      clientCat.setStatus(ReportStatus.PENDING);

      Cat cat = new Cat();
      cat.setId(1L);
      cat.setName("Fluffy");
      cat.setAge(3);
      cat.setBreed("Persian");
      clientCat.setCat(cat);

      // имитируем получение объекта из репозитория
      Mockito.when(repository.findById(1L)).thenReturn(Optional.of(clientCat));

      // имитируем сохранение объекта в репозитории
      Mockito.when(repository.save(clientCat)).thenReturn(clientCat);

      // вызываем метод update() и проверяем результат
      ClientCat returnedClientCat = service.update(clientCat);

      assertNotNull(returnedClientCat);
      assertEquals(clientCat.getName(), returnedClientCat.getName());
      assertEquals(clientCat.getChatId(), returnedClientCat.getChatId());
      assertEquals(clientCat.getPhoneNumber(), returnedClientCat.getPhoneNumber());
      assertEquals(clientCat.getEmail(), returnedClientCat.getEmail());
      assertEquals(clientCat.getYearOfBirth(), returnedClientCat.getYearOfBirth());
      assertEquals(clientCat.getStatus(), returnedClientCat.getStatus());
      assertEquals(clientCat.getCat().getName(), returnedClientCat.getCat().getName());
      assertEquals(clientCat.getCat().getAge(), returnedClientCat.getCat().getAge());
      assertEquals(clientCat.getCat().getBreed(), returnedClientCat.getCat().getBreed());
      assertEquals(0, returnedClientCat.getVolunteerId());

      // проверяем, что метод findById() был вызван один раз с нужным аргументом
      Mockito.verify(repository, Mockito.times(1)).findById(1L);

      // проверяем, что метод save() был вызван один раз с нужным аргументом
      Mockito.verify(repository, Mockito.times(1)).save(clientCat);

      // проверяем, что метод findById() был вызван только один раз
      Mockito.verify(repository, Mockito.times(1)).findById(Mockito.anyLong());

      // проверяем, что метод save() был вызван только один раз
      Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(ClientCat.class));
    }



    @Test
    void removeById() {
      // создаем объект
      ClientCat clientCat = new ClientCat();
      clientCat.setId(1L);
      clientCat.setName("Alice");
      clientCat.setChatId(12345L);
      clientCat.setPhoneNumber("1234567890");
      clientCat.setEmail("alice@example.com");
      clientCat.setYearOfBirth(1990);
      clientCat.setStatus(ReportStatus.PENDING);

      Cat cat = new Cat();
      cat.setId(1L);
      cat.setName("Fluffy");
      cat.setAge(3);
      cat.setBreed("Persian");
      clientCat.setCat(cat);

      // имитируем получение объекта из репозитория
      Mockito.when(repository.findById(1L)).thenReturn(Optional.of(clientCat));

      // вызываем метод removeById()
      service.removeById(1L);

      // проверяем, что метод findById() был вызван один раз с нужным аргументом
      Mockito.verify(repository, Mockito.times(1)).findById(1L);

      // проверяем, что метод deleteById() был вызван один раз с нужным аргументом
      Mockito.verify(repository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void getAll() {
    }

    @Test
    void getByChatId() {
    }
}