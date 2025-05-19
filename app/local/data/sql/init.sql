CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    status ENUM('ACTIVE', 'INACTIVE') NOT NULL DEFAULT 'ACTIVE',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY idx_users_email (email),
    INDEX idx_users_status (status)
);

CREATE TABLE addresses (
    user_id VARCHAR(36) PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    number VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(50),
    zip_code VARCHAR(20),
    country VARCHAR(2),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE parcels (
    id CHAR(36) PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    sender_id CHAR(36) NOT NULL,
    recipient_id CHAR(36) NOT NULL,
    fun_fact TEXT,
    is_holiday BOOLEAN,
    estimated_delivery_date DATE,
    status ENUM('CREATED', 'IN_TRANSIT', 'DELIVERED', 'CANCELLED') DEFAULT 'CREATED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    delivered_at TIMESTAMP NULL,
    FOREIGN KEY (sender_id) REFERENCES users(id),
    FOREIGN KEY (recipient_id) REFERENCES users(id),
    INDEX idx_sender_id (sender_id),
    INDEX idx_recipient_id (recipient_id),
    INDEX idx_parcels_status (status),
    INDEX idx_parcels_estimated_delivery_date (estimated_delivery_date)
);

CREATE TABLE events (
    id CHAR(36) PRIMARY KEY,
    parcel_id CHAR(36) NOT NULL,
    location VARCHAR(255) NOT NULL,
    description TEXT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (parcel_id) REFERENCES parcels(id),
    INDEX idx_parcel_id_created_at (parcel_id, created_at)
);
