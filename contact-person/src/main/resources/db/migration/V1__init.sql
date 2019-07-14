CREATE TABLE person (
  id bigint(20) auto_increment primary key,
  name varchar(100) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE contact (
  id bigint(20) auto_increment primary key,
  person_id bigint(20) not null,
  value varchar(100) NOT NULL,
  type varchar(50) NOT NULL,
  constraint contact_person_fk
  foreign key (person_id)
  references person(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;