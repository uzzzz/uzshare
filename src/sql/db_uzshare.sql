/*
 - 初始数据库
 - 以下为部分带初始数据的表, 其他的表会在系统启动时自动生成
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `mto_channels`
-- ----------------------------
DROP TABLE IF EXISTS `mto_channels`;
CREATE TABLE `mto_channels` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `key_` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2s863lts1h6m7c30152262cvj` (`key_`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `mto_channels`
-- ----------------------------
BEGIN;
INSERT INTO `mto_channels` VALUES ('1', 'banner', 'banner', '1'); 
INSERT INTO `mto_channels` VALUES ('2', 'blog', '博客', '0');
INSERT INTO `mto_channels` VALUES ('3', 'questions', '问答', '0');
INSERT INTO `mto_channels` VALUES ('4', 'share', '分享', '0');
COMMIT;

-- ----------------------------
--  Table structure for `mto_config`
-- ----------------------------
DROP TABLE IF EXISTS `mto_config`;
CREATE TABLE `mto_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `key_` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_99vo6d7ci4wlxruo3gd0q2jq8` (`key_`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `mto_config`
-- ----------------------------
-- ----------------------------
-- Records of mto_config
-- ----------------------------
BEGIN;
INSERT INTO `mto_config` VALUES (1, 'site_name', '0', 'uzshare');
INSERT INTO `mto_config` VALUES (2, 'site_domain', '0', 'http://uzshare.com');
INSERT INTO `mto_config` VALUES (3, 'site_keywords', '0', '区块链,博客,社区');
INSERT INTO `mto_config` VALUES (4, 'site_description', '0', 'Uzzz, 做一个有内涵的技术社区');
INSERT INTO `mto_config` VALUES (5, 'site_metas', '0', '');
INSERT INTO `mto_config` VALUES (6, 'site_copyright', '0', 'Copyright © Uzzz');
INSERT INTO `mto_config` VALUES (7, 'site_icp', '0', '');
COMMIT;

-- ----------------------------
--  Table structure for `mto_users`
-- ----------------------------
DROP TABLE IF EXISTS `mto_users`;
CREATE TABLE `mto_users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` datetime DEFAULT NULL,
  `last_login` datetime DEFAULT NULL,
  `mobile` varchar(11) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `username` varchar(64) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT '/assets/images/ava/default.png',
  `updated` datetime DEFAULT NULL,
  `gender` int(11) NOT NULL,
  `role_id` int(11) DEFAULT NULL,
  `source` int(11) NOT NULL,
  `comments` int(11) NOT NULL,
  `fans` int(11) NOT NULL,
  `favors` int(11) NOT NULL,
  `follows` int(11) NOT NULL,
  `posts` int(11) NOT NULL,
  `signature` varchar(255) DEFAULT NULL,
  `salt` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `mto_users`
--  Admin默认密码：abc1234567
-- ----------------------------
BEGIN;
INSERT INTO `mto_users` VALUES ('1', '2017-08-06 17:52:41', '2017-10-17 13:24:13', null, 'O7SPM9RBLCIKVAOIDK911TNND', '0', 'admin', '执着', '/dist/images/ava/default.png', '2017-07-26 11:08:36', '0', '1', '0', '0', '6', '-2', '0', '19', '', NULL);
COMMIT;

-- ----------------------------
-- Table structure for shiro_permission
-- ----------------------------
DROP TABLE IF EXISTS `shiro_permission`;
CREATE TABLE `shiro_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `parent_id` bigint(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `weight` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_89ve8ffuihnryt1nw4o2t1feu` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shiro_permission
-- ----------------------------
INSERT INTO `shiro_permission` VALUES ('1', '进入后台', 'admin', '0', '0', '0');
INSERT INTO `shiro_permission` VALUES ('2', '栏目管理', 'channel:list', '0', '0', '0');
INSERT INTO `shiro_permission` VALUES ('3', '编辑栏目', 'channel:update', '2', '0', '0');
INSERT INTO `shiro_permission` VALUES ('4', '删除栏目', 'channel:delete', '2', '0', '0');
INSERT INTO `shiro_permission` VALUES ('5', '文章管理', 'post:list', '0', '0', '0');
INSERT INTO `shiro_permission` VALUES ('6', '编辑文章', 'post:update', '5', '0', '0');
INSERT INTO `shiro_permission` VALUES ('7', '删除文章', 'post:delete', '5', '0', '0');
INSERT INTO `shiro_permission` VALUES ('8', '评论管理', 'comment:list', '0', '0', '0');
INSERT INTO `shiro_permission` VALUES ('10', '删除评论', 'comment:delete', '8', '0', '0');
INSERT INTO `shiro_permission` VALUES ('11', '用户管理', 'user:list', '0', '0', '0');
INSERT INTO `shiro_permission` VALUES ('12', '用户授权', 'user:role', '11', '0', '0');
INSERT INTO `shiro_permission` VALUES ('13', '修改密码', 'user:pwd', '11', '0', '0');
INSERT INTO `shiro_permission` VALUES ('14', '激活用户', 'user:open', '11', '0', '0');
INSERT INTO `shiro_permission` VALUES ('15', '关闭用户', 'user:close', '11', '0', '0');
INSERT INTO `shiro_permission` VALUES ('16', '角色管理', 'role:list', '0', '0', '0');
INSERT INTO `shiro_permission` VALUES ('17', '修改角色', 'role:update', '16', '0', '0');
INSERT INTO `shiro_permission` VALUES ('18', '删除角色', 'role:delete', '16', '0', '0');
INSERT INTO `shiro_permission` VALUES ('19', '系统配置', 'config:list', '0', '0', '0');
INSERT INTO `shiro_permission` VALUES ('20', '修改配置', 'config:update', '19', '0', '0');

-- ----------------------------
-- Table structure for shiro_role
-- ----------------------------
DROP TABLE IF EXISTS `shiro_role`;
CREATE TABLE `shiro_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shiro_role
-- ----------------------------
INSERT INTO `shiro_role` VALUES ('1', null, 'admin', '0');

-- ----------------------------
-- Table structure for shiro_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `shiro_role_permission`;
CREATE TABLE `shiro_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `permission_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shiro_role_permission
-- ----------------------------
INSERT INTO `shiro_role_permission` VALUES ('1', '10', '1');
INSERT INTO `shiro_role_permission` VALUES ('2', '4', '1');
INSERT INTO `shiro_role_permission` VALUES ('3', '16', '1');
INSERT INTO `shiro_role_permission` VALUES ('4', '1', '1');
INSERT INTO `shiro_role_permission` VALUES ('5', '14', '1');
INSERT INTO `shiro_role_permission` VALUES ('6', '17', '1');
INSERT INTO `shiro_role_permission` VALUES ('7', '3', '1');
INSERT INTO `shiro_role_permission` VALUES ('8', '12', '1');
INSERT INTO `shiro_role_permission` VALUES ('9', '6', '1');
INSERT INTO `shiro_role_permission` VALUES ('10', '2', '1');
INSERT INTO `shiro_role_permission` VALUES ('11', '5', '1');
INSERT INTO `shiro_role_permission` VALUES ('12', '18', '1');
INSERT INTO `shiro_role_permission` VALUES ('13', '15', '1');
INSERT INTO `shiro_role_permission` VALUES ('14', '19', '1');
INSERT INTO `shiro_role_permission` VALUES ('15', '13', '1');
INSERT INTO `shiro_role_permission` VALUES ('16', '7', '1');
INSERT INTO `shiro_role_permission` VALUES ('17', '20', '1');
INSERT INTO `shiro_role_permission` VALUES ('18', '8', '1');
INSERT INTO `shiro_role_permission` VALUES ('19', '11', '1');

-- ----------------------------
-- Table structure for shiro_user_role
-- ----------------------------
DROP TABLE IF EXISTS `shiro_user_role`;
CREATE TABLE `shiro_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shiro_user_role
-- ----------------------------
INSERT INTO `shiro_user_role` VALUES ('1', '1', '1');

CREATE TABLE `notice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `url` longtext,
  `type` int(11) DEFAULT NULL COMMENT '1. 图文；2. js',
  `content` longtext,
  `created` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;