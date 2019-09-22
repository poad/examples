# Spring Boot Data JPA Query Example


```$bash
CREATE DATABASE test;
use test;
CREATE TABLE artist(id CHAR(36) NOT NULL, name TEXT NOT NULL, nick_name TEXT, birthday DATE, PRIMARY KEY (id)) DEFAULT CHARSET=utf8;
CREATE TABLE song(id CHAR(36) NOT NULL, title TEXT NOT NULL, `release` DATE, artist_id CHAR(36) NOT NULL, PRIMARY KEY (id), FOREIGN KEY (artist_id) REFERENCES artist(id)) DEFAULT CHARSET=utf8;
```