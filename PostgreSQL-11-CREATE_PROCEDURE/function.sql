CREATE OR REPLACE FUNCTION account_type_count(account_type text default 'current') RETURNS integer

    LANGUAGE plpgsql IMMUTABLE
AS
$$

DECLARE
    account_count int;

BEGIN

    SELECT count(*)
    INTO account_count
    FROM accounts
    WHERE accounts.account_type = $1;
    RETURN account_count;

END;
$$;


SELECT *
FROM account_type_count('savings');

SELECT *
FROM account_type_count();

