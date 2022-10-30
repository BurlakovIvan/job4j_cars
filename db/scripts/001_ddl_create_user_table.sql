CREATE TABLE if not exists auto_user (
   id SERIAL PRIMARY KEY,
   login VARCHAR,
   password VARCHAR
);