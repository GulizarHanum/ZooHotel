create table details
(
    id                 serial,
    employee_id        bigint  not null,
    experience         int     not null,
    is_house           boolean not null,
    children           integer not null,
    have_equipment     boolean not null,
    accept_animals     text,
    have_vet_education boolean,
    primary key (id),
    foreign key (employee_id) references employee (id) on delete cascade
);

create table accept_sizes
(
    details_id  bigint,
    accept_size text,
    foreign key (details_id) references details (id) on delete cascade,
    primary key (accept_size)
);

create table employee_animals
(
    employee_animals_id serial not null,
    details_id          bigint,
    pet_id              bigint,
    foreign key (details_id) references details (id) on delete cascade,
    foreign key (pet_id) references pet (id) on delete cascade,
    primary key (employee_animals_id)
);

alter table employee
    add column details_id bigint,
    add foreign key (details_id) references details (id) on delete set null;