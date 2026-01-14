CREATE TABLE discounts (
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(20) NOT NULL,
    percentage NUMERIC(10,2),
    fixed_amount NUMERIC(10,2),
    starts_at TIMESTAMP,
    ends_at TIMESTAMP,
    product_id BIGINT,
    category_id BIGINT,
    min_quantity INTEGER,
    active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_discounts_product
        FOREIGN KEY (product_id) REFERENCES products (id),
    CONSTRAINT fk_discounts_category
        FOREIGN KEY (category_id) REFERENCES categories (id)
);