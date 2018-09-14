CREATE TABLE `est_db`.`USER` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `EMAIL` VARCHAR(45) NOT NULL,
  `USERNAME` VARCHAR(45) NOT NULL,
  `PHONE` VARCHAR(50) NOT NULL,
  `NAME` VARCHAR(100) NOT NULL,
  `SURNAME` VARCHAR(100) NOT NULL,
  `SINCE` DATETIME NOT NULL,
  `LAST_UPDATED` DATETIME NOT NULL,
  `ACTIVE` TINYINT NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `EMAIL_UNIQUE` (`EMAIL` ASC) VISIBLE,
  UNIQUE INDEX `USERNAME_UNIQUE` (`USERNAME` ASC) VISIBLE,
  UNIQUE INDEX `PHONE_UNIQUE` (`PHONE` ASC) VISIBLE);

