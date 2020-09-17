DROP TABLE IF EXISTS "user";

CREATE TABLE "user"
(
    id  serial PRIMARY KEY,
    username VARCHAR(128) UNIQUE
);