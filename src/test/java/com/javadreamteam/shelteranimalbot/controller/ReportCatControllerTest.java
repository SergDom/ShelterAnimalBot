package com.javadreamteam.shelteranimalbot.controller;

import com.javadreamteam.shelteranimalbot.controllers.ClientCatController;
import com.javadreamteam.shelteranimalbot.controllers.ReportCatController;
import com.javadreamteam.shelteranimalbot.controllers.ReportDogController;
import com.javadreamteam.shelteranimalbot.keyboard.ClientStatus;
import com.javadreamteam.shelteranimalbot.keyboard.ReportStatus;
import com.javadreamteam.shelteranimalbot.model.Cat;
import com.javadreamteam.shelteranimalbot.model.ClientCat;
import com.javadreamteam.shelteranimalbot.model.ReportCat;
import com.javadreamteam.shelteranimalbot.model.User;
import com.javadreamteam.shelteranimalbot.repository.ClientCatRepository;
import com.javadreamteam.shelteranimalbot.repository.ReportCatRepository;
import com.javadreamteam.shelteranimalbot.service.ReportCatService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
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

@WebMvcTest (ReportCatController.class)
public class ReportCatControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportCatRepository repository;

    @MockBean
    private ClientCatRepository clientCatRepository;


    @SpyBean
    private ReportCatService service;

    @InjectMocks
    private ReportCatController controller;

    private final JSONObject jsonOwner = new JSONObject();
    private final JSONObject jsonAnimal = new JSONObject();
    private final JSONObject jsonReport = new JSONObject();

    private final ReportCat report = new ReportCat();
    private final ClientCat clientCat = new ClientCat();
    private final Cat cat = new Cat();


    @BeforeEach
//    @Test
    public void setup() throws Exception {
//        final long id = 1L;
//        final String ration = "ration";
//        final String info = "info";
//        final String habits = "habits";
//        final ReportStatus reportStatus = ReportStatus.APPROVED;
//        final Long chat_id = 12345L;
//        final LocalDate dateMessage = LocalDate.now();
//        final byte[] photo = new byte[]{0};
//        final ClientCat clientCat = new ClientCat(1L, "owner", "+123456789");
//
//
//        JSONObject owner = new JSONObject();
//        owner.put("chatId", 1L);
//        owner.put("name", "owner");
//        owner.put("phoneNumber", "+123456789");

        clientCat.setChatId(1L);
        clientCat.setName("Ivan");
        clientCat.setPhoneNumber("+123456789");

        cat.setId(1L);
        cat.setName("Mur-Mur");


        report.setId(1L);
        report.setRation("ration");
        report.setInfo("Well Being");
        report.setHabits("Good");
        report.setLastMessage(LocalDate.now());
        report.setClientCat(clientCat);


        jsonOwner.put("chatId", report.getClientCat().getId());
        jsonOwner.put("name", report.getClientCat().getName());
        jsonOwner.put("phone", report.getClientCat().getPhoneNumber());

//        JSONObject jsonObject = new JSONObject();
//
//
//        jsonObject.put("id", id);
//        jsonObject.put("ration", ration);
//        jsonObject.put("info", info);
//        jsonObject.put("habits", habits);
//        jsonObject.put("reportStatus", reportStatus);
//        jsonObject.put("chatId", chat_id);
//        jsonObject.put("dateMessage", dateMessage);
//        jsonObject.put("photo", Arrays.toString(photo));
//        jsonObject.put("owner", owner);

        jsonReport.put("id", report.getId());
        jsonReport.put("ration", report.getRation());
        jsonReport.put("info", report.getInfo());
        jsonReport.put("habits", report.getHabits());
        jsonReport.put("dateMessage", report.getLastMessage());
        jsonReport.put("clientCat", report.getClientCat());


        jsonAnimal.put("id", report.getClientCat().getId());
        jsonAnimal.put("name", report.getClientCat().getName());
//        jsonAnimal.put("ration", ration);
//        jsonAnimal.put("info", info);
//        jsonAnimal.put("habits", habits);
//        jsonAnimal.put("reportStatus", reportStatus);
//        jsonAnimal.put("chatId", chat_id);
//        jsonAnimal.put("dateMessage", dateMessage);
//        jsonAnimal.put("photo", Arrays.toString(photo));
//        jsonAnimal.put("clientCat", jsonOwner);
//
//        ReportCat report = new ReportCat(chat_id, photo, ration, info,
//                habits, dateMessage);
//        report.setId(id);
//        report.setReportStatus(reportStatus);
//        report.setClientCat(clientCat);
//
//        when(repository.save(any(ReportCat.class))).thenReturn(report);
//        when(repository.findById(any(Long.class))).thenReturn(Optional.of(report));
//        when(repository.existsById(eq(id))).thenReturn(true);
//        when(clientCatRepository.findByChatId(anyLong())).thenReturn(clientCat);

        when(repository.findById(any())).thenReturn(Optional.of(report));
        when(repository.findAll()).thenReturn(List.of(report));
        when(repository.save(any())).thenReturn(report);

//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/report_cat/" + id)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(id))
//                .andExpect(jsonPath("$.ration").value(ration))
//                .andExpect(jsonPath("$.info").value(info))
//                .andExpect(jsonPath("$.habits").value(habits))
//                .andExpect(jsonPath("$.reportStatus").value(reportStatus.toString()))
//                .andExpect(jsonPath("$.chatId").value(chat_id))
//                .andExpect(jsonPath("$.dateMessage").value(dateMessage.toString()))
//                .andExpect(jsonPath("$.clientCatCat").value(clientCat));
//
//        report.setReportStatus(ReportStatus.REFUSED);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .put("/report_cat/" + id)
//                        .content(jsonObject.toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .param("Статус", String.valueOf(ReportStatus.REFUSED)))
//
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(id))
//                .andExpect(jsonPath("$.ration").value(ration))
//                .andExpect(jsonPath("$.info").value(info))
//                .andExpect(jsonPath("$.habits").value(habits))
//                .andExpect(jsonPath("$.reportStatus").value(ReportStatus.REFUSED.toString()))
//                .andExpect(jsonPath("$.chatId").value(chat_id))
//                .andExpect(jsonPath("$.dateMessage").value(dateMessage.toString()))
//                .andExpect(jsonPath("$.clientCatCat").value(clientCat));

    }

    @Test
    public void findReportById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/report_cat/" + report.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(report.getId()))
                .andExpect(jsonPath("$.ration").value(report.getRation()))
                .andExpect(jsonPath("$.info").value(report.getInfo()))
//                .andExpect(jsonPath("$.dateMessage").value(report.getLastMessage().toString()))
                .andExpect(jsonPath("$.habits").value(report.getHabits()))
                .andExpect(jsonPath("$.clientCat.id").value(report.getClientCat().getId()))
                .andExpect(jsonPath("$.clientCat.name").value(report.getClientCat().getName()))
                .andExpect(jsonPath("$.clientCat.phoneNumber").value(report.getClientCat().getPhoneNumber()));
    }

    @Test
    public void deleteById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/report_cat/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void findAllReport() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/report_cat/all")
                        .content(jsonAnimal.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(report.getId()))
                .andExpect(jsonPath("$[0].ration").value(report.getRation()))
                .andExpect(jsonPath("$[0].info").value(report.getInfo()))
//                .andExpect(jsonPath("$[0].dateMessage").value(report.getLastMessage().toString()))
                .andExpect(jsonPath("$[0].habits").value(report.getHabits()))
                .andExpect(jsonPath("$[0].clientCat.id").value(report.getClientCat().getId()))
                .andExpect(jsonPath("$[0].clientCat.name").value(report.getClientCat().getName()))
                .andExpect(jsonPath("$[0].clientCat.phoneNumber").value(report.getClientCat().getPhoneNumber()));
    }

    //    @Test
//    public void createReport() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/report_cat/")
//                        .content(jsonAnimal.toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(jsonAnimal.))
//                .andExpect(jsonPath("$.ration").value(ration))
//                .andExpect(jsonPath("$.info").value(info))
//                .andExpect(jsonPath("$.habits").value(habits))
//                .andExpect(jsonPath("$.reportStatus").value(reportStatus.toString()))
//                .andExpect(jsonPath("$.chatId").value(chat_id))
//                .andExpect(jsonPath("$.dateMessage").value(dateMessage.toString()))
//                .andExpect(jsonPath("$.clientCat").value(clientCat));
//    }
////
////        report.setReportStatus(ReportStatus.REFUSED);
////
    @Test
    public void updateReport() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/report_cat/" + report.getId())
                        .content(jsonAnimal.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .param("Статус", String.valueOf(ReportStatus.REFUSED)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(report.getId()))
                .andExpect(jsonPath("$.ration").value(report.getRation()))
                .andExpect(jsonPath("$.info").value(report.getInfo()))
//                .andExpect(jsonPath("$.dateMessage").value(report.getLastMessage().toString()))
                .andExpect(jsonPath("$.habits").value(report.getHabits()))
                .andExpect(jsonPath("$.clientCat.id").value(report.getClientCat().getId()))
                .andExpect(jsonPath("$.clientCat.name").value(report.getClientCat().getName()))
                .andExpect(jsonPath("$.clientCat.phoneNumber").value(report.getClientCat().getPhoneNumber()));
    }

//        mockMvc.perform(MockMvcRequestBuilders
//                .delete("/report_cat/"+id)
//            .
//
//    contentType(MediaType.APPLICATION_JSON)
//                        .
//
//    accept(MediaType.APPLICATION_JSON))
//            .
//
//    andExpect(status().
//
//    isOk());
//
//        mockMvc.perform(MockMvcRequestBuilders
//                .get("/report_cat")
//                .
//
//    accept(MediaType.APPLICATION_JSON))
//            .
//
//    andExpect(status().
//
//    isOk());
//}
}
