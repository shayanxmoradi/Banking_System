SET schema 'banking';

CREATE TABLE IF NOT EXISTS users
(
    id         SERIAL PRIMARY KEY,
    username   VARCHAR(40) UNIQUE,
    password   VARCHAR(50),
    first_name VARCHAR(100),
    last_name  VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS bank
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(100) unique,
    address TEXT
);

INSERT INTO bank (name, address)
VALUES ('Sparksasse', 'Dortmund, Mitte');
INSERT INTO bank (name, address)
VALUES ('Volks Bank', 'Dortmund, Hansa Platz');
INSERT INTO bank (name, address)
VALUES ('Post bank', 'Berlin, Wasser Strasse');
INSERT INTO bank (name, address)
VALUES ('Commerz bank', 'Bochum, Strift Strasse');

-- Create the 'account' table
CREATE TABLE IF NOT EXISTS account
(
    id              SERIAL PRIMARY KEY,
    user_id_fk      INT REFERENCES users (id),
    user_first_name VARCHAR(100),
    name            VARCHAR(255),
    number          VARCHAR(255) unique,
    paya_number     VARCHAR(255) unique,
    bank_id_fk      INT REFERENCES bank (id),
    bank_name_fk    VARCHAR(100) REFERENCES bank (name),
    balance         FLOAT8
);


CREATE TABLE IF NOT EXISTS card
(
    id            serial primary key,
    name          varchar(255),
    number        varchar(16),
    balance       float8,
    is_active     boolean,
    account_id_fk int references account (id),
    account_name  varchar(100),
    expire_date   date,
    ccv2          int,
    user_id_fk    int references users (id),
    bank_name     varchar(100)
);

select *
from card
where bank_name = 'S'
  AND user_id_fk =?;

UPDATE account
SET balance= 1004300
WHERE id = 1;

select *
from card
         inner join banking.account a on a.id = card.account_id_fk
where card.number = '2344236383736710';


-- Define ENUM types
CREATE TYPE transaction_status_enum AS ENUM ('SUCCESSFUL', 'FAILED', 'IN_PROGRESS');
CREATE TYPE transaction_type_enum AS ENUM ('NORMAL', 'PAYA', 'PAYA_TOGETHER', 'SATNA');

-- Create the transactions table
CREATE TABLE transactions
(
    id                      SERIAL PRIMARY KEY,
    type                    transaction_type_enum,
    transaction_status      transaction_status_enum,
    amount                  float8,
    sender_user_id          int,
    transaction_time        TIME,
    transaction_date        DATE,
    transaction_fee         float8,
    sender_account_number   VARCHAR(50),
    receiver_account_number VARCHAR(50),
    sender_account_id       int references account (id),
    reciver_account_id      int references account (id)
);