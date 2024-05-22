INSERT INTO ml_task_type(name) VALUES ('forecasting');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO ml_task_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from ml_task_type where name='forecasting'), 'forecasting', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO ml_task_type(name) VALUES ('regression');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO ml_task_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from ml_task_type where name='regression'), 'regression', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO ml_task_type(name) VALUES ('classification');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO ml_task_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from ml_task_type where name='classification'), 'classification', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
