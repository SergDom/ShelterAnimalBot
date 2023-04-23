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
    status        VARCHAR        NOT NULL,
    volunteer_id    INT
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

-- changeset sergeyd:1
CREATE TABLE volunteer
(
    id            SERIAL PRIMARY KEY,
    name          TEXT           NOT NULL,
    user_id       BIGINT                 ,
    chat_id       BIGINT                 ,
    username      VARCHAR        NOT NULL,
    phone         TEXT
);

-- changeset sergeyd:2

CREATE TABLE report
(
    id          integer PRIMARY KEY NOT NULL,
    chat_id     integer             NOT NULL,
    ration      varchar             NOT NULL,
    health      varchar             NOT NULL,
    habits      varchar             NOT NULL,
    info        VARCHAR             NOT NULL,
    days        integer             NOT NULL,
    client_dog_id   BIGINT REFERENCES client_dog(id),
    lastMessage date                NOT NULL,
    photo        bytea               NOT NULL,
    status       TEXT DEFAULT 'POSTED'
)