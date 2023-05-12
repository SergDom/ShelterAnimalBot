//package com.javadreamteam.shelteranimalbot.controller;
//
//import com.javadreamteam.shelteranimalbot.controllers.ReportCatController;
//import com.javadreamteam.shelteranimalbot.keyboard.ReportStatus;
//import com.javadreamteam.shelteranimalbot.model.ClientCat;
//import com.javadreamteam.shelteranimalbot.model.ReportCat;
//import com.javadreamteam.shelteranimalbot.repository.ClientCatRepository;
//import com.javadreamteam.shelteranimalbot.repository.ReportCatRepository;
//import com.javadreamteam.shelteranimalbot.service.ReportCatService;
//import org.json.JSONObject;
//import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.mockito.InjectMocks;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.mock.mockito.SpyBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(ReportCatController.class)
//public class ReportCatControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ReportCatRepository repository;
//
//    @MockBean
//    private ClientCatRepository clientCatRepository;
//
//    @SpyBean
//    private ReportCatService service;
//
//    @InjectMocks
//    private ReportCatController controller;
//    private final JSONObject owner = new JSONObject();
//    private final JSONObject jsonObject = new JSONObject();
//
//    ReportCat report = new ReportCat();
//
//    @BeforeEach
//    public void setup() throws Exception {
//        final long id = 1L;
//        final String ration = "ration";
//        final String info = "info";
//        final String habits = "habits";
//        final ReportStatus reportStatus = ReportStatus.POSTED;
//        final Long chat_id = 12345L;
//        final LocalDate dateMessage = LocalDate.now();
//        final byte[] photo = new byte[]{0};
//        final ClientCat clientCat = new ClientCat(1L, "owner", "+1234567");
//
//
//        owner.put("chatId", 1L);
//        owner.put("name", "owner");
//        owner.put("phone", "+1234567");
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
//        jsonObject.put("clientCat", owner);
//
//        ReportCat report = new ReportCat(chat_id, photo, ration, info,
//                habits, dateMessage);
//        report.setId(id);
//        report.setReportStatus(reportStatus);
//        report.setClientCat(clientCat);
//
//        when(repository.save(any())).thenReturn(report);
//        when(repository.findById(any(Long.class))).thenReturn(Optional.of(report));
//        when(clientCatRepository.findByChatId(anyLong())).thenReturn(clientCat);
//    }
//
//    @Test
//    public void createReport() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/report_cat/")
//                        .content(jsonObject.toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(jsonObject.))
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
//    @Test
//    public void updateReport() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders
//                        .put("/report_cat/" + id)
//                        .content(jsonObject.toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .param("Статус", String.valueOf(ReportStatus.REPORT_REJECTED)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(id))
//                .andExpect(jsonPath("$.animalDiet").value(animalDiet))
//                .andExpect(jsonPath("$.generalInfo").value(generalInfo))
//                .andExpect(jsonPath("$.changeBehavior").value(changeBehavior))
//                .andExpect(jsonPath("$.reportStatus").value(ReportStatus.REPORT_REJECTED.toString()))
//                .andExpect(jsonPath("$.chatId").value(chat_id))
//                .andExpect(jsonPath("$.dateMessage").value(dateMessage.toString()))
//                .andExpect(jsonPath("$.ownerCat").value(ownerCat));
//    }
////
////        mockMvc.perform(MockMvcRequestBuilders
////                        .delete("/report_cat/" + id)
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .accept(MediaType.APPLICATION_JSON))
////                .andExpect(status().isOk());
////
////        mockMvc.perform(MockMvcRequestBuilders
////                        .get("/report_cat")
////                        .accept(MediaType.APPLICATION_JSON))
////                .andExpect(status().isOk());
////    }
//
//}
