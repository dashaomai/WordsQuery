-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.19 - MySQL Community Server (GPL)
-- Server OS:                    Linux
-- HeidiSQL Version:             9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for hebangdata_sentences
CREATE DATABASE IF NOT EXISTS `hebangdata_sentences` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin */;
USE `hebangdata_sentences`;

-- Dumping structure for table hebangdata_sentences.probability
CREATE TABLE IF NOT EXISTS `probability` (
  `initial` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '开头字',
  `letter` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '尾随字',
  `count_0` bigint(20) unsigned zerofill NOT NULL COMMENT '中间没有间隔时，词的出现次数',
  `count_1` bigint(20) unsigned zerofill NOT NULL COMMENT '中间有一个间隔时，词的出现次数',
  `count_2` bigint(20) unsigned zerofill NOT NULL COMMENT '中间有两个间隔时，词的出现次数',
  `count_3` bigint(20) unsigned zerofill NOT NULL COMMENT '中间有三个间隔时，词的出现次数',
  PRIMARY KEY (`initial`,`letter`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='词组合的出现概率';

-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
