-- liquibase formatted sql

-- changeset andrew:1
CREATE TABLE client
(
    id           serial PRIMARY KEY,
    name         text,
    middle_name  text,
    last_name    text,
    user_name    text,
    phone_number text,
    email        text
);

-- changeset andrew:2
CREATE TABLE pet
(
    id           serial PRIMARY KEY,
    name         text,
    age          int,
    info         text
);

