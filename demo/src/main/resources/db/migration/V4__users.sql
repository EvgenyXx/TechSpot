CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone_number VARCHAR(12) NOT NULL UNIQUE,
    hash_password VARCHAR(60) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT true,
    cart_id BIGINT UNIQUE,
    password_reset_code VARCHAR(255) UNIQUE,
    reset_code_expiry TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_users_cart
        FOREIGN KEY (cart_id) REFERENCES cart (id)
);