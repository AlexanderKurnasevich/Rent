create table if not exists t_client (id  bigserial not null, authority varchar(255), date_of_birth timestamp, date_of_expiry timestamp,
date_of_issue timestamp, email varchar(255), name varchar(30), passport_no varchar(90), personal_no varchar(90), sex varchar(255),
surname varchar(40), user_id int8, primary key (id))