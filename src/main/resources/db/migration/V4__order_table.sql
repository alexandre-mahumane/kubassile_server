CREATE TABLE orders(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    client_id BIGINT NOT NULL,
    order_status_id BIGINT NOT NULL,
    order_type VARCHAR(50) NOT NULL,
    description VARCHAR(255),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (client_id) REFERENCES client(id),
    FOREIGN KEY (order_status_id) REFERENCES order_status(id)
)