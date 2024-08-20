DROP DATABASE IF EXISTS stock_quote;
CREATE DATABASE stock_quote;
\c stock_quote

CREATE SEQUENCE quote_id START WITH 100;
CREATE SEQUENCE position_id START WITH 100;

DROP TABLE IF EXISTS quote;
CREATE TABLE PUBLIC.quote (
    id                  INT NOT NULL DEFAULT nextval('quote_id'),
    symbol              VARCHAR(10) PRIMARY KEY,
    open                DECIMAL(10, 2) NOT NULL,
    high                DECIMAL(10, 2) NOT NULL,
    low                 DECIMAL(10, 2) NOT NULL,
    price               DECIMAL(10, 2) NOT NULL,
    volume              INT NOT NULL,
    latest_trading_day  DATE NOT NULL,
    previous_close      DECIMAL(10, 2) NOT NULL,
    change              DECIMAL(10, 2) NOT NULL,
    change_percent      VARCHAR(10) NOT NULL,
    timestamp           TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL
);

DROP TABLE IF EXISTS position;
CREATE TABLE PUBLIC.position (
    id                      INT NOT NULL DEFAULT nextval('position_id')
    symbol                  VARCHAR(10) PRIMARY KEY,
    number_of_shares        INT NOT NULL,
    value_paid              DECIMAL(10, 2) NOT NULL,
    CONSTRAINT symbol_fk	FOREIGN KEY (symbol) REFERENCES quote(symbol)
);

ALTER SEQUENCE quote_id OWNED BY quote.id;
ALTER SEQUENCE position_id OWNED BY position.id;