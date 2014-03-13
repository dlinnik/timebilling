/* project */
insert into project (id, description, name) values (1,'pr1','project 1');
insert into project (id, description, name) values (2,'pr2','project 2');
insert into project (id, description, name) values (3,'pr3','project 3');
insert into project (id, description, name) values (4,'pr4','project 4');
insert into project (id, description, name) values (5,'pr5','project 5');

/* user */

insert into user (id, account_expired, account_locked, email, enabled, password, username) values (1, false, false, 'admin@timebilling.ru', true, 'passw0rd', 'admin');
insert into user (id, account_expired, account_locked, email, enabled, password, username) values (2, false, false, 'user@timebilling.ru', true, 'passw0rd', 'user');

/* role */

insert into role (id, role, user_id) values (1, 1, 1);
insert into role (id, role, user_id) values (2, 2, 2);