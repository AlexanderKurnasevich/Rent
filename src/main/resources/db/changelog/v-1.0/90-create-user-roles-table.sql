create table if not exists t_user_roles (user_id int8 not null, roles_id int8 not null, primary key (user_id, roles_id));