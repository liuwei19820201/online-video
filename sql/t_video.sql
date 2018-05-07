/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2018-05-02 17:47:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_video
-- ----------------------------
DROP TABLE IF EXISTS `t_video`;
CREATE TABLE `t_video` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(200) DEFAULT NULL COMMENT '视频名称',
  `address` varchar(200) DEFAULT NULL COMMENT '视频地址',
  `type` varchar(20) DEFAULT NULL COMMENT '类型',
  `member_id` int(10) DEFAULT NULL COMMENT '上传会员id',
  `upload_time` datetime DEFAULT NULL COMMENT '上传时间',
  `del` int(10) DEFAULT NULL COMMENT '删除标记（0未删除，1已删除）',
  `suffix` varchar(100) DEFAULT NULL COMMENT '视频后缀',
  `alias` varchar(100) DEFAULT NULL COMMENT '别名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_video
-- ----------------------------
