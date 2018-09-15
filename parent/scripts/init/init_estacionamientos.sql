CREATE DATABASE `est_db`;

CREATE USER est_app@localhost 
IDENTIFIED BY 'estacionamiento';
GRANT SELECT, UPDATE, INSERT, DELETE ON est_db.* TO est_app@localhost;