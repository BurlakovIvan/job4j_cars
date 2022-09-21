CREATE TABLE if not exists auto_post (
   id SERIAL PRIMARY KEY,
   text VARCHAR(250),
   created timestamp,
   auto_user_id INTEGER
);