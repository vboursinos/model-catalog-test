-- changeset liquibaseuser:1
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
-- rollback -- You may not be able to drop the extension, so no rollback specified.

CREATE SEQUENCE IF NOT EXISTS hibernate_sequence;

CREATE SEQUENCE IF NOT EXISTS revinfo_seq INCREMENT BY 1;

-- changeset liquibaseuser:3
CREATE TABLE model_type (
  id uuid PRIMARY KEY,
  name varchar NOT NULL
);
-- rollback DROP TABLE model_type;

-- changeset liquibaseuser:4
CREATE TABLE ml_task_type (
  id uuid PRIMARY KEY,
  name varchar NOT NULL UNIQUE
);
-- rollback DROP TABLE ml_task_type;

-- changeset liquibaseuser:5
CREATE TABLE model_structure_type (
  id uuid PRIMARY KEY,
  name varchar NOT NULL UNIQUE
);
-- rollback DROP TABLE model_structure_type;

-- changeset liquibaseuser:6
CREATE TABLE model_group_type (
  id uuid PRIMARY KEY,
  name varchar NOT NULL
);
-- rollback DROP TABLE model_group_type;

-- changeset liquibaseuser:7
CREATE TABLE model_ensemble_type (
  id uuid PRIMARY KEY,
  name varchar NOT NULL
);
-- rollback DROP TABLE model_ensemble_type;

-- changeset liquibaseuser:8
CREATE TABLE model_family_type (
  id uuid PRIMARY KEY,
  name varchar NOT NULL
);
-- rollback DROP TABLE model_family_type;

-- changeset liquibaseuser:9
CREATE TABLE dependency_group_type (
  id uuid PRIMARY KEY,
  name varchar NOT NULL
);
-- rollback DROP TABLE dependency_group_type;

-- changeset liquibaseuser:10
CREATE TABLE dependency_type (
  id uuid PRIMARY KEY,
  name varchar NOT NULL,
  dependency_group_id uuid REFERENCES dependency_group_type (id) NOT NULL
);
-- rollback DROP TABLE dependency_type;

-- changeset liquibaseuser:11
CREATE TABLE model (
  id uuid PRIMARY KEY,
  ml_task_id uuid REFERENCES ml_task_type (id) NOT NULL,
  name varchar NOT NULL UNIQUE,
  display_name varchar NOT NULL,
  structure_id uuid REFERENCES model_structure_type (id) NOT NULL,
  description varchar,
  advantages text[],
  disadvantages text[],
  enabled boolean NOT NULL,
  ensemble_type_id uuid REFERENCES model_ensemble_type (id),
  family_type_id uuid REFERENCES model_family_type (id),
  decision_tree boolean NOT NULL,
  dependency_group_id uuid REFERENCES dependency_group_type (id)
);
-- rollback DROP TABLE model;

-- changeset liquibaseuser:
CREATE TABLE rel_model__model_type (
   model_id uuid REFERENCES model (id),
   model_type_id uuid REFERENCES model_type (id)
);
 -- rollback DROP TABLE rel_model__model_type;

-- changeset liquibaseuser:12
CREATE TABLE rel_model__groups (
  model_id uuid REFERENCES model (id),
  group_id uuid REFERENCES model_group_type (id)
);
-- rollback DROP TABLE rel_model__groups;

-- changeset liquibaseuser:13
CREATE TABLE metric (
  id uuid PRIMARY KEY,
  name varchar NOT NULL UNIQUE
);
-- rollback DROP TABLE metric;

-- changeset liquibaseuser:14
CREATE TABLE rel_model__incompatible_metrics (
  model_id uuid REFERENCES model (id),
  metric_id uuid REFERENCES metric (id)
);
-- rollback DROP TABLE rel_model__incompatible_metrics;

-- changeset liquibaseuser:15
CREATE TABLE parameter (
  id uuid PRIMARY KEY,
  model_id uuid REFERENCES model (id),
  name varchar NOT NULL,
  label varchar NOT NULL,
  description varchar,
  enabled boolean NOT NULL,
  fixed_value boolean NOT NULL,
  ordering int NOT NULL
);
-- rollback DROP TABLE parameter;

-- changeset liquibaseuser:16
CREATE TABLE parameter_type (
  id uuid PRIMARY KEY,
  name varchar NOT NULL UNIQUE
);
-- rollback DROP TABLE parameter_type;

-- changeset liquibaseuser:17
CREATE TABLE parameter_distribution_type (
  id uuid PRIMARY KEY,
  name varchar NOT NULL UNIQUE
);
-- rollback DROP TABLE parameter_distribution_type;

-- changeset liquibaseuser:18
CREATE TABLE parameter_type_definition (
  id uuid PRIMARY KEY,
  parameter_id uuid REFERENCES parameter (id) NOT NULL,
  parameter_type_id uuid REFERENCES parameter_type (id) NOT NULL,
  parameter_distribution_type_id uuid REFERENCES parameter_distribution_type (id) NOT NULL,
  ordering int NOT NULL
);
-- rollback DROP TABLE parameter_type_definition;

-- changeset liquibaseuser:19
CREATE TABLE categorical_parameter (
  id uuid PRIMARY KEY REFERENCES parameter_type_definition (id),
  default_value varchar
);
-- rollback DROP TABLE categorical_parameter;

-- changeset liquibaseuser:20
CREATE TABLE categorical_parameter_value (
  id uuid PRIMARY KEY,
  parameter_type_definition_id uuid REFERENCES categorical_parameter (id) NOT NULL,
  value varchar NOT NULL
);
-- rollback DROP TABLE categorical_parameter_value;

-- changeset liquibaseuser:21
CREATE TABLE integer_parameter (
  id uuid PRIMARY KEY REFERENCES parameter_type_definition (id),
  default_value int
);
-- rollback DROP TABLE integer_parameter;

-- changeset liquibaseuser:22
CREATE TABLE integer_parameter_value (
  id uuid PRIMARY KEY,
  parameter_type_definition_id uuid REFERENCES integer_parameter (id) NOT NULL,
  lower int NOT NULL,
  upper int NOT NULL
);
-- rollback DROP TABLE integer_parameter_value;

-- changeset liquibaseuser:23
CREATE TABLE float_parameter (
  id uuid PRIMARY KEY REFERENCES parameter_type_definition (id),
  default_value double precision
);
-- rollback DROP TABLE float_parameter;

-- changeset liquibaseuser:24
CREATE TABLE float_parameter_range (
  id uuid PRIMARY KEY,
  parameter_type_definition_id uuid REFERENCES float_parameter (id) NOT NULL,
  is_left_open boolean NOT NULL,
  is_right_open boolean NOT NULL,
  lower double precision NOT NULL,
  upper double precision NOT NULL
);
-- rollback DROP TABLE float_parameter_range;

-- changeset liquibaseuser:25
CREATE TABLE boolean_parameter (
  id uuid PRIMARY KEY REFERENCES parameter_type_definition (id),
  default_value boolean
);
-- rollback DROP TABLE boolean_parameter;
-- changeset liquibaseuser:29
CREATE TABLE model_type_AUD (
  id uuid,
  rev int NOT NULL,
  revtype smallint,
  created_at timestamp,
  updated_at timestamp,
  name varchar,
  PRIMARY KEY (id, rev) 
);
-- rollback DROP TABLE model_type_AUD;

-- changeset liquibaseuser:30
CREATE TABLE ml_task_type_AUD (
  id uuid,
  rev int NOT NULL,
  revtype smallint,
  created_at timestamp,
  updated_at timestamp,
  name varchar,
  PRIMARY KEY (id, rev) 
);
-- rollback DROP TABLE ml_task_type_AUD;

-- changeset liquibaseuser:31
CREATE TABLE model_structure_type_AUD (
  id uuid,
  rev int NOT NULL,
  revtype smallint,
  created_at timestamp,
  updated_at timestamp,
  name varchar,
  PRIMARY KEY (id, rev) 
);
-- rollback DROP TABLE model_structure_type_AUD;

-- changeset liquibaseuser:32
CREATE TABLE model_group_type_AUD (
  id uuid,
  rev int NOT NULL,
  revtype smallint,
  created_at timestamp,
  updated_at timestamp,
  name varchar,
  PRIMARY KEY (id, rev) 
);
-- rollback DROP TABLE model_group_type_AUD;

-- changeset liquibaseuser:33
CREATE TABLE model_ensemble_type_AUD (
  id uuid,
  rev int NOT NULL,
  revtype smallint,
  created_at timestamp,
  updated_at timestamp,
  name varchar,
  PRIMARY KEY (id, rev) 
);
-- rollback DROP TABLE model_ensemble_type_AUD;

-- changeset liquibaseuser:34
CREATE TABLE model_family_type_AUD (
  id uuid,
  rev int NOT NULL,
  revtype smallint,
  created_at timestamp,
  updated_at timestamp,
  name varchar,
  PRIMARY KEY (id, rev) 
);
-- rollback DROP TABLE model_family_type_AUD;

-- changeset liquibaseuser:35
CREATE TABLE model_AUD (
  id uuid,
  rev int NOT NULL,
  revtype smallint,
  created_at timestamp,
  updated_at timestamp,
  ml_task_id uuid,
  name varchar,
  display_name varchar,
  structure_id uuid,
  description varchar,
  advantages text[],
  disadvantages text[],
  enabled boolean,
  ensemble_type_id uuid,
  family_type_id uuid,
  decision_tree boolean,
  dependency_group_id uuid,
  PRIMARY KEY (id, rev) 
);
-- rollback DROP TABLE model_AUD;

-- changeset liquibaseuser:37
CREATE TABLE metric_AUD (
  id uuid,
  rev int NOT NULL,
  revtype smallint,
  created_at timestamp,
  updated_at timestamp,
  name varchar,
  PRIMARY KEY (id, rev) 
);
-- rollback DROP TABLE metric_AUD;-- changeset liquibaseuser:38

-- changeset liquibaseuser:39
CREATE TABLE parameter_AUD (
  id uuid,
  rev int NOT NULL,
  revtype smallint,
  created_at timestamp,
  updated_at timestamp,
  model_id uuid,
  name varchar,
  label varchar,
  description varchar,
  enabled boolean,
  fixed_value boolean,
  ordering integer,
  PRIMARY KEY (id, rev) 
);
-- rollback DROP TABLE parameter_AUD;

-- changeset liquibaseuser:40
CREATE TABLE parameter_type_AUD (
  id uuid,
  rev int NOT NULL,
  revtype smallint,
  created_at timestamp,
  updated_at timestamp,
  name varchar,
  PRIMARY KEY (id, rev) 
);
-- rollback DROP TABLE parameter_type_AUD;

-- changeset liquibaseuser:41
CREATE TABLE parameter_distribution_type_AUD (
  id uuid,
  rev int NOT NULL,
  revtype smallint,
  created_at timestamp,
  updated_at timestamp,
  name varchar,
  PRIMARY KEY (id, rev) 
);
-- rollback DROP TABLE parameter_distribution_type_AUD;

-- changeset liquibaseuser:42
CREATE TABLE parameter_type_definition_AUD (
  id uuid,
  rev int NOT NULL,
  revtype smallint,
  created_at timestamp,
  updated_at timestamp,
  parameter_id uuid,
  parameter_type_id uuid,
  parameter_distribution_type_id uuid,
  ordering int,
  PRIMARY KEY (id, rev) 
);
-- rollback DROP TABLE parameter_type_definition_AUD;

-- changeset liquibaseuser:43
CREATE TABLE categorical_parameter_AUD (
  id uuid,
  rev int NOT NULL,
  revtype smallint,
  created_at timestamp,
  updated_at timestamp,
  default_value varchar,
  PRIMARY KEY (id, rev)
);
-- rollback DROP TABLE categorical_parameter_AUD;

-- changeset liquibaseuser:44
CREATE TABLE categorical_parameter_value_AUD (
  id uuid,
  parameter_type_definition_id uuid,
  value varchar,
  rev int NOT NULL,
  revtype smallint,
  created_at timestamp,
  updated_at timestamp,
  PRIMARY KEY (id, rev) 
);
-- rollback DROP TABLE categorical_parameter_value_AUD;

-- changeset liquibaseuser:45
CREATE TABLE boolean_parameter_AUD (
  id uuid,
  rev int NOT NULL,
  revtype smallint,
  created_at timestamp,
  updated_at timestamp,
  default_value boolean,
  PRIMARY KEY (id, rev) 
);
-- rollback DROP TABLE boolean_parameter_AUD;

-- changeset liquibaseuser:46
CREATE TABLE float_parameter_AUD (
  id uuid,
  rev int NOT NULL,
  revtype smallint,
  created_at timestamp,
  updated_at timestamp,
  default_value double precision,
  PRIMARY KEY (id, rev) 
);
-- rollback DROP TABLE float_parameter_AUD;

-- changeset liquibaseuser:47
CREATE TABLE integer_parameter_AUD (
  id uuid,
  rev int NOT NULL,
  revtype smallint,
  created_at timestamp,
  updated_at timestamp,
  default_value int,
  PRIMARY KEY (id, rev) 
);
-- rollback DROP TABLE integer_parameter_AUD;

-- changeset liquibaseuser:48
CREATE TABLE float_parameter_range_AUD (
  id uuid,
  parameter_type_definition_id uuid,
  is_left_open boolean,
  is_right_open boolean,
  lower double precision,
  upper double precision,
  rev int NOT NULL,
  revtype smallint,
  created_at timestamp,
  updated_at timestamp,
  PRIMARY KEY (id, rev) 
);
-- rollback DROP TABLE float_parameter_range_AUD;

-- changeset liquibaseuser:49
CREATE TABLE integer_parameter_value_AUD (
  id uuid,
  parameter_type_definition_id uuid,
  lower int,
  upper int,
  rev int NOT NULL,
  revtype smallint,
  created_at timestamp,
  updated_at timestamp,
  PRIMARY KEY (id, rev) 
);
-- rollback DROP TABLE integer_parameter_value_AUD;

-- changeset liquibaseuser:50
CREATE TABLE revinfo (
  rev SERIAL PRIMARY KEY,
  revtstmp timestamp
);
-- rollback DROP TABLE revinfo;
-- changeset liquibaseuser:51
CREATE TABLE rel_model__model_type_AUD (
  model_id uuid,
  model_type_id uuid,
  rev int NOT NULL,
  revtype smallint,
  created_at timestamp,
  updated_at timestamp,
  PRIMARY KEY (model_id,model_type_id,rev) 
);
 -- rollback DROP TABLE rel_model__model_type;

-- changeset liquibaseuser:52
CREATE TABLE rel_model__groups_AUD (
  model_id uuid,
  group_id uuid,
  rev int NOT NULL,
  revtype smallint,
  created_at timestamp,
  updated_at timestamp,
  PRIMARY KEY (model_id,group_id,rev) 
);
-- rollback DROP TABLE rel_model__groups;

-- changeset liquibaseuser:53
CREATE TABLE rel_model__incompatible_metrics_AUD (
  model_id uuid,
  metric_id uuid,
  rev int NOT NULL,
  revtype smallint,
  created_at timestamp,
  updated_at timestamp,
  PRIMARY KEY (model_id,metric_id,rev)
);
-- rollback DROP TABLE rel_model__incompatible_metrics;

-- changeset liquibaseuser:54
CREATE TABLE dependency_group_type_AUD (
  id uuid,
  name varchar,
  rev int NOT NULL,
  revtype smallint,
  created_at timestamp,
  updated_at timestamp,
  PRIMARY KEY (id, rev) 
);
-- rollback DROP TABLE dependency_group_type_AUD;

-- changeset liquibaseuser:55
CREATE TABLE dependency_type_AUD (
  id uuid,
  rev int NOT NULL,
  revtype smallint,
  created_at timestamp,
  updated_at timestamp,
  name varchar,
  dependency_group_id uuid,
  PRIMARY KEY (id, rev) 
);
-- rollback DROP TABLE dependency_type_AUD;

-- Insert Parameter Distribution Types
INSERT INTO parameter_distribution_type(id,name) VALUES ('a71f6eaa-86e1-46c1-8233-c479871f07f6','uniform');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO parameter_distribution_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from parameter_distribution_type where name='uniform'), 'uniform', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');

-- Insert Model Ensemble / Family Types
INSERT INTO model_ensemble_type(id,name) VALUES ('a71f6eaa-86e1-46c1-8233-c479871f07f7','other');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO model_ensemble_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from model_ensemble_type where name='other'), 'other', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO model_ensemble_type(id,name) VALUES ('a71f6eaa-86e1-46c1-8233-c479871f07f1','forest');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO model_ensemble_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from model_ensemble_type where name='forest'), 'forest', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO model_ensemble_type(id,name) VALUES ('a71f6eaa-86e1-46c1-8233-c479871f07f0','none');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO model_ensemble_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from model_ensemble_type where name='none'), 'none', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO model_family_type(id,name) VALUES ('a71f6eaa-86e1-46c1-8233-c479871f07f2','other');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO model_family_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from model_family_type where name='other'), 'other', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO model_family_type(id,name) VALUES ('a71f6eaa-86e1-46c1-8233-c479871f07f3','linear');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO model_family_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from model_family_type where name='linear'), 'linear', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO model_family_type(id,name) VALUES ('a71f6eaa-86e1-46c1-8233-c479871f07f4','tree');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO model_family_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from model_family_type where name='tree'), 'tree', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');

-- Insert Model Group Types
INSERT INTO model_group_type(id,name) VALUES ('b71f6eaa-86e1-46c1-8233-c479871f07f1','fast');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO model_group_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from model_group_type where name='fast'), 'fast', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO model_group_type(id,name) VALUES ('b71f6eaa-86e1-46c1-8233-c479871f07f2','explainable');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO model_group_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from model_group_type where name='explainable'), 'explainable', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO model_group_type(id,name) VALUES ('b71f6eaa-86e1-46c1-8233-c479871f07f3','advanced');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO model_group_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from model_group_type where name='advanced'), 'advanced', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');

-- Insert Metrics
INSERT INTO metric(id,name) VALUES ('c71f6eaa-86e1-46c1-8233-c479871f07f1','classification-log-loss');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO metric_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from metric where name='classification-log-loss'), 'classification-log-loss', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO metric(id,name) VALUES ('c71f6eaa-86e1-46c1-8233-c479871f07f2','classification-roc');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO metric_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from metric where name='classification-roc'), 'classification-roc', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');

-- Insert Ml Task Types
INSERT INTO ml_task_type(id,name) VALUES ('b71f6eaa-86e1-46c1-8233-c479871f07f3','forecasting');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO ml_task_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from ml_task_type where name='forecasting'), 'forecasting', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO ml_task_type(id,name) VALUES ('b71f6eaa-86e1-46c1-8233-c479871f07f4','regression');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO ml_task_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from ml_task_type where name='regression'), 'regression', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO ml_task_type(id,name) VALUES ('b71f6eaa-86e1-46c1-8233-c479871f07f5','classification');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO ml_task_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from ml_task_type where name='classification'), 'classification', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');

-- Insert Model Structure Types
INSERT INTO model_structure_type(id,name) VALUES ('c71f6eaa-86e1-46c1-8233-c479871f07f3','dynamic');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO model_structure_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from model_structure_type where name='dynamic'), 'dynamic', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO model_structure_type(id,name) VALUES ('c71f6eaa-86e1-46c1-8233-c479871f07f4','base');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO model_structure_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from model_structure_type where name='base'), 'base', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');

-- Insert Model Types
INSERT INTO model_type(id,name) VALUES ('c81f6eaa-86e1-46c1-8233-c479871f07f1','Linear Model');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO model_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from model_type where name='Linear Model'), 'Linear Model', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO model_type(id,name) VALUES ('c81f6eaa-86e1-46c1-8233-c479871f07f2','Kernel Model');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO model_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from model_type where name='Kernel Model'), 'Kernel Model', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO model_type(id,name) VALUES ('c81f6eaa-86e1-46c1-8233-c479871f07f3','Baseline Model');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO model_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from model_type where name='Baseline Model'), 'Baseline Model', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO model_type(id,name) VALUES ('c81f6eaa-86e1-46c1-8233-c479871f07f4','Tree-Based Model');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO model_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from model_type where name='Tree-Based Model'), 'Tree-Based Model', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO model_type(id,name) VALUES ('c81f6eaa-86e1-46c1-8233-c479871f07f5','Nearest Neighbours Model');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO model_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from model_type where name='Nearest Neighbours Model'), 'Nearest Neighbours Model', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO model_type(id,name) VALUES ('c81f6eaa-86e1-46c1-8233-c479871f07f6','Ensemble Model');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO model_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from model_type where name='Ensemble Model'), 'Ensemble Model', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO model_type(id,name) VALUES ('c81f6eaa-86e1-46c1-8233-c479871f07f7','Bayesian Model');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO model_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from model_type where name='Bayesian Model'), 'Bayesian Model', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO model_type(id,name) VALUES ('c81f6eaa-86e1-46c1-8233-c479871f07f8','Deep Learning Model');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO model_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from model_type where name='Deep Learning Model'), 'Deep Learning Model', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO model_type(id,name) VALUES ('c81f6eaa-86e1-46c1-8233-c479871f07f9','Support Vector Machine Model');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO model_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from model_type where name='Support Vector Machine Model'), 'Support Vector Machine Model', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO model_type(id,name) VALUES ('c91f6eaa-86e1-46c1-8233-c479871f07f1','Gradient Model');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO model_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from model_type where name='Gradient Model'), 'Gradient Model', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO model_type(id,name) VALUES ('c91f6eaa-86e1-46c1-8233-c479871f07f0','Statistical Model');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO model_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from model_type where name='Statistical Model'), 'Statistical Model', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');

-- Insert Parameter Types
INSERT INTO parameter_type(id,name) VALUES ('c81f6eaa-86e1-46c1-8233-c479871f07f2','categorical');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO parameter_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from parameter_type where name='categorical'), 'categorical', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO parameter_type(id,name) VALUES ('c81f6eaa-86e1-46c1-8233-c479871f07f3','float');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO parameter_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from parameter_type where name='float'), 'float', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO parameter_type(id,name) VALUES ('c81f6eaa-86e1-46c1-8233-c479871f07f4','integer');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO parameter_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from parameter_type where name='integer'), 'integer', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO parameter_type(id,name) VALUES ('c81f6eaa-86e1-46c1-8233-c479871f07f5','boolean');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO parameter_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from parameter_type where name='boolean'), 'boolean', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');

--Insert Dependency Group Types
INSERT INTO dependency_group_type(id,name) VALUES ('c11f6eaa-86e1-46c1-8233-c479871f07f1','lightning');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_group_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_group_type where name='lightning'), 'lightning', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO dependency_group_type(id,name) VALUES ('c11f6eaa-86e1-46c1-8233-c479871f07f2','catboost');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_group_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_group_type where name='catboost'), 'catboost', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO dependency_group_type(id,name) VALUES ('c11f6eaa-86e1-46c1-8233-c479871f07f3','sktime');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_group_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_group_type where name='sktime'), 'sktime', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO dependency_group_type(id,name) VALUES ('c11f6eaa-86e1-46c1-8233-c479871f07f4','statsforecast');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_group_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_group_type where name='statsforecast'), 'statsforecast', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO dependency_group_type(id,name) VALUES ('c11f6eaa-86e1-46c1-8233-c479871f07f5','darts');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_group_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_group_type where name='darts'), 'darts', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO dependency_group_type(id,name) VALUES ('c11f6eaa-86e1-46c1-8233-c479871f07f6','huggingface');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_group_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_group_type where name='huggingface'), 'huggingface', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO dependency_group_type(id,name) VALUES ('c11f6eaa-86e1-46c1-8233-c479871f07f7','intelex');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_group_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_group_type where name='intelex'), 'intelex', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO dependency_group_type(id,name) VALUES ('c11f6eaa-86e1-46c1-8233-c479871f07f8','lightgbm');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_group_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_group_type where name='lightgbm'), 'lightgbm', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO dependency_group_type(id,name) VALUES ('c11f6eaa-86e1-46c1-8233-c479871f07f9','xgboost');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_group_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_group_type where name='xgboost'), 'xgboost', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO dependency_group_type(id,name) VALUES ('c12f6eaa-86e1-46c1-8233-c479871f07f1','base');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_group_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_group_type where name='base'), 'base', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO dependency_group_type(id,name) VALUES ('c12f6eaa-86e1-46c1-8233-c479871f07f2','stacking');
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_group_type_AUD(id, name, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_group_type where name='stacking'), 'stacking', (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');

--Insert Dependency Types
INSERT INTO dependency_type(id,name,dependency_group_id) VALUES ('c13f6eaa-86e1-46c1-8233-c479871f07f0','catboost',(select id from dependency_group_type where name='catboost'));
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_type_AUD(id, name, dependency_group_id, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_type where name='catboost'), 'catboost', (select id from dependency_group_type where name='catboost'), (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO dependency_type(id,name,dependency_group_id) VALUES ('c13f6eaa-86e1-46c1-8233-c479871f07f1','darts',(select id from dependency_group_type where name='darts'));
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_type_AUD(id, name, dependency_group_id, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_type where name='darts'), 'darts', (select id from dependency_group_type where name='darts'), (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO dependency_type(id,name,dependency_group_id) VALUES ('c13f6eaa-86e1-46c1-8233-c479871f07f2','tensorboardx',(select id from dependency_group_type where name='darts'));
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_type_AUD(id, name, dependency_group_id, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_type where name='tensorboardx'), 'tensorboardx', (select id from dependency_group_type where name='darts'), (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO dependency_type(id,name,dependency_group_id) VALUES ('c13f6eaa-86e1-46c1-8233-c479871f07f3','holidays',(select id from dependency_group_type where name='darts'));
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_type_AUD(id, name, dependency_group_id, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_type where name='holidays'), 'holidays', (select id from dependency_group_type where name='darts'), (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO dependency_type(id,name,dependency_group_id) VALUES ('c13f6eaa-86e1-46c1-8233-c479871f07f4','ipython',(select id from dependency_group_type where name='darts'));
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_type_AUD(id, name, dependency_group_id, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_type where name='ipython'), 'ipython', (select id from dependency_group_type where name='darts'), (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO dependency_type(id,name,dependency_group_id) VALUES ('c13f6eaa-86e1-46c1-8233-c479871f07f5','transformers',(select id from dependency_group_type where name='huggingface'));
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_type_AUD(id, name, dependency_group_id, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_type where name='transformers'), 'transformers', (select id from dependency_group_type where name='huggingface'), (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO dependency_type(id,name,dependency_group_id) VALUES ('c13f6eaa-86e1-46c1-8233-c479871f07f6','datasets',(select id from dependency_group_type where name='huggingface'));
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_type_AUD(id, name, dependency_group_id, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_type where name='datasets'), 'datasets', (select id from dependency_group_type where name='huggingface'), (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO dependency_type(id,name,dependency_group_id) VALUES ('c13f6eaa-86e1-46c1-8233-c479871f07f7','scikit-learn-intelex',(select id from dependency_group_type where name='intelex'));
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_type_AUD(id, name, dependency_group_id, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_type where name='scikit-learn-intelex'), 'scikit-learn-intelex', (select id from dependency_group_type where name='intelex'), (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO dependency_type(id,name,dependency_group_id) VALUES ('c13f6eaa-86e1-46c1-8233-c479871f07f8','lightgbm',(select id from dependency_group_type where name='lightgbm'));
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_type_AUD(id, name, dependency_group_id, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_type where name='lightgbm'), 'lightgbm', (select id from dependency_group_type where name='lightgbm'), (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO dependency_type(id,name,dependency_group_id) VALUES ('c13f6eaa-86e1-46c1-8233-c479871f07f9','sklearn-contrib-lightning',(select id from dependency_group_type where name='lightning'));
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_type_AUD(id, name, dependency_group_id, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_type where name='sklearn-contrib-lightning'), 'sklearn-contrib-lightning', (select id from dependency_group_type where name='lightning'), (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO dependency_type(id,name,dependency_group_id) VALUES ('c14f6eaa-86e1-46c1-8233-c479871f07f0','mrmr-selection',(select id from dependency_group_type where name='stacking'));
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_type_AUD(id, name, dependency_group_id, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_type where name='mrmr-selection'), 'mrmr-selection', (select id from dependency_group_type where name='stacking'), (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO dependency_type(id,name,dependency_group_id) VALUES ('c14f6eaa-86e1-46c1-8233-c479871f07f1','polars',(select id from dependency_group_type where name='stacking'));
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_type_AUD(id, name, dependency_group_id, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_type where name='polars'), 'polars', (select id from dependency_group_type where name='stacking'), (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO dependency_type(id,name,dependency_group_id) VALUES ('c14f6eaa-86e1-46c1-8233-c479871f07f2','statsforecast',(select id from dependency_group_type where name='statsforecast'));
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_type_AUD(id, name, dependency_group_id, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_type where name='statsforecast'), 'statsforecast', (select id from dependency_group_type where name='statsforecast'), (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO dependency_type(id,name,dependency_group_id) VALUES ('c14f6eaa-86e1-46c1-8233-c479871f07f3','sktime',(select id from dependency_group_type where name='sktime'));
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_type_AUD(id, name, dependency_group_id, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_type where name='sktime'), 'sktime', (select id from dependency_group_type where name='sktime'), (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO dependency_type(id,name,dependency_group_id) VALUES ('c14f6eaa-86e1-46c1-8233-c479871f07f4','pmdarima',(select id from dependency_group_type where name='sktime'));
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_type_AUD(id, name, dependency_group_id, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_type where name='pmdarima'), 'pmdarima', (select id from dependency_group_type where name='sktime'), (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
INSERT INTO dependency_type(id,name,dependency_group_id) VALUES ('c14f6eaa-86e1-46c1-8233-c479871f07f5','xgboost',(select id from dependency_group_type where name='xgboost'));
INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '2024-04-17 16:31:23');
INSERT INTO dependency_type_AUD(id, name, dependency_group_id, rev, revtype, created_at, updated_at) VALUES ((select id from dependency_type where name='xgboost'), 'xgboost', (select id from dependency_group_type where name='xgboost'), (select max(rev) from revinfo),0,'2024-04-17 16:31:23','2024-04-17 16:31:23');
