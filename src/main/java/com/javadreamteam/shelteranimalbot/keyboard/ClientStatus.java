package com.javadreamteam.shelteranimalbot.keyboard;
/**
 * Enum ClientStatus
 * Используется для констант статуса владельца
 */

public enum ClientStatus {

    IN_SEARCH("В поиске питомца"),
    PROBATION("На испытательном сроке"),
    APPROVED("Одобрен"),
    IN_BLACK_LIST("Не прошел испытательный срок");



        private final String description;


    ClientStatus(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }


