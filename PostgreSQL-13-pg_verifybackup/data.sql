DROP TABLE IF EXISTS transaction;

CREATE TABLE transaction
(
    ts       timestamptz,
    currency VARCHAR(3),
    amount   decimal
);

INSERT INTO transaction
SELECT '2021-01-01'::timestamptz + '45 seconds'::interval * g,
       'EUR',
       g
FROM generate_series(1, 100 * 1000) g;

SELECT count(*) FROM transaction;

SELECT pg_size_pretty(pg_relation_size('transaction'));
