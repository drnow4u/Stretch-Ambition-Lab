DROP TABLE IF EXISTS accounts;

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

CREATE OR REPLACE PROCEDURE deactivate_accounts_below_balance(balance_threshold decimal)
    LANGUAGE SQL
AS
$$

UPDATE accounts
SET active = false
WHERE balance < balance_threshold;

$$;

SELECT COUNT(*)
FROM accounts
WHERE active;

CALL deactivate_accounts_below_balance(1);

SELECT COUNT(*)
FROM accounts
WHERE active;

CREATE OR REPLACE PROCEDURE account_insert(quantity int)
    LANGUAGE plpgsql
AS
$$
BEGIN
    FOR i IN 0..quantity
        LOOP
            INSERT INTO accounts VALUES (true, i, 'savings');
            IF i % 2 = 0 THEN
                COMMIT;
            ELSE
                ROLLBACK;
            END IF;
        END LOOP;
END;
$$;

CALL account_insert(100);

SELECT COUNT(*)
FROM accounts
WHERE active;
