create schema production;
INSERT INTO users (id, name, email, password)
VALUES (1,'Оля', 'olya@gmail.com', '{noop}password'),
       (2,'Вера', 'vera@gmail.com', 'admin'),
       (3,'Катя', 'katya@gmail.com', 'guest'),
       (4,'Неизвестно', 'unknown@gmail.com', 'admin'),
       (5,'user', 'user@gmail.com', '{noop}password');

INSERT INTO role(ROLE, USER_ID)
VALUES ('USER', 1),
       ('ADMIN', 1),
       ('USER', 2),
       ('USER', 3),
       ('USER', 4);

-- INSERT INTO laser(id, name, capacity, max_size)
-- VALUES (1, '1л', 0, 160),
--        (2,'2л', 0, 160),
--        (3,'3л', 0, 200),
--        (4,'4л', 0, 200),
--        (5,'5л', 0, 800),
--        (6,'6л', 0, 800);
