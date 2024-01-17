CREATE TABLE metric (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE ml_task_type (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE model_group_type (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE model_type (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Inserting sample data into the "metrics" table
INSERT INTO metric (id, name)
VALUES
    ('1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d23', 'Metric1'),
    ('2b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d24', 'Metric2'),
    ('3b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d25', 'Metric3'),
    ('4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d26', 'Metric4');

INSERT INTO ml_task_type (id, name)
VALUES
    ('1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27', 'mltask1'),
    ('2b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d28', 'mltask2'),
    ('3b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d29', 'mltask3'),
    ('4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21', 'mltask4');

INSERT INTO model_group_type (id, name)
VALUES
    ('1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27', 'modelgroup1'),
    ('2b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d28', 'modelgroup2'),
    ('3b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d29', 'modelgroup3'),
    ('4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21', 'modelgroup4');

INSERT INTO model_type (id, name)
VALUES
    ('1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27', 'modeltype1'),
    ('2b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d28', 'modeltype2'),
    ('3b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d29', 'modeltype3'),
    ('4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21', 'modeltype4');

