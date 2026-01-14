CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    price NUMERIC(19,2) NOT NULL,
    quantity INTEGER NOT NULL DEFAULT 0,
    description VARCHAR(1000) NOT NULL,
    category_id BIGINT,
    user_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_products_category
        FOREIGN KEY (category_id) REFERENCES categories (id),
    CONSTRAINT fk_products_user
        FOREIGN KEY (user_id) REFERENCES users (id)
);