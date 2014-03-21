/* applications */
insert into application (id, name, screen_name) values (1,'app1','my first app');
insert into application (id, name, screen_name) values (2,'app2','Смирнов и партнеры');

/* project */
insert into project (id, description, name, appname) values (1,'pr1','project 1', 'app1');
insert into project (id, description, name, appname) values (2,'pr2','project 2', 'app1');
insert into project (id, description, name, appname) values (3,'pr3','project 3', 'app1');
insert into project (id, description, name, appname) values (4,'pr4','project 4', 'app2');
insert into project (id, description, name, appname) values (5,'pr5','project 5', 'app2');

/* user */

insert into user (id, account_expired, account_locked, email, enabled, password, username) values (1, false, false, 'admin@timebilling.ru', true, 'passw0rd', 'admin');
insert into user (id, account_expired, account_locked, email, enabled, password, username) values (2, false, false, 'user@timebilling.ru', true, 'passw0rd', 'user');

/* role */

insert into role (id, role, user_id) values (1, 1, 1);
insert into role (id, role, user_id) values (2, 2, 2);