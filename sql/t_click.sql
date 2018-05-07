/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2018-05-02 17:47:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_click
-- ----------------------------
DROP TABLE IF EXISTS `t_click`;
CREATE TABLE `t_click` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `video_id` int(10) NOT NULL COMMENT '视频id',
  `chick_rate` int(10) NOT NULL DEFAULT '0' COMMENT '点击率',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_click
-- ----------------------------
