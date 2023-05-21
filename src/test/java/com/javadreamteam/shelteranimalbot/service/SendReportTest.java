package com.javadreamteam.shelteranimalbot.service;

import com.javadreamteam.shelteranimalbot.keyboard.KeyboardConstant;
import com.javadreamteam.shelteranimalbot.keyboard.KeyboardShelter;
import com.javadreamteam.shelteranimalbot.keyboard.SendReport;
import com.javadreamteam.shelteranimalbot.repository.ReportCatRepository;
import com.javadreamteam.shelteranimalbot.repository.ReportDogRepository;
import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SendReportTest {

    @Mock
    private TelegramBot telegramBot;
    @Mock
    private ReportDogRepository reportDogRepository;
    @Mock
    private ReportCatRepository catRepository;

    @Mock
    private ReportDogService reportDogService;
    @Mock
    private ReportCatService reportCatService;
    @InjectMocks
    SendReport sendReport;
    private static final String TEXT = """
            ЗАГРУЗИТЕ ОТЧЕТ В ФОРМАТЕ:\s
            \s
            Рацион: данные о рационе\s
            Информация: общая информация\s
            Привычки: данные о изменении привычек\s
            И прикрепите фото к отчету.""";

    private final GetFileResponse getFileResponse = BotUtils.fromJson("""
            {
            "ok": true,
            "file": {
                "file_id": "qwerty"
                }
            }
            """, GetFileResponse.class);
    private final byte[] photo = Files.readAllBytes(
            Paths.get(Objects.requireNonNull(UpdatesListener.class.getResource("/static/Cat.jpeg")).toURI())
    );

    public SendReportTest() throws IOException, URISyntaxException {
    }

    @Test
    public void reportFormTest() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        SendMessage info = new SendMessage(update.message().chat().id(), KeyboardConstant.REPORT_LIST);

        when(chat.id()).thenReturn(123L);
        when(message.chat()).thenReturn(chat);
        when(message.text()).thenReturn(KeyboardShelter.REPORT_STORE);
        when(update.message()).thenReturn(message);

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        SendMessage sendMessage = argumentCaptor.getValue();
        Assertions.assertEquals(sendMessage.getParameters().get("chat_id"), 123L);
        Assertions.assertEquals(sendMessage.getParameters().get("text"), info);
    }

    @Test
    public void reportTest() throws IOException {
        when(telegramBot.execute(any(GetFile.class))).thenReturn(getFileResponse);
        when(telegramBot.getFileContent(any())).thenReturn(photo);
        ArgumentCaptor<GetFile> argumentCaptor = ArgumentCaptor.forClass(GetFile.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        GetFile actual = argumentCaptor.getValue();
        Assertions.assertEquals(1L, actual.getParameters().get("chat_id"));
        Assertions.assertEquals("Отчет успешно принят!", actual.getParameters().get("text"));
    }
}
