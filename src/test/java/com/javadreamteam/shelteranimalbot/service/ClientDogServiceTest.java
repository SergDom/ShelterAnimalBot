package com.javadreamteam.shelteranimalbot.service;

import com.javadreamteam.shelteranimalbot.exceptions.ClientException;
import com.javadreamteam.shelteranimalbot.model.ClientDog;
import com.javadreamteam.shelteranimalbot.repository.ClientDogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ClientDogServiceTest {
    @Mock
    private ClientDogRepository repository;
    @InjectMocks
    private ClientDogService clientDogService;


    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        clientDogService = new ClientDogService(repository);
    }

    @Test
    void testCreate() {
        ClientDog clientDog = new ClientDog("John", 123456789L, "123-456-7890");
         assertNull(clientDog.getId());

        clientDogService.create(clientDog);

        assertNotNull(clientDog.getId());
    }


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
    public void testUpdateSuccess() {
        Long id = 1L;
        ClientDog clientDog = new ClientDog();
        clientDog.setId(id);
        clientDog.setName("Test");
        clientDog.setChatId(123456789L);
        clientDog.setPhoneNumber("1234567890");

        when(repository.getById(id)).thenReturn(clientDog);
        when(repository.save(clientDog)).thenReturn(clientDog);

        ClientDog result = clientDogService.update(clientDog);

        assertEquals(clientDog, result);
    }

    @Test
    public void testUpdateFail() {
        Long id = 1L;
        ClientDog clientDog = new ClientDog();
        clientDog.setId(id);
        clientDog.setName("Test");
        clientDog.setChatId(123456789L);
        clientDog.setPhoneNumber("1234567890");

        when(repository.getById(id)).thenReturn(null);

        clientDogService.update(clientDog);
    }



}


