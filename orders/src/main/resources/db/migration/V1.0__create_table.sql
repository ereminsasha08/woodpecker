-- drop schema if exists production CASCADE ;
-- drop schema if exists good cascade ;
-- create schema production;
-- create schema good;
--
-- create table orders
-- (
--     id           integer generated BY DEFAULT AS IDENTITY not null,
--     date_create  timestamp                                not null,
--     completed    boolean                                  not null,
--     market_place boolean                                  not null,
--     is_paid      boolean default false                    not null,
--     user_id      integer                                  not null,
--     primary key (id)
-- );
--
--
-- create table maps
-- (
--     id                          integer generated BY DEFAULT AS IDENTITY not null,
--     contact                     varchar(255),
--     description                 varchar(800),
--     price                       integer                                  not null,
--     type_map                    varchar(255)                             not null,
--     size                        integer                                  not null,
--     language                    varchar(255)                             not null,
--     is_multi_level              boolean                                  not null,
--     color                       varchar(255)                             not null,
--     is_state                    boolean                                  not null,
--     is_capital                  boolean                                  not null,
--     is_monochromatic            boolean                                  not null,
--     is_color_plywood            boolean                                  not null,
--     is_plexiglas                boolean                                  not null,
--     is_view                     boolean default true,
--     light                       varchar(255)                             not null,
--     additional                  varchar(4000),
--     geography_map_production_id integer                                  not null,
--     order_id                    integer,
--     primary key (id),
--     constraint uq_geography_map_production_id unique (geography_map_production_id),
--     constraint fk_order_id foreign key (order_id) references orders (id)
-- );
--
-- create table production.geography_map_production
-- (
--     id               integer generated BY DEFAULT AS IDENTITY not null,
--     geography_map_id integer                                  not null,
--     is_painted       boolean                                  not null,
--     stage            varchar(255) default 'NEW_ORDER'         not null,
--     laser            varchar(255),
--     painter          varchar(255),
--     is_availability  boolean,
--     cut_begin        timestamp,
--     cut_end          timestamp,
--     painting_begin   timestamp,
--     painting_end     timestamp,
--     gluing_begin     timestamp,
--     gluing_end       timestamp,
--     packed_end       timestamp,
--     post_end         timestamp,
--     primary key (id),
--     constraint uq_geography_map_id unique (geography_map_id),
--     constraint fk_geography_map_id foreign key (geography_map_id) references maps (geography_map_production_id)
-- );
--
-- alter table if exists maps add constraint fk_geography_map_production_id foreign key (geography_map_production_id)
--     references production.geography_map_production (geography_map_id) on
-- delete
-- cascade;
--
-- create table laser
-- (
--     id       integer      not null,
--     name     varchar(128) not null,
--     capacity integer,
--     max_size integer,
--     primary key (id)
-- );
--
-- create table lists
-- (
--     plywood_list_id integer not null,
--     list            varchar(255)
-- );
--
-- create table plywood_list
-- (
--     id integer not null,
--     primary key (id)
-- );
--
-- create table plywood_list_for_map
-- (
--     order_map_id integer not null,
--     plywood_list varchar(255)
-- );
--
-- create table role
-- (
--     user_id integer not null,
--     role    varchar(255)
-- );
--
-- create table task
-- (
--     id      integer not null,
--     comment varchar(255),
--     map     varchar(255),
--     number  integer,
--     target  varchar(255),
--     primary key (id)
-- );
--
-- create table users
-- (
--     id         integer                 not null,
--     name       varchar(128)            not null,
--     email      varchar(128)            not null,
--     enabled    boolean   default true  not null,
--     password   varchar(256)            not null,
--     registered timestamp default now() not null,
--     primary key (id)
-- );
--
-- alter table if exists role drop constraint if exists uk_user_role;
--
-- alter table if exists role add constraint uk_user_role unique (user_id, role);
--
-- alter table if exists users drop constraint if exists uk_email;
--
-- alter table if exists users add constraint uk_email unique (email);
--
-- alter table if exists lists add constraint uk_user_id foreign key (plywood_list_id) references plywood_list;
--
-- alter table if exists orders add constraint uk_user_id foreign key (user_id) references users;
--
-- alter table if exists plywood_list_for_map add constraint fk_user_id foreign key (order_map_id) references orders;
--
-- alter table if exists role add constraint fk_user_id foreign key (user_id) references users on delete cascade;