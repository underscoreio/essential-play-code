# --- !Ups

create table message (
  id     integer primary key,
  posted timestamp without time zone,
  author varchar(256),
  text   varchar(4096)
);

# --- !Downs

drop table message;
