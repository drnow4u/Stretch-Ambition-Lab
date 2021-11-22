# pg_verifybackup

https://www.postgresql.org/docs/13/app-pgverifybackup.html

This feature was first introduced in PostgreSQL 13.

1. Create backup:

```shell
docker-compose exec -T postgres pg_basebackup -h localhost -U postgres -P -D /var/backup
```

2. Verify backup:

```shell
docker-compose exec -T postgres pg_verifybackup /var/backup
```
