package com.javadreamteam.shelteranimalbot.controller;

import com.javadreamteam.shelteranimalbot.controllers.ReportCatController;
import com.javadreamteam.shelteranimalbot.controllers.ReportDogController;
import com.javadreamteam.shelteranimalbot.keyboard.ReportStatus;
import com.javadreamteam.shelteranimalbot.model.*;
import com.javadreamteam.shelteranimalbot.repository.ClientCatRepository;
import com.javadreamteam.shelteranimalbot.repository.ClientDogRepository;
import com.javadreamteam.shelteranimalbot.repository.ReportCatRepository;
import com.javadreamteam.shelteranimalbot.repository.ReportDogRepository;
import com.javadreamteam.shelteranimalbot.service.ReportCatService;
import com.javadreamteam.shelteranimalbot.service.ReportDogService;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportDogController.class)
public class ReportDogControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportDogRepository repository;

    @MockBean
    private ClientDogRepository clientDogRepository;


    @SpyBean
    private ReportDogService service;

    @InjectMocks
    private ReportDogController controller;

    private final JSONObject jsonOwner = new JSONObject();
    private final JSONObject jsonAnimal = new JSONObject();
    private final JSONObject jsonReport = new JSONObject();

    private final ReportDog report = new ReportDog();
    private final ClientDog clientDog = new ClientDog();
    private final Dog dog = new Dog();


    @BeforeEach
    public void setup() throws Exception {

        clientDog.setChatId(1L);
        clientDog.setName("Ivan");
        clientDog.setPhoneNumber("+123456789");

        dog.setId(1L);
        dog.setName("Mur-Mur");


        report.setId(1L);
        report.setRation("ration");
        report.setInfo("Well Being");
        report.setHabits("Good");
        report.setLastMessage(LocalDate.now());
        report.setClientDog(clientDog);


        jsonOwner.put("chatId", report.getClientDog().getId());
        jsonOwner.put("name", report.getClientDog().getName());
        jsonOwner.put("phone", report.getClientDog().getPhoneNumber());


        jsonReport.put("id", report.getId());
        jsonReport.put("ration", report.getRation());
        jsonReport.put("info", report.getInfo());
        jsonReport.put("habits", report.getHabits());
        jsonReport.put("dateMessage", report.getLastMessage());
        jsonReport.put("clientDog", report.getClientDog());


        jsonAnimal.put("id", report.getClientDog().getId());
        jsonAnimal.put("name", report.getClientDog().getName());

        when(repository.findById(any())).thenReturn(Optional.of(report));
        when(repository.findAll()).thenReturn(List.of(report));
        when(repository.save(any())).thenReturn(report);


    }

    @Test
    public void findReportById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/report_dog/" + report.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(report.getId()))
                .andExpect(jsonPath("$.ration").value(report.getRation()))
                .andExpect(jsonPath("$.info").value(report.getInfo()))
                .andExpect(jsonPath("$.dateMessage").value(report.getLastMessage().toString()))
                .andExpect(jsonPath("$.habits").value(report.getHabits()))
                .andExpect(jsonPath("$.clientDog.id").value(report.getClientDog().getId()))
                .andExpect(jsonPath("$.clientDog.name").value(report.getClientDog().getName()))
                .andExpect(jsonPath("$.clientDog.phone").value(report.getClientDog().getPhoneNumber()));
    }

    @Test
    public void createReport() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/report_dog/")
                        .content(jsonAnimal.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(report.getId()))
                .andExpect(jsonPath("$.ration").value(report.getRation()))
                .andExpect(jsonPath("$.info").value(report.getInfo()))
                .andExpect(jsonPath("$.dateMessage").value(report.getLastMessage().toString()))
                .andExpect(jsonPath("$.habits").value(report.getHabits()))
                .andExpect(jsonPath("$.clientDog.id").value(report.getClientDog().getId()))
                .andExpect(jsonPath("$.clientDog.name").value(report.getClientDog().getName()))
                .andExpect(jsonPath("$.clientDog.phone").value(report.getClientDog().getPhoneNumber()));
    }


    @Test
    public void deleteById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/report_dog/1"))
                .andExpect(status().isOk());
    }


    @Test
    public void findAllReport() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/report_dog/all")
                        .content(jsonAnimal.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(report.getId()))
                .andExpect(jsonPath("$[0].ration").value(report.getRation()))
                .andExpect(jsonPath("$[0].info").value(report.getInfo()))
                .andExpect(jsonPath("$[0].dateMessage").value(report.getLastMessage().toString()))
                .andExpect(jsonPath("$[0].habits").value(report.getHabits()))
                .andExpect(jsonPath("$[0].clientDog.id").value(report.getClientDog().getId()))
                .andExpect(jsonPath("$[0].clientDog.name").value(report.getClientDog().getName()))
                .andExpect(jsonPath("$[0].clientDog.phone").value(report.getClientDog().getPhoneNumber()));
    }

    @Test
    public void updateReport() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/report_dog/" + report.getId())
                        .content(jsonAnimal.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(report.getId()))
                .andExpect(jsonPath("$.ration").value(report.getRation()))
                .andExpect(jsonPath("$.info").value(report.getInfo()))
                .andExpect(jsonPath("$.dateMessage").value(report.getLastMessage().toString()))
                .andExpect(jsonPath("$.habits").value(report.getHabits()))
                .andExpect(jsonPath("$.clientDog.id").value(report.getClientDog().getId()))
                .andExpect(jsonPath("$.clientDog.name").value(report.getClientDog().getName()))
                .andExpect(jsonPath("$.clientDog.phone").value(report.getClientDog().getPhoneNumber()));
    }


}
