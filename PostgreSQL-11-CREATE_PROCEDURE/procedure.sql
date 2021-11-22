create table if not exists accounts
(
    active       boolean,
    balance      decimal,
    account_type text
);

INSERT INTO accounts
SELECT true, i, 'current'
FROM generate_series(-50, 50) s(i);

INSERT INTO accounts
SELECT true, i, 'savings'
FROM generate_series(-100, 100) s(i);

CREATE OR REPLACE PROCEDURE deactivate_unpaid_accounts()
    LANGUAGE SQL
AS
$$

UPDATE accounts
SET active = false
WHERE balance < 0;

$$;

CALL deactivate_unpaid_accounts();


DROP TABLE IF EXISTS accounts;