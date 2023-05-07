package com.javadreamteam.shelteranimalbot.service;

import com.javadreamteam.shelteranimalbot.keyboard.ReportStatus;
import com.javadreamteam.shelteranimalbot.model.*;
import com.javadreamteam.shelteranimalbot.model.ClientDog;
import com.javadreamteam.shelteranimalbot.repository.ClientDogRepository;
import com.javadreamteam.shelteranimalbot.repository.ClientDogRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
@SpringBootTest
class ClientDogServiceTest {
    @Mock
    private ClientDogRepository repository;
    @InjectMocks
    private ClientDogService service;
    @Test
    void create() {
// Create a mock ClientDog object
        ClientDog clientDog = new ClientDog();
        clientDog.setName("Buddy");
        clientDog.setChatId(123456789L);
        clientDog.setPhoneNumber("+1234567890");
        clientDog.setEmail("buddy@example.com");
        clientDog.setYearOfBirth(2015);
        clientDog.setStatus(ReportStatus.PENDING);

        // Create a mock Dog object
        Dog dog = new Dog();
        dog.setName("Max");
        dog.setBreed("Labrador Retriever");
        dog.setAge(6);


        // Set the Dog object in the ClientDog object
        clientDog.setDog(dog);

        // Mock the repository.save() method to return the same ClientDog object
        when(repository.save(clientDog)).thenReturn(clientDog);

        // Call the service method and assert that it returns the same ClientDog object
        ClientDog savedClientDog = service.create(clientDog);
        assertEquals(clientDog, savedClientDog);

        // Verify that the repository.save() method was called exactly once with the same ClientDog object
        verify(repository, times(1)).save(clientDog);
    }

    @Test
    void getById() {
// создаем объекты
        ClientDog clientDog = new ClientDog();
        clientDog.setId(1L);
        clientDog.setName("Alice");
        clientDog.setChatId(12345L);
        clientDog.setPhoneNumber("1234567890");
        clientDog.setEmail("alice@example.com");
        clientDog.setYearOfBirth(1990);
        clientDog.setStatus(ReportStatus.PENDING);

        Dog dog = new Dog();
        dog.setId(1L);
        dog.setName("Fluffy");
        dog.setAge(3);
        dog.setBreed("Persian");

        clientDog.setDog(dog);

        // имитируем получение объекта из репозитория
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(clientDog));

        // вызываем метод getById() и проверяем результат
        ClientDog returnedClientDog = service.getById(1L);

        assertNotNull(returnedClientDog);
        assertEquals(clientDog.getName(), returnedClientDog.getName());
        assertEquals(clientDog.getChatId(), returnedClientDog.getChatId());
        assertEquals(clientDog.getPhoneNumber(), returnedClientDog.getPhoneNumber());
        assertEquals(clientDog.getEmail(), returnedClientDog.getEmail());
        assertEquals(clientDog.getYearOfBirth(), returnedClientDog.getYearOfBirth());
        assertEquals(clientDog.getStatus(), returnedClientDog.getStatus());
        assertEquals(clientDog.getDog().getName(), returnedClientDog.getDog().getName());
        assertEquals(clientDog.getDog().getAge(), returnedClientDog.getDog().getAge());
        assertEquals(clientDog.getDog().getBreed(), returnedClientDog.getDog().getBreed());


        // проверяем, что метод findById() был вызван один раз с нужным аргументом
        Mockito.verify(repository, Mockito.times(1)).findById(1L);

        // проверяем, что метод findById() был вызван только один раз
        Mockito.verify(repository, Mockito.times(1)).findById(Mockito.anyLong());
    }

    @Test
    void update() {
        // создание объекта ClientDog и заполнение его полями
        ClientDog clientDog = new ClientDog();
        clientDog.setId(1L);
        clientDog.setName("Buddy");
        clientDog.setChatId(123456789L);
        clientDog.setPhoneNumber("+1234567890");
        clientDog.setEmail("buddy@example.com");
        clientDog.setYearOfBirth(2018);
        clientDog.setStatus(ReportStatus.NEW);

        Dog dog = new Dog();
        dog.setId(1L);
        dog.setName("Buddy's Dog");
        clientDog.setDog(dog);

        // мокирование метода repository.findById() для возврата созданного объекта ClientDog
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(clientDog));

        // вызов метода update() с измененным полем name
        clientDog.setName("Charlie");
        ClientDog updatedClientDog = service.update(clientDog);

        // проверка, что возвращается тот же объект ClientDog
        Assert.assertEquals(clientDog, updatedClientDog);

        // проверка, что метод repository.save() был вызван ровно один раз с указанным объектом ClientDog
        Mockito.verify(repository, Mockito.times(1)).save(clientDog);


    }

    @Test
    void removeById() {
        // создаем объект
        ClientDog clientDog = new ClientDog();
        clientDog.setId(1L);
        clientDog.setName("Alice");
        clientDog.setChatId(12345L);
        clientDog.setPhoneNumber("1234567890");
        clientDog.setEmail("alice@example.com");
        clientDog.setYearOfBirth(1990);
        clientDog.setStatus(ReportStatus.PENDING);

        Dog dog = new Dog();
        dog.setId(1L);
        dog.setName("Fluffy");
        dog.setAge(3);
        dog.setBreed("Persian");
        clientDog.setDog(dog);

        // имитируем получение объекта из репозитория
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(clientDog));

        // вызываем метод removeById()
        service.removeById(1L);

        // проверяем, что метод findById() был вызван один раз с нужным аргументом
        Mockito.verify(repository, Mockito.times(1)).findById(1L);

        // проверяем, что метод deleteById() был вызван один раз с нужным аргументом
        Mockito.verify(repository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void getAll() {
        ClientDog clientDog1 = new ClientDog();
        clientDog1.setName("John");
        clientDog1.setChatId(123456789L);
        clientDog1.setPhoneNumber("+1234567890");
        clientDog1.setStatus(ReportStatus.NEW);

        Dog dog1 = new Dog();
        dog1.setName("Tom");
        dog1.setBreed("Siamese");
        clientDog1.setDog(dog1);

        ClientDog ClientDog2 = new ClientDog();
        ClientDog2.setName("Alice");
        ClientDog2.setChatId(987654321L);
        ClientDog2.setPhoneNumber("+0987654321");
        ClientDog2.setEmail("alice@example.com");
        ClientDog2.setYearOfBirth(1990);
        ClientDog2.setStatus(ReportStatus.PENDING);

        Dog dog2 = new Dog();
        dog2.setName("Luna");
        dog2.setBreed("Persian");
        ClientDog2.setDog(dog2);

        List<ClientDog> ClientDogs = Arrays.asList(clientDog1, ClientDog2);

        Mockito.when(repository.findAll()).thenReturn(ClientDogs);

        Collection<ClientDog> result = service.getAll();

        Assert.assertEquals(ClientDogs.size(), result.size());
        Assert.assertTrue(result.containsAll(ClientDogs));
    }

    @Test
    void getByChatId() {
        Long chatId = 123456789L;
        ClientDog clientDog = new ClientDog();
        clientDog.setName("John");
        clientDog.setChatId(chatId);
        clientDog.setPhoneNumber("+1234567890");
        clientDog.setStatus(ReportStatus.NEW);

        Dog dog = new Dog();
        dog.setName("Tom");
         dog.setBreed("Siamese");
        clientDog.setDog(dog);

        Mockito.when(repository.findByChatId(chatId)).thenReturn(clientDog);

        ClientDog result = service.getByChatId(chatId);

        Assert.assertEquals(clientDog, result);
    }
}