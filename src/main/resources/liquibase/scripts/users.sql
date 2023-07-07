-- liquibase formatted sql

-- changeset renasafetysea:1


CREATE TABLE if not exists images
(
    id         INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    image       bytea
);


CREATE TABLE users
(
    id         INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    first_name VARCHAR NOT NULL,
    last_name  VARCHAR NOT NULL,
    phone      VARCHAR NOT NULL,
    password   VARCHAR NOT NULL,
    userName   VARCHAR NOT NULL,
    email      VARCHAR NOT NULL,
    role       VARCHAR(255),
    image_id   VARCHAR(255)
);


CREATE TABLE if not exists ads
(
    id           INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    description VARCHAR(255),
    price       INTEGER,
    title       VARCHAR(255),
    user_id     INTEGER,
    image_id    VARCHAR(255)
);

CREATE TABLE if not exists comments
(
    id         INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    created_at timestamp,
    text       VARCHAR(255),
    user_id    INTEGER,
    ads_id     INTEGER
);







