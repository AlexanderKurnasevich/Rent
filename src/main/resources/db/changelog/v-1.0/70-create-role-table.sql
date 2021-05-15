create table if not exists t_role (id int8 not null, name varchar(255), primary key (id));

INSERT INTO t_role(id, name)
VALUES (1, 'ROLE_USER');

INSERT INTO t_role(id, name)
VALUES (2, 'ROLE_ADMIN');