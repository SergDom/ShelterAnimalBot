package com.javadreamteam.shelteranimalbot.service;

import com.javadreamteam.shelteranimalbot.exceptions.ClientException;
import com.javadreamteam.shelteranimalbot.model.ClientDog;
import com.javadreamteam.shelteranimalbot.repository.ClientDogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
class ClientDogServiceTest {
    @Mock
    private ClientDogRepository repository;
    private ClientDogService clientDogService;


    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        clientDogService = new ClientDogService(repository);
    }

    @Test
    void testCreate() {
        ClientDog clientDog = new ClientDog();
        clientDog.setName("John");
        clientDog.setChatId(123456L);

        when(repository.save(clientDog)).thenReturn(clientDog);

        ClientDog createdClientDog = clientDogService.create(clientDog);

        assertNotNull(createdClientDog);
        assertEquals(clientDog.getName(), createdClientDog.getName());
        assertEquals(clientDog.getChatId(), createdClientDog.getChatId());

        verify(repository, times(1)).save(clientDog);
    }


//
    @Test
    public void testGetById() {
        Long id = 1L;
        ClientDog clientDog = new ClientDog();
        clientDog.setId(id);
        clientDog.setName("Test");
        clientDog.setChatId(123456789L);
        clientDog.setPhoneNumber("1234567890");

        when(repository.findById(id)).thenReturn(Optional.of(clientDog));

        ClientDog result = clientDogService.getById(id);

        assertEquals(clientDog, result);

 }


@Test
public void testRemoveById() {

    ClientDog clientDog = new ClientDog();
    clientDog.setId(1L);
    clientDog.setName("Test Dog");
    repository.save(clientDog);
    clientDogService.removeById(1L);
    assertFalse(repository.existsById(1L));
}
    @Test
    public void testGetAll() {
        // Создаем тестовые сущности в базе данных
        ClientDog clientDog1 = new ClientDog();
        clientDog1.setId(1L);
        clientDog1.setName("Test Dog 1");
        repository.save(clientDog1);

        ClientDog clientDog2 = new ClientDog();
        clientDog2.setId(2L);
        clientDog2.setName("Test Dog 2");
        repository.save(clientDog2);

        // Вызываем метод getAll()
        Collection<ClientDog> allClientDogs = clientDogService.getAll();

        // Проверяем, что количество полученных сущностей соответствует количеству созданных в базе данных
        assertEquals(2, allClientDogs.size());

        // Проверяем, что полученные сущности содержат правильные данные
        assertTrue(allClientDogs.stream().anyMatch(cd -> cd.getId().equals(1L) && cd.getName().equals("Test Dog 1")));
        assertTrue(allClientDogs.stream().anyMatch(cd -> cd.getId().equals(2L) && cd.getName().equals("Test Dog 2")));
    }

    @Test
    void getByChatId() {
        // Создаем тестовую сущность в базе данных
        ClientDog clientDog = new ClientDog();
        clientDog.setId(1L);
        clientDog.setName("Test Dog");
        clientDog.setChatId(123456789L);
        repository.save(clientDog);

        // Вызываем метод getByChatId() с существующим chatId
        ClientDog foundClientDog = clientDogService.getByChatId(123456789L);

        // Проверяем, что найденная сущность не null и содержит правильные данные
        assertNotNull(foundClientDog);
        assertEquals(1L, foundClientDog.getId().longValue());
        assertEquals("Test Dog", foundClientDog.getName());
        assertEquals(123456789L, foundClientDog.getChatId().longValue());

        // Вызываем метод getByChatId() с несуществующим chatId
        ClientDog notFoundClientDog = clientDogService.getByChatId(987654321L);

        // Проверяем, что не найдена никакая сущность
        assertNull(notFoundClientDog);

    }
}




