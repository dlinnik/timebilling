/* applications */

insert into application (id, name, screenname) values (1,'app1','my first app');
insert into application (id, name, screenname) values (2,'app2','Смирнов и партнеры');
insert into application (id, name, screenname) values (3,'app10','Тест');


/* project */
insert into project (id, description, name, appname) values (1,'pr1','project 1', 'app1');
insert into project (id, description, name, appname) values (2,'pr2','project 2', 'app1');
insert into project (id, description, name, appname) values (3,'pr3','project 3', 'app1');
insert into project (id, description, name, appname) values (4,'pr4','project 4', 'app2');
insert into project (id, description, name, appname) values (5,'pr5','project 5', 'app2');

/* user */

insert into user (id, accountexpired, accountlocked, email, enabled, password, username, appname) values (1, false, false, 'admin@timebilling.ru', true, 'passw0rd', 'admin', 'app1');
insert into user (id, accountexpired, accountlocked, email, enabled, password, username, appname) values (2, false, false, 'user@timebilling.ru', true, 'passw0rd', 'user', 'app1');

insert into user (id, accountexpired, accountlocked, email, enabled, password, username, appname) values (3, false, false, 'admin2@timebilling.ru', true, 'passw0rd', 'admin2', 'app2');
insert into user (id, accountexpired, accountlocked, email, enabled, password, username, appname) values (4, false, false, 'user2@timebilling.ru', true, 'passw0rd', 'user2', 'app2');

insert into user (id, accountexpired, accountlocked, email, enabled, password, username, appname) values (5, false, false, 'admin10@timebilling.ru', true, 'passw0rd', 'admin10', 'app10');
insert into user (id, accountexpired, accountlocked, email, enabled, password, username, appname) values (6, false, false, 'user10@timebilling.ru', true, 'passw0rd', 'user10', 'app10');

/* role */

insert into role (id, role, user_id, appname) values (1, 1, 1, 'app1');
insert into role (id, role, user_id, appname) values (2, 2, 2, 'app1');

insert into role (id, role, user_id, appname) values (3, 1, 3, 'app2');
insert into role (id, role, user_id, appname) values (4, 2, 4, 'app2');

insert into role (id, role, user_id, appname) values (5, 1, 5, 'app10');
insert into role (id, role, user_id, appname) values (6, 2, 6, 'app10');
