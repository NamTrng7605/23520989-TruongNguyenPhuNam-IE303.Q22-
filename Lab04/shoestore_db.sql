
CREATE DATABASE shoestore_db;
USE shoestore_db;

-- Tạo bảng products
CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    brand VARCHAR(100) NOT NULL,
    image_path VARCHAR(255) NOT NULL
);

-- Nạp dữ liệu vào bảng products
INSERT INTO products (name, price, brand, image_path) VALUES
('4DFWD PULSE SHOES', 160.00, 'Adidas', 'file:img/img1.png'),
('FORUM MID SHOES', 100.00, 'Adidas', 'file:img/img2.png'),
('SUPERNOVA SHOES', 150.00, 'Adidas', 'file:img/img3.png'),
('NMD CITY STOCK 2', 160.00, 'Adidas', 'file:img/img4.png'),
('ULTRA BOOST 21', 120.00, 'Adidas', 'file:img/img5.png'),
('ZX 2K BOOST', 160.00, 'Adidas', 'file:img/img6.png');