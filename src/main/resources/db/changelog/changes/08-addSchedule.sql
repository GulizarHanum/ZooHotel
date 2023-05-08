create table schedule
(
    id              serial,
    sender_id       bigint,
    service_type    varchar(100),
    start_date_time timestamp not null,
    end_date_time   timestamp not null,
    foreign key (sender_id) references employee (id) on delete cascade,
    primary key (id)
)