SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `category`;
DROP TABLE IF EXISTS `product`;
DROP TABLE IF EXISTS `orders`;
DROP TABLE IF EXISTS `cart`;
DROP TABLE IF EXISTS `favorites`;
DROP TABLE IF EXISTS `orders_product`;
DROP TABLE IF EXISTS `shipment`;
DROP TABLE IF EXISTS `category_product`;
DROP TABLE IF EXISTS `notification`;
DROP TABLE IF EXISTS `comment`;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE `user` (
    `user_id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `address` VARCHAR(255),
    `phone_number` VARCHAR(11),
    PRIMARY KEY (`user_id`),
    UNIQUE (`email`)
);

CREATE TABLE `category` (
    `category_id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `item_count` INTEGER DEFAULT 0,
    PRIMARY KEY (`category_id`),
    UNIQUE (`name`)
);

CREATE TABLE `product` (
    `product_id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `price` DOUBLE NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    `user_id` BIGINT NOT NULL,
    `created_at` DATETIME NOT NULL,
    PRIMARY KEY (`product_id`)
);

CREATE TABLE `orders` (
    `orders_id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL,
    `orders_date` DATETIME NOT NULL,
    `total_price` DOUBLE DEFAULT 0,
    `status` VARCHAR(255),
    PRIMARY KEY (`orders_id`)
);

CREATE TABLE `cart` (
    `user_id` BIGINT NOT NULL,
    `product_id` BIGINT NOT NULL,
    `quantity` INTEGER NOT NULL,
    
    PRIMARY KEY (`product_id`, `user_id`)
    
);

CREATE TABLE `favorites` (
    `product_id` BIGINT NOT NULL,
    `user_id` BIGINT NOT NULL,
    PRIMARY KEY (`product_id`, `user_id`)
);

CREATE TABLE `orders_product` (
    `product_id` BIGINT NOT NULL,
    `orders_id` BIGINT NOT NULL,
    `quantity` INTEGER NOT NULL,
    `shipment_id` BIGINT NOT NULL,
    PRIMARY KEY (`product_id`, `orders_id`)
);

CREATE TABLE `shipment` (
    `shipment_id` BIGINT NOT NULL AUTO_INCREMENT,
    `status` VARCHAR(255) NOT NULL,
    `address` VARCHAR(255) NOT NULL,
    `last_update` DATETIME NOT NULL,
    `user_id` BIGINT NOT NULL,
    PRIMARY KEY (`shipment_id`)
);

CREATE TABLE `category_product` (
    `product_id` BIGINT NOT NULL,
    `category_id` BIGINT NOT NULL,
    PRIMARY KEY (`product_id`, `category_id`)
);

CREATE TABLE `notification` (
    `notification_id` BIGINT NOT NULL AUTO_INCREMENT,
    `content` VARCHAR(255) NOT NULL,
    `readed` BOOLEAN NOT NULL,
    `created_at` DATETIME,
    `user_id` BIGINT NOT NULL,
    PRIMARY KEY (`notification_id`)
);

CREATE TABLE `comment` (
    `product_id` BIGINT NOT NULL,
    `user_id` BIGINT NOT NULL,
    `content` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`product_id`, `user_id`)
);


ALTER TABLE `product` ADD FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`);
ALTER TABLE `orders` ADD FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`);
ALTER TABLE `cart` ADD FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`);
ALTER TABLE `cart` ADD FOREIGN KEY (`product_id`) REFERENCES `product`(`product_id`);
ALTER TABLE `favorites` ADD FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`);
ALTER TABLE `favorites` ADD FOREIGN KEY (`product_id`) REFERENCES `product`(`product_id`);
ALTER TABLE `orders_product` ADD FOREIGN KEY (`orders_id`) REFERENCES `orders`(`orders_id`);
ALTER TABLE `orders_product` ADD FOREIGN KEY (`product_id`) REFERENCES `product`(`product_id`);
ALTER TABLE `orders_product` ADD FOREIGN KEY (`shipment_id`) REFERENCES `shipment`(`shipment_id`);
ALTER TABLE `shipment` ADD FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`);
ALTER TABLE `category_product` ADD FOREIGN KEY (`category_id`) REFERENCES `category`(`category_id`);
ALTER TABLE `category_product` ADD FOREIGN KEY (`product_id`) REFERENCES `product`(`product_id`);
ALTER TABLE `notification` ADD FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`);
ALTER TABLE `comment` ADD FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`);
ALTER TABLE `comment` ADD FOREIGN KEY (`product_id`) REFERENCES `product`(`product_id`);