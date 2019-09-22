# java-maven-example

## Setup the database.

```
create database test;
use test;
create table message(id char(36) NOT NULL, message text NOT NULL, primary key (id));
```

## Setup the projects.

```$bash
cd java-maven-example-bom
./mvnw install -Dmaven.test.skip=true
```
