INSERT INTO dependency_group_type(name) VALUES ('lightning');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_group_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_group_type where name='lightning'), 'lightning', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO dependency_group_type(name) VALUES ('catboost');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_group_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_group_type where name='catboost'), 'catboost', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO dependency_group_type(name) VALUES ('sktime');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_group_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_group_type where name='sktime'), 'sktime', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO dependency_group_type(name) VALUES ('statsforecast');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_group_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_group_type where name='statsforecast'), 'statsforecast', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO dependency_group_type(name) VALUES ('darts');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_group_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_group_type where name='darts'), 'darts', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO dependency_group_type(name) VALUES ('huggingface');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_group_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_group_type where name='huggingface'), 'huggingface', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO dependency_group_type(name) VALUES ('intelex');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_group_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_group_type where name='intelex'), 'intelex', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO dependency_group_type(name) VALUES ('lightgbm');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_group_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_group_type where name='lightgbm'), 'lightgbm', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO dependency_group_type(name) VALUES ('xgboost');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_group_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_group_type where name='xgboost'), 'xgboost', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO dependency_group_type(name) VALUES ('base');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_group_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_group_type where name='base'), 'base', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO dependency_group_type(name) VALUES ('stacking');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_group_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_group_type where name='stacking'), 'stacking', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
