package com.javadreamteam.shelteranimalbot.controller;

import com.javadreamteam.shelteranimalbot.controllers.*;
import com.javadreamteam.shelteranimalbot.keyboard.ClientStatus;
import com.javadreamteam.shelteranimalbot.model.ClientCat;
import com.javadreamteam.shelteranimalbot.model.ClientDog;
import com.javadreamteam.shelteranimalbot.repository.*;
import com.javadreamteam.shelteranimalbot.service.*;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest (ClientDogController.class)
public class ClientDogControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientDogRepository clientDogRepository;


    @MockBean
    private ClientDogService clientDogService;


    @InjectMocks
    private ClientDogController clientDogController;

    private final ClientDog client = new ClientDog();
    private final JSONObject jsonClient = new JSONObject();

    @BeforeEach
    public void setup() throws Exception {

        client.setId(1L);
        client.setName("Иван");
        client.setPhoneNumber("89991234567");


        jsonClient.put("id", client.getId());
        jsonClient.put("name", client.getName());
        jsonClient.put("phoneNumber", client.getPhoneNumber());
        jsonClient.put("status", client.getStatus());


        when(clientDogService.create(client, ClientStatus.IN_SEARCH)).thenReturn(client);
        when(clientDogService.update(client)).thenReturn(client);
        when(clientDogService.getById(any())).thenReturn(client);
        when(clientDogService.getAll()).thenReturn(List.of(client));
    }

    @Test
    public void createClient () throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/clients")
                        .content(jsonClient.toString())
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(client.getId()))
                .andExpect(jsonPath("$.name").value(client.getName()))
                .andExpect(jsonPath("$.phoneNumber").value(client.getPhoneNumber()))
                .andExpect(jsonPath("$.status").value(client.getStatus()));

    }

    @Test
    public void updateClient () throws Exception {
        client.setName("New name");
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/clients")
                        .content(jsonClient.toString())
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chatId").value(client.getChatId()))
                .andExpect(jsonPath("$.name").value(client.getName()))
                .andExpect(jsonPath("$.phoneNumber").value(client.getPhoneNumber()));

    }

    @Test
    public void removeClient () throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/clients/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getClient () throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/clients/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void getClientAll () throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/clients/find-all-records")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
