CREATE TABLE user(
    id BIGINT(20) AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE user_address(
    id BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT(20) AUTO_INCREMENT NOT NULL,
    cep VARCHAR(8) NOT NULL,
    street VARCHAR(32) NOT NULL,
    neighborhood VARCHAR(20),
    `number` VARCHAR(20) NOT NULL,
    `complements` VARCHAR(20),
    city VARCHAR(20) NOT NULL,
    state VARCHAR(2) NOT NULL,
    INDEX (cep),
    INDEX (user_id),
);