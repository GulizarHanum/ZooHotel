create table cost_service_type
(
    id           serial,
    employee_id  bigint       not null,
    service_type varchar(100) not null,
    cost         decimal      not null,
    primary key (id),
    foreign key (employee_id) references employee (id) on delete cascade
)