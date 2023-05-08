create table refresh_token
(
    id          serial,
    user_id     bigint,
    token       uuid,
    expiry_date timestamp,
    foreign key (user_id) references users (id) on delete cascade,
    primary key (id)
)