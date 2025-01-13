CREATE TABLE payment_status(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(25) UNIQUE
);

INSERT INTO payment_status (status) VALUES ("PENDING"), ("REALIZED")