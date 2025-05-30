-- BƯỚC 1: Tạo database
CREATE DATABASE HDSGTest;

-- BƯỚC 2: Sử dụng database đó
\c HDSGTest;

-- BƯỚC 3: Tạo bảng users
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(), -- yêu cầu extension
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100),
    avatar BYTEA,
    role VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- BƯỚC 4: Thêm dữ liệu mẫu
INSERT INTO users (id, username, email, password, full_name, avatar, role)
VALUES 
(gen_random_uuid(), 'user1', 'user1@example.com', '$2a$10$VXTE1mfOlDNWoOqKtP4mOeHYtPUdkaVU2lZd3N7bIG66qL8Vds63S', 'Nguyễn Văn A', NULL, 'ROLE_USER'),
(gen_random_uuid(), 'user2', 'user2@example.com', '$2a$10$VXTE1mfOlDNWoOqKtP4mOeHYtPUdkaVU2lZd3N7bIG66qL8Vds63S', 'Trần Thị B', NULL, 'ROLE_USER'),
(gen_random_uuid(), 'admin', 'admin@example.com', '$2a$10$VXTE1mfOlDNWoOqKtP4mOeHYtPUdkaVU2lZd3N7bIG66qL8Vds63S', 'Admin Account', NULL, 'ROLE_ADMIN'),
(gen_random_uuid(), 'user3', 'user3@example.com', '$2a$10$VXTE1mfOlDNWoOqKtP4mOeHYtPUdkaVU2lZd3N7bIG66qL8Vds63S', 'Lê Văn C', NULL, 'ROLE_USER');