CREATE TABLE payment_method(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    method VARCHAR(50) UNIQUE
);

INSERT INTO payment_method (method) VALUES ("MONEY"), ("CARD"), ("TRANSFER")