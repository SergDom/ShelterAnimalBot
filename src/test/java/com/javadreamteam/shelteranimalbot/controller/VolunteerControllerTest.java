package com.javadreamteam.shelteranimalbot.controller;


import com.javadreamteam.shelteranimalbot.controllers.VolunteerController;
import com.javadreamteam.shelteranimalbot.model.Volunteer;
import com.javadreamteam.shelteranimalbot.repository.VolunteerRepository;
import com.javadreamteam.shelteranimalbot.service.VolunteerService;
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

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VolunteerController.class)
public class VolunteerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private VolunteerRepository repository;
    @MockBean
    private VolunteerService service;
    @InjectMocks
    private VolunteerController controller;
    final JSONObject jsonObject = new JSONObject();
    Volunteer volunteer = new Volunteer();

    @BeforeEach
    public void setup() throws Exception {

        final long id = 1L;
        final long chatId = 1L;
        final String name = "Ivan";
        final String phone = "+79001234567";


        jsonObject.put("id", id);
        jsonObject.put("chatId", chatId);
        jsonObject.put("name", name);
        jsonObject.put("phone", phone);

        volunteer.setId(id);
        volunteer.setChatId(chatId);
        volunteer.setName(name);
        volunteer.setPhone(phone);


        when(service.createVolunteer(volunteer)).thenReturn(volunteer);
        when(service.updateVolunteer(volunteer.getId(), volunteer)).thenReturn(volunteer);
        when(service.getById(volunteer.getId())).thenReturn(volunteer);

    }

    @Test
    public void createVolunteer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/volunteers")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.id").value(volunteer.getId()))
                .andExpect(jsonPath("$.chatId").value(volunteer.getChatId()))
                .andExpect(jsonPath("$.name").value(volunteer.getName()))
                .andExpect(jsonPath("$.phone").value(volunteer.getPhone()));
    }

    @Test
    public void updateVolunteer() throws Exception {
//        volunteer.setName("New name");
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/volunteers/1")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.id").value(volunteer.getId()))
                .andExpect(jsonPath("$.chatId").value(volunteer.getChatId()))
                .andExpect(jsonPath("$.name").value(volunteer.getName()))
                .andExpect(jsonPath("$.phone").value(volunteer.getPhone()));
    }
    @Test
    public void getVolunteer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/volunteers/1")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.id").value(volunteer.getId()))
                .andExpect(jsonPath("$.chatId").value(volunteer.getChatId()))
                .andExpect(jsonPath("$.name").value(volunteer.getName()))
                .andExpect(jsonPath("$.phone").value(volunteer.getPhone()));
    }
}
