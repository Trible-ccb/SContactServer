/*
Navicat MySQL Data Transfer

Source Server         : TribleDB
Source Server Version : 50616
Source Host           : 127.0.0.1:1206
Source Database       : social_contact

Target Server Type    : MYSQL
Target Server Version : 50616
File Encoding         : 65001

Date: 2014-05-04 17:37:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for groupsinfo
-- ----------------------------
DROP TABLE IF EXISTS `groupsinfo`;
CREATE TABLE `groupsinfo` (
  `group_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `group_display_name` text NOT NULL,
  `group_description` text COMMENT '联系电话号码',
  `group_type` text NOT NULL COMMENT '群组类型\r\n例如 付费群组，开发者群组，普通群组',
  `group_owner_user_id` bigint(20) unsigned NOT NULL DEFAULT '0',
  `group_capacity` int(10) unsigned NOT NULL DEFAULT '50',
  `group_status` int(10) DEFAULT NULL COMMENT '冻结，在用，删除。。。',
  `group_create_time` bigint(20) DEFAULT NULL,
  `group_update_time` bigint(20) DEFAULT NULL,
  `group_identify` int(11) DEFAULT NULL,
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of groupsinfo
-- ----------------------------
INSERT INTO `groupsinfo` VALUES ('2', 'group1', null, 'normal', '1', '100', '0', '1396698555336', '1396698555336', '0');
INSERT INTO `groupsinfo` VALUES ('3', 'group2', null, 'normal', '1', '100', '0', '1396704117092', '1396704117092', '0');
INSERT INTO `groupsinfo` VALUES ('4', '测试', null, 'normal', '4', '50', '0', '1397136648336', '1397136648336', '1');
INSERT INTO `groupsinfo` VALUES ('5', '测试2', null, 'normal', '4', '50', '0', '1397136779175', '1397136779175', '1');
INSERT INTO `groupsinfo` VALUES ('6', '我的额', null, 'normal', '5', '50', '0', '1397137982135', '1397137982135', '1');
INSERT INTO `groupsinfo` VALUES ('7', '0508', null, 'normal', '6', '50', '1', '1397138049813', '1397138049813', '1');
INSERT INTO `groupsinfo` VALUES ('8', '524', null, 'normal', '9', '50', '1', '1399193302613', '1399193302613', '1');

-- ----------------------------
-- Table structure for group_validate
-- ----------------------------
DROP TABLE IF EXISTS `group_validate`;
CREATE TABLE `group_validate` (
  `id` bigint(20) NOT NULL,
  `start_user_id` bigint(20) NOT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  `contact_ids` text COLLATE utf8_latvian_ci,
  `end_user_id` bigint(20) DEFAULT NULL,
  `is_group_to_user` varchar(1) COLLATE utf8_latvian_ci NOT NULL,
  PRIMARY KEY (`id`,`is_group_to_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_latvian_ci;

-- ----------------------------
-- Records of group_validate
-- ----------------------------
INSERT INTO `group_validate` VALUES ('2', '6', '4', '[6]', '4', '0');

-- ----------------------------
-- Table structure for phone_group_info
-- ----------------------------
DROP TABLE IF EXISTS `phone_group_info`;
CREATE TABLE `phone_group_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `group_id` bigint(20) unsigned NOT NULL,
  `user_id` bigint(20) unsigned DEFAULT NULL,
  `contact_ids` text COMMENT 'raw phone numbers 的id数组字符串 逗号分隔such as{1,2}',
  PRIMARY KEY (`id`),
  KEY `group_fk` (`group_id`),
  CONSTRAINT `group_fk` FOREIGN KEY (`group_id`) REFERENCES `groupsinfo` (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of phone_group_info
-- ----------------------------
INSERT INTO `phone_group_info` VALUES ('1', '3', '4', '[4]');
INSERT INTO `phone_group_info` VALUES ('2', '4', '4', '[4]');
INSERT INTO `phone_group_info` VALUES ('3', '5', '4', '[4]');
INSERT INTO `phone_group_info` VALUES ('4', '6', '5', '[5]');
INSERT INTO `phone_group_info` VALUES ('5', '7', '6', '[6]');
INSERT INTO `phone_group_info` VALUES ('6', '3', '6', '[6]');
INSERT INTO `phone_group_info` VALUES ('8', '8', '9', '[15]');
INSERT INTO `phone_group_info` VALUES ('9', '7', '9', '[15]');

-- ----------------------------
-- Table structure for raw_phone_numbers
-- ----------------------------
DROP TABLE IF EXISTS `raw_phone_numbers`;
CREATE TABLE `raw_phone_numbers` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL,
  `phone_number` text NOT NULL,
  `status` int(10) unsigned DEFAULT '0' COMMENT '在用，删除',
  `latest_used_time` bigint(20) DEFAULT NULL,
  `type` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `raw_phone_numbers_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `usersinfo` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='the table record the relationship between user and his phone numbers.cause'' someone may has many number in real word.';

-- ----------------------------
-- Records of raw_phone_numbers
-- ----------------------------
INSERT INTO `raw_phone_numbers` VALUES ('4', '4', '啊', '0', '1396755230179', null);
INSERT INTO `raw_phone_numbers` VALUES ('5', '5', '额', '0', '1396756446701', null);
INSERT INTO `raw_phone_numbers` VALUES ('6', '6', '15882003904', '0', '1396760826337', null);
INSERT INTO `raw_phone_numbers` VALUES ('7', '7', '吖', '0', '1396760988972', null);
INSERT INTO `raw_phone_numbers` VALUES ('8', '4', '15882003904', '0', '1397233805436', null);
INSERT INTO `raw_phone_numbers` VALUES ('9', '4', '02861857754', '0', '1397233864642', null);
INSERT INTO `raw_phone_numbers` VALUES ('10', '4', '110', '0', '1397233989705', null);
INSERT INTO `raw_phone_numbers` VALUES ('11', '8', 'yangch905', '0', '1397561807389', null);
INSERT INTO `raw_phone_numbers` VALUES ('12', '8', '1567890345', '0', '1397561852834', null);
INSERT INTO `raw_phone_numbers` VALUES ('13', '8', 'fffff', '0', '1397562115977', null);
INSERT INTO `raw_phone_numbers` VALUES ('14', '8', 'yyyyggh', '0', '1397562127696', null);
INSERT INTO `raw_phone_numbers` VALUES ('15', '9', 'yangcheng', '0', '1397562184897', null);

-- ----------------------------
-- Table structure for usersinfo
-- ----------------------------
DROP TABLE IF EXISTS `usersinfo`;
CREATE TABLE `usersinfo` (
  `user_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_display_name` text COLLATE utf8_latvian_ci NOT NULL,
  `user_password` text COLLATE utf8_latvian_ci NOT NULL,
  `user_phone_number` text COLLATE utf8_latvian_ci COMMENT '联系电话号码',
  `user_email` text COLLATE utf8_latvian_ci,
  `user_real_name` text COLLATE utf8_latvian_ci,
  `user_description` text COLLATE utf8_latvian_ci,
  `user_type` text COLLATE utf8_latvian_ci COMMENT '用户类型\r\n例如 付费用户，普通用户，开发用户，测试用户 etc',
  `user_gender` int(11) DEFAULT '0' COMMENT '0 没设置\r\n1 男\r\n2 女\r\n3 其他',
  `user_birthday` bigint(20) DEFAULT NULL,
  `user_status` int(11) DEFAULT NULL COMMENT '在用，冻结，删除',
  `user_cookie` text COLLATE utf8_latvian_ci,
  `user_create_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_latvian_ci;

-- ----------------------------
-- Records of usersinfo
-- ----------------------------
INSERT INTO `usersinfo` VALUES ('4', '啊', '098f6bcd4621d373cade4e832627b4f6', '啊', null, null, null, 'normal', '0', null, '0', null, '1396755230003');
INSERT INTO `usersinfo` VALUES ('5', '额', '098f6bcd4621d373cade4e832627b4f6', '额', null, null, null, 'normal', '0', null, '0', null, '1396756446525');
INSERT INTO `usersinfo` VALUES ('6', 'chenchuibo', '098f6bcd4621d373cade4e832627b4f6', '15882003904', null, null, null, 'normal', '0', null, '0', null, '1396760826025');
INSERT INTO `usersinfo` VALUES ('7', '吖', '098f6bcd4621d373cade4e832627b4f6', '吖', null, null, null, 'normal', '0', null, '0', null, '1396760988891');
INSERT INTO `usersinfo` VALUES ('8', 'yccccy', 'd4f91a1a8936db2ced528b8eb122838b', 'yangch905', null, null, null, 'normal', '0', null, '0', null, '1397561807254');
INSERT INTO `usersinfo` VALUES ('9', 'yangch', 'c908aa7ed26160dab1fb85692b1863a2', 'yangcheng', null, null, null, 'normal', '0', null, '0', null, '1397562184787');
