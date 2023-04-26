package com.javadreamteam.shelteranimalbot.controller;

import com.javadreamteam.shelteranimalbot.controllers.*;
import com.javadreamteam.shelteranimalbot.model.ClientDog;
import com.javadreamteam.shelteranimalbot.repository.*;
import com.javadreamteam.shelteranimalbot.service.*;
import org.json.JSONObject;
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

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ClientDogControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DogRepository dogRepository;
    @MockBean
    private CatRepository catRepository;
    @MockBean
    private ClientDogRepository clientDogRepository;
    @MockBean
    private ClientCatRepository clientCatRepository;
    @MockBean
    private ReportRepository reportRepository;

    @SpyBean
    private DogService dogService;
    @SpyBean
    private CatService catService;

    @SpyBean
    private ClientDogService clientDogService;
    @SpyBean
    private ClientCatService clientCatService;
    @SpyBean
    private ReportService reportService;

    @InjectMocks
    private DogController dogController;
    @InjectMocks
    private CatController catController;
    @InjectMocks
    private ClientDogController clientDogController;
    @InjectMocks
    private ClientCatController clientCatController;
    @InjectMocks
    private ReportController reportController;

    @Test
    public void clientDogTest() throws Exception {
        Long chatId = 12345L;
        String name = "Иван";
        String phoneNumber = "89991234567";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 1L);
        jsonObject.put("chatId", chatId);
        jsonObject.put("name", name);
        jsonObject.put("phoneNumber", phoneNumber);

        ClientDog clientDog = new ClientDog(name, chatId, phoneNumber);

        when(clientDogRepository.save(any(ClientDog.class))).thenReturn(clientDog);
        when(clientDogRepository.findById(any(Long.class))).thenReturn(Optional.of(clientDog));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/clients")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.chatId").value(chatId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value(phoneNumber));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/clients/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.chatId").value(chatId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value(phoneNumber));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/clients")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/clients/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/clients/find-all-records")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
