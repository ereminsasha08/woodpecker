
CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name             VARCHAR                           NOT NULL,
    email            VARCHAR                           NOT NULL,
    password         VARCHAR                           NOT NULL,
    registered       TIMESTAMP           DEFAULT now() NOT NULL,
    enabled          BOOL                DEFAULT TRUE  NOT NULL
);

CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
                 (
                     user_id INTEGER NOT NULL,
                     role    VARCHAR NOT NULL,
                     CONSTRAINT user_roles_idx UNIQUE (user_id, role),
                     FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
                 );

CREATE TABLE maps
                 (
                     id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
                     user_id       INTEGER   NOT NULL,
                     size          INTEGER   NOT NULL,
                     price          INTEGER   NOT NULL,
                     language_map  TEXT      NOT NULL,
                     type_map      TEXT      NOT NULL,
                     is_state      boolean   NOT NULL,
                     light     TEXT      NOT NULL,
                     is_multi_level boolean   NOT NULL,
                     color         TEXT      NOT NULL,
                     additional         TEXT      NOT NULL,
                     manager         TEXT      NOT NULL,
                     contact   TEXT      NOT NULL,
                     date_time     TIMESTAMP NOT NULL,
                     description   TEXT      NOT NULL,

                     FOREIGN KEY (user_id) REFERENCES users (id)
                 );

INSERT INTO users (id, name, email, password)
VALUES (1, 'Оля', 'olya@yandex.ru', 'password'),
       (2, 'Вера', 'vare@gmail.com', 'admin'),
       (3, 'Катя', 'katya@gmail.com', 'guest'),
       (4, 'Неизвестно', 'unknown@gmail.com', 'admin');

INSERT INTO user_roles (ROLE, USER_ID)
VALUES ('USER', 2),
       ('ADMIN', 1),
       ('USER', 3),
       ('USER', 4);
