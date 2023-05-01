package com.javadreamteam.shelteranimalbot.service;

import com.javadreamteam.shelteranimalbot.keyboard.ReportStatus;
import com.javadreamteam.shelteranimalbot.model.Report;
import com.javadreamteam.shelteranimalbot.repository.ClientDogRepository;
import com.javadreamteam.shelteranimalbot.repository.ReportRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
@SpringBootTest
class ReportServiceTest {
    @Mock
    private ReportRepository reportRepository;
    @InjectMocks
    private ReportService reportService;

    @Test
      void createReport() {


        // Создаем тестовый отчет
        Report report = new Report();
        report.setChatId(123456789L);
        report.setRation("Test ration");
        report.setHealth("Test health");
        report.setHabits("Test habits");
        report.setLastMessage(LocalDate.now());
        report.setPhoto(new byte[]{1, 2, 3});

        // Задаем поведение mock объекта reportRepository

        when(reportRepository.save(report)).thenReturn(report);

        // Вызываем метод createReport()
        Report createdReport = reportService.createReport(report);

        // Проверяем, что созданный отчет не null и содержит правильные данные
        assertNotNull(createdReport);
        assertNotNull(createdReport.getId());
        assertEquals(123456789L, createdReport.getChatId().longValue());
        assertEquals("Test ration", createdReport.getRation());
        assertEquals("Test health", createdReport.getHealth());
        assertEquals("Test habits", createdReport.getHabits());
        assertEquals(LocalDate.now(), createdReport.getLastMessage());
        assertArrayEquals(new byte[]{1, 2, 3}, createdReport.getPhoto());

        // Проверяем, что метод save() был вызван у mock объекта reportRepository
        verify(reportRepository, times(1)).save(report);
    }

    @Test
    void downloadReport() {
        // Создаем тестовые данные
        Long chatId = 123456789L;
        String ration = "Test ration";
        String health = "Test health";
        String habits = "Test habits";
        LocalDate lastMessage = LocalDate.now();
        byte[] photo = new byte[]{1, 2, 3};

        // Задаем поведение mock объекта reportRepository
        Report report = new Report();
        when(reportRepository.save(any(Report.class))).thenReturn(report);

        // Вызываем метод downloadReport()
        Report downloadedReport = reportService.downloadReport(chatId, ration, health, habits, lastMessage, photo);

        // Проверяем, что созданный отчет не null и содержит правильные данные
        assertNotNull(downloadedReport);
        assertNotNull(downloadedReport.getId());
        assertEquals(chatId, downloadedReport.getChatId());
        assertEquals(ration, downloadedReport.getRation());
        assertEquals(health, downloadedReport.getHealth());
        assertEquals(habits, downloadedReport.getHabits());
        assertEquals(lastMessage, downloadedReport.getLastMessage());
        assertArrayEquals(photo, downloadedReport.getPhoto());
        assertEquals(ReportStatus.POSTED, downloadedReport.getReportStatus());

        // Проверяем, что метод save() был вызван у mock объекта reportRepository
        verify(reportRepository, times(1)).save(any(Report.class));
    }
//
    @Test
    void findReportById() {
// Создаем тестовые данные
Long id = 1L;
    Report report = new Report();
        report.setId(id);

    // Задаем поведение mock объекта reportRepository
    when(reportRepository.findById(id)).thenReturn(Optional.of(report));

    // Вызываем метод findReportById()
    Report foundReport = reportService.findReportById(id);

    // Проверяем, что найденный отчет не null и содержит правильный id
    assertNotNull(foundReport);
    assertEquals(id, foundReport.getId());

    // Проверяем, что метод findById() был вызван у mock объекта reportRepository
    verify(reportRepository, times(1)).findById(id);
}

//
    @Test
    void updateReport() {
        // Создаем тестовые данные
        Long id = 1L;
        Report report = new Report();
        report.setId(id);

        // Задаем поведение mock объекта reportRepository
        when(reportRepository.findById(id)).thenReturn(Optional.of(report));
        when(reportRepository.save(report)).thenReturn(report);

        // Вызываем метод updateReport()
        Report updatedReport = reportService.updateReport(report);

        // Проверяем, что обновленный отчет не null и содержит правильный id
        assertNotNull(updatedReport);
        assertEquals(id, updatedReport.getId());

        // Проверяем, что метод findById() был вызван у mock объекта reportRepository
        verify(reportRepository, times(1)).findById(id);

        // Проверяем, что метод save() был вызван у mock объекта reportRepository
        verify(reportRepository, times(1)).save(report);
    }

    @Test
    void deleteReport() {
Long id = 1L;

    Report report = new Report();
        report.setId(id);

    when(reportRepository.findById(id)).thenReturn(Optional.of(report));

        reportService.deleteReport(id);

    verify(reportRepository).deleteById(id);
}


    @Test
    void getAllReports() {
        Report report1 = new Report();
        report1.setId(1L);
        report1.setChatId(123456L);
        report1.setRation("Dry food");
        report1.setHealth("Good");
        report1.setHabits("Likes to play fetch");
        report1.setInfo("Report for the week of 10/25/2021");
        report1.setPhoto(new byte[] {0, 1, 2});
        report1.setLastMessage(LocalDate.now());
        report1.setReportStatus(ReportStatus.PENDING);

        Report report2 = new Report();
        report2.setId(2L);
        report2.setChatId(789012L);
        report2.setRation("Wet food");
        report2.setHealth("Needs to lose weight");
        report2.setHabits("Sleeps a lot");
        report2.setInfo("Report for the week of 10/18/2021");
        report2.setPhoto(new byte[] {3, 4, 5});
        report2.setLastMessage(LocalDate.now().minusWeeks(1));
        report2.setReportStatus(ReportStatus.APPROVED);

        Collection<Report> reports = new ArrayList<>();
        reports.add(report1);
        reports.add(report2);

        when(reportRepository.findAll()).thenReturn((List<Report>) reports);

        Collection<Report> result = reportService.getAllReports();

        assertEquals(2, result.size());
        assertEquals("Dry food", result.iterator().next().getRation());
    }
    }

