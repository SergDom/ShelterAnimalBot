package com.javadreamteam.shelteranimalbot.listener;

import com.javadreamteam.shelteranimalbot.util.BotTestUtils;
import com.pengrad.telegrambot.TelegramBot;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.javadreamteam.shelteranimalbot.keyboard.KeyboardConstant.*;
import static com.javadreamteam.shelteranimalbot.keyboard.KeyboardShelter.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ShelterAnimalBotUpdatesListenerTest {

    private static final Long CHAT_ID = 1L;

    @Autowired
    private ShelterAnimalBotUpdatesListener listener;

    @MockBean
    private TelegramBot telegramBot;

    @Test
    public void startCommandTest() {
        var update = BotTestUtils.getUpdate(CHAT_ID, START);
        var sendMessage = BotTestUtils.getSendMessage(telegramBot, listener, update);
        assertNotNull(sendMessage);
        assertNotNull(sendMessage.getParameters().get("reply_markup"));
        assertEquals(CHAT_ID, (Long) sendMessage.getParameters().get("chat_id"));
        assertEquals("Выберите, кого хотите приютить:", sendMessage.getParameters().get("text"));
    }

    @Test
    public void aboutBotCommandTest() {
        var update = BotTestUtils.getUpdate(CHAT_ID, ABOUT_BOT);
        var sendMessage = BotTestUtils.getSendMessage(telegramBot, listener, update);
        assertNotNull(sendMessage);
        assertEquals(CHAT_ID, (Long) sendMessage.getParameters().get("chat_id"));
        assertEquals(INFO_ABOUT_BOT, sendMessage.getParameters().get("text"));
    }

    @Test
    public void carPassTest() {
        var update = BotTestUtils.getUpdate(CHAT_ID, CAR_PASS);
        var sendMessage = BotTestUtils.getSendMessage(telegramBot, listener, update);
        assertNotNull(sendMessage);
        assertEquals(CHAT_ID, (Long) sendMessage.getParameters().get("chat_id"));
        assertEquals(PASS, sendMessage.getParameters().get("text"));
    }

    @Test
    public void reportFromCommandTest() {
        var update = BotTestUtils.getUpdate(CHAT_ID, REPORT_FORM);
        var sendMessage = BotTestUtils.getSendMessage(telegramBot, listener, update);
        assertNotNull(sendMessage);
        assertEquals(CHAT_ID, (Long) sendMessage.getParameters().get("chat_id"));
        assertEquals(REPORT_LIST, sendMessage.getParameters().get("text"));
    }

    @Test
    public void RequestVolunteerCommandTest() {
        var update = BotTestUtils.getUpdate(CHAT_ID, REQUEST_VOLUNTEER);
        var sendMessage = BotTestUtils.getSendMessage(telegramBot, listener, update);
        assertNotNull(sendMessage);
        assertEquals(CHAT_ID, (Long) sendMessage.getParameters().get("chat_id"));
        assertEquals(CALL_VOLUNTEERS, sendMessage.getParameters().get("text"));
    }

    @Test
    public void emptyCommandTest() {
        var update = BotTestUtils.getUpdate(CHAT_ID, "");
        var sendMessage = BotTestUtils.getSendMessage(telegramBot, listener, update);
        assertNotNull(sendMessage);
        assertEquals(CHAT_ID, (Long) sendMessage.getParameters().get("chat_id"));
        assertEquals("Пустое сообщение", sendMessage.getParameters().get("text"));
    }

    @Test
    public void unknownCommandTest() {
        var update = BotTestUtils.getUpdate(CHAT_ID, "123");
        var sendMessage = BotTestUtils.getSendMessage(telegramBot, listener, update);
        assertNotNull(sendMessage);
        assertEquals(CHAT_ID, (Long) sendMessage.getParameters().get("chat_id"));
        assertEquals("Я не знаю такой команды", sendMessage.getParameters().get("text"));
    }
}