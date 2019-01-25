/*
Navicat MySQL Data Transfer

Source Server         : ftc
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : ftc

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2019-01-24 18:04:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ftc_deal
-- ----------------------------
DROP TABLE IF EXISTS `ftc_deal`;
CREATE TABLE `ftc_deal` (
  `tid` bigint(20) NOT NULL,
  `uid` bigint(20) NOT NULL COMMENT '可交易资产',
  `deal_amount` double DEFAULT NULL,
  `deal_date` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ftc_deal
-- ----------------------------

-- ----------------------------
-- Table structure for ftc_dict
-- ----------------------------
DROP TABLE IF EXISTS `ftc_dict`;
CREATE TABLE `ftc_dict` (
  `did` bigint(20) NOT NULL AUTO_INCREMENT,
  `name1` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `name2` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `value1` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `value2` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  PRIMARY KEY (`did`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ftc_dict
-- ----------------------------
INSERT INTO `ftc_dict` VALUES ('1', 'EOS-FTC', 'EOS', '200', null);
INSERT INTO `ftc_dict` VALUES ('2', 'EOS-FTC', 'FTC', '5000', null);
INSERT INTO `ftc_dict` VALUES ('3', 'FTCPrice-Num', 'univalent', '1', null);
INSERT INTO `ftc_dict` VALUES ('4', 'FTCPrice-Num', 'quantity', '500', null);
INSERT INTO `ftc_dict` VALUES ('5', 'User-level', 'level', '6', null);

-- ----------------------------
-- Table structure for ftc_flow_oneside
-- ----------------------------
DROP TABLE IF EXISTS `ftc_flow_oneside`;
CREATE TABLE `ftc_flow_oneside` (
  `uid` bigint(20) NOT NULL,
  `type` int(255) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `cny` double DEFAULT NULL,
  `ftc` double DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ftc_flow_oneside
-- ----------------------------

-- ----------------------------
-- Table structure for ftc_message
-- ----------------------------
DROP TABLE IF EXISTS `ftc_message`;
CREATE TABLE `ftc_message` (
  `uid` bigint(20) NOT NULL,
  `phone` int(11) DEFAULT NULL,
  `leaving_date` datetime DEFAULT NULL,
  `replay_date` datetime DEFAULT NULL,
  `msg` text,
  `r_msg` text,
  `pic_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ftc_message
-- ----------------------------

-- ----------------------------
-- Table structure for ftc_order
-- ----------------------------
DROP TABLE IF EXISTS `ftc_order`;
CREATE TABLE `ftc_order` (
  `oid` bigint(20) NOT NULL,
  `seller_id` bigint(20) DEFAULT NULL,
  `buyer_id` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `seller_phone` int(11) DEFAULT NULL,
  `buyer_phone` int(11) DEFAULT NULL,
  `pay_date` datetime DEFAULT NULL,
  `get_date` datetime DEFAULT NULL,
  `oder_date` datetime DEFAULT NULL,
  `cny` double(255,0) DEFAULT NULL,
  `ftc` double DEFAULT NULL,
  `pay_way` int(11) DEFAULT NULL,
  `finish_date` datetime DEFAULT NULL,
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ftc_order
-- ----------------------------

-- ----------------------------
-- Table structure for ftc_relation
-- ----------------------------
DROP TABLE IF EXISTS `ftc_relation`;
CREATE TABLE `ftc_relation` (
  `rid` bigint(20) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `child_id` bigint(20) DEFAULT NULL,
  `invite_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ftc_relation
-- ----------------------------

-- ----------------------------
-- Table structure for ftc_reward
-- ----------------------------
DROP TABLE IF EXISTS `ftc_reward`;
CREATE TABLE `ftc_reward` (
  `userid` bigint(20) NOT NULL,
  `level` int(255) DEFAULT NULL,
  `amount` double(255,0) DEFAULT NULL,
  `reward_date` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ftc_reward
-- ----------------------------

-- ----------------------------
-- Table structure for ftc_user
-- ----------------------------
DROP TABLE IF EXISTS `ftc_user`;
CREATE TABLE `ftc_user` (
  `id` bigint(20) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `phone` int(11) DEFAULT NULL COMMENT '用户表',
  `password` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `teamid` bigint(255) DEFAULT NULL,
  `admin_status` int(11) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `regist_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `flag` int(11) DEFAULT NULL,
  `pay_pwd` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ftc_user
-- ----------------------------
INSERT INTO `ftc_user` VALUES ('7681578573824', null, null, '123456', '123456', '7681578573825', null, null, '2019-01-23 15:56:36', null);
INSERT INTO `ftc_user` VALUES ('7993796067328', null, null, '1234567', '123456', '7993796067329', null, null, '2019-01-23 10:21:08', null);

-- ----------------------------
-- Table structure for ftc_user_payinfo
-- ----------------------------
DROP TABLE IF EXISTS `ftc_user_payinfo`;
CREATE TABLE `ftc_user_payinfo` (
  `pid` bigint(20) NOT NULL,
  `uid` bigint(11) NOT NULL,
  `name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `card_id` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `bank_name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `ali_pay` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `ali_url` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `wechat_pay` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `wechat_url` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `bank_address` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ftc_user_payinfo
-- ----------------------------

-- ----------------------------
-- Table structure for ftc_veritify
-- ----------------------------
DROP TABLE IF EXISTS `ftc_veritify`;
CREATE TABLE `ftc_veritify` (
  `vid` bigint(20) NOT NULL,
  `phone` int(11) DEFAULT NULL,
  `code` int(11) DEFAULT NULL,
  `new_time` datetime DEFAULT NULL,
  `loss_time` datetime DEFAULT NULL,
  PRIMARY KEY (`vid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ftc_veritify
-- ----------------------------
