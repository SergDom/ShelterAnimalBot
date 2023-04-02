-- liquibase formatted sql

-- changeset andrew:1
CREATE TABLE client_dog
(
    id            INTEGER PRIMARY KEY,
    name          TEXT           NOT NULL,
    chat_id       INTEGER        NOT NULL,
    phone_number  TEXT           NOT NULL,
    email         TEXT           NOT NULL,
    year_of_birth INTEGER        NOT NULL,
    dog_id        INTEGER UNIQUE NOT NULL,
    status        VARCHAR        NOT NULL
);

-- changeset andrew:2
CREATE TABLE dog
(
    id    INTEGER PRIMARY KEY,
    name  TEXT    NOT NULL,
    age   INTEGER NOT NULL,
    breed TEXT    NOT NULL,
    info  TEXT    NOT NULL,
    FOREIGN KEY (id) REFERENCES client_dog (dog_id)
);

