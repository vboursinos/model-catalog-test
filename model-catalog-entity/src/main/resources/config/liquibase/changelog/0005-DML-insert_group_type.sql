INSERT INTO model_group_type(name) VALUES ('fast');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO model_group_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from model_group_type where name='fast'), 'fast', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO model_group_type(name) VALUES ('explainable');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO model_group_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from model_group_type where name='explainable'), 'explainable', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO model_group_type(name) VALUES ('advanced');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO model_group_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from model_group_type where name='advanced'), 'advanced', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
