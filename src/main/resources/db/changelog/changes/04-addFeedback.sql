create table feedback
(
    id             serial,
    sender_id      bigint not null,
    recipient_id   bigint not null,
    text           text,
    send_date_time timestamp,
    mark           decimal,
    primary key (id),
    foreign key (sender_id) references customer (id) on delete set null,
    foreign key (recipient_id) references employee (id) on delete cascade
)
