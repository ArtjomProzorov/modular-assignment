CREATE TABLE IF NOT EXISTS Account
(
    accountId  INTEGER NOT NULL GENERATED always as identity,
    customerId INTEGER,
    country    VARCHAR(22),
    primary key (accountId)
);


CREATE TABLE IF NOT EXISTS Balance
(
    balanceId INTEGER NOT NULL GENERATED always as identity,
    accountId INTEGER,
    currency  VARCHAR(200),
    amount    BIGINT,
    primary key (balanceId)

);

CREATE TABLE IF NOT EXISTS Transaction
(
    transactionId    INTEGER NOT NULL GENERATED always as identity,
    accountId        INTEGER,
    amount           BIGINT,
    currency         VARCHAR(200),
    direction        VARCHAR(200),
    description      VARCHAR(200),
    remainingBalance BIGINT,
    primary key (transactionId)
);

