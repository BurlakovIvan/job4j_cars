CREATE TABLE if not exists auto_post (
   id SERIAL PRIMARY KEY,
   text VARCHAR,
   created timestamp,
   auto_user_id int REFERENCES auto_user(id)
);