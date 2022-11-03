CREATE TABLE IF NOT EXISTS auto_post (
   id SERIAL PRIMARY KEY,
   text VARCHAR,
   created timestamp,
   photo bytea,
   auto_user_id INT REFERENCES auto_user(id),
   car_id INT REFERENCES cars(id)
);