CREATE EXTENSION IF NOT EXISTS citus;

DROP TABLE IF EXISTS transaction_row;
DROP TABLE IF EXISTS transaction_col;

CREATE TABLE transaction_row
(
    ts       timestamp,
    currency VARCHAR(3),
    amount   decimal
);

CREATE TABLE transaction_col
(
    ts       timestamp,
    currency VARCHAR(3),
    amount   decimal
) using columnar;

INSERT INTO transaction_row
SELECT '2021-11-19'::timestamp + '45 seconds'::interval * g,
       'EUR',
       g
FROM generate_series(1, 100 * 1000) g;

INSERT INTO transaction_col
SELECT '2021-11-19'::timestamp + '45 seconds'::interval * g,
       'EUR',
       g
FROM generate_series(1, 100 * 1000) g;

SELECT pg_size_pretty(pg_relation_size('transaction_row'));

SELECT pg_size_pretty(pg_relation_size('transaction_col'));
