
-- Create customers table
CREATE TABLE rm_customer (
    customer_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    customer_name VARCHAR(255) NOT NULL,
    contact_no VARCHAR(10),
    email VARCHAR(255) UNIQUE NOT NULL
);

-- Create an index on email for faster searches
CREATE INDEX idx_customers_email ON rm_customer(email);

-- Create cuisines table
CREATE TABLE rm_cuisine (
    cuisine_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    cuisine_name VARCHAR(100) UNIQUE NOT NULL,
    cuisine_description TEXT,
    cuisine_type VARCHAR(20) NOT NULL
);

-- Create an index on cuisine_name for faster searches
CREATE INDEX idx_cuisine_name ON rm_cuisine(cuisine_name);

-- Create menu table
CREATE TABLE rm_menu (
    item_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    cuisine_id BIGINT,
    portion VARCHAR(10), -- FULL, HALF
    price NUMERIC(10, 2) NOT NULL,
    FOREIGN KEY (cuisine_id) REFERENCES rm_cuisine(cuisine_id) ON DELETE CASCADE
);

-- Create an index on cuisine_id for faster queries
CREATE INDEX idx_menu_cuisine_id ON rm_menu(cuisine_id);

-- Create tables table
CREATE TABLE rm_table (
    table_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    table_number VARCHAR(2) NOT NULL UNIQUE,
    table_status VARCHAR(10) -- AVAILABLE, OCCUPIED
);

-- Create cart table (no customer_id here)
CREATE TABLE rm_cart (
    cart_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    total_item INT NOT NULL DEFAULT 0,
    cart_value NUMERIC(10, 2) NOT NULL DEFAULT 0.00,
    table_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create an index on cart_id for faster queries
CREATE INDEX idx_cart_id ON rm_cart(cart_id);

-- Create cart items table
CREATE TABLE rm_cart_items (
    cart_id BIGINT,
    item_id BIGINT,
    quantity INT NOT NULL DEFAULT 1,
    PRIMARY KEY (cart_id, item_id),
    FOREIGN KEY (cart_id) REFERENCES rm_cart(cart_id) ON DELETE CASCADE,
    FOREIGN KEY (item_id) REFERENCES rm_menu(item_id) ON DELETE CASCADE
);

-- Create indexes on cart_id and item_id for quicker joins
CREATE INDEX idx_cart_items_cart_id ON rm_cart_items(cart_id);
CREATE INDEX idx_cart_items_item_id ON rm_cart_items(item_id);

-- Create orders table
CREATE TABLE rm_order (
    order_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    customer_id BIGINT, -- Customer details are provided when the order is placed
    table_id BIGINT,
    total_item INT NOT NULL DEFAULT 0,
    total_cost NUMERIC(10, 2) NOT NULL DEFAULT 0.00,
    order_status VARCHAR(10) NOT NULL, -- CONFIRMED, PREPARING, SERVED, COMPLETED, CANCELLED
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE,
    FOREIGN KEY (table_id) REFERENCES rm_table(table_id) ON DELETE SET NULL
);

-- Create an index on customer_id and table_id for faster lookups
CREATE INDEX idx_order_customer_id ON rm_order(customer_id);
CREATE INDEX idx_order_table_id ON rm_order(table_id);

-- Create order items table
CREATE TABLE rm_order_items (
    order_id BIGINT,
    item_id BIGINT,
    quantity INT NOT NULL DEFAULT 1,
    PRIMARY KEY (order_id, item_id),
    FOREIGN KEY (order_id) REFERENCES rm_order(order_id) ON DELETE CASCADE,
    FOREIGN KEY (item_id) REFERENCES rm_menu(item_id) ON DELETE CASCADE
);

-- Create indexes on order_id and item_id for faster order processing
CREATE INDEX idx_order_items_order_id ON rm_order_items(order_id);
CREATE INDEX idx_order_items_item_id ON rm_order_items(item_id);

-- Create payments table
CREATE TABLE rm_payment (
    payment_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    order_id BIGINT,
    payment_method VARCHAR(50) NOT NULL, -- e.g., CASH, CREDIT_CARD, DEBIT_CARD, UPI
    payment_status VARCHAR(10), -- PAID, UNPAID
    amount_paid NUMERIC(10, 2) NOT NULL CHECK (amount_paid >= 0),
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES rm_order(order_id) ON DELETE CASCADE
);

-- Create an index on order_id for faster payment retrievals
CREATE INDEX idx_payments_order_id ON rm_payment(order_id);