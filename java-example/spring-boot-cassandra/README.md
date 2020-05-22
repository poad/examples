# Spring Boot with Spring Data Cassandra Example

## require
 - Cassandra 3.11.6+
 
## Prepare

```$cqlsh
CREATE KEYSPACE artist WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 1};
```

## Use docker-compose

```$sh
docker-compose up -d cassandra1
docker-compose exec cassandra1 cqlsh
```

```$cqlsh
CREATE KEYSPACE artist WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 1};
exit
```

```$sh
docker-compose up -d front
```