package com.javadreamteam.shelteranimalbot.controller;

import com.javadreamteam.shelteranimalbot.controllers.*;
import com.javadreamteam.shelteranimalbot.model.Dog;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest (DogController.class)
public class DogControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DogRepository dogRepository;


    @MockBean
    private DogService dogService;


    @InjectMocks
    private DogController dogController;



    private final Dog animal = new Dog();
    private final JSONObject jsonAnimal = new JSONObject();

    @BeforeEach
    public void setup() throws Exception {

        animal.setId(1L);
        animal.setName("Лайка");
        animal.setAge(2);
        animal.setBreed("metis");
        animal.setInfo("С пятном на спине");

        jsonAnimal.put("id", animal.getId());
        jsonAnimal.put("name", animal.getName());
        jsonAnimal.put("age", animal.getAge());
        jsonAnimal.put("breed", animal.getBreed());
        jsonAnimal.put("info", animal.getInfo());

        when(dogService.create(animal)).thenReturn(animal);
        when(dogService.update(animal)).thenReturn(animal);
        when(dogService.getById(any())).thenReturn(animal);
        when(dogService.getAll()).thenReturn(List.of(animal));
    }


    @Test
    public void createDog () throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/dogs")
                        .content(jsonAnimal.toString())
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(animal.getName()))
                .andExpect(jsonPath("$.age").value(animal.getAge()))
                .andExpect(jsonPath("$.breed").value(animal.getBreed()))
                .andExpect(jsonPath("$.info").value(animal.getInfo()));
    }

    @Test
    public void updateDog () throws Exception {
        animal.setName("New name");
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/dogs")
                        .content(jsonAnimal.toString())
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(animal.getName()))
                .andExpect(jsonPath("$.age").value(animal.getAge()))
                .andExpect(jsonPath("$.breed").value(animal.getBreed()))
                .andExpect(jsonPath("$.info").value(animal.getInfo()));
    }

    @Test
    public void removeDog () throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/dogs/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getDog () throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/dogs/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void getDogAll () throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/dogs/find-all-dogs")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
