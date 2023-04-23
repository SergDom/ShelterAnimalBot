package com.javadreamteam.shelteranimalbot.keyboard;

public enum ReportStatus {
    POSTED ("Отправлен"),
    APPROVED ("Одобрен"),
    REFUSED ("Отклонен");

    /**
     * Поле "Описание"
     */
    private final String description;

    /**
     * Конструктор создания статуса отчета
     * @param description описание статуса
     */
    ReportStatus(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
