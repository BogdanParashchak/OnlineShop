docker run --name online-shop -e POSTGRES_DB=online_shop -e POSTGRES_USER=app -e POSTGRES_PASSWORD=app -p 5432:5432 -d postgres:alpine
psql -h localhost -d online_shop -p 5432 -U app
CREATE TABLE products (id SERIAL PRIMARY KEY, name VARCHAR(50) NOT NULL, price NUMERIC(5,2) NOT NULL);
INSERT INTO products (name, price) VALUES ('MILK', 50.89);
INSERT INTO products (name, price) VALUES ('BREAD', 28.56);
INSERT INTO products (name, price) VALUES ('MEAT', 367.58);

\q
docker rm -f online-shop