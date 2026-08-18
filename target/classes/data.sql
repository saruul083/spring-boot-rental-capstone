-- Seed data for categories, items, and customers (run on startup if tables are empty)
-- Categories
INSERT INTO categories (name, description) VALUES
    ('Electronics', 'Electronic devices and gadgets'),
    ('Tools', 'Hand and power tools for DIY and professional use'),
    ('Sports', 'Sports equipment and gear'),
    ('Books', 'Books and literature');

-- Items
INSERT INTO items (category_id, name, description, daily_rate, available) VALUES
    (1, 'Laptop', 'High-performance laptop for work and gaming', 25.00, true),
    (1, 'Camera', 'DSLR camera with kit lens', 15.00, true),
    (2, 'Drill', 'Cordless drill driver set', 10.00, true),
    (2, 'Saw', 'Hand saw for woodworking', 8.00, true),
    (3, 'Football', 'Size 5 football for outdoor play', 5.00, true),
    (3, 'Basketball', 'Official size basketball', 7.00, true),
    (4, 'Novel A', 'Best-selling fiction novel', 3.00, true),
    (4, 'Textbook', 'University level textbook', 10.00, true);

-- Customers
INSERT INTO customers (name, email, phone) VALUES
    ('John Doe', 'john@example.com', '555-1234'),
    ('Jane Smith', 'jane@example.com', '555-5678'),
    ('Bob Wilson', 'bob@example.com', '555-9012');