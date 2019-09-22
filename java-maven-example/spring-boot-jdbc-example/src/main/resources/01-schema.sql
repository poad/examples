drop table if exists message;

create table if not exists message (
  id char(36) NOT NULL,
  message text NOT NULL,
  primary key (id)
);
