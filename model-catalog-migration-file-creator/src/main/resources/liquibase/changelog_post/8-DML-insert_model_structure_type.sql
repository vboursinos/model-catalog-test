INSERT INTO model_structure_type(name) VALUES ('dynamic');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO model_structure_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from model_structure_type where name='dynamic'), 'dynamic', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO model_structure_type(name) VALUES ('base');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO model_structure_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from model_structure_type where name='base'), 'base', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
