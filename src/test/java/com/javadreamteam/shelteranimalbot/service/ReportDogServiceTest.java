package com.javadreamteam.shelteranimalbot.service;

import com.javadreamteam.shelteranimalbot.keyboard.ReportStatus;
import com.javadreamteam.shelteranimalbot.model.ReportCat;
import com.javadreamteam.shelteranimalbot.model.ReportDog;
import com.javadreamteam.shelteranimalbot.repository.ReportCatRepository;
import com.javadreamteam.shelteranimalbot.repository.ReportDogRepository;
import org.apache.catalina.Service;
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
import static org.mockito.Mockito.*;

@SpringBootTest
class ReportDogServiceTest {
    @Mock
    private ReportDogRepository reportRepository;
    @InjectMocks
    private ReportDogService reportService;

    @Test
    void createReport() {
        ReportDog reportDog = new ReportDog();
        reportDog.setChatId(123456789L);
        reportDog.setRation("Dry food");
        reportDog.setHealth("Good");
        reportDog.setHabits("Likes to play fetch");
        reportDog.setInfo("Report on dog's daily routine");
        reportDog.setPhoto(new byte[] {0x00, 0x01, 0x02});
        reportDog.setLastMessage(LocalDate.now());
        reportDog.setReportStatus(ReportStatus.NEW);

        Mockito.when(reportRepository.save(reportDog)).thenReturn(reportDog);

        ReportDog result = reportService.createReport(reportDog);

        Assert.assertEquals(reportDog, result);

        //В этом тесте  создаем объект ReportDog с заполненными полями, используем Mockito для имитации вызова метода save() репозитория,
        // который возвращает этот объект. Затем вызываем метод createReport() сервиса с объектом ReportDog и сравниваем результат
        // с ожидаемым объектом.
    }

    @Test
    void downloadReport() {
        ReportDog reportDog = new ReportDog();
        reportDog.setChatId(123456789L);
        reportDog.setRation("Dry food");
        reportDog.setHealth("Good");
        reportDog.setHabits("Likes to play fetch");
        reportDog.setLastMessage(LocalDate.now());
        reportDog.setPhoto(new byte[] {0x00, 0x01, 0x02});
        reportDog.setReportStatus(ReportStatus.POSTED);

        Mockito.when(reportRepository.save(reportDog)).thenReturn(reportDog);

        ReportDog result = reportService.downloadReport(123456789L, "Dry food", "Good", "Likes to play fetch", LocalDate.now(), new byte[] {0x00, 0x01, 0x02});

        Assert.assertEquals(reportDog, result);
        //В этом тесте  создаем объект ReportDog с заполненными полями, используем Mockito для имитации вызова метода save() репозитория,
        // который возвращает этот объект. Затем мы вызываем метод downloadReport() сервиса с параметрами и сравниваем результат с
        // ожидаемым объектом.
    }

    @Test
    void findReportById() {
        ReportDog reportDog = new ReportDog();
        reportDog.setId(1L);
        reportDog.setChatId(123456789L);
        reportDog.setRation("Dry food");
        reportDog.setHealth("Good");
        reportDog.setHabits("Likes to play fetch");
        reportDog.setLastMessage(LocalDate.now());
        reportDog.setPhoto(new byte[] {0x00, 0x01, 0x02});
        reportDog.setReportStatus(ReportStatus.POSTED);

        Mockito.when(reportRepository.findById(1L)).thenReturn(Optional.of(reportDog));

        ReportDog result = reportService.findReportById(1L);

        Assert.assertEquals(reportDog, result);
        //В этом тесте  создаем объект ReportDog с заполненными полями и используем Mockito для имитации вызова метода findById() репозитория,
        // который возвращает этот объект. Затем мы вызываем метод findReportById() сервиса с параметром и сравниваем результат с
        // ожидаемым объектом.
    }

    @Test
    void updateReport() {
        // Создаем тестовые данные
        Long id = 1L;
        ReportDog report = new ReportDog();
        report.setId(id);

        // Задаем поведение mock объекта reportRepository
        when(reportRepository.findById(id)).thenReturn(Optional.of(report));
        when(reportRepository.save(report)).thenReturn(report);

        // Вызываем метод updateReport()
        ReportDog updatedReport = reportService.updateReport(report);

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
        ReportDog report = new ReportDog();
        report.setId(id);
        when(reportRepository.findById(id)).thenReturn(Optional.of(report));
        reportService.deleteReport(id);
        verify(reportRepository).deleteById(id);
    }

    @Test
    void getAllReports() {
        ReportDog report1 = new ReportDog();
        report1.setId(1L);
        report1.setChatId(123456L);
        report1.setRation("Dry food");
        report1.setHealth("Good");
        report1.setHabits("Likes to play fetch");
        report1.setInfo("Report for the week of 10/25/2021");
        report1.setPhoto(new byte[] {0, 1, 2});
        report1.setLastMessage(LocalDate.now());
        report1.setReportStatus(ReportStatus.PENDING);

        ReportDog report2 = new ReportDog();
        report2.setId(2L);
        report2.setChatId(789012L);
        report2.setRation("Wet food");
        report2.setHealth("Needs to lose weight");
        report2.setHabits("Sleeps a lot");
        report2.setInfo("Report for the week of 10/18/2021");
        report2.setPhoto(new byte[] {3, 4, 5});
        report2.setLastMessage(LocalDate.now().minusWeeks(1));
        report2.setReportStatus(ReportStatus.APPROVED);

        Collection<ReportDog> reports = new ArrayList<>();
        reports.add(report1);
        reports.add(report2);

        when(reportRepository.findAll()).thenReturn((List<ReportDog>) reports);

        Collection<ReportDog> result = reportService.getAllReports();

        assertEquals(2, result.size());
        assertEquals("Dry food", result.iterator().next().getRation());
    }
}