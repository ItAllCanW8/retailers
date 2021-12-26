-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema retailers
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `retailers` ;

-- -----------------------------------------------------
-- Schema retailers
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `retailers` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `retailers` ;

-- -----------------------------------------------------
-- Table `retailers`.`address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `retailers`.`address` ;

CREATE TABLE IF NOT EXISTS `retailers`.`address` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `state_code` VARCHAR(2) NULL DEFAULT NULL,
  `city` VARCHAR(255) NULL DEFAULT NULL,
  `first_line` VARCHAR(255) NULL DEFAULT NULL,
  `second_line` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 99
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `retailers`.`customer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `retailers`.`customer` ;

CREATE TABLE IF NOT EXISTS `retailers`.`customer` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `registration_date` DATE NULL DEFAULT NULL,
  `active` BIT(1) NULL DEFAULT b'1',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `retailers`.`location`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `retailers`.`location` ;

CREATE TABLE IF NOT EXISTS `retailers`.`location` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `identifier` VARCHAR(255) NOT NULL,
  `type` VARCHAR(45) NULL DEFAULT NULL,
  `total_capacity` INT NULL DEFAULT NULL,
  `rental_tax_rate` FLOAT NULL DEFAULT '0',
  `address_id` BIGINT NULL DEFAULT NULL,
  `customer_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `identifier_UNIQUE` (`identifier` ASC) VISIBLE,
  INDEX `fk_location_customer_idx` (`customer_id` ASC) VISIBLE,
  INDEX `fk_locations_addresses_idx` (`address_id` ASC) VISIBLE,
  CONSTRAINT `fk_location_address`
    FOREIGN KEY (`address_id`)
    REFERENCES `retailers`.`address` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_location_customer`
    FOREIGN KEY (`customer_id`)
    REFERENCES `retailers`.`customer` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 29
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `retailers`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `retailers`.`role` ;

CREATE TABLE IF NOT EXISTS `retailers`.`role` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `role` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `role_UNIQUE` (`role` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `retailers`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `retailers`.`user` ;

CREATE TABLE IF NOT EXISTS `retailers`.`user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `surname` VARCHAR(255) NULL DEFAULT NULL,
  `birthday` DATE NULL DEFAULT NULL,
  `email` VARCHAR(255) NOT NULL,
  `login` VARCHAR(255) NULL DEFAULT NULL,
  `password` VARCHAR(255) NOT NULL,
  `active` BIT(1) NULL DEFAULT b'1',
  `role_id` BIGINT NULL DEFAULT NULL,
  `address_id` BIGINT NULL DEFAULT NULL,
  `location_id` BIGINT NULL DEFAULT NULL,
  `customer_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_customer_id` (`customer_id` ASC) VISIBLE,
  INDEX `fk_user_role` (`role_id` ASC) VISIBLE,
  INDEX `fk_user_address_idx` (`address_id` ASC) VISIBLE,
  INDEX `fk_user_location_idx` (`location_id` ASC) VISIBLE,
  CONSTRAINT `fk_customer_id`
    FOREIGN KEY (`customer_id`)
    REFERENCES `retailers`.`customer` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_address`
    FOREIGN KEY (`address_id`)
    REFERENCES `retailers`.`address` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_location`
    FOREIGN KEY (`location_id`)
    REFERENCES `retailers`.`location` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_role`
    FOREIGN KEY (`role_id`)
    REFERENCES `retailers`.`role` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 31
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `retailers`.`application`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `retailers`.`application` ;

CREATE TABLE IF NOT EXISTS `retailers`.`application` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `application_number` VARCHAR(45) NOT NULL,
  `reg_date_time` DATETIME NULL DEFAULT NULL,
  `last_upd_date_time` DATETIME NULL DEFAULT NULL,
  `status` VARCHAR(45) NULL DEFAULT NULL,
  `source_location` BIGINT NULL DEFAULT NULL,
  `destination_location` BIGINT NULL DEFAULT NULL,
  `created_by` BIGINT NULL DEFAULT NULL,
  `last_upd_by` BIGINT NULL DEFAULT NULL,
  `customer_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `application_number_UNIQUE` (`application_number` ASC) VISIBLE,
  INDEX `fk_application_created_by_idx` (`created_by` ASC) VISIBLE,
  INDEX `fk_application_dest_location_idx` (`destination_location` ASC) VISIBLE,
  INDEX `fk_application_src_location_idx` (`source_location` ASC) VISIBLE,
  INDEX `fk_application_upd_by_idx` (`last_upd_by` ASC) VISIBLE,
  INDEX `fk_application_customer_idx` (`customer_id` ASC) VISIBLE,
  CONSTRAINT `fk_application_created_by`
    FOREIGN KEY (`created_by`)
    REFERENCES `retailers`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_application_customer`
    FOREIGN KEY (`customer_id`)
    REFERENCES `retailers`.`customer` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_application_dest_location`
    FOREIGN KEY (`destination_location`)
    REFERENCES `retailers`.`location` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_application_src_location`
    FOREIGN KEY (`source_location`)
    REFERENCES `retailers`.`location` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_application_upd_by`
    FOREIGN KEY (`last_upd_by`)
    REFERENCES `retailers`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `retailers`.`category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `retailers`.`category` ;

CREATE TABLE IF NOT EXISTS `retailers`.`category` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `retailers`.`item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `retailers`.`item` ;

CREATE TABLE IF NOT EXISTS `retailers`.`item` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `upc` VARCHAR(20) NOT NULL,
  `label` VARCHAR(45) NULL DEFAULT NULL,
  `units` INT NULL DEFAULT NULL,
  `category_id` BIGINT NULL DEFAULT NULL,
  `customer_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `upc_UNIQUE` (`upc` ASC) VISIBLE,
  INDEX `fk_category_id_idx` (`category_id` ASC) VISIBLE,
  CONSTRAINT `fk_category_id`
    FOREIGN KEY (`category_id`)
    REFERENCES `retailers`.`category` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `retailers`.`application_item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `retailers`.`application_item` ;

CREATE TABLE IF NOT EXISTS `retailers`.`application_item` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `amount` INT NULL DEFAULT NULL,
  `cost` FLOAT NULL DEFAULT NULL,
  `application_id` BIGINT NULL DEFAULT NULL,
  `item_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_application_item_application_idx` (`application_id` ASC) VISIBLE,
  INDEX `fk_items_idx` (`item_id` ASC) VISIBLE,
  CONSTRAINT `fk_application_item_application`
    FOREIGN KEY (`application_id`)
    REFERENCES `retailers`.`application` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_application_item_item`
    FOREIGN KEY (`item_id`)
    REFERENCES `retailers`.`item` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `retailers`.`bill`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `retailers`.`bill` ;

CREATE TABLE IF NOT EXISTS `retailers`.`bill` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `number` VARCHAR(45) NOT NULL,
  `date_time` DATETIME NULL DEFAULT NULL,
  `location_id` BIGINT NULL DEFAULT NULL,
  `shop_manager_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `number_UNIQUE` (`number` ASC) VISIBLE,
  INDEX `fk_bill_location_idx` (`location_id` ASC) VISIBLE,
  INDEX `fk_bills_users_idx` (`shop_manager_id` ASC) VISIBLE,
  CONSTRAINT `fk_bill_location`
    FOREIGN KEY (`location_id`)
    REFERENCES `retailers`.`location` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_bill_user`
    FOREIGN KEY (`shop_manager_id`)
    REFERENCES `retailers`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 40
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `retailers`.`bill_item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `retailers`.`bill_item` ;

CREATE TABLE IF NOT EXISTS `retailers`.`bill_item` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `amount` INT NULL DEFAULT NULL,
  `price` FLOAT NULL DEFAULT NULL,
  `bill_id` BIGINT NULL DEFAULT NULL,
  `item_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_bills_idx` (`bill_id` ASC) VISIBLE,
  INDEX `fk_items_idx` (`item_id` ASC) VISIBLE,
  CONSTRAINT `fk_bill_item_bill`
    FOREIGN KEY (`bill_id`)
    REFERENCES `retailers`.`bill` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_bill_item_item`
    FOREIGN KEY (`item_id`)
    REFERENCES `retailers`.`item` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 24
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `retailers`.`customer_category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `retailers`.`customer_category` ;

CREATE TABLE IF NOT EXISTS `retailers`.`customer_category` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `category_tax` FLOAT NULL DEFAULT '0',
  `customer_id` BIGINT NULL DEFAULT NULL,
  `category_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_customer_category_category_idx` (`category_id` ASC) VISIBLE,
  INDEX `fk_customer_category_customer_idx` (`customer_id` ASC) VISIBLE,
  CONSTRAINT `fk_customer_category_category`
    FOREIGN KEY (`category_id`)
    REFERENCES `retailers`.`category` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_customer_category_customer`
    FOREIGN KEY (`customer_id`)
    REFERENCES `retailers`.`customer` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `retailers`.`supplier`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `retailers`.`supplier` ;

CREATE TABLE IF NOT EXISTS `retailers`.`supplier` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `identifier` VARCHAR(255) NOT NULL,
  `active` BIT(1) NULL DEFAULT b'1',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `identifier_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `identifier` (`identifier` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 16
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `retailers`.`customer_supplier`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `retailers`.`customer_supplier` ;

CREATE TABLE IF NOT EXISTS `retailers`.`customer_supplier` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `customer_id` BIGINT NOT NULL,
  `supplier_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_customer_supplier_customer_idx` (`customer_id` ASC) VISIBLE,
  INDEX `fk_customer_supplier_supplier_idx` (`supplier_id` ASC) VISIBLE,
  CONSTRAINT `fk_customer_supplier_customer`
    FOREIGN KEY (`customer_id`)
    REFERENCES `retailers`.`customer` (`id`)
    ON UPDATE CASCADE,
  CONSTRAINT `fk_customer_supplier_supplier`
    FOREIGN KEY (`supplier_id`)
    REFERENCES `retailers`.`supplier` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `retailers`.`location_item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `retailers`.`location_item` ;

CREATE TABLE IF NOT EXISTS `retailers`.`location_item` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `location_id` BIGINT NOT NULL,
  `item_id` BIGINT NOT NULL,
  `amount` INT NOT NULL,
  `cost` FLOAT NOT NULL,
  `price` FLOAT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_location_item_item` (`item_id` ASC) VISIBLE,
  INDEX `fk_location_item_location_idx` (`location_id` ASC) VISIBLE,
  CONSTRAINT `fk_location_item_item`
    FOREIGN KEY (`item_id`)
    REFERENCES `retailers`.`item` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_location_item_location`
    FOREIGN KEY (`location_id`)
    REFERENCES `retailers`.`location` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `retailers`.`state_tax`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `retailers`.`state_tax` ;

CREATE TABLE IF NOT EXISTS `retailers`.`state_tax` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `state_code` VARCHAR(2) NULL DEFAULT NULL,
  `tax` FLOAT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `state_code_UNIQUE` (`state_code` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 33
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `retailers`.`warehouse`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `retailers`.`warehouse` ;

CREATE TABLE IF NOT EXISTS `retailers`.`warehouse` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `address_id` BIGINT NULL DEFAULT NULL,
  `supplier_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_warehouse_address_idx` (`address_id` ASC) VISIBLE,
  INDEX `fk_warehouse_supplier_idx` (`supplier_id` ASC) VISIBLE,
  CONSTRAINT `fk_warehouse_address`
    FOREIGN KEY (`address_id`)
    REFERENCES `retailers`.`address` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_warehouse_supplier`
    FOREIGN KEY (`supplier_id`)
    REFERENCES `retailers`.`supplier` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 29
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `retailers`.`write_off_act`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `retailers`.`write_off_act` ;

CREATE TABLE IF NOT EXISTS `retailers`.`write_off_act` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `identifier` VARCHAR(255) NOT NULL,
  `date_time` DATETIME NULL DEFAULT NULL,
  `total_amount` BIGINT NULL DEFAULT NULL,
  `total_sum` BIGINT NULL DEFAULT NULL,
  `location_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `identifier_UNIQUE` (`identifier` ASC) VISIBLE,
  INDEX `fk_write_off_act_location_idx` (`location_id` ASC) VISIBLE,
  CONSTRAINT `fk_write_off_act_location`
    FOREIGN KEY (`location_id`)
    REFERENCES `retailers`.`location` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `retailers`.`written_off_item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `retailers`.`written_off_item` ;

CREATE TABLE IF NOT EXISTS `retailers`.`written_off_item` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `amount` INT NULL DEFAULT NULL,
  `reason` VARCHAR(45) NULL DEFAULT NULL,
  `write_off_id` BIGINT NOT NULL,
  `item_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_item_idx` (`item_id` ASC) VISIBLE,
  INDEX `fk_write_off_item_write_off_idx` (`write_off_id` ASC) VISIBLE,
  CONSTRAINT `fk_written_off_item_item`
    FOREIGN KEY (`item_id`)
    REFERENCES `retailers`.`item` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_written_off_item_write_off`
    FOREIGN KEY (`write_off_id`)
    REFERENCES `retailers`.`write_off_act` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
