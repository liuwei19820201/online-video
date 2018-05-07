/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2018-04-28 14:32:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `video_id` int(10) NOT NULL COMMENT '视频id',
  `content` varchar(2000) NOT NULL COMMENT '评论内容',
  `commentator` int(10) NOT NULL COMMENT '评论会员id',
  `comment_time` datetime NOT NULL COMMENT '评论时间',
  `del` int(10) NOT NULL COMMENT '删除标记（0未删除，1已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_comment
-- ----------------------------
