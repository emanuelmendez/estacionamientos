CREATE DATABASE `est_db`;

CREATE TABLE `est_db`.`test_table` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `nombre` VARCHAR(45) DEFAULT NULL,
    PRIMARY KEY (`id`)
)  ENGINE=INNODB AUTO_INCREMENT=3;

CREATE USER est_app@localhost 
IDENTIFIED BY 'estacionamiento';
GRANT SELECT, UPDATE, INSERT, DELETE ON est_db.* TO est_app@localhost;