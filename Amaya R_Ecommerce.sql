USE ecommerce;
-- Customers Table
CREATE TABLE customers (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- Products Table
CREATE TABLE products (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    description TEXT,
    stockQuantity INT NOT NULL
);

-- Cart Table
CREATE TABLE cart (
    cart_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    product_id INT,
    quantity INT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ,
    FOREIGN KEY (product_id) REFERENCES products(product_id) 
);

-- Orders Table
CREATE TABLE orders (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    order_date DATE NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    shipping_address VARCHAR(255) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

-- Order Items Table
CREATE TABLE order_items (
    order_item_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    product_id INT,
    quantity INT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

INSERT INTO customers (name, email, password) VALUES
('Alice Johnson', 'alice@example.com', 'pass123'),
('Bob Smith', 'bob@example.com', 'secure456'),
('Charlie Brown', 'charlie@example.com', 'hello789'),
('David Wilson', 'david@example.com', 'mypassword'),
('Eve Adams', 'eve@example.com', 'evepass'),
('Frank White', 'frank@example.com', 'frankpass'),
('Grace Hall', 'grace@example.com', 'gracepass'),
('Hannah Lee', 'hannah@example.com', 'hannahpass'),
('Ian King', 'ian@example.com', 'ianpass'),
('Jack Taylor', 'jack@example.com', 'jackpass');

SELECT * FROM customers;

INSERT INTO products (name, price, description, stockQuantity) VALUES
('Laptop', 750.00, 'High-performance laptop', 10),
('Smartphone', 500.00, 'Latest model smartphone', 15),
('Tablet', 300.00, '10-inch screen tablet', 20),
('Smartwatch', 200.00, 'Water-resistant smartwatch', 25),
('Headphones', 150.00, 'Noise-canceling headphones', 30),
('Camera', 600.00, '4K resolution camera', 12),
('Gaming Console', 400.00, 'Next-gen gaming console', 8),
('Monitor', 250.00, '27-inch full HD monitor', 14),
('Keyboard', 50.00, 'Mechanical keyboard', 40),
('Mouse', 30.00, 'Wireless mouse', 35);

SELECT * FROM products;

INSERT INTO cart (customer_id, product_id, quantity) VALUES
(1, 2, 1),
(2, 3, 2),
(3, 1, 1),
(4, 5, 3),
(5, 6, 2),
(6, 7, 1),
(7, 8, 2),
(8, 9, 1),
(9, 10, 3),
(10, 4, 1);

select * from cart;

INSERT INTO orders (customer_id, order_date, total_price, shipping_address) VALUES
(1, '2025-03-10', 500.00, '123 Street, NY'),
(2, '2025-03-11', 600.00, '456 Avenue, CA'),
(3, '2025-03-12', 750.00, '789 Boulevard, TX'),
(4, '2025-03-13', 200.00, '101 Road, FL'),
(5, '2025-03-14', 400.00, '222 Lane, IL'),
(6, '2025-03-15', 900.00, '333 Drive, OH'),
(7, '2025-03-16', 250.00, '444 Parkway, GA'),
(8, '2025-03-17', 100.00, '555 Street, WA'),
(9, '2025-03-18', 350.00, '666 Avenue, NV'),
(10, '2025-03-19', 300.00, '777 Road, CO');

select * from orders;

INSERT INTO order_items (order_id, product_id, quantity) VALUES
(1, 2, 1),
(2, 3, 2),
(3, 1, 1),
(4, 5, 3),
(5, 6, 2),
(6, 7, 1),
(7, 8, 2),
(8, 9, 1),
(9, 10, 3),
(10, 4, 1);

select * from order_items;