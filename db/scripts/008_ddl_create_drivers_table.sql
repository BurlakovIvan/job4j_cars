CREATE TABLE IF NOT EXISTS drivers (
    id SERIAL PRIMARY KEY,
    name VARCHAR,
    auto_user_id INT NOT NULL REFERENCES auto_user(id)
)