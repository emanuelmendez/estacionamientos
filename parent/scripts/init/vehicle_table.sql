
CREATE TABLE `est_db`.`VEHICLE` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `PLATE` VARCHAR(45) NOT NULL,
  `ACTIVE` TINYINT NOT NULL,
  `USER` INT NOT NULL,
  `BRAND` VARCHAR(70) NOT NULL,
  `MODEL` VARCHAR(70) NOT NULL,
  `COLOR` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `PLATE_UNIQUE` (`PLATE` ASC) VISIBLE,
  INDEX `fk_VEHICLE_USER_idx` (`USER` ASC) VISIBLE,
  CONSTRAINT `fk_VEHICLE_USER`
    FOREIGN KEY (`USER`)
    REFERENCES `est_db`.`USER` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

    
/***************************************/
    
INSERT INTO vehicle(PLATE,ACTIVE,USER,BRAND,MODEL,COLOR)
VALUES
('LSK953',1,1,'Ford','Ka','Blanco'),
('TGB444',1,1,'Fiat','Palio','Rojo'),
('GST541',1,1,'Renault','Clio','Azul claro'),
('YHN885',1,1,'Ford','Fiesta','Turquesa'),
('ESG413',1,1,'Audi','C4','Gris claro'),
('LSK953',1,1,'Peugeot','206','Negro');
