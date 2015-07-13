/*
CREATE DATABASE IF NOT EXISTS shop_test;

USE shop_test;
*/


SET REFERENTIAL_INTEGRITY FALSE;

CREATE TABLE IF NOT EXISTS `address` (
  `addressID`     INT(11) NOT NULL,
  `ownerUsername` TEXT    NULL,
  `city`          TEXT    NULL,
  `street`        TEXT    NULL,
  PRIMARY KEY (`addressID`)
);

CREATE TABLE IF NOT EXISTS `cart` (
  `cartID`        INT(11) NOT NULL,
  `itemID`        INT(11) NULL DEFAULT NULL,
  `ownerUsername` TEXT    NOT NULL,
  `purchaseID`    INT(11) NULL DEFAULT NULL,
  `amount`        INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`cartID`)
);

CREATE TABLE IF NOT EXISTS `customer` (
  `customerID` INT(11) NOT NULL AUTO_INCREMENT,
  `username`   TEXT    NULL,
  `password`   TEXT    NULL,
  `role`       TEXT    NULL,
  `enabled`    BIT(1)  NULL DEFAULT NULL,
  PRIMARY KEY (`customerID`)
);

CREATE TABLE IF NOT EXISTS `homepageimage` (
  `id`    INT(11)  NOT NULL,
  `image` LONGBLOB NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `item` (
  `itemID`      INT(11)      NOT NULL,
  `itemName`    VARCHAR(255) NULL DEFAULT NULL,
  `type`        VARCHAR(50)  NULL DEFAULT NULL,
  `image`       LONGBLOB   NULL,
  `price`       FLOAT        NULL DEFAULT NULL,
  `leftOnStore` INT(11)      NULL DEFAULT NULL,
  `available`   TINYINT(4)   NULL DEFAULT NULL,
  PRIMARY KEY (`itemID`)
);

CREATE TABLE IF NOT EXISTS `itemdelivery` (
  `itemDeliveryID` INT(11)   NULL DEFAULT NULL,
  `itemID`         INT(11)   NULL DEFAULT NULL,
  `itemQuantity`   INT(11)   NULL DEFAULT NULL,
  `dateOfDelivery` TIMESTAMP NULL DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `purchase` (
  `purchaseID`    INT(11) NOT NULL,
  `ownerUsername` TEXT    NULL,
  `price`         DOUBLE  NULL DEFAULT NULL,
  `addressID`     INT(11) NULL DEFAULT NULL,
  `status`        TEXT    NULL,
  PRIMARY KEY (`purchaseID`)
);
