CREATE TABLE if not exists auto_user (
   id SERIAL PRIMARY KEY,
   login VARCHAR(250),
   password VARCHAR(250)
);