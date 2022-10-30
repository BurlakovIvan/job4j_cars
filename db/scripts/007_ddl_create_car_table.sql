CREATE TABLE IF NOT EXISTS cars(
    id SERIAL PRIMARY KEY,
    name VARCHAR,
    engine_id INT NOT NULL UNIQUE REFERENCES engine(id),
    driver_id INT NOT NULL REFERENCES drivers(id)
);