package com.javadreamteam.shelteranimalbot.service;

import com.javadreamteam.shelteranimalbot.exceptions.ReportException;
import com.javadreamteam.shelteranimalbot.keyboard.ReportStatus;
import com.javadreamteam.shelteranimalbot.model.ReportCat;
import com.javadreamteam.shelteranimalbot.model.ReportDog;
import com.javadreamteam.shelteranimalbot.repository.ClientCatRepository;
import com.javadreamteam.shelteranimalbot.repository.ReportCatRepository;
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
public class ReportCatService {
    private final ReportCatRepository reportCatRepository;
    private final ClientCatRepository clientCatRepository;
    private final Logger logger = LoggerFactory.getLogger(ReportCatService.class);

    public ReportCatService(ReportCatRepository reportCatRepository, ClientCatRepository clientCatRepository) {
        this.reportCatRepository = reportCatRepository;
        this.clientCatRepository = clientCatRepository;
    }


    /**
     * Создание отчета и сохранение его в БД
     * Используется метод репозитория {@link ReportCatRepository#save(Object)}
     * @param report создается объект отчет
     * @return созданный отчет
     */

  public ReportCat createReport (ReportCat report){
      logger.info("Method createReport has been run {}", report);
      return reportCatRepository.save(report);
  }

    /**
     * Метод скачивает отчет из БД {@link ReportCat}
     * с помощью метода {@link ReportCatRepository#save(Object)}.
     *
     */

    public ReportCat downloadReport(Long chatId, String ration, String info, String habits, LocalDate lastMessage,
                                    byte [] photo) {
        logger.info("Method downloadReport has been run");

        ReportCat reportCat = new ReportCat();
        reportCat.setChatId(chatId);
        reportCat.setRation(ration);
        reportCat.setInfo(info);
        reportCat.setHabits(habits);
        reportCat.setLastMessage(lastMessage);
        reportCat.setPhoto(photo);
        reportCat.setReportStatus(ReportStatus.POSTED);
        reportCat.setClientCat(clientCatRepository.findByChatId(chatId));

        return reportCatRepository.save(reportCat);
    }

    /**
     * Метод ищет данные об отчете по его id.
     * Используется метод {@link ReportDogRepository#findById(Object)}
     *
     * @param id идентификатор отчета
     * @throws  ReportException, если отчет не найден с указанным id в БД
     * @return данные об отчете
     */
    public ReportCat findReportById(long id) {
        logger.info("Was invoked method - findReportById");

        return reportCatRepository.findById(id).orElseThrow(ReportException::new);
    }
    /**
     * Изменение данных отчета в БД
     * Используется метод репозитория {@link ReportCatRepository#save(Object)}
     * @param id id отчета
     * @param status статус отчета
     * @throws ReportException, если отчет не найден в БД
     * @return измененный отчет
     */
    public ReportCat updateReport (Long id, ReportStatus status){
        logger.info("Was invoked method - updateReport {}", id);
        if(id != null) {
            ReportCat reportCat = findReportById(id);
            if(reportCat !=null){
                reportCat.setReportStatus(status);
               return reportCatRepository.save(reportCat);
            }
        }
            logger.error("Request report is not found");
            throw new ReportException();

        }

    /**
     * Метод удаляет сущность {@link ReportCat} по-указанному id.
     * Используется метод {@link ReportCatRepository#deleteById(Object)}
     *
     * @param id идентификатор удаляемого отчета
     */
    public void deleteReport(Long id) {
        logger.info("Was invoked method - deleteReport, by id {}", id);

        reportCatRepository.deleteById(id);
    }
    /**
     * Метод возвращает список всех отчетов в БД.
     *
     * @return список всех отчетов
     */
    public Collection <ReportCat> getAllReports () {
        logger.info("Was invoked method - findAllReports");

        return reportCatRepository.findAll();
    }

}
