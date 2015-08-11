ALTER TABLE `m_client`
	ADD COLUMN `dependents` INT(11) NULL DEFAULT NULL AFTER `mobile_no`,
	ADD COLUMN `marital_cv_id` INT(11) NULL DEFAULT NULL AFTER `dependents`,
	ADD COLUMN `address_line_1` VARCHAR(500) NULL DEFAULT NULL AFTER `marital_cv_id`,
	ADD COLUMN `address_line_2` VARCHAR(500) NULL DEFAULT NULL AFTER `address_line_1`,
	ADD COLUMN `town` VARCHAR(50) NULL DEFAULT NULL AFTER `address_line_2`,
	ADD COLUMN `city` VARCHAR(50) NULL DEFAULT NULL AFTER `town`,
	ADD COLUMN `state` VARCHAR(50) NULL DEFAULT NULL AFTER `city`,
	ADD COLUMN `zip` VARCHAR(20) NULL DEFAULT NULL AFTER `state`,
	ADD COLUMN `country` VARCHAR(50) NULL DEFAULT NULL AFTER `zip`,
	ADD COLUMN `residence_no` VARCHAR(20) NULL DEFAULT NULL AFTER `country`,
	ADD COLUMN `email` VARCHAR(100) NULL DEFAULT NULL AFTER `residence_no`,
	ADD COLUMN `state_of_origin` VARCHAR(50) NULL DEFAULT NULL AFTER `email`,
	ADD COLUMN `lga_of_origin` VARCHAR(50) NULL DEFAULT NULL AFTER `state_of_origin`,
	ADD COLUMN `longitude` VARCHAR(20) NULL DEFAULT NULL AFTER `lga_of_origin`,
	ADD COLUMN `latitude` VARCHAR(20) NULL DEFAULT NULL AFTER `longitude`,
	ADD COLUMN `next_of_kin_firstname` VARCHAR(50) NULL DEFAULT NULL AFTER `latitude`,
	ADD COLUMN `next_of_kin_lastname` VARCHAR(20) NULL DEFAULT NULL AFTER `next_of_kin_firstname`,
	ADD COLUMN `next_of_kin_address_line_1` VARCHAR(500) NULL DEFAULT NULL AFTER `next_of_kin_lastname`,
	ADD COLUMN `next_of_kin_address_line_2` VARCHAR(500) NULL DEFAULT NULL AFTER `next_of_kin_address_line_1`,
	ADD COLUMN `next_of_kin_town` VARCHAR(50) NULL DEFAULT NULL AFTER `next_of_kin_address_line_2`,
	ADD COLUMN `next_of_kin_city` VARCHAR(50) NULL DEFAULT NULL AFTER `next_of_kin_town`,
	ADD COLUMN `next_of_kin_state` VARCHAR(50) NULL DEFAULT NULL AFTER `next_of_kin_city`,
	ADD COLUMN `next_of_kin_zip` VARCHAR(20) NULL DEFAULT NULL AFTER `next_of_kin_state`,
	ADD COLUMN `next_of_kin_country` VARCHAR(50) NULL DEFAULT NULL AFTER `next_of_kin_zip`,
	ADD COLUMN `next_of_kin_residence_no` VARCHAR(20) NULL DEFAULT NULL AFTER `next_of_kin_country`,
	ADD COLUMN `next_of_kin_email` VARCHAR(100) NULL DEFAULT NULL AFTER `next_of_kin_residence_no`,
	ADD COLUMN `next_of_kin_longitude` VARCHAR(20) NULL DEFAULT NULL AFTER `next_of_kin_email`,
	ADD COLUMN `next_of_kin_latitude` VARCHAR(20) NULL DEFAULT NULL AFTER `next_of_kin_longitude`,
	ADD COLUMN `next_of_kin_relationship_cv_id` INT(11) NULL DEFAULT NULL AFTER `next_of_kin_latitude`;

INSERT INTO `m_code` (`code_name`, `is_system_defined`) VALUES ('KinRelationship', 1);
INSERT INTO `m_code` (`code_name`, `is_system_defined`) VALUES ('Marital', 1);

INSERT INTO `m_code_value` (`code_id`, `code_value`, `order_position`) SELECT mc.id, 'Spouse', 1 FROM m_code mc 	WHERE mc.`code_name` = "KinRelationship";
INSERT INTO `m_code_value` (`code_id`, `code_value`, `order_position`) SELECT mc.id, 'Parent', 2 FROM m_code mc 	WHERE mc.`code_name` = "KinRelationship";
INSERT INTO `m_code_value` (`code_id`, `code_value`, `order_position`) SELECT mc.id, 'Sibling', 3 FROM m_code mc 	WHERE mc.`code_name` = "KinRelationship";
INSERT INTO `m_code_value` (`code_id`, `code_value`, `order_position`) SELECT mc.id, 'Business Associate', 4 FROM m_code mc 	WHERE mc.`code_name` = "KinRelationship";
INSERT INTO `m_code_value` (`code_id`, `code_value`, `order_position`) SELECT mc.id, 'Other', 5 FROM m_code mc 	WHERE mc.`code_name` = "KinRelationship";

INSERT INTO `m_code_value`(`code_id`,`code_value`,`order_position`) SELECT mc.id, 'Married', 1 FROM m_code mc WHERE mc.`code_name` = "Marital";
INSERT INTO `m_code_value`(`code_id`,`code_value`,`order_position`) SELECT mc.id, 'Single', 2 FROM m_code mc WHERE mc.`code_name` = "Marital";
INSERT INTO `m_code_value`(`code_id`,`code_value`,`order_position`) SELECT mc.id, 'Other', 3 FROM m_code mc WHERE mc.`code_name` = "Marital";

INSERT INTO `m_code_value`(`code_id`,`code_value`,`order_position`) SELECT mc.id, 'Male', 1 FROM m_code mc WHERE mc.`code_name` = "Gender";
INSERT INTO `m_code_value`(`code_id`,`code_value`,`order_position`) SELECT mc.id, 'Female', 2 FROM m_code mc WHERE mc.`code_name` = "Gender";
INSERT INTO `m_code_value`(`code_id`,`code_value`,`order_position`) SELECT mc.id, 'Other', 3 FROM m_code mc WHERE mc.`code_name` = "Gender";

ALTER TABLE `m_client`
ADD CONSTRAINT `FK_m_client_next_of_kin_relationship_m_code_value` FOREIGN KEY (`next_of_kin_relationship_cv_id`) REFERENCES `m_code_value` (`id`),
ADD CONSTRAINT `FK_m_client_marital_m_code_value` FOREIGN KEY (`marital_cv_id`) REFERENCES `m_code_value` (`id`);
