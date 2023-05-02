package com.javadreamteam.shelteranimalbot.service;

import com.javadreamteam.shelteranimalbot.keyboard.ReportStatus;
import com.javadreamteam.shelteranimalbot.model.ReportCat;
import com.javadreamteam.shelteranimalbot.model.ReportDog;
import com.javadreamteam.shelteranimalbot.repository.ReportCatRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
class ReportCatServiceTest {
    @Mock
    private ReportCatRepository reportRepository;
    @InjectMocks
    private ReportCatService reportService;

    @Test
      void createReport() {


        ReportCat reportCat = new ReportCat();
        reportCat.setChatId(123456789L);
        reportCat.setRation("Dry food");
        reportCat.setHealth("Good");
        reportCat.setHabits("Likes to play fetch");
        reportCat.setInfo("Report on Cat's daily routine");
        reportCat.setPhoto(new byte[] {0x00, 0x01, 0x02});
        reportCat.setLastMessage(LocalDate.now());
        reportCat.setReportStatus(ReportStatus.NEW);

        Mockito.when(reportRepository.save(reportCat)).thenReturn(reportCat);

        ReportCat result = reportService.createReport(reportCat);

        Assert.assertEquals(reportCat, result);
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
        ReportCat report = new ReportCat();
        when(reportRepository.save(any(ReportCat.class))).thenReturn(report);

        // Вызываем метод downloadReport()
        ReportCat downloadedReport = reportService.downloadReport(chatId, ration, health, habits, lastMessage, photo);

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
        verify(reportRepository, times(1)).save(any(ReportCat.class));
    }
//
    @Test
    void findReportById() {
// Создаем тестовые данные
Long id = 1L;
    ReportCat report = new ReportCat();
        report.setId(id);

    // Задаем поведение mock объекта reportRepository
    when(reportRepository.findById(id)).thenReturn(Optional.of(report));

    // Вызываем метод findReportById()
    ReportCat foundReport = reportService.findReportById(id);

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
        ReportCat report = new ReportCat();
        report.setId(id);

        // Задаем поведение mock объекта reportRepository
        when(reportRepository.findById(id)).thenReturn(Optional.of(report));
        when(reportRepository.save(report)).thenReturn(report);

        // Вызываем метод updateReport()
        ReportCat updatedReport = reportService.updateReport(report);

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

    ReportCat report = new ReportCat();
        report.setId(id);

    when(reportRepository.findById(id)).thenReturn(Optional.of(report));

        reportService.deleteReport(id);

    verify(reportRepository).deleteById(id);
}


    @Test
    void getAllReports() {
        ReportCat report1 = new ReportCat();
        report1.setId(1L);
        report1.setChatId(123456L);
        report1.setRation("Dry food");
        report1.setHealth("Good");
        report1.setHabits("Likes to play fetch");
        report1.setInfo("Report for the week of 10/25/2021");
        report1.setPhoto(new byte[] {0, 1, 2});
        report1.setLastMessage(LocalDate.now());
        report1.setReportStatus(ReportStatus.PENDING);

        ReportCat report2 = new ReportCat();
        report2.setId(2L);
        report2.setChatId(789012L);
        report2.setRation("Wet food");
        report2.setHealth("Needs to lose weight");
        report2.setHabits("Sleeps a lot");
        report2.setInfo("Report for the week of 10/18/2021");
        report2.setPhoto(new byte[] {3, 4, 5});
        report2.setLastMessage(LocalDate.now().minusWeeks(1));
        report2.setReportStatus(ReportStatus.APPROVED);

        Collection<ReportCat> reports = new ArrayList<>();
        reports.add(report1);
        reports.add(report2);

        when(reportRepository.findAll()).thenReturn((List<ReportCat>) reports);

        Collection<ReportCat> result = reportService.getAllReports();

        assertEquals(2, result.size());
        assertEquals("Dry food", result.iterator().next().getRation());
    }
    }

