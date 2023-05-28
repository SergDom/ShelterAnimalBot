package com.javadreamteam.shelteranimalbot.service;

import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@WebMvcTest
public class KeyboardShelterTest {

    @Autowired
    private MockMvc mockMvc;


//    @Test
//    public void sendDocumentTest() {
//        // Создаем объект класса SendDocument
//        SendDocument sendDocument = new SendDocument();
//
//        // Получаем файл для отправки
//        String fileName = "my_document.pdf";
//        byte[] fileBytes = readFile(fileName);
//
//        // Отправляем документ
//        sendDocument.(fileBytes);
//
//        // Проверяем, что документ успешно отправлен
//        assertEquals("document sent successfully", sendDocument.getStatus());
//    }
//
//    private byte[] readFile(String fileName) throws IOException {
//        return new byte[0];
//    }

}
