package com.javadreamteam.shelteranimalbot.model;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**
     * Идентификатор записи, primary key
     */
    private Long id;

    /**
     * Дата отправки отчета
     */
    private LocalDateTime dateReport;
    /**
     * Идентификатор чата хозяина животного, отправившего отчет
     */
    private Long chatId;
    /**
     * Текст отчета
     */
    private String textReport;
    /**
     * Путь к фото
     */
    private String filePath;
    /**
     * Размер фото
     */
    private long fileSize;
    /**
     * Тип данных у фото
     */
    private String mediaType;
    /**
     * Сам файл фото - массив байтов
     */
    private byte[] photo;
    /**
     * id питомца
     */
    @ManyToOne
    @JoinColumn(name = "dog_id")
    private Dog dog;

    public Report() {
    }

    public Report(Long id, LocalDateTime dateReport, Long chatId, String textReport, String filePath, long fileSize, String mediaType, byte[] photo, Dog dog) {
        this.id = id;
        this.dateReport = dateReport;
        this.chatId = chatId;
        this.textReport = textReport;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.mediaType = mediaType;
        this.photo = photo;
        this.dog = dog;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateReport() {
        return dateReport;
    }

    public void setDateReport(LocalDateTime dateReport) {
        this.dateReport = dateReport;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getTextReport() {
        return textReport;
    }

    public void setTextReport(String textReport) {
        this.textReport = textReport;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

}
