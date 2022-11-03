INSERT INTO auto_user (login, password) VALUES ('Ivanov', 'root');
INSERT INTO auto_user (login, password) VALUES ('Petrov', 'root');
INSERT INTO auto_user (login, password) VALUES ('Sidorov', 'root');

INSERT INTO engine (name) VALUES ('engine1');
INSERT INTO engine (name) VALUES ('engine2');
INSERT INTO engine (name) VALUES ('engine3');

INSERT INTO drivers (name, auto_user_id) SELECT 'driver1', a.id FROM auto_user a;


