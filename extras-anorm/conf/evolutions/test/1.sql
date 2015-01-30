# --- !Ups

create table message (
  id     integer primary key auto_increment,
  posted timestamp,
  author varchar(256),
  text   varchar(4096)
);

# --- !Downs

drop table message;
