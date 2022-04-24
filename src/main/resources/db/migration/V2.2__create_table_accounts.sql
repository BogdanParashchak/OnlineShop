CREATE TABLE accounts
(
    id            serial PRIMARY KEY,
    name          VARCHAR(10) UNIQUE NOT NULL,
    password      VARCHAR(10)        NOT NULL,
    creation_date TIMESTAMP          NOT NULL
);