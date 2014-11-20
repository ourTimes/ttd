CREATE TABLE message (
   `id` INT(11) NOT NULL AUTO_INCREMENT,
   `user_id` INT(11) NOT NULL,
   `msg_type` SMALLINT(4) NOT NULL DEFAULT '0',
   `title` VARCHAR(60) NOT NULL,
   `desc` VARCHAR(250) NOT NULL DEFAULT '',
   `quote` DECIMAL(10,2) DEFAULT NULL COMMENT '报价',
   `send_date` DATE NOT NULL,
   `arrive_date` DATE NOT NULL,
   `from_province` VARCHAR(30) NULL,
   `from_city` VARCHAR(60) NULL,
   `from_addr` VARCHAR(250) NULL,
   `to_province` VARCHAR(30) NULL,
   `to_city` VARCHAR(60) NULL,
   `to_addr` VARCHAR(250) NULL,

   `create_time` DATE NOT NULL,
   `last_updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;