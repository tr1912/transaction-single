/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : localhost:3306
 Source Schema         : wx_transaction

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 21/08/2019 21:08:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_auth
-- ----------------------------
DROP TABLE IF EXISTS `tb_auth`;
CREATE TABLE `tb_auth` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `auth_name` varchar(50) DEFAULT NULL COMMENT '权限名称',
  `auth_code` varchar(100) DEFAULT NULL COMMENT '权限编码',
  `type` tinyint(4) DEFAULT NULL COMMENT '类型',
  `is_del` tinyint(4) DEFAULT NULL COMMENT '是否标记删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for tb_dic_area
-- ----------------------------
DROP TABLE IF EXISTS `tb_dic_area`;
CREATE TABLE `tb_dic_area` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `code` int(10) DEFAULT '0' COMMENT '行政区划代码',
  `pcode` int(10) DEFAULT '0' COMMENT '父级行政区划代码',
  `name` varchar(100) DEFAULT NULL COMMENT '区域名称',
  `initials` varchar(32) DEFAULT NULL COMMENT '首字母拼音',
  `depth` tinyint(4) DEFAULT '1' COMMENT '深度（1：省  2：市  3：区县  4：乡镇街道）',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_code` (`code`) USING BTREE,
  KEY `idx_depth` (`depth`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=43127 DEFAULT CHARSET=utf8 COMMENT='地区表';

-- ----------------------------
-- Table structure for tb_user_auth
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_auth`;
CREATE TABLE `tb_user_auth` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(20) DEFAULT '0' COMMENT '用户ID',
  `auth_id` int(20) DEFAULT '0' COMMENT '权限ID',
  `is_del` tinyint(4) DEFAULT NULL COMMENT '是否标记删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for tb_user_base
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_base`;
CREATE TABLE `tb_user_base` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_name` varchar(50) DEFAULT '' COMMENT '姓名',
  `password` varchar(200) DEFAULT '' COMMENT '密码',
  `auth` int(11) DEFAULT '0' COMMENT '权限',
  `is_del` tinyint(4) DEFAULT '1' COMMENT '删除标记',
  `mobile` varchar(30) DEFAULT '' COMMENT '电话号码',
  `area` varchar(30) DEFAULT '' COMMENT '区域',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for tb_user_logs
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_logs`;
CREATE TABLE `tb_user_logs` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(20) DEFAULT '0' COMMENT '用户ID',
  `log_text` varchar(200) DEFAULT '' COMMENT '日志内容',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `type` tinyint(4) DEFAULT '0' COMMENT '类型',
  `value` varchar(255) DEFAULT '' COMMENT '可用值',
  `is_del` tinyint(4) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
