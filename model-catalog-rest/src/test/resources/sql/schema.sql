CREATE TABLE metrics (
    id UUID PRIMARY KEY,
    metric VARCHAR(100) NOT NULL
);


-- Inserting sample data into the "metrics" table
INSERT INTO metrics (id, metric)
VALUES
    ('1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d23', 'Metric1'),
    ('2b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d24', 'Metric2'),
    ('3b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d25', 'Metric3'),
    ('4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d26', 'Metric4');