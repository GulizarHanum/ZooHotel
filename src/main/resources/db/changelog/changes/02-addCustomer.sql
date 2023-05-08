create table customer
(
    id                 serial,
    last_name          varchar(75) not null,
    first_name         varchar(75) not null,
    photo              bytea,
    city               varchar(50) not null,
    creation_date_time timestamp   not null,
    birth_date         date        not null,
    user_id            bigint      not null,
    foreign key (user_id) references users (id) on delete cascade,
    primary key (id)
);