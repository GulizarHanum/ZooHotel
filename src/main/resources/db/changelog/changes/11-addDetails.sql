create table details
(
    id                 serial,
    employee_id        bigint not null,
    experience         int,
    have_animals       boolean,
    is_house           boolean,
    children           integer,
    have_equipment     boolean,
    have_vet_education boolean,
    primary key (id),
    foreign key (employee_id) references employee (id) on delete cascade
);

create table accept_sizes
(
    details_id  bigint,
    accept_size text,
    foreign key (details_id) references details (id) on delete cascade,
    primary key (details_id, accept_size)
);

create table accept_animals
(
    details_id    bigint,
    accept_animal text,
    foreign key (details_id) references details (id) on delete cascade,
    primary key (details_id, accept_animal)
);

alter table employee
    add column details_id bigint,
    add foreign key (details_id) references details (id) on delete set null;