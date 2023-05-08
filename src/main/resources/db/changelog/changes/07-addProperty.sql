create table property
(
    id     serial,
    name   varchar(100) not null,
    value  varchar(200),
    pet_id bigint not null,
    primary key (id),
    foreign key (pet_id) references pet (id) on delete set null
)