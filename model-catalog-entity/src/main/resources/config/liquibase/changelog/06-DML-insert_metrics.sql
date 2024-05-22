INSERT INTO metric(name) VALUES ('classification-log-loss');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO metric_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from metric where name='classification-log-loss'), 'classification-log-loss', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO metric(name) VALUES ('classification-roc');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO metric_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from metric where name='classification-roc'), 'classification-roc', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
