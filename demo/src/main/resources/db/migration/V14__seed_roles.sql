INSERT INTO roles (name, created_at, updated_at) VALUES 
('ROLE_ADMIN', NOW(), NOW()),
('ROLE_USER', NOW(), NOW()),
('ROLE_MODERATOR', NOW(), NOW()),
('ROLE_SELLER',NOW(),NOW())
ON CONFLICT (name) DO NOTHING;
