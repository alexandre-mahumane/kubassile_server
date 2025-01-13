CREATE TABLE order_status (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(25)
);

INSERT INTO order_status(status) VALUES ("READY"), ("DELIVERED"), ("PENDING")