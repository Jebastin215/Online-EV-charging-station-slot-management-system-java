CREATE DATABASE IF NOT EXISTS `ev_charging`;
USE `ev_charging`;

CREATE TABLE IF NOT EXISTS `ev_register` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `account` varchar(255) DEFAULT NULL,
  `card` varchar(255) DEFAULT NULL,
  `bank` varchar(255) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `uname` varchar(255) DEFAULT NULL,
  `pass` varchar(255) DEFAULT NULL,
  `latitude` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `ev_station` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `stype` varchar(255) DEFAULT NULL,
  `num_charger` int(11) DEFAULT NULL,
  `area` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `lat` varchar(255) DEFAULT NULL,
  `lon` varchar(255) DEFAULT NULL,
  `uname` varchar(255) DEFAULT NULL,
  `pass` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT 0,
  `landmark` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `distance` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `ev_booking` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uname` varchar(255) DEFAULT NULL,
  `station` varchar(255) DEFAULT NULL,
  `carno` varchar(255) DEFAULT NULL,
  `reserve` varchar(255) DEFAULT NULL,
  `slot` int(11) DEFAULT NULL,
  `cimage` varchar(255) DEFAULT NULL,
  `mins` int(11) DEFAULT NULL,
  `plan` int(11) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `rtime` varchar(255) DEFAULT NULL,
  `etime` varchar(255) DEFAULT NULL,
  `rdate` varchar(255) DEFAULT NULL,
  `edate` varchar(255) DEFAULT NULL,
  `otp` varchar(255) DEFAULT NULL,
  `charge` double DEFAULT NULL,
  `charge_time` int(11) DEFAULT NULL,
  `charge_min` int(11) DEFAULT NULL,
  `charge_sec` int(11) DEFAULT NULL,
  `charge_st` int(11) DEFAULT NULL,
  `pay_mode` varchar(255) DEFAULT NULL,
  `pay_st` int(11) DEFAULT NULL,
  `sms_st` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `btime1` varchar(255) DEFAULT NULL,
  `btime2` varchar(255) DEFAULT NULL,
  `alert_st` int(11) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `ev_slot` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `station` varchar(255) DEFAULT NULL,
  `slot` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `ev_admin` (
  `username` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT IGNORE INTO `ev_admin` (`username`, `password`) VALUES ('admin', 'admin');
