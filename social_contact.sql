/*
Navicat MySQL Data Transfer

Source Server         : TribleDB
Source Server Version : 50616
Source Host           : 127.0.0.1:1206
Source Database       : social_contact

Target Server Type    : MYSQL
Target Server Version : 50616
File Encoding         : 65001

Date: 2014-07-03 15:08:28
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
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for group_validate
-- ----------------------------
DROP TABLE IF EXISTS `group_validate`;
CREATE TABLE `group_validate` (
  `id` bigint(20) NOT NULL,
  `start_user_id` bigint(20) DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  `contact_ids` text COLLATE utf8_latvian_ci,
  `end_user_id` bigint(20) DEFAULT NULL,
  `is_group_to_user` int(10) DEFAULT '0' COMMENT '0表示不是群主邀请用户，1表示群主邀请用户加入',
  `create_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_latvian_ci;

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
  KEY `group_fk` (`group_id`)
) ENGINE=MyISAM AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for raw_phone_numbers
-- ----------------------------
DROP TABLE IF EXISTS `raw_phone_numbers`;
CREATE TABLE `raw_phone_numbers` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL,
  `contact_string` text CHARACTER SET utf8 COLLATE utf8_latvian_ci NOT NULL,
  `status` int(10) unsigned DEFAULT '0' COMMENT '在用，删除',
  `latest_used_time` bigint(20) DEFAULT NULL,
  `type` text CHARACTER SET utf8 COLLATE utf8_latvian_ci,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='the table record the relationship between user and his phone numbers.cause'' someone may has many number in real word.';

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
  `user_photo_url` text COLLATE utf8_latvian_ci COMMENT '头像地址url',
  `user_notify_id` text COLLATE utf8_latvian_ci,
  `user_third_usid` text COLLATE utf8_latvian_ci,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_latvian_ci;

-- ----------------------------
-- Table structure for user_relationship_info
-- ----------------------------
DROP TABLE IF EXISTS `user_relationship_info`;
CREATE TABLE `user_relationship_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned DEFAULT NULL,
  `follow_user_id` bigint(20) unsigned NOT NULL COMMENT '好友关系用两条记录 即两条双向边表示无向边。contactids是useid暴露给followuserid的联系id',
  `contact_ids` text COMMENT 'raw phone numbers 的id数组字符串 逗号分隔such as{1,2}',
  PRIMARY KEY (`id`),
  KEY `group_fk` (`follow_user_id`),
  KEY `user_id` (`user_id`),
  KEY `id` (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
