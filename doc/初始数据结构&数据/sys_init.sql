/*
Navicat MySQL Data Transfer

Source Server         : 恒丰测试
Source Server Version : 50721
Source Host           : 192.168.10.245:3306
Source Database       : blockchain

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2019-07-10 10:41:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_code` varchar(32) NOT NULL COMMENT '用户编号',
  `user_name` varchar(32) NOT NULL COMMENT '用户名',
  `real_name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `mobile_phone` varchar(11) NOT NULL COMMENT '手机号码',
  `id_card` varchar(18) DEFAULT NULL COMMENT '身份证',
  `pwd` varchar(100) NOT NULL COMMENT '登录密码',
  `login_time` timestamp NULL DEFAULT NULL COMMENT '登录时间',
  `login_ip` varchar(100) NOT NULL COMMENT '登录ip',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '用户状态，0：正常，1：锁定，2：离职,3:未启用',
  `register_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `user_pic` varchar(200) DEFAULT NULL COMMENT '用户头像',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_unique_user_code` (`user_code`) USING BTREE,
  UNIQUE KEY `index_unique_mobile_phone` (`mobile_phone`) USING BTREE,
  UNIQUE KEY `index_unique_id_card` (`id_card`) USING BTREE,
  KEY `index_normal_user_name` (`user_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8 COMMENT='管理员表';

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('105', 'user00001', 'admin', 'dyf', '18321835396', '22222', '195fd40a5105b624a9d84402fae82228', '2019-07-10 10:30:09', '192.168.10.89', '0', '2019-07-02 10:33:30', '2019-07-10 10:30:33', null);

-- ----------------------------
-- Table structure for admin_actions
-- ----------------------------
DROP TABLE IF EXISTS `admin_actions`;
CREATE TABLE `admin_actions` (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `url` varchar(100) NOT NULL COMMENT '链接地址',
  `pid` int(32) NOT NULL DEFAULT '0' COMMENT '所属菜单id',
  `level` int(1) NOT NULL DEFAULT '0' COMMENT '菜单等级',
  `paixu` int(1) NOT NULL DEFAULT '0' COMMENT '排序',
  `is_menu` bit(1) NOT NULL DEFAULT b'0' COMMENT '菜单类型，0：功能，1：菜单',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标样式',
  `menu_pos_str` varchar(50) DEFAULT NULL COMMENT '菜单定位符',
  `sys_type` tinyint(1) DEFAULT '0' COMMENT '所属系统，0：后台系统，1：APP系统',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=363 DEFAULT CHARSET=utf8 COMMENT='地址表';

-- ----------------------------
-- Records of admin_actions
-- ----------------------------
INSERT INTO `admin_actions` VALUES ('1', '后台管理系统', '', '0', '1', '0', '\0', null, null, '0', '2018-11-26 10:27:06', '2019-01-23 14:34:51');
INSERT INTO `admin_actions` VALUES ('2', '数据总览', '/dataScreen', '1', '2', '22', '', '', 'dataScreen', '0', '2018-10-31 11:27:03', '2019-07-02 14:23:25');
INSERT INTO `admin_actions` VALUES ('3', '用户列表', '/userList', '1', '2', '22', '', '', 'userList', '0', '2018-10-31 11:27:03', '2019-07-02 14:23:29');
INSERT INTO `admin_actions` VALUES ('4', '交易记录', '/transactionRecords', '1', '2', '22', '', '', 'transactionRecords', '0', '2018-10-31 11:27:03', '2019-07-02 14:23:32');
INSERT INTO `admin_actions` VALUES ('356', '未分配', '/admin/dataStatistics/data/look', '0', '1', '0', '\0', null, null, '0', '2019-07-02 13:37:45', '2019-07-02 13:37:45');
INSERT INTO `admin_actions` VALUES ('357', '未分配', '/admin/dataStatistics/platform/income', '0', '1', '0', '\0', null, null, '0', '2019-07-02 13:37:45', '2019-07-02 13:37:45');
INSERT INTO `admin_actions` VALUES ('358', '未分配', '/admin/user/list', '0', '1', '0', '\0', null, null, '0', '2019-07-02 13:52:53', '2019-07-02 13:52:53');
INSERT INTO `admin_actions` VALUES ('359', '未分配', '/admin/dataStatistics/order/list', '0', '1', '0', '\0', null, null, '0', '2019-07-02 13:56:00', '2019-07-02 13:56:00');
INSERT INTO `admin_actions` VALUES ('360', '未分配', '/admin/dataStatistics/order/info', '0', '1', '0', '\0', null, null, '0', '2019-07-02 14:12:36', '2019-07-02 14:12:36');
INSERT INTO `admin_actions` VALUES ('361', '未分配', '/admin/user/hidden/message', '0', '1', '0', '\0', null, null, '0', '2019-07-02 14:30:31', '2019-07-02 14:30:31');
INSERT INTO `admin_actions` VALUES ('362', '未分配', '/admin/user/open/message', '0', '1', '0', '\0', null, null, '0', '2019-07-02 14:31:41', '2019-07-02 14:31:41');

-- ----------------------------
-- Table structure for admin_role_actions
-- ----------------------------
DROP TABLE IF EXISTS `admin_role_actions`;
CREATE TABLE `admin_role_actions` (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `rid` int(32) NOT NULL COMMENT '角色id（表roles的id）',
  `aid` int(32) NOT NULL COMMENT '地址id（表admin_actions的id）',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限表';

-- ----------------------------
-- Records of admin_role_actions
-- ----------------------------

-- ----------------------------
-- Table structure for admin_roles
-- ----------------------------
DROP TABLE IF EXISTS `admin_roles`;
CREATE TABLE `admin_roles` (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `aid` int(32) NOT NULL COMMENT '用户id（表admin的id）',
  `rid` int(32) NOT NULL COMMENT '角色id（表roles的id）',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员角色表';

-- ----------------------------
-- Records of admin_roles
-- ----------------------------

-- ----------------------------
-- Table structure for api_token
-- ----------------------------
DROP TABLE IF EXISTS `api_token`;
CREATE TABLE `api_token` (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_code` varchar(32) NOT NULL COMMENT '用户编号',
  `device_id` varchar(200) NOT NULL COMMENT '设备编号',
  `device_name` varchar(200) NOT NULL COMMENT '设备名称',
  `token` varchar(100) NOT NULL COMMENT 'token',
  `status` tinyint(1) DEFAULT '0' COMMENT 'token类型，0：有效，1：过期，2：失效，3：另外设备登录',
  `change_time` timestamp NULL DEFAULT NULL COMMENT 'token变更时间',
  `date_add` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `date_update` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=132 DEFAULT CHARSET=utf8 COMMENT='app登录token表';

-- ----------------------------
-- Table structure for bc_city
-- ----------------------------
DROP TABLE IF EXISTS `bc_city`;
CREATE TABLE `bc_city` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `city_code` varchar(32) NOT NULL COMMENT '城市内码',
  `city_name` varchar(128) NOT NULL COMMENT '城市名称',
  `city_name_all` varchar(4096) NOT NULL COMMENT '城市名称多语言',
  `lat` decimal(10,7) NOT NULL COMMENT '纬度',
  `lon` decimal(10,7) NOT NULL COMMENT '经度',
  `init_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '初始化状态，0:平台出售 1:用户出售',
  `is_lock` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否锁定(防并发购买) 0:unlock 1:lock ',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_show` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否显示（0不显示 1显示）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_city_code` (`city_code`) USING BTREE,
  KEY `index_city_name` (`city_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9712 DEFAULT CHARSET=utf8 COMMENT='城市表';

CREATE TABLE `bc_city_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `city_code` varchar(32) NOT NULL COMMENT '城市内码',
  `user_code` varchar(32) NOT NULL COMMENT '用户内码',
  `order_no` varchar(32) NOT NULL COMMENT '交易内码',
  `message` varchar(4096) DEFAULT NULL COMMENT '留言信息',
  `city_sell_status` tinyint(1) NOT NULL DEFAULT '2' COMMENT '状态，1:待出售 2:暂不出售 ',
  `url` varchar(128) DEFAULT NULL COMMENT '链接地址',
  `amount` decimal(20,2) DEFAULT '0.00' COMMENT '价格',
  `currency` varchar(5) DEFAULT 'USD' COMMENT '货币单位 BSV  USD',
  `message_status` bit(1) NOT NULL DEFAULT b'1' COMMENT '留言状态 0:禁用不显示 ,1:正常',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `index_detail` (`city_code`,`user_code`,`order_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8 COMMENT='城市详情表';


-- ----------------------------
-- Table structure for bc_city_detail_history
-- ----------------------------
DROP TABLE IF EXISTS `bc_city_detail_history`;
CREATE TABLE `bc_city_detail_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `his_id` int(11) NOT NULL COMMENT '原先id',
  `city_code` varchar(32) NOT NULL COMMENT '城市内码',
  `his_user_code` varchar(32) NOT NULL COMMENT '用户内码',
  `his_order_no` varchar(32) NOT NULL COMMENT '交易内码',
  `his_message_status` bit(1) NOT NULL DEFAULT b'1' COMMENT '留言状态 0:禁用不显示 ,1:正常',
  `his_message` varchar(4096) DEFAULT NULL COMMENT '留言信息',
  `his_city_sell_status` tinyint(1) NOT NULL DEFAULT '2' COMMENT '状态，1:待出售 2:暂不出售 ',
  `his_url` varchar(128) DEFAULT NULL COMMENT 'l链接地址',
  `his_amount` decimal(20,2) DEFAULT '0.00' COMMENT '价格',
  `his_currency` varchar(5) DEFAULT 'USD' COMMENT '货币单位 BSV  USD',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_his_id` (`his_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='城市详情历史表';


-- ----------------------------
-- Table structure for bc_message_send
-- ----------------------------
DROP TABLE IF EXISTS `bc_message_send`;
CREATE TABLE `bc_message_send` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `event` varchar(64) NOT NULL COMMENT '消息事件',
  `data_id` text COMMENT '消息内容',
  `expand_data_id` varchar(32) DEFAULT NULL COMMENT '拓展数据',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=584739 DEFAULT CHARSET=utf8 COMMENT='消息表';


-- ----------------------------
-- Table structure for bc_order
-- ----------------------------
DROP TABLE IF EXISTS `bc_order`;
CREATE TABLE `bc_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `buyer_code` varchar(32) NOT NULL COMMENT '买方用户',
  `seller_code` varchar(32) NOT NULL COMMENT '卖方用户(首次售卖为platform_001)',
  `city_code` varchar(32) NOT NULL COMMENT '城市内码',
  `order_no` varchar(32) NOT NULL COMMENT '唯一订单号 buttonId',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '订单状态 0:PENDING 1:RECEIVED 2.COMPLETED 3.FAILED 4.CLOSED',
  `amount` decimal(20,2) DEFAULT '0.00' COMMENT '交易总价格',
  `currency` varchar(5) DEFAULT 'USD' COMMENT '货币单位 BSV  USD',
  `txid` varchar(64) DEFAULT '' COMMENT 'moneybutton交易号',
  `normalizedTxid` varchar(128) DEFAULT '',
  `satoshis` varchar(128) DEFAULT '',
  `buttonData` varchar(128) DEFAULT '',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `plat_amount` decimal(20,2) DEFAULT NULL,
  `seller_amount` decimal(20,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_order_no` (`order_no`) USING BTREE,
  KEY `index_buyser_code` (`buyer_code`) USING BTREE,
  KEY `index_seller_code` (`seller_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=195 DEFAULT CHARSET=utf8 COMMENT='交易表';



-- ----------------------------
-- Table structure for bc_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `bc_order_detail`;
CREATE TABLE `bc_order_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `order_no` varchar(32) NOT NULL COMMENT '唯一订单号 buttonId',
  `user_code` varchar(32) DEFAULT '' COMMENT '用户内码/平台内码',
  `amount` decimal(20,2) DEFAULT '0.00' COMMENT '价格',
  `currency` varchar(5) DEFAULT 'USD' COMMENT '货币单位 BSV  USD',
  `type` varchar(3) DEFAULT NULL COMMENT '交易金钱方向（IN ， OUT）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `index_user_code` (`user_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8 COMMENT='交易详情表';


-- ----------------------------
-- Table structure for bc_platform
-- ----------------------------
DROP TABLE IF EXISTS `bc_platform`;
CREATE TABLE `bc_platform` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `platform_code` varchar(32) DEFAULT 'platform_001' COMMENT '平台编码',
  `plat_name` varchar(64) DEFAULT 'platform_001' COMMENT '平台名称',
  `amount` decimal(20,2) DEFAULT '0.00' COMMENT '城市初始价格',
  `currency` varchar(5) DEFAULT 'USD' COMMENT '货币单位 BSV  USD',
  `fee_rate` decimal(3,3) DEFAULT '0.000' COMMENT '分佣比例',
  `wallet_address` varchar(128) DEFAULT NULL COMMENT '平台钱包地址',
  `client_identifier` varchar(64) NOT NULL COMMENT 'moneybutton Client Identifier',
  `client_secret` varchar(64) NOT NULL COMMENT 'moneybutton Client Secret',
  `webhook_secret` varchar(64) NOT NULL COMMENT 'moneybutton Webhook Secret',
  `webhook_url` varchar(256) NOT NULL COMMENT 'moneybutton Webhook Url',
  `oauth_identifier` varchar(64) NOT NULL COMMENT 'moneybutton OAuth Identifier',
  `oauth_redirect_url` varchar(256) NOT NULL COMMENT 'moneybutton OAuth Redirect URL',
  `palt_message` varchar(255) DEFAULT NULL COMMENT '平台留言',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='平台信息表';

-- ----------------------------
-- Records of bc_platform
-- ----------------------------
INSERT INTO `bc_platform` VALUES ('1', 'platform_001', 'platform_001', '1.00', 'USD', '0.200', '7607', 'a34bfa12c184b4cd83ccb7d91aaca2ea', '951589876959b60a9d96b1c2c05dfd8b', '23cff9c4b954aa362831f15189baf1a4', 'https://cityonchain.com/api/notify', 'cd41bec17935578ba85cda78b447dc88', 'https://cityonchain.com/oauth', null);

-- ----------------------------
-- Table structure for bc_user
-- ----------------------------
DROP TABLE IF EXISTS `bc_user`;
CREATE TABLE `bc_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_code` varchar(32) NOT NULL COMMENT 'oauth id',
  `user_name` varchar(32) NOT NULL COMMENT '用户名',
  `register_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_user_code` (`user_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='用户表';


-- ----------------------------
-- Table structure for bc_userbak
-- ----------------------------
DROP TABLE IF EXISTS `bc_userbak`;
CREATE TABLE `bc_userbak` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_code` varchar(32) NOT NULL COMMENT '用户编号',
  `user_name` varchar(32) NOT NULL COMMENT '用户名',
  `email` varchar(64) NOT NULL COMMENT '用户邮箱',
  `pwd` varchar(100) NOT NULL COMMENT '登录密码',
  `wallet_address` varchar(128) DEFAULT NULL COMMENT '钱包地址',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态，0：已注册，1：已认证',
  `register_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_user_code` (`user_code`) USING BTREE,
  UNIQUE KEY `index_user_name` (`user_name`) USING BTREE,
  UNIQUE KEY `index_email` (`email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='用户表';


-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sent_user` varchar(20) NOT NULL DEFAULT '0' COMMENT '发送用户',
  `receive_user` varchar(20) NOT NULL DEFAULT '0' COMMENT '接收用户',
  `title` varchar(100) NOT NULL DEFAULT '' COMMENT '标题',
  `content` text NOT NULL COMMENT '内容',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态，0：未读，1：已读',
  `del_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除类型，0：未删除，1：已删除',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `add_ip` varchar(15) DEFAULT '' COMMENT '添加ip',
  `nid` varchar(30) DEFAULT NULL COMMENT 'nid标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='站内信表';

-- ----------------------------
-- Records of message
-- ----------------------------

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `nid` varchar(30) NOT NULL DEFAULT '' COMMENT 'nid标识',
  `type` int(1) NOT NULL COMMENT '通知类型，1：短信，2：邮件，3：站内信',
  `sent_user` varchar(20) NOT NULL DEFAULT '0' COMMENT '发送用户',
  `receive_user` varchar(20) NOT NULL DEFAULT '0' COMMENT '接收用户',
  `receive_addr` varchar(50) NOT NULL DEFAULT '' COMMENT '接收地址',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态，0：未发送，1：已发送',
  `title` varchar(50) NOT NULL DEFAULT '' COMMENT '标题',
  `content` text NOT NULL COMMENT '发送内容',
  `code` varchar(32) DEFAULT NULL COMMENT '验证码',
  `result` varchar(1000) DEFAULT '' COMMENT '发送结果信息',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8 COMMENT='通知记录表';

-- ----------------------------
-- Records of notice
-- ----------------------------

-- ----------------------------
-- Table structure for notice_type
-- ----------------------------
DROP TABLE IF EXISTS `notice_type`;
CREATE TABLE `notice_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '发送类型，1：系统通知，2：用户通知',
  `nid` varchar(30) NOT NULL DEFAULT '' COMMENT 'nid标识',
  `notice_type` tinyint(1) NOT NULL COMMENT '通知类型，1：短信，2：邮件，3：站内信',
  `name` varchar(30) NOT NULL DEFAULT '' COMMENT '名称',
  `title_templet` varchar(250) NOT NULL DEFAULT '' COMMENT '标题的freemarker模板',
  `templet` text NOT NULL COMMENT '内容的freemarker模板',
  `send` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否发送，0：不发送，1：发送',
  `can_switch` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否可以切换发送类型，0：不可以切换，1：可以切换',
  `remark` varchar(250) DEFAULT '' COMMENT '备注',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `add_ip` varchar(15) DEFAULT '' COMMENT '添加IP',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_ip` varchar(15) DEFAULT '' COMMENT '更新IP',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_unique` (`nid`,`type`,`notice_type`) USING BTREE,
  KEY `index_normal` (`nid`,`type`,`notice_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='通知类型表';

-- ----------------------------
-- Records of notice_type
-- ----------------------------

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(32) NOT NULL COMMENT '名称',
  `profile` varchar(32) NOT NULL COMMENT '说明',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of roles
-- ----------------------------

-- ----------------------------
-- Table structure for s_config
-- ----------------------------
DROP TABLE IF EXISTS `s_config`;
CREATE TABLE `s_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(30) NOT NULL DEFAULT '' COMMENT '名称',
  `nid` varchar(50) NOT NULL DEFAULT '' COMMENT '标识',
  `value` varchar(600) DEFAULT NULL COMMENT '名称对应的值',
  `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '类型 1:系统底层配置信息， 2:各种费率配置信息 ，3:邮件/短信配置信息 ，4:附加增值功能配置信息 ，5:第三方资金托管相关的配置 ',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态，0：禁用，1：启用',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_unique_nid` (`nid`) USING BTREE,
  KEY `index_normal_nid` (`nid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='系统参数表';

-- ----------------------------
-- Records of s_config
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `admin_id` int(11) NOT NULL COMMENT '操作人员',
  `module_type` varchar(32) NOT NULL COMMENT '操作模块',
  `oprate_type` varchar(32) NOT NULL COMMENT '操作类型',
  `name` varchar(200) NOT NULL COMMENT '操作名称',
  `uri` varchar(200) NOT NULL COMMENT '操作url',
  `msg` varchar(1000) NOT NULL COMMENT '操作内容',
  `ip` varchar(200) NOT NULL COMMENT '操作ip',
  `date_add` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2060 DEFAULT CHARSET=utf8 COMMENT='后台系统操作日志表';


-- ----------------------------
-- Table structure for sys_task_handel
-- ----------------------------
DROP TABLE IF EXISTS `sys_task_handel`;
CREATE TABLE `sys_task_handel` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `module_name` varchar(64) NOT NULL COMMENT '任务名称',
  `hostname` varchar(64) NOT NULL COMMENT '执行机器名称',
  `ip_address` varchar(64) NOT NULL COMMENT '执行机器ip',
  `is_enabled` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否启用，0：禁用，1：启用',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='定时任务表';


-- ----------------------------
-- Table structure for sys_task_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_task_log`;
CREATE TABLE `sys_task_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `module_name` varchar(64) NOT NULL COMMENT '任务名称',
  `hostname` varchar(64) NOT NULL COMMENT '执行机器名称',
  `ip_address` varchar(64) NOT NULL COMMENT '执行机器ip',
  `is_success` bit(1) NOT NULL COMMENT '是否执行成功，0：失败，1：成功',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=129473 DEFAULT CHARSET=utf8 COMMENT='定时任务日志表';

