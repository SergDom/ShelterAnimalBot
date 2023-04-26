package com.javadreamteam.shelteranimalbot.service;

import com.javadreamteam.shelteranimalbot.exceptions.ReportException;
import com.javadreamteam.shelteranimalbot.keyboard.ReportStatus;
import com.javadreamteam.shelteranimalbot.model.Report;
import com.javadreamteam.shelteranimalbot.repository.ReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



import java.time.LocalDate;
import java.util.Collection;


/**
 * Класс сервиса для работы с {@link ReportRepository} и сущностью {@link Report}
 */
@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final Logger logger = LoggerFactory.getLogger(ReportService.class);

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }
    /**
     * Создание отчета и сохранение его в БД
     * Используется метод репозитория {@link ReportRepository#save(Object)}
     * @param report создается объект отчет
     * @return созданный отчет
     */

  public Report createReport (Report report){
      logger.info("Method createReport has been run {}", report);
      return reportRepository.save(report);
  }

    /**
     * Метод скачивает отчет из БД {@link Report}
     * с помощью метода {@link ReportRepository#save(Object)}.
     *
     */

    public Report downloadReport(Long chatId, String ration, String health, String habits, LocalDate lastMessage,
    byte [] photo) {
        logger.info("Method downloadReport has been run");

        Report report = new Report();
        report.setChatId(chatId);
        report.setRation(ration);
        report.setHealth(health);
        report.setHabits(habits);
        report.setLastMessage(lastMessage);
        report.setPhoto(photo);
        report.setReportStatus(ReportStatus.POSTED);

        return reportRepository.save(report);
    }

    /**
     * Метод ищет данные об отчете по его id.
     * Используется метод {@link ReportRepository#findById(Object)}
     *
     * @param id идентификатор отчета
     * @throws  ReportException, если отчет не найден с указанным id в БД
     * @return данные об отчете
     */
    public Report findReportById(long id) {
        logger.info("Was invoked method - findReportById");

        return reportRepository.findById(id).orElseThrow(ReportException::new);
    }
    /**
     * Изменение данных отчета в БД
     * Используется метод репозитория {@link ReportRepository#save(Object)}
     * @param report отчет
     * @throws ReportException, если отчет не найден в БД
     * @return измененный отчет
     */
    public Report updateReport (Report report){
        logger.info("Was invoked method - updateReport");
        if(report.getId() != null) {
            if(findReportById(report.getId()) !=null){
                reportRepository.save(report);
            }
        }
        else {
            logger.error("Request report is not found");
            throw new ReportException();}
        return report;
        }

    /**
     * Метод удаляет сущность {@link Report} по-указанному id.
     * Используется метод {@link ReportRepository#deleteById(Object)}
     *
     * @param id идентификатор удаляемого отчета
     */
    public void deleteReport(Long id) {
        logger.info("Was invoked method - deleteReport, by id {}", id);

        reportRepository.deleteById(id);
    }
    /**
     * Метод возвращает список всех отчетов в БД.
     *
     * @return список всех отчетов
     */
    public Collection <Report> getAllReports () {
        logger.info("Was invoked method - findAllReports");

        return reportRepository.findAll();
    }

}
