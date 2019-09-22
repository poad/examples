# Spring Boot Web Example

## Setup the database.

```
create database test;
use test;
create table artist(id char(36) NOT NULL, name text NOT NULL, primary key (id)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
create table song(id char(36) NOT NULL, name text NOT NULL, artist_id char(36) NOT NULL, primary key (id), foreign key (artist_id) references artist(id)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

## Build

```$bash
cd ../java-maven-example-bom
mvn install -Dmaven.test.skip=true
```
