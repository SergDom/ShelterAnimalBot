package com.javadreamteam.shelteranimalbot.controller;

import com.javadreamteam.shelteranimalbot.controllers.*;
import com.javadreamteam.shelteranimalbot.model.Cat;
import com.javadreamteam.shelteranimalbot.model.Dog;
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
public class CatControllerTest {

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
    public void catTest() throws Exception {

        Long id = 3L;
        String name = "Мурка";
        Integer age = 5;
        String breed = "Британская";
        String info = "Гладкошерстая";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("name", name);
        jsonObject.put("age", age);
        jsonObject.put("breed", breed);
        jsonObject.put("info", info);

        Cat cat = new Cat(id, name, breed, age, info);

        when(catRepository.findById(any(Long.class))).thenReturn(Optional.of(cat));
        when(catRepository.save(cat)).thenReturn(cat);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/cats")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(age))
                .andExpect(MockMvcResultMatchers.jsonPath("$.breed").value(breed))
                .andExpect(MockMvcResultMatchers.jsonPath("$.info").value(info));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/cats/3")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(age))
                .andExpect(MockMvcResultMatchers.jsonPath("$.breed").value(breed))
                .andExpect(MockMvcResultMatchers.jsonPath("$.info").value(info));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/cats")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/cats/3")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/cats/find-all-cats")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
