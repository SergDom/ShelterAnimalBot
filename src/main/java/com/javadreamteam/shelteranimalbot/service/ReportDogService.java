package com.javadreamteam.shelteranimalbot.service;

import com.javadreamteam.shelteranimalbot.exceptions.ReportException;
import com.javadreamteam.shelteranimalbot.keyboard.ReportStatus;
import com.javadreamteam.shelteranimalbot.model.ReportDog;
import com.javadreamteam.shelteranimalbot.repository.ReportDogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



import java.time.LocalDate;
import java.util.Collection;


/**
 * Класс сервиса для работы с {@link ReportDogRepository} и сущностью {@link ReportDog}
 */
@Service
public class ReportDogService {
    private final ReportDogRepository reportDogRepository;
    private final Logger logger = LoggerFactory.getLogger(ReportDogService.class);

    public ReportDogService(ReportDogRepository reportDogRepository) {
        this.reportDogRepository = reportDogRepository;
    }
    /**
     * Создание отчета и сохранение его в БД
     * Используется метод репозитория {@link ReportDogRepository#save(Object)}
     * @param report создается объект отчет
     * @return созданный отчет
     */

  public ReportDog createReport (ReportDog report){
      logger.info("Method createReport has been run {}", report);
      return reportDogRepository.save(report);
  }

    /**
     * Метод скачивает отчет из БД {@link ReportDog}
     * с помощью метода {@link ReportDogRepository#save(Object)}.
     *
     */

    public ReportDog downloadReport(Long chatId, String ration, String health, String habits, LocalDate lastMessage,
                                    byte [] photo) {
        logger.info("Method downloadReport has been run");

        ReportDog reportDog = new ReportDog();
        reportDog.setChatId(chatId);
        reportDog.setRation(ration);
        reportDog.setHealth(health);
        reportDog.setHabits(habits);
        reportDog.setLastMessage(lastMessage);
        reportDog.setPhoto(photo);
        reportDog.setReportStatus(ReportStatus.POSTED);

        return reportDogRepository.save(reportDog);
    }

    /**
     * Метод ищет данные об отчете по его id.
     * Используется метод {@link ReportDogRepository#findById(Object)}
     *
     * @param id идентификатор отчета
     * @throws  ReportException, если отчет не найден с указанным id в БД
     * @return данные об отчете
     */
    public ReportDog findReportById(long id) {
        logger.info("Was invoked method - findReportById");

        return reportDogRepository.findById(id).orElseThrow(ReportException::new);
    }
    /**
     * Изменение данных отчета в БД
     * Используется метод репозитория {@link ReportDogRepository#save(Object)}
     * @param reportDog отчет
     * @throws ReportException, если отчет не найден в БД
     * @return измененный отчет
     */
    public ReportDog updateReport (ReportDog reportDog){
        logger.info("Was invoked method - updateReport");
        if(reportDog.getId() != null) {
            if(findReportById(reportDog.getId()) !=null){
                reportDogRepository.save(reportDog);
            }
        }
        else {
            logger.error("Request report is not found");
            throw new ReportException();}
        return reportDog;
        }

    /**
     * Метод удаляет сущность {@link ReportDog} по-указанному id.
     * Используется метод {@link ReportDogRepository#deleteById(Object)}
     *
     * @param id идентификатор удаляемого отчета
     */
    public void deleteReport(Long id) {
        logger.info("Was invoked method - deleteReport, by id {}", id);

        reportDogRepository.deleteById(id);
    }
    /**
     * Метод возвращает список всех отчетов в БД.
     *
     * @return список всех отчетов
     */
    public Collection <ReportDog> getAllReports () {
        logger.info("Was invoked method - findAllReports");

        return reportDogRepository.findAll();
    }

}
