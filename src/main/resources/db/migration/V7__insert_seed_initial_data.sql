-- Seed initial Tenant
INSERT INTO tenants (id, name, cnpj, subdomain, settings, created_at, updated_at)
VALUES (
    'd290f1ee-6c54-4b01-90e6-d701748f0851', 
    'Clínica Modelo', 
    '12345678000199', 
    'clinica-modelo', 
    '{"theme": "light", "timezone": "America/Sao_Paulo"}', 
    NOW(), 
    NOW()
) ON CONFLICT DO NOTHING;

-- Seed initial Admin User (password: password123)
-- Hash generated using BCrypt standard for 'password123'
INSERT INTO users (id, tenant_id, name, email, password_hash, role, status, created_at, updated_at)
VALUES (
    'f47ac10b-58cc-4372-a567-0e02b2c3d479', 
    'd290f1ee-6c54-4b01-90e6-d701748f0851', 
    'Admin Raac', 
    'admin@raacsystem.com.br', 
    '$2a$10$Lqz3mvpEsh8aXofvSxedY.jeHTvqbcNeiolehuWjyqduwfZW.13Mu', 
    'ADMIN', 
    'ACTIVE', 
    NOW(), 
    NOW()
) ON CONFLICT (id) DO UPDATE SET password_hash = EXCLUDED.password_hash;



