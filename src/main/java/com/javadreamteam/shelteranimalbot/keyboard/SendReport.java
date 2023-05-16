package com.javadreamteam.shelteranimalbot.keyboard;

import com.javadreamteam.shelteranimalbot.listener.ShelterAnimalBotUpdatesListener;
import com.javadreamteam.shelteranimalbot.model.ReportCat;
import com.javadreamteam.shelteranimalbot.model.ReportDog;
import com.javadreamteam.shelteranimalbot.model.Volunteer;
import com.javadreamteam.shelteranimalbot.repository.ClientCatRepository;
import com.javadreamteam.shelteranimalbot.repository.ClientDogRepository;
import com.javadreamteam.shelteranimalbot.repository.ReportCatRepository;
import com.javadreamteam.shelteranimalbot.repository.ReportDogRepository;
import com.javadreamteam.shelteranimalbot.service.ReportCatService;
import com.javadreamteam.shelteranimalbot.service.ReportDogService;
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
    private final ReportDogService reportDogService;
    private final ReportCatService reportCatService;
    private Volunteer volunteer;
    private final ReportDogRepository reportDogRepository;

    private final ReportCatRepository reportCatRepository;

    private static final long telegramChatVolunteer = 426343815L; //SergeyD

    private static final Pattern REPORT_PATTERN = Pattern.compile(
            "([А-яA-z\\s\\d\\D]+):(\\s)([А-яA-z\\s\\d\\D]+)\n" +
                    "([А-яA-z\\s\\d\\D]+):(\\s)([А-яA-z\\s\\d\\D]+)\n" +
                    "([А-яA-z\\s\\d\\D]+):(\\s)([А-яA-z\\s\\d\\D]+)");
    private final ClientDogRepository clientDogRepository;
    private final ClientCatRepository clientCatRepository;



    public SendReport(TelegramBot telegramBot, ReportDogService reportDogService, ReportCatService reportCatService, ReportDogRepository reportDogRepository,
                      ReportCatRepository reportCatRepository, ClientDogRepository clientDogRepository, ClientCatRepository clientCatRepository) {
        this.telegramBot = telegramBot;
        this.reportDogService = reportDogService;
        this.reportCatService = reportCatService;
        this.reportDogRepository = reportDogRepository;
        this.reportCatRepository = reportCatRepository;

        this.clientDogRepository = clientDogRepository;
        this.clientCatRepository = clientCatRepository;
    }

    /**
     * Метод сохранения отчета из чата телеграм
     *
     * @param update доступное обновление
     */
    public void downloadReport(Update update) {
        logger.info("Launched for user with id: " +
                update.message().chat().id());

        String message = update.message().caption();
        Matcher matcher = REPORT_PATTERN.matcher(message);

            logger.info("Received data " + message);

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
                    if (clientDogRepository.findByChatId(update.message().chat().id()) != null
                    & clientDogRepository.findByChatId(update.message().chat().id()).getStatus() != ClientStatus.IN_SEARCH) {
                        reportDogService.downloadReport(update.message().chat().id(), ration, info,
                                habits, LocalDate.from(date.atStartOfDay()), photo);
                    telegramBot.execute(new SendMessage(update.message().chat().id(), "Отчет успешно принят!"));
                }else if (clientCatRepository.findByChatId(update.message().chat().id()) != null
                    & clientCatRepository.findByChatId(update.message().chat().id()).getStatus() != ClientStatus.IN_SEARCH){
                  reportCatService.downloadReport(update.message().chat().id(), ration, info,
                          habits, LocalDate.from(date.atStartOfDay()), photo);
                  telegramBot.execute(new SendMessage(update.message().chat().id(), "Отчет успешно принят!"));

              }
                    else {telegramBot.execute(new SendMessage(update.message().chat().id(),"У вас отсутвует питомец"));}
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



        @Scheduled(cron = "0 0 14 ? * *")
    public void sendNotification() {
        logger.info("Requests report to OwnerDog");
        for (ReportDog reportDog : reportDogService.getAllReports()) {
            Long ownerDogId = reportDog.getClientDog().getId();
            long daysBetween = DAYS.between(LocalDate.now(), reportDog.getLastMessage());
            if (reportDog.getLastMessage().isBefore(LocalDate.now().minusDays(1))) {
                SendMessage sendMessage = new SendMessage(volunteer.getChatId(), "Отчет о собаке "
                        + reportDog.getClientDog().getDog().getName() + " (id: " + reportDog.getClientDog().getDog().getId() + ") от владельца "
                        + reportDog.getClientDog().getName() + " (id: " + ownerDogId + ") не поступал уже " + daysBetween + " дней. "
                        + "Дата последнего отчета: " + reportDog.getLastMessage());
                telegramBot.execute(sendMessage);
            }
            if (reportDog.getLastMessage().equals(LocalDate.now().minusDays(1))) {
                SendMessage sendToOwner = new SendMessage(reportDog.getClientDog().getChatId(), "Пожалуйста, " +
                        "не забудьте отправить отчет сегодня");
                telegramBot.execute(sendToOwner);
            }
        }
        for (ReportDog reportDog : reportDogService.getAllReports()) {
            Long clientDogId = reportDog.getClientDog().getId();
            if (reportDog.getLastMessage().equals(LocalDate.now().minusDays(30))) {
                reportDogRepository.save(reportDog);
                reportDog.getClientDog().setStatus(ClientStatus.APPROVED);
                SendMessage sendMessage = new SendMessage(clientDogId, reportDog.getClientDog().getName() + "! поздравляем," +
                        "испытательный срок в 30 дней для собаки " + reportDog.getClientDog().getDog().getName() +
                        " (id: " + reportDog.getClientDog().getDog().getId() + ") закончен");
                telegramBot.execute(sendMessage);
            }
        }
            logger.info("Requests report to OwnerCat");
            for (ReportCat reportCat : reportCatService.getAllReports()) {
                Long ownerCatId = reportCat.getClientCat().getId();
                long daysBetween = DAYS.between(LocalDate.now(), reportCat.getLastMessage());
                if (reportCat.getLastMessage().isBefore(LocalDate.now().minusDays(1))) {
                    SendMessage sendMessage = new SendMessage(volunteer.getChatId(), "Отчет о собаке "
                            + reportCat.getClientCat().getCat().getName() + " (id: " + reportCat.getClientCat().getCat().getId() + ") от владельца "
                            + reportCat.getClientCat().getName() + " (id: " + ownerCatId + ") не поступал уже " + daysBetween + " дней. "
                            + "Дата последнего отчета: " + reportCat.getLastMessage());
                    telegramBot.execute(sendMessage);
                }
                if (reportCat.getLastMessage().equals(LocalDate.now().minusDays(1))) {
                    SendMessage sendToOwner = new SendMessage(reportCat.getClientCat().getChatId(), "Пожалуйста, " +
                            "не забудьте отправить отчет сегодня");
                    telegramBot.execute(sendToOwner);
                }
            }
            for (ReportCat reportCat : reportCatService.getAllReports()) {
                Long clientCatId = reportCat.getClientCat().getId();
                if (reportCat.getLastMessage().equals(LocalDate.now().minusDays(30))) {
                    reportCatRepository.save(reportCat);
                    reportCat.getClientCat().setStatus(ClientStatus.APPROVED);
                    SendMessage sendMessage = new SendMessage(clientCatId, reportCat.getClientCat().getName() + "! поздравляем," +
                            "испытательный срок в 30 дней для собаки " + reportCat.getClientCat().getCat().getName() +
                            " (id: " + reportCat.getClientCat().getCat().getId() + ") закончен");
                    telegramBot.execute(sendMessage);
                }
            }

    }
}
