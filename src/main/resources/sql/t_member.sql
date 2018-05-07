/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2018-04-28 14:32:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_member
-- ----------------------------
DROP TABLE IF EXISTS `t_member`;
CREATE TABLE `t_member` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account` varchar(100) NOT NULL COMMENT '账户',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `name` varchar(100) NOT NULL COMMENT '姓名',
  `sex` varchar(100) NOT NULL COMMENT '性别',
  `id_num` varchar(100) DEFAULT NULL COMMENT '身份证',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `role` varchar(100) DEFAULT NULL COMMENT '角色',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `del` int(10) NOT NULL COMMENT '删除标记（0，未删除；1，已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_member
-- ----------------------------
INSERT INTO `t_member` VALUES ('1', 'liuwei', '1', '刘巍', '男', '2203221111111111', '13611172510', '13611172510@163.com', 'ROOT', '2018-04-24 09:59:59', '0');
INSERT INTO `t_member` VALUES ('2', 'zhangsan', '1', '张三', '女', '110010111111111111', '13525822582', '13525825825@163.com', 'ADMIN', '2018-04-24 10:01:10', '0');
