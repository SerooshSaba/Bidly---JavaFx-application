drop table stores;
drop table antiqes;
drop table customers;
drop table bids;

PRAGMA foreign_keys = ON;

-- TABLES

CREATE TABLE stores (
                        store_id    INTEGER NOT NULL PRIMARY KEY,
                        name        TEXT NOT NULL
);

CREATE TABLE antiqes(
                        antiqe_id   INTEGER NOT NULL PRIMARY KEY,
                        name        TEXT NOT NULL,
                        description TEXT NOT NULL,
                        pic_url     TEXT NOT NULL,
                        price       INTEGER NOT NULL,
                        store_id    INTEGER NOT NULL,

                        FOREIGN KEY (store_id) REFERENCES stores(store_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE customers(
                          customer_id INTEGER NOT NULL PRIMARY KEY,
                          name        TEXT NOT NULL,
                          card_info   TEXT NOT NULL
);

CREATE TABLE bids (
                      bid_id      INTEGER NOT NULL PRIMARY KEY,
                      amount      INTEGER NOT NULL,
                      antiqe_id   INTEGER NOT NULL,
                      customer_id INTEGER NOT NULL,
                      FOREIGN KEY (antiqe_id) REFERENCES antiqes(antiqe_id) ON UPDATE CASCADE ON DELETE CASCADE,
                      FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE payments (
                          payment_id  INTEGER NOT NULL PRIMARY KEY,
                          amount      INTEGER NOT NULL,
                          date        TEXT NOT NULL,
                          store_id    INTEGER NOT NULL,
                          FOREIGN KEY (store_id) REFERENCES stores(store_id) ON UPDATE CASCADE ON DELETE CASCADE
);

-- INSERT