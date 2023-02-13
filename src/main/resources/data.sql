insert into plywood_list (id)
values (141),
       (251);

insert into lists(plywood_list_id, list)
values (141, 1),
       (141, 2),
       (251, 1),
       (251, 2);

INSERT INTO users (name, email, password)
VALUES ('Оля', 'olya@gmail.com', '{noop}password'),
       ('Вера', 'vera@gmail.com', 'admin'),
       ('Катя', 'katya@gmail.com', 'guest'),
       ('Неизвестно', 'unknown@gmail.com', 'admin'),
       ('user', 'user@gmail.com', '{noop}password');

INSERT INTO role(ROLE, USER_ID)
VALUES ('USER', 1),
       ('ADMIN', 1),
       ('USER', 2),
       ('USER', 3),
       ('USER', 4);

INSERT INTO laser(name, capacity, max_size)
VALUES ('1л', 0, 160),
       ('2л', 0, 160),
       ('3л', 0, 200),
       ('4л', 0, 200),
       ('5л', 0, 800),
       ('6л', 0, 800);

INSERT INTO maps (USER_ID, ID, SIZE, PRICE, DATE_TIME, LANGUAGE_MAP, TYPE_MAP, IS_STATE, LIGHT, IS_MULTI_LEVEL, COLOR,
                  CONTACT, DESCRIPTION)
VALUES (5, 2135, 160, 14000, '2019-01-01', 'Английский', 'Мир', 'false', 'Без подсветки', 'true', 'темный орех',
        'вотсап', 'если получится быстрее сделать'),
       (5, 2136, 140, 9000, '2019-01-01', 'Английский', 'Мир', 'false', 'Без подсветки', 'true', 'однот орех', 'вотсап',
        'срочно'),
       (5, 2137, 140, 4500, '2019-01-01', 'Русский', 'Мир', 'false', 'Без подсветки', 'false', 'орех', 'вотсап',
        'срочно'),
       (5, 2138, 200, 31000, '2019-08-15', 'Русский', 'Мир', 'false', 'Подсветка', 'true', 'орех', 'millefram', ''),
       (5, 2139, 250, 21000, '2019-01-01', 'Английский', 'Мир', 'false', 'Без подсветки', 'true', 'орех', 'Вотсап',
        'налож платеж 20000'),
       (5, 2140, 140, 10000, '2019-01-01', 'Русский', 'Мир', 'false', 'Без подсветки', 'true', 'орех', 'Вотсап', ''),
       (5, 2142, 140, 11000, '2019-01-01', 'Английский', 'Мир', 'false', 'Без подсветки', 'true', 'орех с серым',
        'Вотсап', ''),
       (3, 2143, 120, 3500, '2019-08-16', 'Русский', 'Мир', 'false', 'Без подсветки', 'false', 'орех', 'kashnanlena',
        ''),
       (4, 2144, 200, 32000, '2019-01-01', 'Русский', 'Мир', 'false', 'Подсветка', 'true', 'пиздец',
        'Татьяна  Оля Вконтакте ', ''),
       (2, 2145, 160, 7000, '2019-01-01', 'Английский', 'Мир', 'false', 'Без подсветки', 'false', 'тёмный орех',
        'Вотсап', ''),
       (2, 2146, 180, 16000, '2019-01-01', 'Русский', 'Мир', 'false', 'Без подсветки', 'true', 'орех с синим',
        'Вотсап и Почта ', ''),
       (3, 2147, 180, 15000, '2019-01-01', 'Русский', 'Мир', 'false', 'Без подсветки', 'true', 'карамель', 'wifeche',
        ''),
       (3, 2148, 120, 3500, '2019-01-01', 'Русский', 'Мир', 'false', 'Без подсветки', 'false', 'орех', 'asda', ''),
       (3, 2149, 160, 13300, '2019-01-01', 'Русский', 'Мир', 'false', 'Без подсветки', 'true', 'орех/махагон',
        'Эльвира Калинина (ВК)', 'ГЕОТЕГИ'),
       (2, 2150, 140, 6000, '2019-01-01', 'Английский', 'Мир', 'false', 'Без подсветки', 'false', 'серый орех',
        'вотсап', 'желательно к 1-2 сентября'),
       (4, 2151, 250, 20000, '2019-01-01', 'Русский', 'Мир', 'false', 'Без подсветки', 'true', 'пиздец', 'Wu', ''),
       (2, 2152, 120, 11000, '2019-01-01', 'Английский', 'Мир', 'false', 'Без подсветки', 'true', 'орех',
        'Вотсап', 'Ооо юниткэрри'),
       (3, 2154, 140, 4500, '2019-08-19', 'Русский', 'Мир', 'false', 'Без подсветки', 'false', 'орех', 'lavaleri5', ''),
       (2, 2155, 120, 3500, '2019-01-01', 'Английский', 'Мир', 'false', 'Без подсветки', 'false', 'орех', 'Вотсап', ''),
       (2, 2156, 250, 15000, '2022-12-07', 'Русский', 'Мир', 'false', 'Без подсветки', 'true', 'орех',
        'почта Сандалкин', 'табичка Архагельск, надпись на упаковке'),
       (3, 2158, 200, 16000, '2019-01-01', 'Английский', 'Мир', 'false', 'Без подсветки', 'true', 'орех/серый (фото)',
        'dasd', ''),
       (4, 2159, 140, 10000, '2019-01-01', 'Английский', 'Мир', 'false', 'Без подсветки', 'true', 'орех с синим', 'Wu',
        ''),
       (2, 2160, 140, 5800, '2019-01-01', 'Английский', 'Мир', 'false', 'Без подсветки', 'false', 'черный',
        'Вотсап', ''),
       (2, 2161, 140, 5800, '2019-01-01', 'Английский', 'Мир', 'false', 'Без подсветки', 'false', 'орех',
        'вотсап', ''),
       (2, 2162, 200, 16000, '2019-01-01', 'Английский', 'Мир', 'false', 'Без подсветки', 'true', 'карамель ', 'Вотсап',
        ''),
       (2, 2164, 140, 13000, '2019-01-01', 'Русский', 'Мир', 'false', 'Без подсветки', 'true', 'Цветная по артикулам',
        'Вотсап', 'налож платеж'),
       (2, 2165, 140, 6500, '2019-01-01', 'Русский', 'Мир', 'false', 'Без подсветки', 'false', 'орех с серым', 'вотсап',
        ''),
       (4, 2166, 250, 20000, '2019-01-01', 'Русский', 'Мир', 'false', 'Без подсветки', 'true', 'орех с серым', 'Wu',
        'Бн ип Колесников'),
       (4, 2167, 140, 4500, '2019-01-01', 'Английский', 'Мир', 'false', 'Без подсветки', 'false', 'орех', 'Wu', ''),
       (3, 2168, 200, 16000, '2019-08-23', 'Русский', 'Мир', 'false', 'Без подсветки', 'true', 'дуб', 'sgsd', ''),
       (1, 2169, 160, 14000, '2019-08-23', 'Русский', 'Мир', 'false', 'Без подсветки', 'true',
        'основа орех светлая/белый/темносиний', 'sdcsdc', ''),
       (1, 2170, 200, 18600, '2019-01-01', 'Английский', 'Россия', 'false', 'Без подсветки', 'true',
        'орех и махагон - страны снг без темного', 'почта ', 'ООО "Сарториус Стедим РУС"'),
       (1, 2171, 180, 10000, '2019-08-24', 'Русский', 'Мир', 'false', 'Без подсветки', 'false', 'орех',
        'sdas', 'СРОЧНАЯ'),
       (3, 7156, 180, 15400, '2022-11-09', 'Русский', 'Мир', 'false', 'Без подсветки', 'true', 'оттенки дуба', 'васта',
        ''),
       (3, 7157, 200, 16320, '2022-11-10', 'Русский', 'Мир', 'true', 'Без подсветки', 'true', 'орех и серо/кофейный',
        'ватсап', ''),
       (3, 7159, 140, 20800, '2022-11-10', 'Русский', 'Мир', 'false', 'Подсветка', 'true', 'серый дуб', 'asdffasd', ''),
       (3, 7161, 200, 24800, '2022-11-10', 'Русский', 'Мир', 'false', 'Без подсветки', 'true', 'дуб/платан', 'sdfasfd',
        ''),
       (3, 7162, 160, 23200, '2022-11-10', 'Русский', 'Мир', 'true', 'Подсветка', 'true', 'по фото (макетя)',
        'sadfasfd', ''),
       (3, 7163, 250, -1, '2022-11-12', 'Русский', 'Мир', 'false', 'Подсветка', 'true', 'орех', 'asdfsadf', ''),
       (3, 7164, 160, 10400, '2022-11-12', 'Русский', 'Мир', 'false', 'Без подсветки', 'true', 'орех/серый', 'sdfaf',
        ''),
       (3, 7165, 160, 4200, '2022-11-12', 'Русский', 'Мир', 'false', 'Без подсветки', 'false', 'орех', 'sadfsad', ''),
       (3, 7166, 200, 13300, '2022-11-12', 'Русский', 'Мир', 'false', 'Без подсветки', 'false', 'дуб/платан', 'dasdsf',
        ''),
       (3, 7167, 140, 9100, '2022-11-13', 'Русский', 'Мир', 'false', 'Без подсветки', 'true', 'орех/серый/синий',
        'ВК sadfa sdf', ''),
       (3, 7168, 160, 12300, '2022-11-13', 'Русский', 'Мир', 'true', 'Без подсветки', 'true', 'орех/серый/синий',
        'ватсап sdf', ''),
       (3, 7169, 160, 16850, '2022-11-15', 'Русский', 'Мир', 'false', 'Без подсветки', 'true', 'серый орех/бирюза',
        'вастап asdfsa', '');
