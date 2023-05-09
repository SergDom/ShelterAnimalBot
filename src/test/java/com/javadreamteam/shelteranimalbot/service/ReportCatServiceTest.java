package com.javadreamteam.shelteranimalbot.service;

import static org.junit.jupiter.api.Assertions.*;
import com.javadreamteam.shelteranimalbot.keyboard.ReportStatus;
import com.javadreamteam.shelteranimalbot.model.ClientCat;
import com.javadreamteam.shelteranimalbot.model.ReportCat;
import com.javadreamteam.shelteranimalbot.model.ReportDog;
import com.javadreamteam.shelteranimalbot.repository.ClientCatRepository;
import com.javadreamteam.shelteranimalbot.repository.ReportCatRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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
    @Mock
    private ClientCatRepository clientCatRepository;
    @InjectMocks
    private ReportCatService reportService;

    @Test
    void createReport() {


        ReportCat reportCat = new ReportCat();
        reportCat.setChatId(123456789L);
        reportCat.setRation("Dry food");
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
        // Создание объекта ReportCat
        ReportCat report = new ReportCat();
        report.setId(1L);
        report.setChatId(123456789L);
        report.setRation("Some ration");
        report.setHabits("Some habits");
        report.setInfo("Some info");
        report.setPhoto(new byte[]{1, 2, 3});
        report.setLastMessage(LocalDate.now());
        report.setReportStatus(ReportStatus.POSTED);

        // Задание поведения зависимости clientCatRepository
        when(clientCatRepository.findByChatId(123456789L)).thenReturn(new ClientCat());

        // Задание поведения зависимости reportCatRepository
        when(reportRepository.save(any(ReportCat.class))).thenReturn(report);

        // Вызов метода downloadReport
        ReportCat result = reportService.downloadReport(123456789L, "Some ration", "Some info", "Some habits",
                LocalDate.now(), new byte[]{1, 2, 3});

        // Проверка, что метод findByChatId был вызван один раз
        verify(clientCatRepository, times(1)).findByChatId(123456789L);

        // Проверка, что метод save был вызван один раз с переданным объектом ReportCat
        ArgumentCaptor<ReportCat> argumentCaptor = ArgumentCaptor.forClass(ReportCat.class);
        verify(reportRepository, times(1)).save(argumentCaptor.capture());
        ReportCat savedReport = argumentCaptor.getValue();
        assertEquals(123456789L, savedReport.getChatId());
        assertEquals("Some ration", savedReport.getRation());
        assertEquals("Some info", savedReport.getInfo());
        assertEquals("Some habits", savedReport.getHabits());
        assertEquals(LocalDate.now(), savedReport.getLastMessage());
        assertArrayEquals(new byte[]{1, 2, 3}, savedReport.getPhoto());

        // Проверка, что результат равен созданному объекту ReportCat
        assertEquals(report, result);
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
        report1.setHabits("Likes to play fetch");
        report1.setInfo("Report for the week of 10/25/2021");
        report1.setPhoto(new byte[] {0, 1, 2});
        report1.setLastMessage(LocalDate.now());
        report1.setReportStatus(ReportStatus.PENDING);

        ReportCat report2 = new ReportCat();
        report2.setId(2L);
        report2.setChatId(789012L);
        report2.setRation("Wet food");
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