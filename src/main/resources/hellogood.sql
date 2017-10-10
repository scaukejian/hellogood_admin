/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50523
Source Host           : localhost:3306
Source Database       : hellogood

Target Server Type    : MYSQL
Target Server Version : 50523
File Encoding         : 65001

Date: 2017-09-27 16:57:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin_action`
-- ----------------------------
DROP TABLE IF EXISTS `admin_action`;
CREATE TABLE `admin_action` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `NAME` varchar(50) DEFAULT NULL COMMENT '菜单名',
  `url` varchar(200) CHARACTER SET latin1 DEFAULT NULL COMMENT '菜单url',
  `type` varchar(50) DEFAULT NULL COMMENT '菜单类型  1一级 2二级 3三级...',
  `available` tinyint(2) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL COMMENT '菜单描述',
  `parent` bigint(20) DEFAULT NULL COMMENT '父级菜单',
  `seqNum` bigint(4) DEFAULT NULL COMMENT '菜单序号',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=193 DEFAULT CHARSET=utf8 COMMENT=' 后台菜单';

-- ----------------------------
-- Records of admin_action
-- ----------------------------
INSERT INTO `admin_action` VALUES ('32', '系统管理', '', '1', '0', '权限管理', '0', '9999', null, '2016-09-09 19:01:48');
INSERT INTO `admin_action` VALUES ('33', '菜单管理', '/pages/action/action-list.jsp', '2', '0', '系统管理--菜单管理', '32', '3', null, null);
INSERT INTO `admin_action` VALUES ('34', '员工管理', '/pages/employee/employee-list.jsp', '2', '0', '系统管理--员工管理', '32', '2', null, '2015-05-28 19:18:15');
INSERT INTO `admin_action` VALUES ('35', '角色管理', '/pages/role/role-list.jsp', '2', '0', '系统管理---角色管理', '32', '1', null, '2017-09-20 16:57:42');
INSERT INTO `admin_action` VALUES ('191', '用户管理', '', '1', '0', '', '0', '1', '2017-09-25 10:40:34', '2017-09-25 10:40:34');
INSERT INTO `admin_action` VALUES ('192', '用户列表', '/pages/user/user-list.jsp', '2', '0', '', '191', '6', '2017-09-25 10:41:13', '2017-09-25 10:41:13');

-- ----------------------------
-- Table structure for `admin_employee`
-- ----------------------------
DROP TABLE IF EXISTS `admin_employee`;
CREATE TABLE `admin_employee` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `account` varchar(100) NOT NULL COMMENT '账号',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `position` varchar(100) DEFAULT NULL COMMENT '职务',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile_phone` varchar(20) DEFAULT NULL COMMENT '手机',
  `city` varchar(100) DEFAULT NULL COMMENT '城市',
  `status` tinyint(2) DEFAULT NULL COMMENT '员工状态 0 禁用 1 有效 2删除状态',
  `telephone` varchar(20) DEFAULT NULL COMMENT '电话',
  `org_id` varchar(100) DEFAULT NULL COMMENT '部门',
  `nation` varchar(100) DEFAULT NULL COMMENT '民族',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `user_code` varchar(100) DEFAULT NULL COMMENT '账号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='后台员工表';

-- ----------------------------
-- Records of admin_employee
-- ----------------------------
INSERT INTO `admin_employee` VALUES ('6', '系统超级管理员', 'sysadmin', '0668CFCF8CE4E0433684581393D11EB4', null, 'sysadmin@edate.com', '18000000112', '深圳-上海-广州-北京', '1', null, null, null, null, '2017-06-29 14:49:35', null);
INSERT INTO `admin_employee` VALUES ('7', '审核', 'resadmin', '21138EC0905448C52A553EEE8A93CB74', null, 'resadmin@edate.com.cn', '18000000000', '广州', '1', null, null, null, null, '2017-06-27 11:14:17', '');
INSERT INTO `admin_employee` VALUES ('10', '测试员', 'ceshi', '90DB7A241EE36462C1D24A2CC45C1C8C', null, 'sjfksjf@lskdf.sd', '18867351255', '', '2', null, null, null, '2015-08-25 17:15:08', '2015-08-25 17:15:08', null);
INSERT INTO `admin_employee` VALUES ('15', '测试验收', 'uat', 'FF26C4E8C0A8429911FC2756ADFCFAE8', null, '19511112222@11.com', '13011112222', null, '1', null, null, null, '2016-11-07 16:29:17', '2017-05-17 16:46:15', null);
INSERT INTO `admin_employee` VALUES ('24', '测试用户', 'test0508', 'F69FEDF663E57C99DBCC3F1BDEEB6579', null, '4435162646@qq.com', '18000000000', '北京-上海', '1', null, null, null, null, '2017-05-17 16:46:15', '');
INSERT INTO `admin_employee` VALUES ('25', '柯坚测试', 'kejian', '043FC02C4E8BDCE70B1E4B4AAAE4C8E3', null, '954657344@qq.com', '15989099048', '', '1', null, null, null, '2017-09-19 17:37:17', '2017-09-19 17:38:39', null);

-- ----------------------------
-- Table structure for `admin_emp_role_relation`
-- ----------------------------
DROP TABLE IF EXISTS `admin_emp_role_relation`;
CREATE TABLE `admin_emp_role_relation` (
  `emp_id` bigint(20) NOT NULL COMMENT '员工id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`emp_id`,`role_id`),
  KEY `fk_back_emp_role_relation_roleid` (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='员工角色关系表';

-- ----------------------------
-- Records of admin_emp_role_relation
-- ----------------------------
INSERT INTO `admin_emp_role_relation` VALUES ('6', '17');
INSERT INTO `admin_emp_role_relation` VALUES ('25', '17');
INSERT INTO `admin_emp_role_relation` VALUES ('7', '18');
INSERT INTO `admin_emp_role_relation` VALUES ('7', '19');
INSERT INTO `admin_emp_role_relation` VALUES ('6', '22');
INSERT INTO `admin_emp_role_relation` VALUES ('15', '23');
INSERT INTO `admin_emp_role_relation` VALUES ('25', '23');

-- ----------------------------
-- Table structure for `admin_err_log`
-- ----------------------------
DROP TABLE IF EXISTS `admin_err_log`;
CREATE TABLE `admin_err_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `emp_name` varchar(100) DEFAULT NULL COMMENT '用户名称',
  `emp_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `type` varchar(100) DEFAULT NULL COMMENT '日志类型：ORDER, USER',
  `target_id` int(11) DEFAULT NULL COMMENT '目标ID 订单或用户ID',
  `operation_time` datetime DEFAULT NULL COMMENT '操作时间',
  `remark` text COMMENT '备注',
  `processor` int(11) DEFAULT NULL COMMENT '处理者',
  `process_time` datetime DEFAULT NULL COMMENT '处理时间',
  `process_status` varchar(10) DEFAULT NULL COMMENT '处理状态：待处理, 处理中, 已处理',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_err_log
-- ----------------------------

-- ----------------------------
-- Table structure for `admin_role`
-- ----------------------------
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `code` varchar(20) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL COMMENT '角色名',
  `description` varchar(200) DEFAULT NULL COMMENT '角色描述',
  `level` tinyint(2) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='后台角色表';

-- ----------------------------
-- Records of admin_role
-- ----------------------------
INSERT INTO `admin_role` VALUES ('17', 'sysadmin', '系统超级管理员', '系统超级管理员', '0', null, '2016-08-04 18:01:43');
INSERT INTO `admin_role` VALUES ('18', 'check', '审核资料专员', '审核资料专员', '0', null, '2016-08-04 18:01:57');
INSERT INTO `admin_role` VALUES ('19', 'customerService', '客服专员', '客服专员', '0', null, '2016-08-04 18:02:06');
INSERT INTO `admin_role` VALUES ('21', 'sales', '销售专员', '销售专员', '0', '2016-08-04 18:06:48', '2016-08-04 18:06:48');
INSERT INTO `admin_role` VALUES ('22', 'customerManager', '客服总监', '客服总监', '0', '2016-09-09 15:34:04', '2016-09-09 15:34:04');
INSERT INTO `admin_role` VALUES ('23', 'uat', 'UAT测试验收', '测试验收-投资人', '0', '2016-11-07 16:26:08', '2016-11-07 17:41:14');

-- ----------------------------
-- Table structure for `admin_role_action_relation`
-- ----------------------------
DROP TABLE IF EXISTS `admin_role_action_relation`;
CREATE TABLE `admin_role_action_relation` (
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `action_id` bigint(20) NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`role_id`,`action_id`),
  KEY `fk_back_role_action_relation_actionid` (`action_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='角色菜单关系表';

-- ----------------------------
-- Records of admin_role_action_relation
-- ----------------------------
INSERT INTO `admin_role_action_relation` VALUES ('17', '32');
INSERT INTO `admin_role_action_relation` VALUES ('17', '33');
INSERT INTO `admin_role_action_relation` VALUES ('17', '34');
INSERT INTO `admin_role_action_relation` VALUES ('18', '34');
INSERT INTO `admin_role_action_relation` VALUES ('17', '35');
INSERT INTO `admin_role_action_relation` VALUES ('17', '191');
INSERT INTO `admin_role_action_relation` VALUES ('17', '192');
INSERT INTO `admin_role_action_relation` VALUES ('18', '192');

-- ----------------------------
-- Table structure for `api_area`
-- ----------------------------
DROP TABLE IF EXISTS `api_area`;
CREATE TABLE `api_area` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `code` int(12) DEFAULT NULL COMMENT '城市代码',
  `name` varchar(20) DEFAULT NULL COMMENT '城市名称',
  `pinyin_name` varchar(128) DEFAULT NULL COMMENT '城市拼音名称',
  `parent_id` int(12) DEFAULT NULL COMMENT '父id',
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=510 DEFAULT CHARSET=utf8 COMMENT='城市区域表';

-- ----------------------------
-- Records of api_area
-- ----------------------------
INSERT INTO `api_area` VALUES ('5', '130100', '石家庄', 'shijiazhuang', '130000');
INSERT INTO `api_area` VALUES ('6', '130200', '唐山', 'tangshan', '130000');
INSERT INTO `api_area` VALUES ('7', '130300', '秦皇岛', 'qinhuangdao', '130000');
INSERT INTO `api_area` VALUES ('8', '130400', '邯郸', 'handan', '130000');
INSERT INTO `api_area` VALUES ('9', '130500', '邢台', 'xingtai', '130000');
INSERT INTO `api_area` VALUES ('10', '130600', '保定', 'baoding', '130000');
INSERT INTO `api_area` VALUES ('11', '130700', '张家口', 'zhangjiakou', '130000');
INSERT INTO `api_area` VALUES ('12', '130800', '承德', 'chengde', '130000');
INSERT INTO `api_area` VALUES ('13', '130900', '沧州', 'cangzhou', '130000');
INSERT INTO `api_area` VALUES ('14', '131000', '廊坊', 'langfang', '130000');
INSERT INTO `api_area` VALUES ('15', '131100', '衡水', 'hengshui', '130000');
INSERT INTO `api_area` VALUES ('16', '140100', '太原', 'taiyuan', '140000');
INSERT INTO `api_area` VALUES ('17', '140200', '大同', 'datong', '140000');
INSERT INTO `api_area` VALUES ('18', '140300', '阳泉', 'yangquan', '140000');
INSERT INTO `api_area` VALUES ('19', '140400', '长治', 'zhangzhi', '140000');
INSERT INTO `api_area` VALUES ('20', '140500', '晋城', 'jincheng', '140000');
INSERT INTO `api_area` VALUES ('21', '140600', '朔州', 'shuozhou', '140000');
INSERT INTO `api_area` VALUES ('22', '140700', '晋中', 'jinzhong', '140000');
INSERT INTO `api_area` VALUES ('23', '140800', '运城', 'yuncheng', '140000');
INSERT INTO `api_area` VALUES ('24', '140900', '忻州', 'xinzhou', '140000');
INSERT INTO `api_area` VALUES ('25', '141000', '临汾', 'linfen', '140000');
INSERT INTO `api_area` VALUES ('26', '141100', '吕梁', 'lu:liang', '140000');
INSERT INTO `api_area` VALUES ('27', '150100', '呼和浩特', 'huhehaote', '150000');
INSERT INTO `api_area` VALUES ('28', '150200', '包头', 'baotou', '150000');
INSERT INTO `api_area` VALUES ('29', '150300', '乌海', 'wuhai', '150000');
INSERT INTO `api_area` VALUES ('30', '150400', '赤峰', 'chifeng', '150000');
INSERT INTO `api_area` VALUES ('31', '150500', '通辽', 'tongliao', '150000');
INSERT INTO `api_area` VALUES ('32', '150600', '鄂尔多斯', 'eerduosi', '150000');
INSERT INTO `api_area` VALUES ('33', '150700', '呼伦贝尔', 'hulunbeier', '150000');
INSERT INTO `api_area` VALUES ('34', '150800', '巴彦淖尔', 'bayannaoer', '150000');
INSERT INTO `api_area` VALUES ('35', '150900', '乌兰察布', 'wulanchabu', '150000');
INSERT INTO `api_area` VALUES ('36', '152200', '兴安盟', 'xinganmeng', '150000');
INSERT INTO `api_area` VALUES ('37', '152500', '锡林郭勒盟', 'xilinguolemeng', '150000');
INSERT INTO `api_area` VALUES ('38', '152900', '阿拉善盟', 'alashanmeng', '150000');
INSERT INTO `api_area` VALUES ('39', '210100', '沈阳', 'shenyang', '210000');
INSERT INTO `api_area` VALUES ('40', '210200', '大连', 'dalian', '210000');
INSERT INTO `api_area` VALUES ('41', '210300', '鞍山', 'anshan', '210000');
INSERT INTO `api_area` VALUES ('42', '210400', '抚顺', 'fushun', '210000');
INSERT INTO `api_area` VALUES ('43', '210500', '本溪', 'benxi', '210000');
INSERT INTO `api_area` VALUES ('44', '210600', '丹东', 'dandong', '210000');
INSERT INTO `api_area` VALUES ('45', '210700', '锦州', 'jinzhou', '210000');
INSERT INTO `api_area` VALUES ('46', '210800', '营口', 'yingkou', '210000');
INSERT INTO `api_area` VALUES ('47', '210900', '阜新', 'fuxin', '210000');
INSERT INTO `api_area` VALUES ('48', '211000', '辽阳', 'liaoyang', '210000');
INSERT INTO `api_area` VALUES ('49', '211100', '盘锦', 'panjin', '210000');
INSERT INTO `api_area` VALUES ('50', '211200', '铁岭', 'tieling', '210000');
INSERT INTO `api_area` VALUES ('51', '211300', '朝阳', 'chaoyang', '210000');
INSERT INTO `api_area` VALUES ('52', '211400', '葫芦岛', 'huludao', '210000');
INSERT INTO `api_area` VALUES ('53', '220100', '长春', 'zhangchun', '220000');
INSERT INTO `api_area` VALUES ('54', '220200', '吉林', 'jilin', '220000');
INSERT INTO `api_area` VALUES ('55', '220300', '四平', 'siping', '220000');
INSERT INTO `api_area` VALUES ('56', '220400', '辽源', 'liaoyuan', '220000');
INSERT INTO `api_area` VALUES ('57', '220500', '通化', 'tonghua', '220000');
INSERT INTO `api_area` VALUES ('58', '220600', '白山', 'baishan', '220000');
INSERT INTO `api_area` VALUES ('59', '220700', '松原', 'songyuan', '220000');
INSERT INTO `api_area` VALUES ('60', '220800', '白城', 'baicheng', '220000');
INSERT INTO `api_area` VALUES ('61', '222400', '延边', 'yanbian', '220000');
INSERT INTO `api_area` VALUES ('62', '230100', '哈尔滨', 'haerbin', '230000');
INSERT INTO `api_area` VALUES ('63', '230200', '齐齐哈尔', 'qiqihaer', '230000');
INSERT INTO `api_area` VALUES ('64', '230300', '鸡西', 'jixi', '230000');
INSERT INTO `api_area` VALUES ('65', '230400', '鹤岗', 'hegang', '230000');
INSERT INTO `api_area` VALUES ('66', '230500', '双鸭山', 'shuangyashan', '230000');
INSERT INTO `api_area` VALUES ('67', '230600', '大庆', 'daqing', '230000');
INSERT INTO `api_area` VALUES ('68', '230700', '伊春', 'yichun', '230000');
INSERT INTO `api_area` VALUES ('69', '230800', '佳木斯', 'jiamusi', '230000');
INSERT INTO `api_area` VALUES ('70', '230900', '七台河', 'qitaihe', '230000');
INSERT INTO `api_area` VALUES ('71', '231000', '牡丹江', 'mudanjiang', '230000');
INSERT INTO `api_area` VALUES ('72', '231100', '黑河', 'heihe', '230000');
INSERT INTO `api_area` VALUES ('73', '231200', '绥化', 'suihua', '230000');
INSERT INTO `api_area` VALUES ('74', '232700', '大兴安岭', 'daxinganling', '230000');
INSERT INTO `api_area` VALUES ('75', '310000', '上海', 'shanghai', '310000');
INSERT INTO `api_area` VALUES ('77', '320100', '南京', 'nanjing', '320000');
INSERT INTO `api_area` VALUES ('78', '320200', '无锡', 'wuxi', '320000');
INSERT INTO `api_area` VALUES ('79', '320300', '徐州', 'xuzhou', '320000');
INSERT INTO `api_area` VALUES ('80', '320400', '常州', 'changzhou', '320000');
INSERT INTO `api_area` VALUES ('81', '320500', '苏州', 'suzhou', '320000');
INSERT INTO `api_area` VALUES ('82', '320600', '南通', 'nantong', '320000');
INSERT INTO `api_area` VALUES ('83', '320700', '连云港', 'lianyungang', '320000');
INSERT INTO `api_area` VALUES ('84', '320800', '淮安', 'huaian', '320000');
INSERT INTO `api_area` VALUES ('85', '320900', '盐城', 'yancheng', '320000');
INSERT INTO `api_area` VALUES ('86', '321000', '扬州', 'yangzhou', '320000');
INSERT INTO `api_area` VALUES ('87', '321100', '镇江', 'zhenjiang', '320000');
INSERT INTO `api_area` VALUES ('88', '321200', '泰州', 'taizhou', '320000');
INSERT INTO `api_area` VALUES ('89', '321300', '宿迁', 'suqian', '320000');
INSERT INTO `api_area` VALUES ('90', '330100', '杭州', 'hangzhou', '330000');
INSERT INTO `api_area` VALUES ('91', '330200', '宁波', 'ningbo', '330000');
INSERT INTO `api_area` VALUES ('92', '330300', '温州', 'wenzhou', '330000');
INSERT INTO `api_area` VALUES ('93', '330400', '嘉兴', 'jiaxing', '330000');
INSERT INTO `api_area` VALUES ('94', '330500', '湖州', 'huzhou', '330000');
INSERT INTO `api_area` VALUES ('95', '330600', '绍兴', 'shaoxing', '330000');
INSERT INTO `api_area` VALUES ('96', '330700', '金华', 'jinhua', '330000');
INSERT INTO `api_area` VALUES ('97', '330800', '衢州', 'quzhou', '330000');
INSERT INTO `api_area` VALUES ('98', '330900', '舟山', 'zhoushan', '330000');
INSERT INTO `api_area` VALUES ('99', '331000', '台州', 'taizhou', '330000');
INSERT INTO `api_area` VALUES ('100', '331100', '丽水', 'lishui', '330000');
INSERT INTO `api_area` VALUES ('101', '340100', '合肥', 'hefei', '340000');
INSERT INTO `api_area` VALUES ('102', '340200', '芜湖', 'wuhu', '340000');
INSERT INTO `api_area` VALUES ('103', '340300', '蚌埠', 'bangbu', '340000');
INSERT INTO `api_area` VALUES ('104', '340400', '淮南', 'huainan', '340000');
INSERT INTO `api_area` VALUES ('105', '340500', '马鞍山', 'maanshan', '340000');
INSERT INTO `api_area` VALUES ('106', '340600', '淮北', 'huaibei', '340000');
INSERT INTO `api_area` VALUES ('107', '340700', '铜陵', 'tongling', '340000');
INSERT INTO `api_area` VALUES ('108', '340800', '安庆', 'anqing', '340000');
INSERT INTO `api_area` VALUES ('109', '341000', '黄山', 'huangshan', '340000');
INSERT INTO `api_area` VALUES ('110', '341100', '滁州', 'chuzhou', '340000');
INSERT INTO `api_area` VALUES ('111', '341200', '阜阳', 'fuyang', '340000');
INSERT INTO `api_area` VALUES ('112', '341300', '宿州', 'suzhou', '340000');
INSERT INTO `api_area` VALUES ('113', '341400', '巢湖', 'chaohu', '340000');
INSERT INTO `api_area` VALUES ('114', '341500', '六安', 'liuan', '340000');
INSERT INTO `api_area` VALUES ('115', '341600', '亳州', 'bozhou', '340000');
INSERT INTO `api_area` VALUES ('116', '341700', '池州', 'chizhou', '340000');
INSERT INTO `api_area` VALUES ('117', '341800', '宣城', 'xuancheng', '340000');
INSERT INTO `api_area` VALUES ('118', '350100', '福州', 'fuzhou', '350000');
INSERT INTO `api_area` VALUES ('119', '350200', '厦门', 'shamen', '350000');
INSERT INTO `api_area` VALUES ('120', '350300', '莆田', 'putian', '350000');
INSERT INTO `api_area` VALUES ('121', '350400', '三明', 'sanming', '350000');
INSERT INTO `api_area` VALUES ('122', '350500', '泉州', 'quanzhou', '350000');
INSERT INTO `api_area` VALUES ('123', '350600', '漳州', 'zhangzhou', '350000');
INSERT INTO `api_area` VALUES ('124', '350700', '南平', 'nanping', '350000');
INSERT INTO `api_area` VALUES ('125', '350800', '龙岩', 'longyan', '350000');
INSERT INTO `api_area` VALUES ('126', '350900', '宁德', 'ningde', '350000');
INSERT INTO `api_area` VALUES ('127', '360100', '南昌', 'nanchang', '360000');
INSERT INTO `api_area` VALUES ('128', '360200', '景德镇', 'jingdezhen', '360000');
INSERT INTO `api_area` VALUES ('129', '360300', '萍乡', 'pingxiang', '360000');
INSERT INTO `api_area` VALUES ('130', '360400', '九江', 'jiujiang', '360000');
INSERT INTO `api_area` VALUES ('131', '360500', '新余', 'xinyu', '360000');
INSERT INTO `api_area` VALUES ('132', '360600', '鹰潭', 'yingtan', '360000');
INSERT INTO `api_area` VALUES ('133', '360700', '赣州', 'ganzhou', '360000');
INSERT INTO `api_area` VALUES ('134', '360800', '吉安', 'jian', '360000');
INSERT INTO `api_area` VALUES ('135', '360900', '宜春', 'yichun', '360000');
INSERT INTO `api_area` VALUES ('136', '361000', '抚州', 'fuzhou', '360000');
INSERT INTO `api_area` VALUES ('137', '361100', '上饶', 'shangrao', '360000');
INSERT INTO `api_area` VALUES ('138', '370100', '济南', 'jinan', '370000');
INSERT INTO `api_area` VALUES ('139', '370200', '青岛', 'qingdao', '370000');
INSERT INTO `api_area` VALUES ('140', '370300', '淄博', 'zibo', '370000');
INSERT INTO `api_area` VALUES ('141', '370400', '枣庄', 'zaozhuang', '370000');
INSERT INTO `api_area` VALUES ('142', '370500', '东营', 'dongying', '370000');
INSERT INTO `api_area` VALUES ('143', '370600', '烟台', 'yantai', '370000');
INSERT INTO `api_area` VALUES ('144', '370700', '潍坊', 'weifang', '370000');
INSERT INTO `api_area` VALUES ('145', '370800', '济宁', 'jining', '370000');
INSERT INTO `api_area` VALUES ('146', '370900', '泰安', 'taian', '370000');
INSERT INTO `api_area` VALUES ('147', '371000', '威海', 'weihai', '370000');
INSERT INTO `api_area` VALUES ('148', '371100', '日照', 'rizhao', '370000');
INSERT INTO `api_area` VALUES ('149', '371200', '莱芜', 'laiwu', '370000');
INSERT INTO `api_area` VALUES ('150', '371300', '临沂', 'linyi', '370000');
INSERT INTO `api_area` VALUES ('151', '371400', '德州', 'dezhou', '370000');
INSERT INTO `api_area` VALUES ('152', '371500', '聊城', 'liaocheng', '370000');
INSERT INTO `api_area` VALUES ('153', '371600', '滨州', 'binzhou', '370000');
INSERT INTO `api_area` VALUES ('154', '371700', '荷泽', 'heze', '370000');
INSERT INTO `api_area` VALUES ('155', '410100', '郑州', 'zhengzhou', '410000');
INSERT INTO `api_area` VALUES ('156', '410200', '开封', 'kaifeng', '410000');
INSERT INTO `api_area` VALUES ('157', '410300', '洛阳', 'luoyang', '410000');
INSERT INTO `api_area` VALUES ('158', '410400', '平顶山', 'pingdingshan', '410000');
INSERT INTO `api_area` VALUES ('159', '410500', '安阳', 'anyang', '410000');
INSERT INTO `api_area` VALUES ('160', '410600', '鹤壁', 'hebi', '410000');
INSERT INTO `api_area` VALUES ('161', '410700', '新乡', 'xinxiang', '410000');
INSERT INTO `api_area` VALUES ('162', '410800', '焦作', 'jiaozuo', '410000');
INSERT INTO `api_area` VALUES ('163', '410900', '濮阳', 'puyang', '410000');
INSERT INTO `api_area` VALUES ('164', '411000', '许昌', 'xuchang', '410000');
INSERT INTO `api_area` VALUES ('165', '411100', '漯河', 'luohe', '410000');
INSERT INTO `api_area` VALUES ('166', '411200', '三门峡', 'sanmenxia', '410000');
INSERT INTO `api_area` VALUES ('167', '411300', '南阳', 'nanyang', '410000');
INSERT INTO `api_area` VALUES ('168', '411400', '商丘', 'shangqiu', '410000');
INSERT INTO `api_area` VALUES ('169', '411500', '信阳', 'xinyang', '410000');
INSERT INTO `api_area` VALUES ('170', '411600', '周口', 'zhoukou', '410000');
INSERT INTO `api_area` VALUES ('171', '411700', '驻马店', 'zhumadian', '410000');
INSERT INTO `api_area` VALUES ('172', '420100', '武汉', 'wuhan', '420000');
INSERT INTO `api_area` VALUES ('173', '420200', '黄石', 'huangshi', '420000');
INSERT INTO `api_area` VALUES ('174', '420300', '十堰', 'shiyan', '420000');
INSERT INTO `api_area` VALUES ('175', '420500', '宜昌', 'yichang', '420000');
INSERT INTO `api_area` VALUES ('176', '420600', '襄阳', 'xiangfan', '420000');
INSERT INTO `api_area` VALUES ('177', '420700', '鄂州', 'ezhou', '420000');
INSERT INTO `api_area` VALUES ('178', '420800', '荆门', 'jingmen', '420000');
INSERT INTO `api_area` VALUES ('179', '420900', '孝感', 'xiaogan', '420000');
INSERT INTO `api_area` VALUES ('180', '421000', '荆州', 'jingzhou', '420000');
INSERT INTO `api_area` VALUES ('181', '421100', '黄冈', 'huanggang', '420000');
INSERT INTO `api_area` VALUES ('182', '421200', '咸宁', 'xianning', '420000');
INSERT INTO `api_area` VALUES ('183', '421300', '随州', 'suizhou', '420000');
INSERT INTO `api_area` VALUES ('184', '422800', '恩施', 'enshi', '420000');
INSERT INTO `api_area` VALUES ('185', '429000', '省直辖行政单位', 'shengzhixiaxingzhengdanwei', '420000');
INSERT INTO `api_area` VALUES ('186', '430100', '长沙', 'zhangsha', '430000');
INSERT INTO `api_area` VALUES ('187', '430200', '株洲', 'zhuzhou', '430000');
INSERT INTO `api_area` VALUES ('188', '430300', '湘潭', 'xiangtan', '430000');
INSERT INTO `api_area` VALUES ('189', '430400', '衡阳', 'hengyang', '430000');
INSERT INTO `api_area` VALUES ('190', '430500', '邵阳', 'shaoyang', '430000');
INSERT INTO `api_area` VALUES ('191', '430600', '岳阳', 'yueyang', '430000');
INSERT INTO `api_area` VALUES ('192', '430700', '常德', 'changde', '430000');
INSERT INTO `api_area` VALUES ('193', '430800', '张家界', 'zhangjiajie', '430000');
INSERT INTO `api_area` VALUES ('194', '430900', '益阳', 'yiyang', '430000');
INSERT INTO `api_area` VALUES ('195', '431000', '郴州', 'chenzhou', '430000');
INSERT INTO `api_area` VALUES ('196', '431100', '永州', 'yongzhou', '430000');
INSERT INTO `api_area` VALUES ('197', '431200', '怀化', 'huaihua', '430000');
INSERT INTO `api_area` VALUES ('198', '431300', '娄底', 'loudi', '430000');
INSERT INTO `api_area` VALUES ('199', '433100', '湘西', 'xiangxi', '430000');
INSERT INTO `api_area` VALUES ('200', '440100', '广州', 'guangzhou', '440000');
INSERT INTO `api_area` VALUES ('201', '440200', '韶关', 'shaoguan', '440000');
INSERT INTO `api_area` VALUES ('202', '440300', '深圳', 'shenzhen', '440000');
INSERT INTO `api_area` VALUES ('203', '440400', '珠海', 'zhuhai', '440000');
INSERT INTO `api_area` VALUES ('204', '440500', '汕头', 'shantou', '440000');
INSERT INTO `api_area` VALUES ('205', '440600', '佛山', 'foshan', '440000');
INSERT INTO `api_area` VALUES ('206', '440700', '江门', 'jiangmen', '440000');
INSERT INTO `api_area` VALUES ('207', '440800', '湛江', 'zhanjiang', '440000');
INSERT INTO `api_area` VALUES ('208', '440900', '茂名', 'maoming', '440000');
INSERT INTO `api_area` VALUES ('209', '441200', '肇庆', 'zhaoqing', '440000');
INSERT INTO `api_area` VALUES ('210', '441300', '惠州', 'huizhou', '440000');
INSERT INTO `api_area` VALUES ('211', '441400', '梅州', 'meizhou', '440000');
INSERT INTO `api_area` VALUES ('212', '441500', '汕尾', 'shanwei', '440000');
INSERT INTO `api_area` VALUES ('213', '441600', '河源', 'heyuan', '440000');
INSERT INTO `api_area` VALUES ('214', '441700', '阳江', 'yangjiang', '440000');
INSERT INTO `api_area` VALUES ('215', '441800', '清远', 'qingyuan', '440000');
INSERT INTO `api_area` VALUES ('216', '441900', '东莞', 'dongguan', '440000');
INSERT INTO `api_area` VALUES ('217', '442000', '中山', 'zhongshan', '440000');
INSERT INTO `api_area` VALUES ('218', '445100', '潮州', 'chaozhou', '440000');
INSERT INTO `api_area` VALUES ('219', '445200', '揭阳', 'jieyang', '440000');
INSERT INTO `api_area` VALUES ('220', '445300', '云浮', 'yunfu', '440000');
INSERT INTO `api_area` VALUES ('221', '450100', '南宁', 'nanning', '450000');
INSERT INTO `api_area` VALUES ('222', '450200', '柳州', 'liuzhou', '450000');
INSERT INTO `api_area` VALUES ('223', '450300', '桂林', 'guilin', '450000');
INSERT INTO `api_area` VALUES ('224', '450400', '梧州', 'wuzhou', '450000');
INSERT INTO `api_area` VALUES ('225', '450500', '北海', 'beihai', '450000');
INSERT INTO `api_area` VALUES ('226', '450600', '防城港', 'fangchenggang', '450000');
INSERT INTO `api_area` VALUES ('227', '450700', '钦州', 'qinzhou', '450000');
INSERT INTO `api_area` VALUES ('228', '450800', '贵港', 'guigang', '450000');
INSERT INTO `api_area` VALUES ('229', '450900', '玉林', 'yulin', '450000');
INSERT INTO `api_area` VALUES ('230', '451000', '百色', 'baise', '450000');
INSERT INTO `api_area` VALUES ('231', '451100', '贺州', 'hezhou', '450000');
INSERT INTO `api_area` VALUES ('232', '451200', '河池', 'hechi', '450000');
INSERT INTO `api_area` VALUES ('233', '451300', '来宾', 'laibin', '450000');
INSERT INTO `api_area` VALUES ('234', '451400', '崇左', 'chongzuo', '450000');
INSERT INTO `api_area` VALUES ('235', '460100', '海口', 'haikou', '460000');
INSERT INTO `api_area` VALUES ('236', '460200', '三亚', 'sanya', '460000');
INSERT INTO `api_area` VALUES ('237', '469000', '省直辖县级行政单位', 'shengzhixiaxianjixingzhengdanwei', '460000');
INSERT INTO `api_area` VALUES ('241', '510100', '成都', 'chengdou', '510000');
INSERT INTO `api_area` VALUES ('242', '510300', '自贡', 'zigong', '510000');
INSERT INTO `api_area` VALUES ('243', '510400', '攀枝花', 'panzhihua', '510000');
INSERT INTO `api_area` VALUES ('244', '510500', '泸州', 'luzhou', '510000');
INSERT INTO `api_area` VALUES ('245', '510600', '德阳', 'deyang', '510000');
INSERT INTO `api_area` VALUES ('246', '510700', '绵阳', 'mianyang', '510000');
INSERT INTO `api_area` VALUES ('247', '510800', '广元', 'guangyuan', '510000');
INSERT INTO `api_area` VALUES ('248', '510900', '遂宁', 'suining', '510000');
INSERT INTO `api_area` VALUES ('249', '511000', '内江', 'neijiang', '510000');
INSERT INTO `api_area` VALUES ('250', '511100', '乐山', 'leshan', '510000');
INSERT INTO `api_area` VALUES ('251', '511300', '南充', 'nanchong', '510000');
INSERT INTO `api_area` VALUES ('252', '511400', '眉山', 'meishan', '510000');
INSERT INTO `api_area` VALUES ('253', '511500', '宜宾', 'yibin', '510000');
INSERT INTO `api_area` VALUES ('254', '511600', '广安', 'guangan', '510000');
INSERT INTO `api_area` VALUES ('255', '511700', '达州', 'dazhou', '510000');
INSERT INTO `api_area` VALUES ('256', '511800', '雅安', 'yaan', '510000');
INSERT INTO `api_area` VALUES ('257', '511900', '巴中', 'bazhong', '510000');
INSERT INTO `api_area` VALUES ('258', '512000', '资阳', 'ziyang', '510000');
INSERT INTO `api_area` VALUES ('259', '513200', '阿坝', 'aba', '510000');
INSERT INTO `api_area` VALUES ('260', '513300', '甘孜', 'ganzi', '510000');
INSERT INTO `api_area` VALUES ('261', '513400', '凉山', 'liangshan', '510000');
INSERT INTO `api_area` VALUES ('262', '520100', '贵阳', 'guiyang', '520000');
INSERT INTO `api_area` VALUES ('263', '520200', '六盘水', 'liupanshui', '520000');
INSERT INTO `api_area` VALUES ('264', '520300', '遵义', 'zunyi', '520000');
INSERT INTO `api_area` VALUES ('265', '520400', '安顺', 'anshun', '520000');
INSERT INTO `api_area` VALUES ('266', '522200', '铜仁', 'tongren', '520000');
INSERT INTO `api_area` VALUES ('267', '522300', '黔西', 'qianxi', '520000');
INSERT INTO `api_area` VALUES ('268', '522400', '毕节', 'bijie', '520000');
INSERT INTO `api_area` VALUES ('269', '522600', '黔东', 'qiandong', '520000');
INSERT INTO `api_area` VALUES ('270', '522700', '黔南', 'qiannan', '520000');
INSERT INTO `api_area` VALUES ('271', '530100', '昆明', 'kunming', '530000');
INSERT INTO `api_area` VALUES ('272', '530300', '曲靖', 'qujing', '530000');
INSERT INTO `api_area` VALUES ('273', '530400', '玉溪', 'yuxi', '530000');
INSERT INTO `api_area` VALUES ('274', '530500', '保山', 'baoshan', '530000');
INSERT INTO `api_area` VALUES ('275', '530600', '昭通', 'zhaotong', '530000');
INSERT INTO `api_area` VALUES ('276', '530700', '丽江', 'lijiang', '530000');
INSERT INTO `api_area` VALUES ('277', '530800', '思茅', 'simao', '530000');
INSERT INTO `api_area` VALUES ('278', '530900', '临沧', 'lincang', '530000');
INSERT INTO `api_area` VALUES ('279', '532300', '楚雄', 'chuxiong', '530000');
INSERT INTO `api_area` VALUES ('280', '532500', '红河', 'honghe', '530000');
INSERT INTO `api_area` VALUES ('281', '532600', '文山', 'wenshan', '530000');
INSERT INTO `api_area` VALUES ('282', '532800', '西双版纳', 'xishuangbanna', '530000');
INSERT INTO `api_area` VALUES ('283', '532900', '大理', 'dali', '530000');
INSERT INTO `api_area` VALUES ('284', '533100', '德宏', 'dehong', '530000');
INSERT INTO `api_area` VALUES ('285', '533300', '怒江', 'nujiang', '530000');
INSERT INTO `api_area` VALUES ('286', '533400', '迪庆', 'diqing', '530000');
INSERT INTO `api_area` VALUES ('287', '540100', '拉萨', 'lasa', '540000');
INSERT INTO `api_area` VALUES ('288', '542100', '昌都', 'changdou', '540000');
INSERT INTO `api_area` VALUES ('289', '542200', '山南', 'shannan', '540000');
INSERT INTO `api_area` VALUES ('290', '542300', '日喀则', 'rikaze', '540000');
INSERT INTO `api_area` VALUES ('291', '542400', '那曲', 'neiqu', '540000');
INSERT INTO `api_area` VALUES ('292', '542500', '阿里', 'ali', '540000');
INSERT INTO `api_area` VALUES ('293', '542600', '林芝', 'linzhi', '540000');
INSERT INTO `api_area` VALUES ('294', '610100', '西安', 'xian', '610000');
INSERT INTO `api_area` VALUES ('295', '610200', '铜川', 'tongchuan', '610000');
INSERT INTO `api_area` VALUES ('296', '610300', '宝鸡', 'baoji', '610000');
INSERT INTO `api_area` VALUES ('297', '610400', '咸阳', 'xianyang', '610000');
INSERT INTO `api_area` VALUES ('298', '610500', '渭南', 'weinan', '610000');
INSERT INTO `api_area` VALUES ('299', '610600', '延安', 'yanan', '610000');
INSERT INTO `api_area` VALUES ('300', '610700', '汉中', 'hanzhong', '610000');
INSERT INTO `api_area` VALUES ('301', '610800', '榆林', 'yulin', '610000');
INSERT INTO `api_area` VALUES ('302', '610900', '安康', 'ankang', '610000');
INSERT INTO `api_area` VALUES ('303', '611000', '商洛', 'shangluo', '610000');
INSERT INTO `api_area` VALUES ('304', '620100', '兰州', 'lanzhou', '620000');
INSERT INTO `api_area` VALUES ('305', '620200', '嘉峪关', 'jiayuguan', '620000');
INSERT INTO `api_area` VALUES ('306', '620300', '金昌', 'jinchang', '620000');
INSERT INTO `api_area` VALUES ('307', '620400', '白银', 'baiyin', '620000');
INSERT INTO `api_area` VALUES ('308', '620500', '天水', 'tianshui', '620000');
INSERT INTO `api_area` VALUES ('309', '620600', '武威', 'wuwei', '620000');
INSERT INTO `api_area` VALUES ('310', '620700', '张掖', 'zhangye', '620000');
INSERT INTO `api_area` VALUES ('311', '620800', '平凉', 'pingliang', '620000');
INSERT INTO `api_area` VALUES ('312', '620900', '酒泉', 'jiuquan', '620000');
INSERT INTO `api_area` VALUES ('313', '621000', '庆阳', 'qingyang', '620000');
INSERT INTO `api_area` VALUES ('314', '621100', '定西', 'dingxi', '620000');
INSERT INTO `api_area` VALUES ('315', '621200', '陇南', 'longnan', '620000');
INSERT INTO `api_area` VALUES ('316', '622900', '临夏', 'linxia', '620000');
INSERT INTO `api_area` VALUES ('317', '623000', '甘南', 'gannan', '620000');
INSERT INTO `api_area` VALUES ('318', '630100', '西宁', 'xining', '630000');
INSERT INTO `api_area` VALUES ('319', '632100', '海东', 'haidong', '630000');
INSERT INTO `api_area` VALUES ('320', '632200', '海北', 'haibei', '630000');
INSERT INTO `api_area` VALUES ('321', '632300', '黄南', 'huangnan', '630000');
INSERT INTO `api_area` VALUES ('322', '632500', '海南', 'hainan', '630000');
INSERT INTO `api_area` VALUES ('323', '632600', '果洛', 'guoluo', '630000');
INSERT INTO `api_area` VALUES ('324', '632700', '玉树', 'yushu', '630000');
INSERT INTO `api_area` VALUES ('325', '632800', '海西', 'haixi', '630000');
INSERT INTO `api_area` VALUES ('326', '640100', '银川', 'yinchuan', '640000');
INSERT INTO `api_area` VALUES ('327', '640200', '石嘴山', 'shizuishan', '640000');
INSERT INTO `api_area` VALUES ('328', '640300', '吴忠', 'wuzhong', '640000');
INSERT INTO `api_area` VALUES ('329', '640400', '固原', 'guyuan', '640000');
INSERT INTO `api_area` VALUES ('330', '640500', '中卫', 'zhongwei', '640000');
INSERT INTO `api_area` VALUES ('331', '650100', '乌鲁木齐', 'wulumuqi', '650000');
INSERT INTO `api_area` VALUES ('332', '650200', '克拉玛依', 'kelamayi', '650000');
INSERT INTO `api_area` VALUES ('333', '652100', '吐鲁番', 'tulufan', '650000');
INSERT INTO `api_area` VALUES ('334', '652200', '哈密', 'hami', '650000');
INSERT INTO `api_area` VALUES ('335', '652300', '昌吉', 'changji', '650000');
INSERT INTO `api_area` VALUES ('336', '652700', '博尔塔拉', 'boertala', '650000');
INSERT INTO `api_area` VALUES ('337', '652800', '巴音郭楞', 'bayinguoleng', '650000');
INSERT INTO `api_area` VALUES ('338', '652900', '阿克苏', 'akesu', '650000');
INSERT INTO `api_area` VALUES ('339', '653000', '克孜勒苏柯尔克孜', 'kezilesukeerkezi', '650000');
INSERT INTO `api_area` VALUES ('340', '653100', '喀什', 'kashen', '650000');
INSERT INTO `api_area` VALUES ('341', '653200', '和田', 'hetian', '650000');
INSERT INTO `api_area` VALUES ('342', '654000', '伊犁哈萨克', 'yilihasake', '650000');
INSERT INTO `api_area` VALUES ('343', '654200', '塔城', 'tacheng', '650000');
INSERT INTO `api_area` VALUES ('344', '654300', '阿勒泰', 'aletai', '650000');
INSERT INTO `api_area` VALUES ('345', '659000', '省直辖行政单位', 'shengzhixiaxingzhengdanwei', '650000');
INSERT INTO `api_area` VALUES ('346', '120000', '天津', 'tianjin', '120000');
INSERT INTO `api_area` VALUES ('347', '100000', '北京', 'beijing', '100000');
INSERT INTO `api_area` VALUES ('505', '500000', '重庆', 'chongqin', '500000');
INSERT INTO `api_area` VALUES ('506', '990000', '国外', 'guowai', '990000');
INSERT INTO `api_area` VALUES ('507', '710000', '台湾', 'taiwan', '710000');
INSERT INTO `api_area` VALUES ('508', '810000', '香港', 'xiangguang', '810000');
INSERT INTO `api_area` VALUES ('509', '820000', '澳门', 'aomen', '820000');

-- ----------------------------
-- Table structure for `api_base_data`
-- ----------------------------
DROP TABLE IF EXISTS `api_base_data`;
CREATE TABLE `api_base_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(50) DEFAULT NULL COMMENT '名称类型',
  `code` varchar(50) DEFAULT NULL COMMENT '代码',
  `name` varchar(255) DEFAULT NULL COMMENT '内容',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `STATUS` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=607 DEFAULT CHARSET=utf8 COMMENT='基础数据表';

-- ----------------------------
-- Records of api_base_data
-- ----------------------------
INSERT INTO `api_base_data` VALUES ('1', 'degree', 'A0001', '高中', '2015-06-11 14:05:16', '2017-06-23 11:26:22', '1');
INSERT INTO `api_base_data` VALUES ('2', 'degree', 'A0002', '大专', '2015-06-11 14:05:16', '2017-06-23 11:28:51', '1');
INSERT INTO `api_base_data` VALUES ('3', 'degree', 'A0003', '本科', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('4', 'degree', 'A0004', '硕士', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('5', 'degree', 'A0005', '博士', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('6', 'degree', 'A0006', '博士后', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('7', 'family', 'B0001', '高知', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('8', 'family', 'B0002', '高干', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('9', 'family', 'B0003', '富裕', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('10', 'family', 'B0004', '中产', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('11', 'family', 'B0005', '小康', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('12', 'family', 'B0006', '工薪', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('13', 'job', 'C0001', '职员', '2015-06-11 14:05:16', '2015-07-29 16:23:51', '1');
INSERT INTO `api_base_data` VALUES ('14', 'job', 'C0002', '主管', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('15', 'job', 'C0003', '经理', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('16', 'job', 'C0004', '总监', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('17', 'job', 'C0005', '副总', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('18', 'job', 'C0006', '总裁', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('19', 'job', 'C0007', '董事长', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('20', 'job', 'C0008', '其他', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '0');
INSERT INTO `api_base_data` VALUES ('21', 'car', 'D0001', '帕沙特', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('22', 'car', 'D0002', '凯美瑞', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('23', 'car', 'D0003', '奔驰C级', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('24', 'car', 'D0004', '奔驰E级', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('25', 'car', 'D0005', '宝马5系', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('26', 'car', 'D0006', '特斯拉', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('27', 'asset', 'G0000', '100万以下', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('31', 'industry', 'F0001', '计算机/互联网/通信/电子', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('32', 'industry', 'F0002', '会计/金融/银行/保险', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('33', 'industry', 'F0003', '贸易/消费/制造/营运', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('34', 'industry', 'F0004', '制药/医疗', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('35', 'industry', 'F0005', '广告/媒体', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('36', 'industry', 'F0006', '房地产/建筑', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('37', 'industry', 'F0007', '专业服务/教育/培训', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('38', 'industry', 'F0008', '服务业', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('39', 'industry', 'F0009', '物流/运输', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('40', 'industry', 'F0010', '能源/原材料', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('41', 'industry', 'F0011', '政府/非盈利机构/其他', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('42', 'asset', 'G0001', '100-500万', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('43', 'asset', 'G0002', '500-1000万', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('44', 'asset', 'G0003', '1000-5000万', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('45', 'asset', 'G0004', '5000万-1亿', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('46', 'asset', 'G0005', '1-5亿', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('47', 'asset', 'G0006', '5亿以上', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '1');
INSERT INTO `api_base_data` VALUES ('49', 'marry', 'K0001', '未婚', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '0');
INSERT INTO `api_base_data` VALUES ('50', 'marry', 'K0002', '离异', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '0');
INSERT INTO `api_base_data` VALUES ('51', 'marry', 'K0003', '丧偶', '2015-06-11 14:05:16', '2015-06-11 14:05:16', '0');
INSERT INTO `api_base_data` VALUES ('210', 'apk_source', '1', '华为应用市场', '2015-06-26 11:27:08', '2015-06-26 11:27:11', '1');
INSERT INTO `api_base_data` VALUES ('211', 'apk_source', '2', '360手机助手', '2015-06-26 11:27:08', '2015-06-26 11:27:11', '1');
INSERT INTO `api_base_data` VALUES ('212', 'apk_source', '3', '应用宝', '2015-06-26 11:27:08', '2015-06-26 11:27:11', '1');
INSERT INTO `api_base_data` VALUES ('213', 'apk_source', '4', '豌豆荚', '2015-06-26 11:27:08', '2015-06-26 11:27:11', '1');
INSERT INTO `api_base_data` VALUES ('214', 'apk_source', '5', '小米', '2015-06-26 11:27:08', '2015-06-26 11:27:11', '1');
INSERT INTO `api_base_data` VALUES ('215', 'apk_source', '6', '百度助手', '2015-06-26 11:27:08', '2015-06-26 11:27:11', '1');
INSERT INTO `api_base_data` VALUES ('216', 'apk_source', '7', '91助手', '2015-06-26 11:27:08', '2015-06-26 11:27:11', '1');
INSERT INTO `api_base_data` VALUES ('217', 'apk_source', '8', '安卓市场', '2015-06-26 11:27:08', '2015-06-26 11:27:11', '1');
INSERT INTO `api_base_data` VALUES ('218', 'apk_source', '9', '自有平台', '2015-06-26 11:27:08', '2015-06-26 11:27:11', '1');
INSERT INTO `api_base_data` VALUES ('262', 'contentEdit', 'help', '帮助', '2015-08-07 14:31:49', '2015-08-07 14:31:49', '1');
INSERT INTO `api_base_data` VALUES ('263', 'contentEdit', 'about', '关于我们', '2015-08-07 14:31:49', '2015-08-07 14:31:49', '1');
INSERT INTO `api_base_data` VALUES ('264', 'contentEdit', 'contact', '联系我们', '2015-08-07 14:31:49', '2015-08-07 14:31:49', '1');
INSERT INTO `api_base_data` VALUES ('273', 'mouthSalary', '1', '5000~8000', '2015-09-17 15:06:52', '2015-09-17 15:06:52', '1');
INSERT INTO `api_base_data` VALUES ('274', 'mouthSalary', '2', '8000~15000', '2015-09-17 15:06:53', '2015-09-17 15:06:53', '1');
INSERT INTO `api_base_data` VALUES ('275', 'mouthSalary', '3', '15000~30000', '2015-09-17 15:06:53', '2015-09-17 15:06:53', '1');
INSERT INTO `api_base_data` VALUES ('276', 'mouthSalary', '4', '30000~50000', '2015-09-17 15:06:53', '2015-09-17 15:06:53', '1');
INSERT INTO `api_base_data` VALUES ('277', 'mouthSalary', '5', '50000以上', '2015-09-17 15:06:53', '2015-09-17 15:06:53', '1');
INSERT INTO `api_base_data` VALUES ('304', 'smsChannel', '3', '创蓝短信平台', '2015-10-28 10:19:47', '2015-10-28 10:19:47', '0');
INSERT INTO `api_base_data` VALUES ('340', 'smsChannel', '9999', '互亿短信平台', '2015-10-22 14:15:30', '2017-08-04 17:36:07', '1');
INSERT INTO `api_base_data` VALUES ('346', 'smsNotice', '5', '支付成功', '2015-11-03 15:02:03', '2015-11-03 15:02:03', '1');
INSERT INTO `api_base_data` VALUES ('377', 'pushNotice', '10', '用户资料审核通过', '2015-11-03 15:06:37', '2015-11-03 15:06:37', '1');
INSERT INTO `api_base_data` VALUES ('378', 'pushNotice', '11', '用户资料审核退回', '2015-11-03 15:06:37', '2015-11-03 15:06:37', '1');
INSERT INTO `api_base_data` VALUES ('389', 'smsNotice', '300', '验证码，忘记密码', '2015-11-03 17:25:30', '2015-11-03 17:28:17', '1');
INSERT INTO `api_base_data` VALUES ('390', 'marry', 'K0004', '单身', '2015-11-09 17:36:58', '2015-11-09 17:36:58', '1');
INSERT INTO `api_base_data` VALUES ('391', 'marry', 'K0004', '保密', '2015-11-09 17:37:14', '2015-11-09 17:37:14', '1');
INSERT INTO `api_base_data` VALUES ('406', 'interestTabs', '1', '运动', '2015-11-17 17:47:07', '2015-11-17 17:47:07', '1');
INSERT INTO `api_base_data` VALUES ('407', 'interestTabs', '2', '唱歌', '2015-11-17 17:47:07', '2015-11-17 17:47:07', '1');
INSERT INTO `api_base_data` VALUES ('408', 'interestTabs', '3', '电影', '2015-11-17 17:47:07', '2015-11-17 17:47:07', '1');
INSERT INTO `api_base_data` VALUES ('409', 'interestTabs', '4', '书画', '2015-11-17 17:47:07', '2015-11-17 17:47:07', '1');
INSERT INTO `api_base_data` VALUES ('410', 'interestTabs', '5', '阅读', '2015-11-17 17:47:07', '2015-11-17 17:47:07', '1');
INSERT INTO `api_base_data` VALUES ('411', 'interestTabs', '6', '旅游', '2015-11-17 17:47:07', '2015-11-17 17:47:07', '1');
INSERT INTO `api_base_data` VALUES ('412', 'interestTabs', '7', '购物', '2015-11-17 17:47:07', '2015-11-17 17:47:07', '1');
INSERT INTO `api_base_data` VALUES ('413', 'interestTabs', '8', '摄影', '2015-11-17 17:47:07', '2015-11-17 17:47:07', '1');
INSERT INTO `api_base_data` VALUES ('414', 'interestTabs', '9', '泡吧', '2015-11-17 17:47:07', '2015-11-17 17:47:07', '1');
INSERT INTO `api_base_data` VALUES ('415', 'interestTabs', '10', '烹饪', '2015-11-17 17:47:07', '2015-11-17 17:47:07', '1');
INSERT INTO `api_base_data` VALUES ('416', 'interestTabs', '11', '跳舞', '2015-11-17 17:47:07', '2015-11-17 17:47:07', '1');
INSERT INTO `api_base_data` VALUES ('417', 'interestTabs', '12', '聚会', '2015-11-17 17:47:07', '2015-11-17 17:47:07', '1');
INSERT INTO `api_base_data` VALUES ('418', 'interestTabs', '13', '养宠物', '2015-11-17 17:47:07', '2015-11-17 17:47:07', '1');
INSERT INTO `api_base_data` VALUES ('419', 'interestTabs', '14', '养花草', '2015-11-17 17:47:08', '2015-11-17 17:47:08', '1');
INSERT INTO `api_base_data` VALUES ('420', 'interestTabs', '15', '陶艺', '2015-11-17 17:47:08', '2015-11-17 17:47:08', '1');
INSERT INTO `api_base_data` VALUES ('421', 'interestTabs', '16', '极限运动', '2015-11-17 17:47:08', '2015-11-17 17:47:08', '1');
INSERT INTO `api_base_data` VALUES ('422', 'interestTabs', '17', '名品收藏', '2015-11-17 17:47:08', '2015-11-17 17:47:08', '1');
INSERT INTO `api_base_data` VALUES ('423', 'interestTabs', '18', '茶艺', '2015-11-17 17:47:08', '2015-11-17 17:47:08', '1');
INSERT INTO `api_base_data` VALUES ('424', 'interestTabs', '19', '烘焙', '2015-11-17 17:47:08', '2015-11-17 17:47:08', '1');
INSERT INTO `api_base_data` VALUES ('425', 'interestTabs', '20', '打高尔夫', '2015-11-17 17:47:08', '2015-11-17 17:47:08', '1');
INSERT INTO `api_base_data` VALUES ('443', 'payChannel', 'IAP', 'APP应用内购', '2015-12-29 11:10:40', '2015-12-29 11:10:40', '0');
INSERT INTO `api_base_data` VALUES ('444', 'payChannel', 'OTHER', '微信，银联支付', '2015-12-29 11:11:02', '2015-12-29 11:11:02', '0');
INSERT INTO `api_base_data` VALUES ('502', 'marry', 'K0004', '已婚', '2016-08-18 14:30:16', '2016-08-18 14:30:16', '1');
INSERT INTO `api_base_data` VALUES ('585', 'payChannel', 'UNION_PAY', '银联支付', '2017-06-27 10:39:11', '2017-06-27 10:39:11', '1');
INSERT INTO `api_base_data` VALUES ('586', 'payChannel', 'UNION_PAY', '银联支付', '2017-06-27 11:51:11', '2017-06-27 11:51:11', '0');
INSERT INTO `api_base_data` VALUES ('587', 'payChannel', 'WEIXIN_PAY', '微信支付', '2017-06-27 11:51:11', '2017-06-27 11:51:11', '1');
INSERT INTO `api_base_data` VALUES ('588', 'payChannel', 'WEIXIN_WAP_PAY', '微信网页支付', '2017-06-27 11:51:11', '2017-06-27 11:51:11', '1');
INSERT INTO `api_base_data` VALUES ('589', 'payChannel', 'WEIXIN_MINA_PAY', '微信小程序支付', '2017-06-27 11:51:11', '2017-06-27 11:51:11', '1');
INSERT INTO `api_base_data` VALUES ('590', 'payChannel', 'ALI_PAY', '支付宝支付', '2017-06-27 11:51:11', '2017-06-27 12:17:34', '1');
INSERT INTO `api_base_data` VALUES ('591', 'payChannel', 'ALI_WAP_PAY', '支付宝网页支付', '2017-06-27 11:51:11', '2017-06-27 12:17:39', '1');
INSERT INTO `api_base_data` VALUES ('592', 'payChannel', 'SAY_GOOD_PAY', '赞支付(兑换商品, 易元)', '2017-06-27 11:51:11', '2017-06-27 11:51:11', '1');
INSERT INTO `api_base_data` VALUES ('593', 'payChannel', 'DIAMOND_PAY', '易元支付', '2017-06-27 11:51:11', '2017-06-27 11:51:11', '1');
INSERT INTO `api_base_data` VALUES ('594', 'payChannel', 'IAP_PAY', '苹果内购', '2017-06-27 11:51:11', '2017-06-27 11:51:11', '1');
INSERT INTO `api_base_data` VALUES ('595', 'payChannel', 'ADMIN_PAY', '后台支付(仅限易元充值)', '2017-06-27 11:51:11', '2017-06-27 11:51:11', '1');
INSERT INTO `api_base_data` VALUES ('596', 'payChannel', 'RED_PACKET_PAY', '红包支付(仅限红包操作使用)', '2017-06-27 11:51:11', '2017-06-27 11:51:11', '1');
INSERT INTO `api_base_data` VALUES ('602', 'payChannel', 'OFFLINE_PAY', '线下付费', '2017-06-30 18:41:24', '2017-07-03 12:00:08', '1');
INSERT INTO `api_base_data` VALUES ('606', 'smsNotice', '301', '生日大礼包短信', '2017-07-21 11:54:38', '2017-07-21 11:54:38', '1');

-- ----------------------------
-- Table structure for `api_base_data_type`
-- ----------------------------
DROP TABLE IF EXISTS `api_base_data_type`;
CREATE TABLE `api_base_data_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='基础数据类型表';

-- ----------------------------
-- Records of api_base_data_type
-- ----------------------------
INSERT INTO `api_base_data_type` VALUES ('1', 'degree', '学历');
INSERT INTO `api_base_data_type` VALUES ('2', 'family', '家庭背景');
INSERT INTO `api_base_data_type` VALUES ('3', 'job', '职业');
INSERT INTO `api_base_data_type` VALUES ('4', 'car', '车型');
INSERT INTO `api_base_data_type` VALUES ('5', 'industry', '行业');
INSERT INTO `api_base_data_type` VALUES ('6', 'asset', '资产');
INSERT INTO `api_base_data_type` VALUES ('7', 'marry', '婚姻状况');
INSERT INTO `api_base_data_type` VALUES ('20', 'contentEdit', '内容类型');
INSERT INTO `api_base_data_type` VALUES ('23', 'mouthSalary', '月薪');
INSERT INTO `api_base_data_type` VALUES ('26', 'smsChannel', '短信渠道');
INSERT INTO `api_base_data_type` VALUES ('27', 'smsNotice', '短信通知');
INSERT INTO `api_base_data_type` VALUES ('28', 'pushNotice', '推送通知');
INSERT INTO `api_base_data_type` VALUES ('31', 'interestTabs', '兴趣标签');
INSERT INTO `api_base_data_type` VALUES ('33', 'payChannel', '支付渠道');

-- ----------------------------
-- Table structure for `api_login`
-- ----------------------------
DROP TABLE IF EXISTS `api_login`;
CREATE TABLE `api_login` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '关联用户表',
  `phone` varchar(11) DEFAULT NULL COMMENT '登录手机',
  `blacklist` int(11) DEFAULT '0' COMMENT '是否黑名单：1为是，0为否',
  `phone_client` varchar(20) DEFAULT NULL COMMENT '手机客户端:Android,ADMIN(后台导入),IOS,invite(邀请注册),official(官网注册)',
  `password` varchar(200) DEFAULT NULL COMMENT '登录密码，密文',
  `login_ip` varchar(50) DEFAULT NULL COMMENT '登录IP',
  `login_addr` varchar(255) DEFAULT NULL COMMENT '登录地点',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_logintime` datetime DEFAULT NULL COMMENT '上次登录时间',
  `last_logouttime` datetime DEFAULT NULL COMMENT '上次退出时间',
  `apk_version` varchar(20) DEFAULT NULL COMMENT 'apk的版本',
  `client_info` varchar(2048) DEFAULT NULL COMMENT '客户端信息',
  `easemob_password` varchar(50) DEFAULT NULL COMMENT '环信登陆密码',
  `last_boot_up_time` datetime DEFAULT NULL COMMENT '最后一次启动时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `fk_login_userId` (`user_id`) USING BTREE,
  UNIQUE KEY `index_login_phone` (`phone`) USING BTREE,
  CONSTRAINT `api_login_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `api_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='登录表';

-- ----------------------------
-- Records of api_login
-- ----------------------------

-- ----------------------------
-- Table structure for `api_message`
-- ----------------------------
DROP TABLE IF EXISTS `api_message`;
CREATE TABLE `api_message` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `sender_info_id` int(11) DEFAULT NULL COMMENT '根据类型不同存储发送人的userId或者邀请函的id等等',
  `user_id` int(11) DEFAULT NULL COMMENT '当前用户id',
  `content` varchar(255) DEFAULT NULL COMMENT '内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `type` int(11) DEFAULT NULL COMMENT '消息类型：1为成功邀请注册，2为视频邀请，3为认证通过，4为赴约邀请，5为支付成功，6为余额变更，7为获得抵用券，8为信誉星减少,9 认证拒绝,10 用户资料审核通过,11 用户资料审核退回',
  `status` int(11) DEFAULT NULL COMMENT '消息状态：1 未读，0 已读',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `client_type` varchar(20) DEFAULT NULL COMMENT '客户端类型',
  `device_token` varchar(128) DEFAULT NULL COMMENT 'IOS设备token',
  `client_id` varchar(64) DEFAULT NULL COMMENT '安卓客户端ID',
  `valid_status` int(11) DEFAULT NULL COMMENT '消息有效标志位，相当于删除，1为有效，0为无效',
  PRIMARY KEY (`id`),
  KEY `fk_message_userId` (`user_id`) USING BTREE,
  CONSTRAINT `api_message_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `api_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of api_message
-- ----------------------------

-- ----------------------------
-- Table structure for `api_province`
-- ----------------------------
DROP TABLE IF EXISTS `api_province`;
CREATE TABLE `api_province` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` int(12) DEFAULT NULL,
  `name` varchar(80) DEFAULT NULL,
  `pinyin_name` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='省份表';

-- ----------------------------
-- Records of api_province
-- ----------------------------
INSERT INTO `api_province` VALUES ('1', '100000', '北京', 'beijing');
INSERT INTO `api_province` VALUES ('2', '120000', '天津', 'tianjin');
INSERT INTO `api_province` VALUES ('3', '130000', '河北', 'hebei');
INSERT INTO `api_province` VALUES ('4', '140000', '山西', 'shanxi');
INSERT INTO `api_province` VALUES ('5', '150000', '内蒙古', 'neimenggu');
INSERT INTO `api_province` VALUES ('6', '210000', '辽宁', 'liaoning');
INSERT INTO `api_province` VALUES ('7', '220000', '吉林', 'jilin');
INSERT INTO `api_province` VALUES ('8', '230000', '黑龙江', 'heilongjiang');
INSERT INTO `api_province` VALUES ('9', '310000', '上海', 'shanghai');
INSERT INTO `api_province` VALUES ('10', '320000', '江苏', 'jiangsu');
INSERT INTO `api_province` VALUES ('11', '330000', '浙江', 'zhejiang');
INSERT INTO `api_province` VALUES ('12', '340000', '安徽', 'anhui');
INSERT INTO `api_province` VALUES ('13', '350000', '福建', 'fujian');
INSERT INTO `api_province` VALUES ('14', '360000', '江西', 'jiangxi');
INSERT INTO `api_province` VALUES ('15', '370000', '山东', 'shandong');
INSERT INTO `api_province` VALUES ('16', '410000', '河南', 'henan');
INSERT INTO `api_province` VALUES ('17', '420000', '湖北', 'hubei');
INSERT INTO `api_province` VALUES ('18', '430000', '湖南', 'hunan');
INSERT INTO `api_province` VALUES ('19', '440000', '广东', 'guangdong');
INSERT INTO `api_province` VALUES ('20', '450000', '广西', 'guangxi');
INSERT INTO `api_province` VALUES ('21', '460000', '海南', 'hainan');
INSERT INTO `api_province` VALUES ('22', '500000', '重庆', 'chongqi');
INSERT INTO `api_province` VALUES ('23', '510000', '四川', 'sichuan');
INSERT INTO `api_province` VALUES ('24', '520000', '贵州', 'guizhou');
INSERT INTO `api_province` VALUES ('25', '530000', '云南', 'yunnan');
INSERT INTO `api_province` VALUES ('26', '540000', '西藏', 'xizang');
INSERT INTO `api_province` VALUES ('27', '610000', '陕西', 'shanxi');
INSERT INTO `api_province` VALUES ('28', '620000', '甘肃', 'gansu');
INSERT INTO `api_province` VALUES ('29', '630000', '青海', 'qinghai');
INSERT INTO `api_province` VALUES ('30', '640000', '宁夏', 'ningxia');
INSERT INTO `api_province` VALUES ('31', '650000', '新疆', 'xinjiang');
INSERT INTO `api_province` VALUES ('32', '710000', '台湾', 'taiwan');
INSERT INTO `api_province` VALUES ('33', '810000', '香港', 'xiangguang');
INSERT INTO `api_province` VALUES ('34', '820000', '澳门', 'aomen');
INSERT INTO `api_province` VALUES ('35', '990000', '国外', 'guowai');

-- ----------------------------
-- Table structure for `api_user`
-- ----------------------------
DROP TABLE IF EXISTS `api_user`;
CREATE TABLE `api_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_code` varchar(20) DEFAULT NULL COMMENT '优约会员编号',
  `user_name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `sex` varchar(11) DEFAULT NULL COMMENT '性别：男，女',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `height` int(11) DEFAULT NULL COMMENT '身高',
  `weight` int(11) DEFAULT NULL COMMENT '体重',
  `phone` varchar(11) DEFAULT NULL COMMENT '个人手机',
  `weixin_name` varchar(50) DEFAULT NULL COMMENT '微信号',
  `degree` varchar(20) DEFAULT NULL COMMENT '学历',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `constellation` varchar(10) DEFAULT NULL COMMENT '星座',
  `marital_status` varchar(11) DEFAULT NULL COMMENT '婚姻状况：未婚，离异，丧偶,已婚',
  `native_place` varchar(11) DEFAULT NULL COMMENT '籍贯',
  `live_province` varchar(20) DEFAULT NULL COMMENT '居住省',
  `live_city` varchar(20) DEFAULT NULL COMMENT '居住城市',
  `nationality` varchar(11) DEFAULT NULL COMMENT '民族',
  `school` varchar(64) DEFAULT NULL COMMENT '学校',
  `company` varchar(200) DEFAULT NULL COMMENT '公司',
  `job` varchar(32) DEFAULT NULL COMMENT '工作',
  `characteristic_signature` varchar(255) DEFAULT NULL COMMENT '个性签名',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间（注册时间）',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `valid_status` int(11) DEFAULT NULL COMMENT '有效状态（1为有效，0为无效）',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `qq` varchar(20) DEFAULT NULL COMMENT 'QQ',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone` (`phone`) USING BTREE,
  KEY `user_code` (`user_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3154 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Records of api_user
-- ----------------------------
INSERT INTO `api_user` VALUES ('1', 'scaukejian', '柯坚', '男', '27', '170', '50', '15989099048', 'kejian002', '本科', '1990-09-05 01:00:00', '处女座', '已婚', '广东湛江', '广东', '广州', '汉族', '华南农业大学', '易约', 'java研发', 'nothing is impossible', '2017-09-23 18:27:39', '2017-09-27 14:16:18', '1', '<h1><span style=\"color:#696969\">备注信息</span></h1>', null, null);
INSERT INTO `api_user` VALUES ('3145', 'SCAU_KJ', '柯坚', '男', '27', '170', '55', '15989099047', 'kejian002', '本科', '1990-09-05 01:00:00', '天蝎座', '已婚', null, '广东', '广州', null, '华南农业大学', '易约', 'java研发工程师', '我说过，我可以', '2017-09-26 10:29:22', '2017-09-27 16:20:21', '1', '测试', '954657344', '954657344@qq.com');

-- ----------------------------
-- Table structure for `api_user_photo`
-- ----------------------------
DROP TABLE IF EXISTS `api_user_photo`;
CREATE TABLE `api_user_photo` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `img_name` varchar(255) DEFAULT NULL COMMENT '图片地址',
  `original_img_name` varchar(255) DEFAULT NULL COMMENT '图片原始名称',
  `head_flag` int(11) DEFAULT NULL COMMENT '头像标识：1为头像，0为否',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `thumbnail_img_name` varchar(255) DEFAULT NULL COMMENT '临时图片名称',
  PRIMARY KEY (`id`),
  KEY `index_user_photo_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='用户相册表';

-- ----------------------------
-- Records of api_user_photo
-- ----------------------------
INSERT INTO `api_user_photo` VALUES ('9', '3145', '6_photo-fd987959-85e7-4672-8295-a62525f4ad54.jpg', 'photo-ed865269-c218-4d3f-908c-301761b5ef85.jpg', '1', '2017-09-27 14:40:03', '6_photo-fd987959-85e7-4672-8295-a62525f4ad54.jpg');
INSERT INTO `api_user_photo` VALUES ('11', '1', '6_photo-d5bfa0c9-247f-47c2-9da8-de1b82eb8434.jpg', 'photo-ed865269-c218-4d3f-908c-301761b5ef85.jpg', '1', '2017-09-27 14:41:35', '6_photo-d5bfa0c9-247f-47c2-9da8-de1b82eb8434.jpg');
