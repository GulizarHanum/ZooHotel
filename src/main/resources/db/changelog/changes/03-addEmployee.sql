create table employee
(
    id                 serial,
    last_name          varchar(75) not null,
    first_name         varchar(75) not null,
    photo              bytea,
    description        text,
    rating             decimal,
    city               varchar(50) not null,
    street             varchar(100),
    house              varchar(10),
    creation_date_time timestamp   not null,
    birth_date         date        not null,
    user_id            bigint      not null,
    foreign key (user_id) references users (id) on delete cascade,
    primary key (id)
);

create table employee_photos
(
    id          serial,
    employee_id bigint,
    photo       bytea,
    foreign key (employee_id) references employee (id) on delete cascade,
    primary key (id)
)