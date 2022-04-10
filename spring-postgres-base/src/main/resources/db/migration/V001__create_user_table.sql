create table if not exists users
(
    user_id  uuid         not null,
    name     varchar(256) not null,
    email    varchar(256) not null,
    PRIMARY KEY (user_id),
    UNIQUE (email)
);

CREATE INDEX users_email_index ON users (email);
