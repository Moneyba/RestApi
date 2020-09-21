DROP TABLE IF EXISTS "user";

CREATE TABLE "user"(
    user_id serial constraint user_pk primary key,
    deleted boolean not null,
    username varchar(255) not null unique,
    password varchar(255)
);

CREATE TABLE role(
    role_id serial constraint role_pk primary key,
    role_name varchar(255) not null unique
);

CREATE TABLE user_role(
    user_id int references "user"(user_id),
    role_id int references role(role_id)
);

CREATE TABLE "customer"(
    customer_id serial constraint customer_pk primary key,
    deleted boolean not null,
    name varchar(255) not null,
    surname varchar(255) not null,
    photo_url text,
    created_by_user_id int references "user"(user_id) not null,
    modified_by_user_id int references "user"(user_id)
);