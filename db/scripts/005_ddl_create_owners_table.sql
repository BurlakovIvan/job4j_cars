CREATE TABLE IF NOT EXISTS history_owner (
    id SERIAL PRIMARY KEY,
    car_id INT NOT NULL REFERENCES cars(id),
    driver_id INT NOT NULL REFERENCES drivers(id),
    startAt INT,
    endAt INT
)