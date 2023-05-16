create table order_report
(
    id               serial,
    cost             decimal      not null,
    description      text         not null,
    create_date_time timestamp,
    start_date_time  timestamp,
    end_date_time    timestamp,
    status           varchar(12),
    seller_id        bigint       not null,
    customer_id      bigint       not null,
    service_type     varchar(100) not null,
    foreign key (customer_id) references customer (id) on delete set null,
    foreign key (seller_id) references employee (id) on delete set null,
    primary key (id)
)