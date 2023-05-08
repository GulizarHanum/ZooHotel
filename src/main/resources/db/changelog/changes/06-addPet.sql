CREATE TABLE pet
(
    id                    serial,
    photo                 bytea,
    customer_id           bigint      not null,
    employee_id           bigint      not null,
    name                  varchar(50) not null,
    animal_type           varchar(50),
    have_all_vaccinations boolean     not null,
    weight                decimal,
    is_female             boolean     not null,
    birth_date            date,
    description           text,
    primary key (id),
    foreign key (customer_id) references customer (id) on delete cascade,
    foreign key (employee_id) references employee (id) on delete cascade
)