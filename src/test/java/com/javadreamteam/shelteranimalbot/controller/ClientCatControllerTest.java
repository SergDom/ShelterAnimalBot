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
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest (ClientCatController.class)
public class ClientCatControllerTest {
    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private ClientCatRepository clientCatRepository;


    @MockBean
    private ClientCatService clientCatService;


    @InjectMocks
    private ClientCatController clientCatController;


    private final ClientCat client = new ClientCat();
    private final JSONObject jsonClient = new JSONObject();

    @BeforeEach
    public void setup() throws Exception {

        client.setId(2L);
        client.setName("Федор");
        client.setPhoneNumber("89991234567");
        client.setStatus(ClientStatus.IN_SEARCH);


        jsonClient.put("id", client.getId());
        jsonClient.put("name", client.getName());
        jsonClient.put("phoneNumber", client.getPhoneNumber());
        jsonClient.put("status", client.getStatus());


        when(clientCatService.create(client, ClientStatus.IN_SEARCH)).thenReturn(client);
        when(clientCatService.update(client)).thenReturn(client);
        when(clientCatService.getById(any())).thenReturn(client);
        when(clientCatService.getAll()).thenReturn(List.of(client));
    }

    @Test
    public void createClient() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/clients/cat")
                        .content(jsonClient.toString())
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(client.getId()))
                .andExpect(jsonPath("$.name").value(client.getName()))
                .andExpect(jsonPath("$.phoneNumber").value(client.getPhoneNumber()))
                .andExpect(jsonPath("$.status").value(client.getStatus()));

    }

    @Test
    public void updateClient() throws Exception {
        client.setName("New name");
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/clients/cat")
                        .content(jsonClient.toString())
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chatId").value(client.getChatId()))
                .andExpect(jsonPath("$.name").value(client.getName()))
                .andExpect(jsonPath("$.phoneNumber").value(client.getPhoneNumber()))
                .andExpect(jsonPath("$.status").value(client.getStatus())
                );

    }

    @Test
    public void removeClient() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/clients/cat/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getClient() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/clients/cat/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getClientAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/clients/cat/find-all-records")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}

