CREATE TABLE product (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    price DECIMAL(10, 2),
    active BOOLEAN,
    start_date DATE
);

CREATE TABLE sku (
    id BIGSERIAL PRIMARY KEY,
    sku_code VARCHAR(50),
    description TEXT,
    quantity INT,
    date_added DATE,
    product_id BIGINT REFERENCES product(id)
);