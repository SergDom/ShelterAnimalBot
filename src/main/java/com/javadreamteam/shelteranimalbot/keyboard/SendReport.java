package com.javadreamteam.shelteranimalbot.keyboard;

import com.javadreamteam.shelteranimalbot.listener.ShelterAnimalBotUpdatesListener;
import com.javadreamteam.shelteranimalbot.model.ClientCat;
import com.javadreamteam.shelteranimalbot.model.ClientDog;
import com.javadreamteam.shelteranimalbot.model.Report;
import com.javadreamteam.shelteranimalbot.model.Volunteer;
import com.javadreamteam.shelteranimalbot.repository.ClientCatRepository;
import com.javadreamteam.shelteranimalbot.repository.ClientDogRepository;
import com.javadreamteam.shelteranimalbot.repository.ReportRepository;
import com.javadreamteam.shelteranimalbot.service.ReportService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.ForwardMessage;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class SendReport {
    private final Logger logger = LoggerFactory.getLogger(ShelterAnimalBotUpdatesListener.class);

    private final TelegramBot telegramBot;
    private final ReportService reportService;
    private Volunteer volunteer;
    private final ReportRepository reportRepository;

    private static final long telegramChatVolunteer = 426343815L; //SergeyD

    private static final Pattern REPORT_PATTERN = Pattern.compile(
            "([А-яA-z\\s\\d\\D]+):(\\s)([А-яA-z\\s\\d\\D]+)\n" +
                    "([А-яA-z\\s\\d\\D]+):(\\s)([А-яA-z\\s\\d\\D]+)\n" +
                    "([А-яA-z\\s\\d\\D]+):(\\s)([А-яA-z\\s\\d\\D]+)");
    private final ClientDogRepository clientDogRepository;
    private final ClientCatRepository clientCatRepository;


    public SendReport(TelegramBot telegramBot, ReportService reportService, ReportRepository reportRepository,
                      ClientDogRepository clientDogRepository, ClientCatRepository clientCatRepository) {
        this.telegramBot = telegramBot;
        this.reportService = reportService;
        this.reportRepository = reportRepository;

        this.clientDogRepository = clientDogRepository;
        this.clientCatRepository = clientCatRepository;
    }

    /**
     * Метод сохранения отчета из чата телеграм
     *
     * @param update доступное обновление
     */
    public void storeReport(Update update) {
        logger.info("Launched method: report_form, for user with id: " +
                update.message().chat().id());

        String message = update.message().caption();
        Matcher matcher = REPORT_PATTERN.matcher(message);
//         update.message().text().equals(REPORT_STORE);
            SendMessage text = new SendMessage(update.message().chat().id(), "Введите данные по отчету");
            telegramBot.execute(text);
            logger.info("Received data " + text);

          if (
             matcher.matches()) {
                String ration = matcher.group(3);
                String info = matcher.group(6);
                String habits = matcher.group(9);

                GetFile getFileRequest = new GetFile(update.message().photo()[1].fileId());
                GetFileResponse getFileResponse = telegramBot.execute(getFileRequest);
                try {
                    File file = getFileResponse.file();
                    file.fileSize();

                    byte[] photo = telegramBot.getFileContent(file);
                    LocalDate date = LocalDate.now();
                    reportService.downloadReport(update.message().chat().id(), ration, info,
                            habits, LocalDate.from(date.atStartOfDay()), photo);
                    telegramBot.execute(new SendMessage(update.message().chat().id(), "Отчет успешно принят!"));
                } catch (IOException e) {
                    logger.error("Ошибка загрузки фото");
                    telegramBot.execute(new SendMessage(update.message().chat().id(),
                            "Ошибка загрузки фото"));
                }

            } else {
                telegramBot.execute(new SendMessage(update.message().chat().id(),
                        "Введены не все данные, проверьте все поля в отчете!"));
            }
        }

    public void sendForwardMessage(Long chatId, Integer messageId) {
        ForwardMessage forwardMessage = new ForwardMessage(telegramChatVolunteer, chatId, messageId);
        telegramBot.execute(forwardMessage);
    }


    @Scheduled(cron = "0 0/3 * * * *")
    public void sendNotificationDog() {
        logger.info("Requests report to OwnerDog");
        for (Report report : reportService.getAllReports()) {
            Long ownerDogId = report.getClientDog().getId();
            long daysBetween = DAYS.between(LocalDate.now(), report.getLastMessage());
            if (report.getLastMessage().isBefore(LocalDate.now().minusDays(1))) {
                SendMessage sendMessage = new SendMessage(volunteer.getChatId(), "Отчет о собаке "
                        + report.getClientDog().getDog().getName() + " (id: " + report.getClientDog().getDog().getId() + ") от владельца "
                        + report.getClientDog().getName() + " (id: " + ownerDogId + ") не поступал уже " + daysBetween + " дней. "
                        + "Дата последнего отчета: " + report.getLastMessage());
                telegramBot.execute(sendMessage);
            }
            if (report.getLastMessage().equals(LocalDate.now().minusDays(1))) {
                SendMessage sendToOwner = new SendMessage(report.getClientDog().getChatId(), "Пожалуйста, " +
                        "не забудьте отправить отчет сегодня");
                telegramBot.execute(sendToOwner);
            }
        }
        for (Report report : reportService.getAllReports()) {
            Long clientDogId = report.getClientDog().getId();
            if (report.getLastMessage().equals(LocalDate.now().minusDays(30))) {
                reportRepository.save(report);
                SendMessage sendMessage = new SendMessage(clientDogId, report.getClientDog().getName() + "! поздравляем," +
                        "испытательный срок в 30 дней для собаки " + report.getClientDog().getDog().getName() +
                        " (id: " + report.getClientDog().getDog().getId() + ") закончен");
                telegramBot.execute(sendMessage);
            }
        }
    }
}
