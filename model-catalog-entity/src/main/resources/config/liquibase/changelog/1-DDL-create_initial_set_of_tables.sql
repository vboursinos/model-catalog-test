-- changeset liquibaseuser:1
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
-- rollback -- You may not be able to drop the extension, so no rollback specified.

-- changeset liquibaseuser:2
CREATE OR REPLACE FUNCTION generate_uuid()
RETURNS uuid AS $$
BEGIN
  RETURN gen_random_uuid();
END;
$$ LANGUAGE plpgsql;
-- rollback -- You may not be able to drop the function, so no rollback specified.

-- changeset liquibaseuser:3
CREATE TABLE model_type (
  id uuid DEFAULT generate_uuid() PRIMARY KEY,
  name varchar NOT NULL
);
-- rollback DROP TABLE model_type;

-- changeset liquibaseuser:4
CREATE TABLE ml_task_type (
  id uuid DEFAULT generate_uuid() PRIMARY KEY,
  name varchar NOT NULL UNIQUE
);
-- rollback DROP TABLE ml_task_type;

-- changeset liquibaseuser:5
CREATE TABLE model_structure_type (
  id uuid DEFAULT generate_uuid() PRIMARY KEY,
  name varchar NOT NULL UNIQUE
);
-- rollback DROP TABLE model_structure_type;

-- changeset liquibaseuser:6
CREATE TABLE model_group_type (
  id uuid DEFAULT generate_uuid() PRIMARY KEY,
  name varchar NOT NULL
);
-- rollback DROP TABLE model_group_type;

-- changeset liquibaseuser:7
CREATE TABLE model_ensemble_type (
  id uuid DEFAULT generate_uuid() PRIMARY KEY,
  name varchar NOT NULL
);
-- rollback DROP TABLE model_ensemble_type;

-- changeset liquibaseuser:8
CREATE TABLE model_family_type (
  id uuid DEFAULT generate_uuid() PRIMARY KEY,
  name varchar NOT NULL
);
-- rollback DROP TABLE model_family_type;

-- changeset liquibaseuser:9
CREATE TABLE dependency_group_type (
  id uuid DEFAULT generate_uuid() PRIMARY KEY,
  name varchar NOT NULL
);
-- rollback DROP TABLE dependency_group_type;

-- changeset liquibaseuser:10
CREATE TABLE dependency_type (
  id uuid DEFAULT generate_uuid() PRIMARY KEY,
  name varchar NOT NULL,
  dependency_group_id uuid REFERENCES dependency_group_type (id) NOT NULL
);
-- rollback DROP TABLE dependency_type;

-- changeset liquibaseuser:11
CREATE TABLE model (
  id uuid DEFAULT generate_uuid() PRIMARY KEY,
  model_type_id uuid REFERENCES model_type (id),
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

-- changeset liquibaseuser:12
CREATE TABLE rel_model__groups (
  model_id uuid REFERENCES model (id),
  group_id uuid REFERENCES model_group_type (id)
);
-- rollback DROP TABLE rel_model__groups;

-- changeset liquibaseuser:13
CREATE TABLE metric (
  id uuid DEFAULT generate_uuid() PRIMARY KEY,
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
  id uuid DEFAULT generate_uuid() PRIMARY KEY,
  model_id uuid REFERENCES model (id),
  name varchar NOT NULL,
  label varchar NOT NULL,
  description varchar,
  enabled boolean NOT NULL,
  fixed_value boolean NOT NULL,
  ordering integer NOT NULL
);
-- rollback DROP TABLE parameter;

-- changeset liquibaseuser:16
CREATE TABLE parameter_type (
  id uuid DEFAULT generate_uuid() PRIMARY KEY,
  name varchar NOT NULL UNIQUE
);
-- rollback DROP TABLE parameter_type;

-- changeset liquibaseuser:17
CREATE TABLE parameter_distribution_type (
  id uuid DEFAULT generate_uuid() PRIMARY KEY,
  name varchar NOT NULL UNIQUE
);
-- rollback DROP TABLE parameter_distribution_type;

-- changeset liquibaseuser:18
CREATE TABLE parameter_type_definition (
  id uuid DEFAULT generate_uuid() PRIMARY KEY,
  parameter_id uuid REFERENCES parameter (id) NOT NULL,
  parameter_type_id uuid REFERENCES parameter_type (id) NOT NULL,
  parameter_distribution_type_id uuid REFERENCES parameter_distribution_type (id) NOT NULL,
  ordering integer NOT NULL
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
  id uuid DEFAULT generate_uuid() PRIMARY KEY,
  parameter_type_definition_id uuid REFERENCES categorical_parameter (id) NOT NULL,
  value varchar NOT NULL
);
-- rollback DROP TABLE categorical_parameter_value;

-- changeset liquibaseuser:21
CREATE TABLE integer_parameter (
  id uuid PRIMARY KEY REFERENCES parameter_type_definition (id),
  default_value integer
);
-- rollback DROP TABLE integer_parameter;

-- changeset liquibaseuser:22
CREATE TABLE integer_parameter_value (
  id uuid DEFAULT generate_uuid() PRIMARY KEY,
  parameter_type_definition_id uuid REFERENCES integer_parameter (id) NOT NULL,
  lower integer NOT NULL,
  upper integer NOT NULL
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
  id uuid DEFAULT generate_uuid() PRIMARY KEY,
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

-- changeset liquibaseuser:26
CREATE TABLE constraint_edge (
  id uuid DEFAULT generate_uuid() PRIMARY KEY,
  source_parameter_id uuid REFERENCES parameter (id) NOT NULL,
  target_parameter_id uuid REFERENCES parameter (id) NOT NULL
);
-- rollback DROP TABLE constraint_edge;

-- changeset liquibaseuser:27
CREATE TABLE mapping (
  id uuid DEFAULT generate_uuid() PRIMARY KEY,
  constraint_id uuid REFERENCES constraint_edge (id) NOT NULL,
  parameter_type_definition_id uuid REFERENCES parameter_type_definition (id) NOT NULL
);
-- rollback DROP TABLE mapping;

-- changeset liquibaseuser:28
CREATE TABLE float_constraint_range (
  id uuid DEFAULT generate_uuid() PRIMARY KEY,
  mapping_id uuid REFERENCES mapping (id) NOT NULL,
  is_left_open boolean NOT NULL,
  is_right_open boolean NOT NULL,
  lower double precision NOT NULL,
  upper double precision NOT NULL
);
-- rollback DROP TABLE float_constraint_range;

-- changeset liquibaseuser:29
CREATE TABLE integer_constraint_range (
  id uuid DEFAULT generate_uuid() PRIMARY KEY,
  mapping_id uuid REFERENCES mapping (id) NOT NULL,
  lower integer NOT NULL,
  upper integer NOT NULL
);
-- rollback DROP TABLE integer_constraint_range;

-- changeset liquibaseuser:30
CREATE TABLE categorical_constraint_value (
  id uuid DEFAULT generate_uuid() PRIMARY KEY,
  mapping_id uuid REFERENCES mapping (id) NOT NULL,
  value varchar NOT NULL
);
-- rollback DROP TABLE categorical_constraint
