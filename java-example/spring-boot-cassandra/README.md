# Spring Boot with Spring Data Cassandra Example

## require
 - Cassandra 3.11.5+
 
## Prepare

```$cqlsh
CREATE KEYSPACE test WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 1};
```
