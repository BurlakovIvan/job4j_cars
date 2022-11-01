CREATE TABLE if not exists auto_post (
   id SERIAL PRIMARY KEY,
   text VARCHAR,
   created timestamp,
   photo bytea,
   auto_user_id INT REFERENCES auto_user(id)
);