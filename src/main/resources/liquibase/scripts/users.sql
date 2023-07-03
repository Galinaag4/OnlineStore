-- liquibase formatted sql

-- changeset renasafetysea:1
CREATE TABLE if not exists images
(
    id         INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    image       oid    NOT NULL
);


CREATE TABLE if not exists users
(
    id         INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    first_name VARCHAR NOT NULL,
    last_name  VARCHAR NOT NULL,
    phone      VARCHAR NOT NULL,
    password   VARCHAR NOT NULL,
    userName   VARCHAR NOT NULL,
    role       VARCHAR DEFAULT 'USER',
    image_id   INTEGER REFERENCES images (id)
);


CREATE TABLE if not exists ads
(
    id          INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    description TEXT,
    price       INT,
    title       TEXT,
    user_id     INTEGER REFERENCES users (id),
    ads_id    INTEGER REFERENCES images (id)
);

CREATE TABLE if not exists comments
(
    id         INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    created_at BIGINT  NOT NULL,
    text       VARCHAR NOT NULL,
    user_id    INTEGER REFERENCES users (id),
    ads_id      INTEGER REFERENCES ads (id)
);







