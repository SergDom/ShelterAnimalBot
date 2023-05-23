package com.javadreamteam.shelteranimalbot.service;

import static org.junit.jupiter.api.Assertions.*;

import com.javadreamteam.shelteranimalbot.keyboard.ReportStatus;
import com.javadreamteam.shelteranimalbot.model.ClientDog;
import com.javadreamteam.shelteranimalbot.model.ReportCat;
import com.javadreamteam.shelteranimalbot.model.ReportDog;
import com.javadreamteam.shelteranimalbot.repository.ClientDogRepository;
import com.javadreamteam.shelteranimalbot.repository.ReportCatRepository;
import com.javadreamteam.shelteranimalbot.repository.ReportDogRepository;
import org.apache.catalina.Service;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class ReportDogServiceTest {


        @Mock
        private ReportDogRepository reportDogRepository;
    @Mock
    private ClientDogRepository clientDogRepository;

        @InjectMocks
        private ReportDogService reportDogService;

        @Test
        public void testCreateReport() {
            // Создание объекта report
            ReportDog report = new ReportDog();
            report.setChatId(123456789L);
            report.setRation("Some ration");
            report.setHabits("Some habits");
            report.setInfo("Some info");
            report.setPhoto(new byte[]{1, 2, 3});
            report.setLastMessage(LocalDate.now());
            report.setReportStatus(ReportStatus.POSTED);

            // Задание поведения зависимости reportDogRepository
            when(reportDogRepository.save(report)).thenReturn(report);

            // Вызов метода createReport
            ReportDog result = reportDogService.createReport(report);

            // Проверка результата
            assertEquals(report, result);
        }
    @Test
    public void testFindReportById() {
        // Создание объекта report
        ReportDog report = new ReportDog();
        report.setId(1L);
        report.setChatId(123456789L);
        report.setRation("Some ration");
        report.setHabits("Some habits");
        report.setInfo("Some info");
        report.setPhoto(new byte[]{1, 2, 3});
        report.setLastMessage(LocalDate.now());
        report.setReportStatus(ReportStatus.POSTED);

        // Задание поведения зависимости reportDogRepository
        when(reportDogRepository.findById(1L)).thenReturn(Optional.of(report));

        // Вызов метода findReportById
        ReportDog result = reportDogService.findReportById(1L);

        // Проверка результата
        assertEquals(report, result);
    }
    @Test
    public void testUpdateReport() {
        // Создание объекта report
        ReportDog report = new ReportDog();
        report.setId(1L);
        report.setChatId(123456789L);
        report.setRation("Some ration");
        report.setHabits("Some habits");
        report.setInfo("Some info");
        report.setPhoto(new byte[]{1, 2, 3});
        report.setLastMessage(LocalDate.now());
        report.setReportStatus(ReportStatus.POSTED);

        // Задание поведения зависимости reportDogRepository
        when(reportDogRepository.findById(1L)).thenReturn(Optional.of(report));
        when(reportDogRepository.save(any(ReportDog.class))).thenReturn(report);

        // Вызов метода updateReport
        ReportDog updatedReport = new ReportDog();
        updatedReport.setId(1L);
        updatedReport.setChatId(123456789L);
        updatedReport.setRation("New ration");
        updatedReport.setHabits("New habits");
        updatedReport.setInfo("New info");
        updatedReport.setPhoto(new byte[]{4, 5, 6});
        updatedReport.setLastMessage(LocalDate.now());
        updatedReport.setReportStatus(ReportStatus.POSTED);

        ReportDog result = reportDogService.updateReport(1L, ReportStatus.POSTED);

        // Проверка результата
        assertEquals(updatedReport, result);
    }
    @Test
    public void testDeleteReport() {
        // Создание объекта report
        ReportDog report = new ReportDog();
        report.setId(1L);
        report.setChatId(123456789L);
        report.setRation("Some ration");
        report.setHabits("Some habits");
        report.setInfo("Some info");
        report.setPhoto(new byte[]{1, 2, 3});
        report.setLastMessage(LocalDate.now());
        report.setReportStatus(ReportStatus.POSTED);

        // Задание поведения зависимости reportDogRepository
        doNothing().when(reportDogRepository).deleteById(1L);
        when(reportDogRepository.findById(1L)).thenReturn(Optional.of(report));

        // Вызов метода deleteReport
        reportDogService.deleteReport(1L);

        // Проверка, что метод deleteById был вызван один раз с аргументом 1L
        verify(reportDogRepository, times(1)).deleteById(1L);
    }
    @Test
    public void testGetAllReports() {
        // Создание списка объектов report
        ReportDog report1 = new ReportDog();
        report1.setId(1L);
        report1.setChatId(123456789L);
        report1.setRation("Some ration");
        report1.setHabits("Some habits");
        report1.setInfo("Some info");
        report1.setPhoto(new byte[]{1, 2, 3});
        report1.setLastMessage(LocalDate.now());
        report1.setReportStatus(ReportStatus.POSTED);

        ReportDog report2 = new ReportDog();
        report2.setId(2L);
        report2.setChatId(987654321L);
        report2.setRation("Another ration");
        report2.setHabits("Another habits");
        report2.setInfo("Another info");
        report2.setPhoto(new byte[]{4, 5, 6});
        report2.setLastMessage(LocalDate.now());
        report2.setReportStatus(ReportStatus.POSTED);

        List<ReportDog> reports = new ArrayList<>();
        reports.add(report1);
        reports.add(report2);

        // Задание поведения зависимости reportDogRepository
        when(reportDogRepository.findAll()).thenReturn(reports);

        // Вызов метода getAllReports
        Collection<ReportDog> result = reportDogService.getAllReports();

        // Проверка, что метод findAll был вызван один раз
        verify(reportDogRepository, times(1)).findAll();

        // Проверка, что результат содержит два объекта ReportDog
        assertEquals(2, result.size());
    }
    @Test
    public void testDownloadReport() {
        // Создание объекта ReportDog
        ReportDog report = new ReportDog();
        report.setId(1L);
        report.setChatId(123456789L);
        report.setRation("Some ration");
        report.setHabits("Some habits");
        report.setInfo("Some info");
        report.setPhoto(new byte[]{1, 2, 3});
        report.setLastMessage(LocalDate.now());
        report.setReportStatus(ReportStatus.POSTED);

        // Задание поведения зависимости clientDogRepository
        when(clientDogRepository.findByChatId(123456789L)).thenReturn(new ClientDog());

        // Задание поведения зависимости reportDogRepository
        when(reportDogRepository.save(any(ReportDog.class))).thenReturn(report);

        // Вызов метода downloadReport
        ReportDog result = reportDogService.downloadReport(123456789L, "Some ration", "Some info", "Some habits",
                LocalDate.now(), new byte[]{1, 2, 3});

        // Проверка, что метод findByChatId был вызван один раз
        verify(clientDogRepository, times(1)).findByChatId(123456789L);

        // Проверка, что метод save был вызван один раз с переданным объектом ReportDog
        ArgumentCaptor<ReportDog> argumentCaptor = ArgumentCaptor.forClass(ReportDog.class);
        verify(reportDogRepository, times(1)).save(argumentCaptor.capture());
        ReportDog savedReport = argumentCaptor.getValue();
        assertEquals(123456789L, savedReport.getChatId());
        assertEquals("Some ration", savedReport.getRation());
        assertEquals("Some info", savedReport.getInfo());
        assertEquals("Some habits", savedReport.getHabits());
        assertEquals(LocalDate.now(), savedReport.getLastMessage());
        assertArrayEquals(new byte[]{1, 2, 3}, savedReport.getPhoto());

        // Проверка, что результат равен созданному объекту ReportDog
        assertEquals(report, result);
    }
    }
