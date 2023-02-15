create table laser
(
    id       int4         not null,
    name     varchar(128) not null,
    capacity int4,
    max_size int4,
    primary key (id)
);


create table lists
(
    plywood_list_id int4 not null,
    list            varchar(255)
);

create table maps
(
    id               int4                 not null,
    contact          varchar(255)         not null,
    description      varchar(800),
    price            int4                 not null,
    additional       varchar(800),
    color            varchar(255)         not null,
    date_time        timestamp            not null,
    is_capital       boolean default true not null,
    is_color_plywood boolean,
    is_monochromatic boolean default false,
    is_multi_level   boolean              not null,
    is_plexiglass    boolean default false,
    is_state         boolean              not null,
    is_view          boolean default true,
    language_map     varchar(255)         not null,
    light            varchar(255)         not null,
    size             int4                 not null,
    type_map         varchar(255)         not null,
    user_id          int4                 not null,
    order_map_id     int4,
    primary key (id)
);

create table orders
(
    id               int4      not null,
    completed        boolean   not null,
    cut_begin        timestamp,
    cut_end          timestamp,
    gluing_end       timestamp,
    availability     boolean,
    is_color_plywood boolean,
    laser            varchar(255),
    market_place     boolean,
    painter          varchar(255),
    order_term       timestamp not null,
    packed_end       timestamp,
    painting_begin   timestamp,
    painting_end     timestamp,
    post_end         timestamp,
    stage            int4      not null,
    primary key (id)
);

create table plywood_list
(
    id int4 not null,
    primary key (id)
);

create table plywood_list_for_map
(
    order_map_id int4 not null,
    plywood_list varchar(255)
);

create table role
(
    user_id int4 not null,
    role    varchar(255)
);

create table task
(
    id      int4 not null,
    comment varchar(255),
    map     varchar(255),
    number  int4,
    target  varchar(255),
    primary key (id)
);

create table users
(
    id         int4                    not null,
    name       varchar(128)            not null,
    email      varchar(128)            not null,
    enabled    bool      default true  not null,
    password   varchar(256)            not null,
    registered timestamp default now() not null,
    primary key (id)
);

create sequence my_sequence start 8000 increment 1;

alter table if exists role
drop
constraint if exists uk_user_role;

alter table if exists role
    add constraint uk_user_role unique (user_id, role);

alter table if exists users
drop constraint if exists UK_6dotkott2kjsp8vw4d0m25fb7;

alter table if exists users
    add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email);

alter table if exists lists
    add constraint FKnh890rkk70unph43wm9hsm3mx
    foreign key (plywood_list_id)
    references plywood_list;

alter table if exists maps
    add constraint FKbmuqfclq4y3ay7r4trlmx28ro
    foreign key (user_id)
    references users;

alter table if exists maps
    add constraint FKc4wcaxqaw5b7cuvou9ne1o4wf
    foreign key (order_map_id)
    references orders;

alter table if exists orders
    add constraint FKmbynk042d97r6jgd3i8rqk58u
    foreign key (id)
    references maps;

alter table if exists plywood_list_for_map
    add constraint FKcc7w3qf958iw7dfuwev2gxk8r
    foreign key (order_map_id)
    references orders;

alter table if exists role
    add constraint FKgg3583634e0ydkacyk8wbbm19
    foreign key (user_id)
    references users
    on delete cascade;