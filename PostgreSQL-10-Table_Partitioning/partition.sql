CREATE EXTENSION IF NOT EXISTS citus;

DROP TABLE IF EXISTS transaction;

CREATE TABLE transaction
(
    ts       timestamptz,
    currency VARCHAR(3),
    amount   decimal
) PARTITION BY RANGE (ts);

CREATE TABLE transaction_2021_jan PARTITION OF transaction
FOR VALUES FROM ('2021-01-01') TO ('2021-02-01');

CREATE TABLE transaction_2021_feb PARTITION OF transaction
FOR VALUES FROM ('2021-02-01') TO ('2021-03-01');


INSERT INTO transaction
SELECT '2021-01-01'::timestamptz + '45 seconds'::interval * g,
       'EUR',
       g
FROM generate_series(1, 100 * 1000) g;

SELECT count(*) FROM transaction;

SELECT count(*) FROM transaction_2021_jan;

SELECT count(*) FROM transaction_2021_feb;

SELECT pg_size_pretty(pg_relation_size('transaction_2021_jan'));

SELECT alter_table_set_access_method('transaction_2021_jan', 'columnar');

SELECT pg_size_pretty(pg_relation_size('transaction_2021_jan'));

insert into transaction_2021_jan values ('2021-01-02T01:02:03', 'EUR', 333);

update transaction_2021_jan set amount=999999 where amount=333;

update transaction_2021_feb set amount=999999 where amount=60000;

select * from transaction_2021_feb  where amount=999999;

