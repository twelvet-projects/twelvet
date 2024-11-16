DROP DATABASE IF EXISTS `twelvet`;

CREATE DATABASE `twelvet` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

USE `twelvet`;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`
(
    `dept_id`     bigint(20)                                             NOT NULL AUTO_INCREMENT COMMENT '部门id',
    `parent_id`   bigint(20)                                             NULL DEFAULT 0 COMMENT '父部门id',
    `ancestors`   varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '祖级列表',
    `dept_name`   varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '部门名称',
    `order_num`   int(4)                                                 NULL DEFAULT 0 COMMENT '显示顺序',
    `leader`      varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '负责人',
    `phone`       varchar(11) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '联系电话',
    `email`       varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
    `status`      char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci     NULL DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
    `del_flag`    char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci     NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    `create_by`   varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                               NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                               NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 110
  CHARACTER SET = utf8
  COLLATE = utf8_unicode_ci COMMENT = '部门表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept`
VALUES (100, 0, '0,100,101', 'TWT科技', 1, 'twelvet', '15888888888', '2471835953@qq.com', '0', '0', 'admin',
        '2018-03-16 11:33:00', 'TWT', '2020-12-31 16:49:51');
INSERT INTO `sys_dept`
VALUES (101, 100, '0,100,101,10,100,1010,100,101', '深圳总公司', 1, 'twelvet', '15888888888', '2471835953@qq.com', '0',
        '0', 'admin', '2018-03-16 11:33:00', 'TWT', '2020-10-23 16:03:40');
INSERT INTO `sys_dept`
VALUES (102, 100, '0,100,101,10,100,1010,100,101', '长沙分公司', 2, 'twelvet', '15888888888', '2471835953@qq.com', '0',
        '0', 'admin', '2018-03-16 11:33:00', 'TWT', '2018-03-16 11:33:00');
INSERT INTO `sys_dept`
VALUES (103, 101, '0,100,101,10,100,1010,100,101,10,100,1011', '研发部门', 1, 'twelvet', '15888888888',
        '2471835953@qq.com', '0', '0', 'admin', '2018-03-16 11:33:00', 'TWT', '2020-10-23 14:48:44');
INSERT INTO `sys_dept`
VALUES (104, 101, '0,100,101,10,100,1010,100,101,10,100,1011', '市场部门', 2, 'twelvet', '15888888888',
        '2471835953@qq.com', '0', '0', 'admin', '2018-03-16 11:33:00', 'TWT', '2018-03-16 11:33:00');
INSERT INTO `sys_dept`
VALUES (105, 101, '0,100,101,10,100,1010,100,101,10,100,1011', '测试部门', 3, 'twelvet', '15888888888',
        '2471835953@qq.com', '0', '0', 'admin', '2018-03-16 11:33:00', 'TWT', '2018-03-16 11:33:00');
INSERT INTO `sys_dept`
VALUES (106, 101, '0,100,101,10,100,1010,100,101,10,100,1011', '财务部门', 4, 'twelvet', '15888888888',
        '2471835953@qq.com', '0', '0', 'admin', '2018-03-16 11:33:00', 'TWT', '2018-03-16 11:33:00');
INSERT INTO `sys_dept`
VALUES (107, 101, '0,100,101,10,100,1010,100,101,10,100,1011', '运维部门', 5, 'twelvet', '15888888888',
        '2471835953@qq.com', '0', '0', 'admin', '2018-03-16 11:33:00', 'TWT', '2018-03-16 11:33:00');
INSERT INTO `sys_dept`
VALUES (108, 102, '0,100,101,10,100,1010,100,101,10,100,1012', '市场部门', 1, 'twelvet', '15888888888',
        '2471835953@qq.com', '0', '0', 'admin', '2018-03-16 11:33:00', 'TWT', '2018-03-16 11:33:00');
INSERT INTO `sys_dept`
VALUES (109, 102, '0,100,101,10,100,1010,100,101,10,100,1012', '财务部门', 2, 'twelvet', '15888888888',
        '2471835953@qq.com', '0', '0', 'admin', '2018-03-16 11:33:00', 'TWT', '2018-03-16 11:33:00');

-- ----------------------------
-- Table structure for sys_dfs
-- ----------------------------
DROP TABLE IF EXISTS `sys_dfs`;
CREATE TABLE `sys_dfs`
(
    `file_id`            int(8)                                                        NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `file_name`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件名称',
    `original_file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件原名称',
    `space_name`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '文件分组',
    `path`               varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件路径',
    `size`               int(11)                                                       NOT NULL COMMENT '文件大小',
    `type`               varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '文件类型',
    `create_time`        datetime                                                      NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`file_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4 COMMENT = '分布式文件表'
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dfs
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`
(
    `dict_code`   bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT '字典编码',
    `dict_sort`   int(4)                                                        NULL DEFAULT 0 COMMENT '字典排序',
    `dict_label`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典标签',
    `dict_value`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典键值',
    `dict_type`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典类型',
    `css_class`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
    `list_class`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表格回显样式',
    `is_default`  char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci      NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
    `status`      tinyint(1)                                                    NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
    `create_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                      NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                      NULL DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 121
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '字典数据表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data`
VALUES (1, 1, '男', '0', 'sys_user_sex', '', '', 'Y', 0, 'admin', '2023-03-05 23:41:03', '', NULL, '性别男');
INSERT INTO `sys_dict_data`
VALUES (2, 2, '女', '1', 'sys_user_sex', '', '', 'N', 0, 'admin', '2023-03-05 23:41:03', '', NULL, '性别女');
INSERT INTO `sys_dict_data`
VALUES (3, 3, '未知', '2', 'sys_user_sex', '', '', 'N', 0, 'admin', '2023-03-05 23:41:03', '', NULL, '性别未知');
INSERT INTO `sys_dict_data`
VALUES (4, 1, '显示', '0', 'sys_show_hide', '', 'primary', 'Y', 0, 'admin', '2023-03-05 23:41:03', '', NULL,
        '显示菜单');
INSERT INTO `sys_dict_data`
VALUES (5, 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', 0, 'admin', '2023-03-05 23:41:03', '', NULL, '隐藏菜单');
INSERT INTO `sys_dict_data`
VALUES (6, 1, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', 0, 'admin', '2023-03-05 23:41:03', '', NULL,
        '正常状态');
INSERT INTO `sys_dict_data`
VALUES (7, 2, '停用', '1', 'sys_normal_disable', '', 'danger', 'N', 0, 'admin', '2023-03-05 23:41:03', '', NULL,
        '停用状态');
INSERT INTO `sys_dict_data`
VALUES (8, 1, '正常', '0', 'sys_job_status', '', 'primary', 'Y', 0, 'admin', '2023-03-05 23:41:03', '', NULL,
        '正常状态');
INSERT INTO `sys_dict_data`
VALUES (9, 2, '暂停', '1', 'sys_job_status', '', 'danger', 'N', 0, 'admin', '2023-03-05 23:41:03', '', NULL,
        '停用状态');
INSERT INTO `sys_dict_data`
VALUES (10, 1, '默认', 'DEFAULT', 'sys_job_group', '', '', 'Y', 0, 'admin', '2023-03-05 23:41:03', '', NULL,
        '默认分组');
INSERT INTO `sys_dict_data`
VALUES (11, 2, '系统', 'SYSTEM', 'sys_job_group', '', '', 'N', 0, 'admin', '2023-03-05 23:41:03', '', NULL, '系统分组');
INSERT INTO `sys_dict_data`
VALUES (12, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', 0, 'admin', '2023-03-05 23:41:03', '', NULL, '系统默认是');
INSERT INTO `sys_dict_data`
VALUES (13, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', 0, 'admin', '2023-03-05 23:41:03', '', NULL, '系统默认否');
INSERT INTO `sys_dict_data`
VALUES (14, 1, '通知', '1', 'sys_notice_type', '', 'warning', 'Y', 0, 'admin', '2023-03-05 23:41:03', '', NULL, '通知');
INSERT INTO `sys_dict_data`
VALUES (15, 2, '公告', '2', 'sys_notice_type', '', 'success', 'N', 0, 'admin', '2023-03-05 23:41:03', '', NULL, '公告');
INSERT INTO `sys_dict_data`
VALUES (16, 1, '正常', '0', 'sys_notice_status', '', 'primary', 'Y', 0, 'admin', '2023-03-05 23:41:03', '', NULL,
        '正常状态');
INSERT INTO `sys_dict_data`
VALUES (17, 2, '关闭', '1', 'sys_notice_status', '', 'danger', 'N', 0, 'admin', '2023-03-05 23:41:03', '', NULL,
        '关闭状态');
INSERT INTO `sys_dict_data`
VALUES (18, 99, '其他', '0', 'sys_oper_type', '', 'info', 'N', 0, 'admin', '2023-03-05 23:41:03', '', NULL, '其他操作');
INSERT INTO `sys_dict_data`
VALUES (19, 1, '新增', '1', 'sys_oper_type', '', 'info', 'N', 0, 'admin', '2023-03-05 23:41:03', '', NULL, '新增操作');
INSERT INTO `sys_dict_data`
VALUES (20, 2, '修改', '2', 'sys_oper_type', '', 'info', 'N', 0, 'admin', '2023-03-05 23:41:03', '', NULL, '修改操作');
INSERT INTO `sys_dict_data`
VALUES (21, 3, '删除', '3', 'sys_oper_type', '', 'danger', 'N', 0, 'admin', '2023-03-05 23:41:03', '', NULL,
        '删除操作');
INSERT INTO `sys_dict_data`
VALUES (22, 4, '授权', '4', 'sys_oper_type', '', 'primary', 'N', 0, 'admin', '2023-03-05 23:41:03', '', NULL,
        '授权操作');
INSERT INTO `sys_dict_data`
VALUES (23, 5, '导出', '5', 'sys_oper_type', '', 'warning', 'N', 0, 'admin', '2023-03-05 23:41:03', '', NULL,
        '导出操作');
INSERT INTO `sys_dict_data`
VALUES (24, 6, '导入', '6', 'sys_oper_type', '', 'warning', 'N', 0, 'admin', '2023-03-05 23:41:03', '', NULL,
        '导入操作');
INSERT INTO `sys_dict_data`
VALUES (25, 7, '强退', '7', 'sys_oper_type', '', 'danger', 'N', 0, 'admin', '2023-03-05 23:41:03', '', NULL,
        '强退操作');
INSERT INTO `sys_dict_data`
VALUES (26, 8, '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', 0, 'admin', '2023-03-05 23:41:03', '', NULL,
        '生成操作');
INSERT INTO `sys_dict_data`
VALUES (27, 9, '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', 0, 'admin', '2023-03-05 23:41:03', '', NULL,
        '清空操作');
INSERT INTO `sys_dict_data`
VALUES (28, 1, '成功', '0', 'sys_common_status', '', 'primary', 'N', 0, 'admin', '2023-03-05 23:41:03', '', NULL,
        '正常状态');
INSERT INTO `sys_dict_data`
VALUES (29, 2, '失败', '1', 'sys_common_status', '', 'danger', 'N', 0, 'admin', '2023-03-05 23:41:03', '', NULL,
        '停用状态');
INSERT INTO `sys_dict_data`
VALUES (30, 1, '密码模式', 'password', 'sys_oauth_client_details', NULL, NULL, 'N', 0, 'admin', '2021-01-12 22:09:15',
        '', '2021-01-12 22:09:27', NULL);
INSERT INTO `sys_dict_data`
VALUES (31, 2, '刷新模式', 'refresh_token', 'sys_oauth_client_details', NULL, NULL, 'N', 0, 'admin',
        '2021-01-12 22:09:15', '', '2021-01-12 22:09:27', NULL);
INSERT INTO `sys_dict_data`
VALUES (32, 3, '手机号码模式', 'phone_password', 'sys_oauth_client_details', NULL, NULL, 'N', 0, 'admin',
        '2021-01-12 22:09:15', '', '2021-01-12 22:09:27', NULL);
INSERT INTO `sys_dict_data`
VALUES (33, 4, '授权码模式', 'authorization_code', 'sys_oauth_client_details', NULL, NULL, 'N', 0, 'admin',
        '2021-01-12 22:28:56', '', NULL, NULL);
INSERT INTO `sys_dict_data`
VALUES (110, 1, 'mysql', 'mysql', 'ds_type', NULL, NULL, 'N', 0, 'admin', '2023-11-04 18:50:48', '', NULL, 'mysql');
INSERT INTO `sys_dict_data`
VALUES (111, 2, 'oracle', 'oracle', 'ds_type', NULL, NULL, 'N', 0, 'admin', '2023-11-04 18:50:59', '', NULL, 'oracle');
INSERT INTO `sys_dict_data`
VALUES (112, 3, 'db2', 'db2', 'ds_type', NULL, NULL, 'N', 0, 'admin', '2023-11-04 18:51:09', '', NULL, 'db2');
INSERT INTO `sys_dict_data`
VALUES (113, 4, 'mssql', 'mssql', 'ds_type', NULL, NULL, 'N', 0, 'admin', '2023-11-04 18:51:19', '', NULL, 'mssql');
INSERT INTO `sys_dict_data`
VALUES (114, 5, 'pg', 'pg', 'ds_type', NULL, NULL, 'N', 0, 'admin', '2023-11-04 18:51:28', '', NULL, 'pg');
INSERT INTO `sys_dict_data`
VALUES (115, 6, '达梦', 'dm', 'ds_type', NULL, NULL, 'N', 0, 'admin', '2023-11-04 18:51:38', 'admin',
        '2023-11-16 13:57:15', '达梦');
INSERT INTO `sys_dict_data`
VALUES (116, 1, '主机', '0', 'ds_config_type', NULL, NULL, 'N', 0, 'admin', '2023-11-04 19:04:15', '', NULL, '主机');
INSERT INTO `sys_dict_data`
VALUES (117, 1, 'JDBC', '1', 'ds_config_type', NULL, NULL, 'N', 0, 'admin', '2023-11-04 19:04:27', '', NULL, 'JDBC');
INSERT INTO `sys_dict_data`
VALUES (118, 7, '瀚高', 'highgo', 'ds_type', NULL, NULL, 'N', 0, 'admin', '2023-11-16 13:58:38', 'admin',
        '2023-11-16 13:58:42', '瀚高数据库');
INSERT INTO `sys_dict_data`
VALUES (119, 1, '简体中文', 'zh_CN', 'i18n', NULL, NULL, 'N', 0, 'admin', '2024-03-26 21:16:31', '', NULL, NULL);
INSERT INTO `sys_dict_data`
VALUES (120, 2, 'English US', 'en_US', 'i18n', NULL, NULL, 'N', 0, 'admin', '2024-03-26 21:17:03', '', NULL, NULL);

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`
(
    `dict_id`     bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT '字典主键',
    `dict_name`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典名称',
    `dict_type`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典类型',
    `status`      tinyint(1)                                                    NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
    `create_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                      NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                      NULL DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`dict_id`) USING BTREE,
    UNIQUE INDEX `dict_type` (`dict_type`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 107
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '字典类型表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type`
VALUES (1, '用户性别', 'sys_user_sex', 0, 'admin', '2023-03-05 23:40:55', '', NULL, '用户性别列表');
INSERT INTO `sys_dict_type`
VALUES (2, '菜单状态', 'sys_show_hide', 0, 'admin', '2023-03-05 23:40:55', '', NULL, '菜单状态列表');
INSERT INTO `sys_dict_type`
VALUES (3, '系统开关', 'sys_normal_disable', 0, 'admin', '2023-03-05 23:40:55', '', NULL, '系统开关列表');
INSERT INTO `sys_dict_type`
VALUES (4, '任务状态', 'sys_job_status', 0, 'admin', '2023-03-05 23:40:55', '', NULL, '任务状态列表');
INSERT INTO `sys_dict_type`
VALUES (5, '任务分组', 'sys_job_group', 0, 'admin', '2023-03-05 23:40:55', '', NULL, '任务分组列表');
INSERT INTO `sys_dict_type`
VALUES (6, '系统是否', 'sys_yes_no', 0, 'admin', '2023-03-05 23:40:55', '', NULL, '系统是否列表');
INSERT INTO `sys_dict_type`
VALUES (7, '通知类型', 'sys_notice_type', 0, 'admin', '2023-03-05 23:40:55', '', NULL, '通知类型列表');
INSERT INTO `sys_dict_type`
VALUES (8, '通知状态', 'sys_notice_status', 0, 'admin', '2023-03-05 23:40:55', '', NULL, '通知状态列表');
INSERT INTO `sys_dict_type`
VALUES (9, '操作类型', 'sys_oper_type', 0, 'admin', '2023-03-05 23:40:55', '', NULL, '操作类型列表');
INSERT INTO `sys_dict_type`
VALUES (10, '系统状态', 'sys_common_status', 0, 'admin', '2023-03-05 23:40:55', '', NULL, '登录状态列表');
INSERT INTO `sys_dict_type`
VALUES (11, 'OAuth2终端', 'sys_oauth_client_details', 0, 'admin', '2023-03-05 11:33:00', '', '2023-03-05 22:07:34',
        'OAuth2终端授权模式');
INSERT INTO `sys_dict_type`
VALUES (104, '代码生成器支持的数据库类型', 'ds_type', 0, 'admin', '2023-11-04 18:50:11', 'admin', '2023-11-04 18:50:22',
        '代码生成器支持的数据库类型');
INSERT INTO `sys_dict_type`
VALUES (105, '数据库链接类型', 'ds_config_type', 0, 'admin', '2023-11-04 19:03:57', '', NULL, '数据库链接类型');
INSERT INTO `sys_dict_type`
VALUES (106, '国际化', 'i18n', 0, 'admin', '2024-03-26 21:15:49', '', NULL, '国际化i18n');


-- ----------------------------
-- Table structure for sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_log`;
CREATE TABLE `sys_job_log`
(
    `job_log_id`     bigint(20)                                               NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
    `job_name`       varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci   NOT NULL COMMENT '任务名称',
    `job_group`      varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci   NOT NULL COMMENT '任务组名',
    `invoke_target`  varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci  NOT NULL COMMENT '调用目标字符串',
    `job_message`    varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci  NULL DEFAULT NULL COMMENT '日志信息',
    `status`         char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci       NULL DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
    `exception_info` varchar(2000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '异常信息',
    `create_time`    datetime                                                 NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`job_log_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_unicode_ci COMMENT = '定时任务调度日志表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_job_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_login_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_info`;
CREATE TABLE `sys_login_info`
(
    `info_id`     bigint(20)                                              NOT NULL AUTO_INCREMENT COMMENT '访问ID',
    `user_name`   varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci  NULL DEFAULT '' COMMENT '用户账号',
    `ipaddr`      varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci  NULL DEFAULT '' COMMENT '登录IP地址',
    `status`      char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci      NULL DEFAULT '0' COMMENT '登录状态（0登录成功 1登录失败 2成功退出）',
    `msg`         varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '提示信息',
    `access_time` datetime                                                NULL DEFAULT NULL COMMENT '访问时间',
    `dept_id`     bigint(20)                                              NOT NULL COMMENT '部门ID',
    PRIMARY KEY (`info_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_unicode_ci COMMENT = '系统访问记录'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_login_info
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`
(
    `menu_id`     bigint(20)                                              NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    `menu_name`   varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci  NOT NULL COMMENT '菜单名称',
    `parent_id`   bigint(20)                                              NULL DEFAULT 0 COMMENT '父菜单ID',
    `order_num`   int(4)                                                  NULL DEFAULT 0 COMMENT '显示顺序',
    `path`        varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '路由地址',
    `component`   varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '组件路径',
    `is_frame`    char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci      NULL DEFAULT '1' COMMENT '是否为外链（0是 1否）',
    `menu_type`   char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci      NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
    `visible`     char(1)                                                 NULL DEFAULT 0 COMMENT '菜单状态（0显示 1隐藏）',
    `status`      char(1)                                                 NULL DEFAULT 0 COMMENT '菜单状态（0正常 1停用）',
    `perms`       varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '权限标识',
    `icon`        varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '#' COMMENT '菜单图标',
    `create_by`   varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci  NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci  NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                NULL DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1094
  CHARACTER SET = utf8
  COLLATE = utf8_unicode_ci COMMENT = '菜单权限表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu`
VALUES (1, 'system.menu.system', 0, 1, '/system', NULL, '1', 'M', '0', '0', '', 'icon-system', 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-03-30 19:50:45', '系统管理目录');
INSERT INTO `sys_menu`
VALUES (2, 'system.menu.human', 0, 3, '/human', NULL, '1', 'M', '0', '0', '', 'icon-human-resources', 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-03-30 20:46:39', '系统监控目录');
INSERT INTO `sys_menu`
VALUES (3, 'system.menu.tool', 0, 6, '/tool', NULL, '1', 'M', '0', '0', '', 'icon-tool-box', 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-10-26 18:50:51', '系统工具目录');
INSERT INTO `sys_menu`
VALUES (4, 'system.menu.log', 0, 5, '/log', '', '1', 'M', '0', '0', '', 'icon-log', 'admin', '2019-03-16 11:33:00',
        'admin', '2024-10-26 18:54:18', '日志管理菜单');
INSERT INTO `sys_menu`
VALUES (5, 'TwelveT', 0, 7, 'https://twelvet.cn', NULL, '0', 'M', '0', '0', '', 'icon-system', 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-10-26 18:50:59', '若依官网地址');
INSERT INTO `sys_menu`
VALUES (100, 'system.menu.human.team', 2, 1, '/human/staff', '/human/staff/index', '1', 'C', '0', '0',
        'system:user:list', 'icon-team', 'admin', '2019-03-16 11:33:00', 'admin', '2024-03-31 09:34:38',
        '用户管理菜单');
INSERT INTO `sys_menu`
VALUES (101, 'system.menu.human.role', 2, 2, '/human/role', '/human/role/index', '1', 'C', '0', '0', 'system:role:list',
        'icon-role', 'admin', '2019-03-16 11:33:00', 'admin', '2024-03-31 09:34:44', '角色管理菜单');
INSERT INTO `sys_menu`
VALUES (102, 'system.menu.system.menu', 1, 2, '/system/menu', '/system/menu/index', '1', 'C', '0', '0',
        'system:menu:list', 'icon-menu', 'admin', '2019-03-16 11:33:00', 'admin', '2024-03-31 09:06:54',
        '菜单管理菜单');
INSERT INTO `sys_menu`
VALUES (103, 'system.menu.human.dept', 2, 4, '/human/dept', '/human/dept/index', '1', 'C', '0', '0', 'system:dept:list',
        'icon-dept', 'admin', '2019-03-16 11:33:00', 'admin', '2024-03-31 09:34:51', '部门管理菜单');
INSERT INTO `sys_menu`
VALUES (104, 'system.menu.human.post', 2, 5, '/human/post', '/human/post/index', '1', 'C', '0', '0', 'system:post:list',
        'icon-post', 'admin', '2019-03-16 11:33:00', 'admin', '2024-03-31 09:34:59', '岗位管理菜单');
INSERT INTO `sys_menu`
VALUES (105, 'system.menu.system.dictionaries', 1, 3, '/system/dictionaries', '/system/dictionaries/index', '1', 'C',
        '0', '0', 'system:dictionaries:list', 'icon-dictionaries', 'admin', '2019-03-16 11:33:00', 'admin',
        '2024-03-31 09:07:02', '字典管理菜单');
INSERT INTO `sys_menu`
VALUES (107, 'system.menu.system.client', 1, 4, '/system/client', '/system/client/index', '1', 'C', '0', '0',
        'system:client:list', 'icon-client', 'admin', '2019-03-16 11:33:00', 'admin', '2024-03-31 09:07:10',
        '终端设置菜单');
INSERT INTO `sys_menu`
VALUES (110, 'system.menu.monitor.job', 1083, 5, '/monitor/job', '/monitor/job/index', '1', 'C', '0', '0',
        'monitor:job:list', 'icon-job', 'admin', '2019-03-16 11:33:00', 'admin', '2024-03-31 09:20:27', '定时任务菜单');
INSERT INTO `sys_menu`
VALUES (500, 'system.menu.log.operation', 4, 1, '/log/operation', '/log/operation/index', '1', 'C', '0', '0',
        'system:operlog:list', 'icon-log-operation', 'admin', '2019-03-16 11:33:00', 'admin', '2024-03-31 09:42:33',
        '操作日志菜单');
INSERT INTO `sys_menu`
VALUES (501, 'system.menu.log.login', 4, 2, '/log/login', '/log/login/index', '1', 'C', '0', '0',
        'system:logininfor:list', 'icon-log-login', 'admin', '2019-03-16 11:33:00', 'admin', '2024-03-31 09:42:42',
        '登录日志菜单');
INSERT INTO `sys_menu`
VALUES (1001, 'system.menu.human.team.query', 100, 1, '', '', '1', 'F', '1', '0', 'system:user:query', NULL, 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:21', '');
INSERT INTO `sys_menu`
VALUES (1002, 'system.menu.human.team.insert', 100, 2, '', '', '1', 'F', '1', '0', 'system:user:insert', NULL, 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:21', '');
INSERT INTO `sys_menu`
VALUES (1003, 'system.menu.human.team.update', 100, 3, '', '', '1', 'F', '1', '0', 'system:user:update', NULL, 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:21', '');
INSERT INTO `sys_menu`
VALUES (1004, 'system.menu.human.team.remove', 100, 4, '', '', '1', 'F', '1', '0', 'system:user:remove', NULL, 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:21', '');
INSERT INTO `sys_menu`
VALUES (1005, 'system.menu.human.team.export', 100, 5, '', '', '1', 'F', '1', '0', 'system:user:export', NULL, 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:21', '');
INSERT INTO `sys_menu`
VALUES (1006, 'system.menu.human.team.import', 100, 6, '', '', '1', 'F', '1', '0', 'system:user:import', NULL, 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:21', '');
INSERT INTO `sys_menu`
VALUES (1007, 'system.menu.human.team.resetPwd', 100, 7, '', '', '1', 'F', '1', '0', 'system:user:resetPwd', NULL,
        'admin', '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:21', '');
INSERT INTO `sys_menu`
VALUES (1008, 'system.menu.human.role.query', 101, 1, '', '', '1', 'F', '0', '0', 'system:role:query', NULL, 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:21', '');
INSERT INTO `sys_menu`
VALUES (1009, 'system.menu.human.role.insert', 101, 2, '', '', '1', 'F', '0', '0', 'system:role:insert', NULL, 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:21', '');
INSERT INTO `sys_menu`
VALUES (1010, 'system.menu.human.role.update', 101, 3, '', '', '1', 'F', '0', '0', 'system:role:update', NULL, 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:21', '');
INSERT INTO `sys_menu`
VALUES (1011, 'system.menu.human.role.remove', 101, 4, '', '', '1', 'F', '0', '0', 'system:role:remove', NULL, 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:21', '');
INSERT INTO `sys_menu`
VALUES (1012, 'system.menu.human.role.export', 101, 5, '', '', '1', 'F', '0', '0', 'system:role:export', NULL, 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:21', '');
INSERT INTO `sys_menu`
VALUES (1013, 'system.menu.system.menu.query', 102, 1, '', '', '1', 'F', '0', '0', 'system:menu:query', NULL, 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:21', '');
INSERT INTO `sys_menu`
VALUES (1014, 'system.menu.system.menu.insert', 102, 2, '', '', '1', 'F', '0', '0', 'system:menu:insert', NULL, 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:21', '');
INSERT INTO `sys_menu`
VALUES (1015, 'system.menu.system.menu.update', 102, 3, '', '', '1', 'F', '0', '0', 'system:menu:update', NULL, 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:21', '');
INSERT INTO `sys_menu`
VALUES (1016, 'system.menu.system.menu.remove', 102, 4, '', '', '1', 'F', '0', '0', 'system:menu:remove', NULL, 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:21', '');
INSERT INTO `sys_menu`
VALUES (1017, 'system.menu.human.dept.query', 103, 1, '', '', '1', 'F', '0', '0', 'system:dept:query', NULL, 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:21', '');
INSERT INTO `sys_menu`
VALUES (1018, 'system.menu.human.dept.insert', 103, 2, '', '', '1', 'F', '0', '0', 'system:dept:insert', NULL, 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:21', '');
INSERT INTO `sys_menu`
VALUES (1019, 'system.menu.human.dept.update', 103, 3, '', '', '1', 'F', '0', '0', 'system:dept:update', NULL, 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:21', '');
INSERT INTO `sys_menu`
VALUES (1020, 'system.menu.human.dept.remove', 103, 4, '', '', '1', 'F', '0', '0', 'system:dept:remove', NULL, 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:21', '');
INSERT INTO `sys_menu`
VALUES (1021, 'system.menu.human.post.query', 104, 1, '', '', '1', 'F', '0', '0', 'system:post:query', NULL, 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:21', '');
INSERT INTO `sys_menu`
VALUES (1022, 'system.menu.human.post.insert', 104, 2, '', '', '1', 'F', '0', '0', 'system:post:insert', NULL, 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:21', '');
INSERT INTO `sys_menu`
VALUES (1023, 'system.menu.human.post.update', 104, 3, '', '', '1', 'F', '0', '0', 'system:post:update', NULL, 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:21', '');
INSERT INTO `sys_menu`
VALUES (1024, 'system.menu.human.post.remove', 104, 4, '', '', '1', 'F', '0', '0', 'system:post:remove', NULL, 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:21', '');
INSERT INTO `sys_menu`
VALUES (1025, 'system.menu.human.post.export', 104, 5, '', '', '1', 'F', '0', '0', 'system:post:export', NULL, 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:21', '');
INSERT INTO `sys_menu`
VALUES (1026, 'system.menu.system.dictionaries.query', 105, 1, '#', '', '1', 'F', '1', '0', 'system:dict:query', NULL,
        'admin', '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:21', '');
INSERT INTO `sys_menu`
VALUES (1027, 'system.menu.system.dictionaries.insert', 105, 2, '#', '', '1', 'F', '1', '0', 'system:dict:insert', NULL,
        'admin', '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:21', '');
INSERT INTO `sys_menu`
VALUES (1028, 'system.menu.system.dictionaries.update', 105, 3, '#', '', '1', 'F', '1', '0', 'system:dict:update', NULL,
        'admin', '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:21', '');
INSERT INTO `sys_menu`
VALUES (1029, 'system.menu.system.dictionaries.remove', 105, 4, '#', '', '1', 'F', '1', '0', 'system:dict:remove', NULL,
        'admin', '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:21', '');
INSERT INTO `sys_menu`
VALUES (1030, 'system.menu.system.dictionaries.export', 105, 5, '#', '', '1', 'F', '1', '0', 'system:dict:export', NULL,
        'admin', '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:21', '');
INSERT INTO `sys_menu`
VALUES (1036, 'system.menu.system.client.query', 107, 1, '#', '', '1', 'F', '1', '0', 'system:client:query', NULL,
        'admin', '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:21', '');
INSERT INTO `sys_menu`
VALUES (1037, 'system.menu.system.client.insert', 107, 2, '#', '', '1', 'F', '1', '0', 'system:client:insert', NULL,
        'admin', '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:21', '');
INSERT INTO `sys_menu`
VALUES (1038, 'system.menu.system.client.update', 107, 3, '#', '', '1', 'F', '1', '0', 'system:client:update', NULL,
        'admin', '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:21', '');
INSERT INTO `sys_menu`
VALUES (1039, 'system.menu.system.client.remove', 107, 4, '#', '', '1', 'F', '1', '0', 'system:client:remove', NULL,
        'admin', '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:21', '');
INSERT INTO `sys_menu`
VALUES (1040, 'system.menu.log.operation.query', 500, 1, '#', '', '1', 'F', '0', '0', 'system:operlog:query', NULL,
        'admin', '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1041, 'system.menu.log.operation.remove', 500, 2, '#', '', '1', 'F', '0', '0', 'system:operlog:remove', NULL,
        'admin', '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1042, 'system.menu.log.operation.export', 500, 4, '#', '', '1', 'F', '0', '0', 'system:operlog:export', NULL,
        'admin', '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1043, 'system.menu.log.login.query', 501, 1, '#', '', '1', 'F', '0', '0', 'system:logininfor:query', NULL,
        'admin', '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1044, 'system.menu.log.login.remove', 501, 2, '#', '', '1', 'F', '0', '0', 'system:logininfor:remove', NULL,
        'admin', '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1045, 'system.menu.log.login.export', 501, 3, '#', '', '1', 'F', '0', '0', 'system:logininfor:export', NULL,
        'admin', '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1051, 'system.menu.monitor.job.query', 110, 1, '#', '', '1', 'F', '1', '0', 'monitor:job:query', NULL, 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1052, 'system.menu.monitor.job.insert', 110, 2, '#', '', '1', 'F', '1', '0', 'monitor:job:insert', NULL,
        'admin', '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1053, 'system.menu.monitor.job.update', 110, 3, '#', '', '1', 'F', '1', '0', 'monitor:job:update', NULL,
        'admin', '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1054, 'system.menu.monitor.job.remove', 110, 4, '#', '', '1', 'F', '1', '0', 'monitor:job:remove', NULL,
        'admin', '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1061, 'system.menu.log.job', 4, 3, '/log/job', '/log/job/index', '1', 'C', '0', '0', 'system:operlog',
        'icon-log-job', 'admin', '2019-03-16 11:33:00', 'admin', '2024-03-31 09:42:48', '');
INSERT INTO `sys_menu`
VALUES (1062, 'system.menu.log.job.remove', 1061, 2, '#', '', '1', 'F', '0', '0', 'system:operlog:remove', NULL,
        'admin', '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1063, 'system.menu.log.job.export', 1061, 4, '#', '', '1', 'F', '0', '0', 'system:operlog:export', NULL,
        'admin', '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1064, 'system.menu.log.job.query', 1061, 1, '#', '', '1', 'F', '0', '0', 'system:operlog:query', NULL, 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1071, 'system.menu.system.dfs', 1, 6, '/system/dfs', '/system/dfs/index', '1', 'C', '0', '0', 'dfs:dfs:list',
        'icon-DFS', 'admin', '2019-03-16 11:33:00', 'admin', '2024-03-31 09:07:24', '');
INSERT INTO `sys_menu`
VALUES (1072, 'system.menu.tool.gen.code', 1100, 2, '/tool/code/gen', '/tool/code/gen/index', '1', 'C', '0', '0',
        'gen:list', 'icon-gen-code', 'admin', '2019-03-16 11:33:00', 'admin', '2024-03-31 10:12:10', '');
INSERT INTO `sys_menu`
VALUES (1073, 'system.menu.tool.gen.code.query', 1072, 1, '', NULL, '1', 'F', '0', '0', 'gen:query', '#', 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1074, 'system.menu.tool.gen.code.edit', 1072, 2, '', NULL, '1', 'F', '0', '0', 'gen:edit', '#', 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1075, 'system.menu.tool.gen.code.import', 1072, 3, '', NULL, '1', 'F', '0', '0', 'gen:import', '#', 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1076, 'system.menu.tool.gen.code.remove', 1072, 4, '', NULL, '1', 'F', '0', '0', 'gen:remove', '#', 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1077, 'system.menu.tool.gen.code.preview', 1072, 5, '', NULL, '1', 'F', '0', '0', 'gen:preview', '#', 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1078, 'system.menu.tool.gen.code.code', 1072, 6, '', NULL, '1', 'F', '0', '0', 'gen:code', '#', 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1080, 'system.menu.system.dfs.remove', 1071, 1, '', NULL, '1', 'F', '0', '0', 'dfs:dfs:remove', '#', 'admin',
        '2019-03-16 11:33:00', '', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1082, 'system.menu.index', 0, 0, '/', '/index', '1', 'C', '0', '0', 'index', 'icon-home', 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-03-31 19:06:42', '');
INSERT INTO `sys_menu`
VALUES (1083, 'system.menu.monitor', 0, 2, '/monitor', NULL, '1', 'M', '0', '0', NULL, 'icon-monitor', 'admin',
        '2019-03-16 11:33:00', 'admin', '2024-03-30 20:46:21', '');
INSERT INTO `sys_menu`
VALUES (1084, 'system.menu.monitor.redis', 1083, 1, '/monitor/redis', '/monitor/redis/index', '1', 'C', '0', '0',
        'monitor:redis:query', 'icon-redis', 'admin', '2019-03-16 11:33:00', 'admin', '2024-03-31 09:20:00', '');
INSERT INTO `sys_menu`
VALUES (1092, 'system.menu.system.token', 1, 7, '/system/token', '/system/token/index', '1', 'C', '0', '0',
        'system:token:list', 'icon-token', 'admin', '2019-03-16 11:33:00', 'admin', '2024-03-31 09:07:31', '');
INSERT INTO `sys_menu`
VALUES (1093, 'system.menu.system.token.remove', 1092, 1, '', NULL, '1', 'F', '1', '0', 'system:token:remove', '#',
        'admin', '2019-03-16 11:33:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1094, 'system.menu.tool.gen.ds', 1100, 1, '/tool/code/ds_conf', 'gen/code/dsConf/index', '1', 'C', '0', '0',
        'gen:dsConf:list', 'icon-gen-ds_conf', 'admin', '2018-03-01 00:00:00', 'admin', '2024-03-31 10:11:59',
        '数据源菜单');
INSERT INTO `sys_menu`
VALUES (1095, 'system.menu.tool.gen.ds.query', 1094, 1, '#', '', '1', 'F', '0', '0', 'gen:dsConf:query', '#', 'admin',
        '2018-03-01 00:00:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1096, 'system.menu.tool.gen.ds.add', 1094, 2, '#', '', '1', 'F', '0', '0', 'gen:dsConf:add', '#', 'admin',
        '2018-03-01 00:00:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1097, 'system.menu.tool.gen.ds.edit', 1094, 3, '#', '', '1', 'F', '0', '0', 'gen:dsConf:edit', '#', 'admin',
        '2018-03-01 00:00:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1098, 'system.menu.tool.gen.ds.remove', 1094, 4, '#', '', '1', 'F', '0', '0', 'gen:dsConf:remove', '#', 'admin',
        '2018-03-01 00:00:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1100, 'system.menu.tool.gen', 3, 1, '/tool/code', NULL, '1', 'M', '0', '0', NULL, 'gen', 'admin',
        '2023-11-04 18:04:01', 'admin', '2024-03-31 10:11:50', '');
INSERT INTO `sys_menu`
VALUES (1101, 'system.menu.tool.gen.metadata.type', 1114, 1, '/tool/code/metadata/type', '/Tool/Metadata/Code/Type',
        '1', 'C', '0', '0', 'gen:type:list', 'icon-gen-type', 'admin', '2023-11-04 20:46:29', 'admin',
        '2024-03-31 10:14:11', '');
INSERT INTO `sys_menu`
VALUES (1102, 'system.menu.tool.gen.metadata.group', 1114, 3, '/tool/code/metadata/template_group',
        '/tool/code/metadata/templateGroup/index', '1', 'C', '0', '0', 'gen:group:list', '#', 'admin',
        '2018-03-01 00:00:00', 'admin', '2024-03-31 10:14:33', '模板分组菜单');
INSERT INTO `sys_menu`
VALUES (1103, 'system.menu.tool.gen.metadata.group.query', 1102, 1, '#', '', '1', 'F', '0', '0', 'gen:group:query', '#',
        'admin', '2018-03-01 00:00:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1104, 'system.menu.tool.gen.metadata.group.add', 1102, 2, '#', '', '1', 'F', '0', '0', 'gen:group:add', '#',
        'admin', '2018-03-01 00:00:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1105, 'system.menu.tool.gen.metadata.group.edit', 1102, 3, '#', '', '1', 'F', '0', '0', 'gen:group:edit', '#',
        'admin', '2018-03-01 00:00:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1106, 'system.menu.tool.gen.metadata.group.remove', 1102, 4, '#', '', '1', 'F', '0', '0', 'gen:group:remove',
        '#', 'admin', '2018-03-01 00:00:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1108, 'system.menu.tool.gen.metadata.template', 1114, 2, '/tool/code/metadata/template',
        'tool/code/metadata/template/index', '1', 'C', '0', '0', 'gen:templateGroup:list', '#', 'admin',
        '2018-03-01 00:00:00', 'admin', '2024-03-31 10:14:22', '模板管理菜单');
INSERT INTO `sys_menu`
VALUES (1109, 'system.menu.tool.gen.metadata.template.query', 1108, 1, '#', '', '1', 'F', '0', '0',
        'gen:templateGroup:query', '#', 'admin', '2018-03-01 00:00:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1110, 'system.menu.tool.gen.metadata.template.add', 1108, 2, '#', '', '1', 'F', '0', '0',
        'gen:templateGroup:add', '#', 'admin', '2018-03-01 00:00:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1111, 'system.menu.tool.gen.metadata.template.edit', 1108, 3, '#', '', '1', 'F', '0', '0',
        'gen:templateGroup:edit', '#', 'admin', '2018-03-01 00:00:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1112, 'system.menu.tool.gen.metadata.template.remove', 1108, 4, '#', '', '1', 'F', '0', '0',
        'gen:templateGroup:remove', '#', 'admin', '2018-03-01 00:00:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1114, 'system.menu.tool.gen.metadata', 1100, 3, '/tool/code/metadata', NULL, '1', 'M', '0', '0', '',
        'icon-gen-metadata', 'admin', '2023-11-06 17:29:08', 'admin', '2024-03-31 10:12:17', '');
INSERT INTO `sys_menu`
VALUES (1115, 'system.menu.tool.gen.metadata.type.query', 1101, 1, '#', '', '1', 'F', '0', '0', 'gen:type:query', '#',
        'admin', '2018-03-01 00:00:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1116, 'system.menu.tool.gen.metadata.type.add', 1101, 2, '#', '', '1', 'F', '0', '0', 'gen:type:add', '#',
        'admin', '2018-03-01 00:00:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1117, 'system.menu.tool.gen.metadata.type.edit', 1101, 3, '#', '', '1', 'F', '0', '0', 'gen:type:edit', '#',
        'admin', '2018-03-01 00:00:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1118, 'system.menu.tool.gen.metadata.type.remove', 1101, 4, '#', '', '1', 'F', '0', '0', 'gen:type:remove', '#',
        'admin', '2018-03-01 00:00:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1119, 'system.menu.system.i18n', 1, 8, 'i18n', 'system/i18n/index', '1', 'C', '0', '0', 'system:i18n:list',
        'icon-i18n', 'admin', '2018-03-01 00:00:00', 'admin', '2024-03-31 09:07:53', '国际化菜单');
INSERT INTO `sys_menu`
VALUES (1120, 'system.menu.system.i18n.query', 1119, 1, '#', '', '1', 'F', '0', '0', 'system:i18n:query', '#', 'admin',
        '2018-03-01 00:00:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1121, 'system.menu.system.i18n.add', 1119, 2, '#', '', '1', 'F', '0', '0', 'system:i18n:add', '#', 'admin',
        '2018-03-01 00:00:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1122, 'system.menu.system.i18n.edit', 1119, 3, '#', '', '1', 'F', '0', '0', 'system:i18n:edit', '#', 'admin',
        '2018-03-01 00:00:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1123, 'system.menu.system.i18n.remove', 1119, 4, '#', '', '1', 'F', '0', '0', 'system:i18n:remove', '#',
        'admin', '2018-03-01 00:00:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1124, 'system.menu.system.i18n.export', 1119, 5, '#', '', '1', 'F', '0', '0', 'system:i18n:export', '#',
        'admin', '2018-03-01 00:00:00', 'admin', '2024-03-31 11:17:22', '');
INSERT INTO `sys_menu`
VALUES (1125, 'AI知识库', 0, 4, '/ai', NULL, '1', 'M', '0', '0', NULL, 'icon-ai', 'admin', '2024-10-26 18:50:42',
        'admin', '2024-10-26 18:54:14', '');
INSERT INTO `sys_menu`
VALUES (1126, 'AI助手', 1125, 4, '/ai/chat', '/ai/chat', '1', 'C', '0', '0', 'ai:chat', 'icon-ai-chat', 'admin',
        '2024-10-26 19:15:17', 'admin', '2024-11-16 21:17:51', '');
INSERT INTO `sys_menu`
VALUES (1127, 'AI知识库文档', 1125, 3, 'doc', 'system/doc/index', '1', 'C', '0', '0', 'ai:doc:list', '#', 'admin',
        '2018-03-01 00:00:00', 'admin', '2024-11-16 21:17:29', 'AI知识库文档菜单');
INSERT INTO `sys_menu`
VALUES (1128, 'AI知识库文档查询', 1127, 1, '#', '', '1', 'F', '0', '0', 'ai:doc:query', '#', 'admin',
        '2018-03-01 00:00:00', 'admin', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (1129, 'AI知识库文档新增', 1127, 2, '#', '', '1', 'F', '0', '0', 'ai:doc:add', '#', 'admin',
        '2018-03-01 00:00:00', 'admin', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (1130, 'AI知识库文档修改', 1127, 3, '#', '', '1', 'F', '0', '0', 'ai:doc:edit', '#', 'admin',
        '2018-03-01 00:00:00', 'admin', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (1131, 'AI知识库文档删除', 1127, 4, '#', '', '1', 'F', '0', '0', 'ai:doc:remove', '#', 'admin',
        '2018-03-01 00:00:00', 'admin', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (1132, 'AI知识库文档导出', 1127, 5, '#', '', '1', 'F', '0', '0', 'ai:doc:export', '#', 'admin',
        '2018-03-01 00:00:00', 'admin', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (1133, 'AI知识库文档分片', 1125, 2, 'slice', 'system/slice/index', '1', 'C', '0', '0', 'ai:slice:list', '#',
        'admin', '2018-03-01 00:00:00', 'admin', '2024-11-16 21:17:22', 'AI知识库文档分片菜单');
INSERT INTO `sys_menu`
VALUES (1134, 'AI知识库文档分片查询', 1133, 1, '#', '', '1', 'F', '0', '0', 'ai:slice:query', '#', 'admin',
        '2018-03-01 00:00:00', 'admin', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (1135, 'AI知识库文档分片新增', 1133, 2, '#', '', '1', 'F', '0', '0', 'ai:slice:add', '#', 'admin',
        '2018-03-01 00:00:00', 'admin', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (1136, 'AI知识库文档分片修改', 1133, 3, '#', '', '1', 'F', '0', '0', 'ai:slice:edit', '#', 'admin',
        '2018-03-01 00:00:00', 'admin', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (1137, 'AI知识库文档分片删除', 1133, 4, '#', '', '1', 'F', '0', '0', 'ai:slice:remove', '#', 'admin',
        '2018-03-01 00:00:00', 'admin', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (1138, 'AI知识库文档分片导出', 1133, 5, '#', '', '1', 'F', '0', '0', 'ai:slice:export', '#', 'admin',
        '2018-03-01 00:00:00', 'admin', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (1139, 'AI知识库', 1125, 1, 'model', 'system/model/index', '1', 'C', '0', '0', 'ai:model:list', '#', 'admin',
        '2018-03-01 00:00:00', 'admin', '2024-11-16 20:30:17', 'AI知识库菜单');
INSERT INTO `sys_menu`
VALUES (1140, 'AI知识库查询', 1139, 1, '#', '', '1', 'F', '0', '0', 'ai:model:query', '#', 'admin',
        '2018-03-01 00:00:00', 'admin', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (1141, 'AI知识库新增', 1139, 2, '#', '', '1', 'F', '0', '0', 'ai:model:add', '#', 'admin', '2018-03-01 00:00:00',
        'admin', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (1142, 'AI知识库修改', 1139, 3, '#', '', '1', 'F', '0', '0', 'ai:model:edit', '#', 'admin',
        '2018-03-01 00:00:00', 'admin', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (1143, 'AI知识库删除', 1139, 4, '#', '', '1', 'F', '0', '0', 'ai:model:remove', '#', 'admin',
        '2018-03-01 00:00:00', 'admin', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (1144, 'AI知识库导出', 1139, 5, '#', '', '1', 'F', '0', '0', 'ai:model:export', '#', 'admin',
        '2018-03-01 00:00:00', 'admin', '2018-03-01 00:00:00', '');



-- ----------------------------
-- Table structure for sys_oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `sys_oauth_client_details`;
CREATE TABLE `sys_oauth_client_details`
(
    `client_id`               varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端唯一标识',
    `resource_ids`            varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资源ID标识',
    `client_secret`           varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端安全码',
    `scope`                   varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端授权范围',
    `authorized_grant_types`  varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端授权类型',
    `web_server_redirect_uri` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务器回调地址',
    `authorities`             varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '访问资源所需权限',
    `access_token_validity`   int(11)                                                       NULL DEFAULT NULL COMMENT '设定客户端的access_token的有效时间值（秒）',
    `refresh_token_validity`  int(11)                                                       NULL DEFAULT NULL COMMENT '设定客户端的refresh_token的有效时间值（秒）',
    `additional_information`  varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预留字段',
    `autoapprove`             tinyint(4)                                                    NULL DEFAULT NULL COMMENT '是否登录时跳过授权（默认false）',
    PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '客户端配置表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_oauth_client_details
-- ----------------------------
INSERT INTO `sys_oauth_client_details`
VALUES ('twelvet', NULL, '123456', 'server', 'password,sms,refresh_token,authorization_code', 'http://twelvet.cn', NULL,
        3600, 7200, NULL, NULL);

-- ----------------------------
-- Table structure for sys_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation_log`;
CREATE TABLE `sys_operation_log`
(
    `oper_id`        bigint(20)                                               NOT NULL AUTO_INCREMENT COMMENT '日志主键',
    `service`        varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci   NULL DEFAULT '' COMMENT '模块标题',
    `business_type`  int(2)                                                   NULL DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除）',
    `method`         varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci  NULL DEFAULT '' COMMENT '方法名称',
    `request_method` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci   NULL DEFAULT '' COMMENT '请求方式',
    `operator_type`  tinyint(1)                                               NULL DEFAULT 0 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
    `oper_name`      varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci   NULL DEFAULT '' COMMENT '操作人员',
    `dept_name`      varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci   NULL DEFAULT '' COMMENT '部门名称',
    `oper_url`       varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci  NULL DEFAULT '' COMMENT '请求URL',
    `oper_ip`        varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci   NULL DEFAULT '' COMMENT '主机地址',
    `oper_location`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci  NULL DEFAULT '' COMMENT '操作地点',
    `oper_param`     varchar(2000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '请求参数',
    `json_result`    varchar(2000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '返回参数',
    `status`         tinyint(1)                                               NULL DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
    `error_msg`      varchar(2000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '错误消息',
    `oper_time`      datetime                                                 NULL DEFAULT NULL COMMENT '操作时间',
    `dept_id`        bigint(20)                                               NOT NULL COMMENT '部门ID',
    PRIMARY KEY (`oper_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_unicode_ci COMMENT = '操作日志记录'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_operation_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`
(
    `post_id`     bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `post_code`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '岗位编码',
    `post_name`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '岗位名称',
    `post_sort`   int(4)                                                        NOT NULL COMMENT '显示顺序',
    `status`      char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci      NOT NULL COMMENT '状态（0正常 1停用）',
    `create_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                      NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                      NULL DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '岗位信息表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post`
VALUES (1, 'ceo', '董事长', 1, '0', 'admin', '2018-03-16 11:33:00', 'admin', '2020-12-31 16:49:55', '董事长');
INSERT INTO `sys_post`
VALUES (2, 'se', '项目经理', 2, '0', 'admin', '2018-03-16 11:33:00', 'admin', '2020-10-22 15:19:10', '项目经理');
INSERT INTO `sys_post`
VALUES (3, 'hr', '人力资源', 3, '0', 'admin', '2018-03-16 11:33:00', 'admin', '2020-10-22 15:19:18', '人力资源');
INSERT INTO `sys_post`
VALUES (4, 'staff', '普通员工', 4, '0', 'admin', '2018-03-16 11:33:00', 'admin', '2020-10-22 15:19:30', '普通员工');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `role_id`     bigint(20)                                              NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `role_name`   varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci  NOT NULL COMMENT '角色名称',
    `role_key`    varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '角色权限字符串',
    `role_sort`   int(4)                                                  NOT NULL COMMENT '显示顺序',
    `data_scope`  char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci      NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
    `status`      tinyint(1)                                              NOT NULL COMMENT '角色状态（0正常 1停用）',
    `del_flag`    char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci      NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    `create_by`   varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci  NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci  NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                NULL DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  CHARACTER SET = utf8
  COLLATE = utf8_unicode_ci COMMENT = '角色信息表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role`
VALUES (1, '管理员', 'admin', 1, '1', 0, '0', 'admin', '2020-11-19 14:42:34', 'admin', '2021-01-11 22:40:02', '管理员');
INSERT INTO `sys_role`
VALUES (2, '普通角色', 'common', 2, '4', 0, '0', 'admin', '2020-11-19 14:42:34', 'admin', '2022-03-22 09:44:06',
        '普通角色');
INSERT INTO `sys_role`
VALUES (5, '测试角色', 'test', 3, '1', 0, '0', 'admin', '2020-12-19 20:54:25', 'admin', '2022-03-22 09:45:48', NULL);

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept`
(
    `role_id` bigint(20) NOT NULL COMMENT '角色ID',
    `dept_id` bigint(20) NOT NULL COMMENT '部门ID',
    PRIMARY KEY (`role_id`, `dept_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '角色和部门关联表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------
INSERT INTO `sys_role_dept`
VALUES (2, 103);
INSERT INTO `sys_role_dept`
VALUES (4, 103);
INSERT INTO `sys_role_dept`
VALUES (4, 104);
INSERT INTO `sys_role_dept`
VALUES (4, 105);
INSERT INTO `sys_role_dept`
VALUES (4, 106);
INSERT INTO `sys_role_dept`
VALUES (4, 107);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`
(
    `role_id` bigint(20) NOT NULL COMMENT '角色ID',
    `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_unicode_ci COMMENT = '角色和菜单关联表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu`
VALUES (2, 2);
INSERT INTO `sys_role_menu`
VALUES (2, 4);
INSERT INTO `sys_role_menu`
VALUES (2, 100);
INSERT INTO `sys_role_menu`
VALUES (2, 101);
INSERT INTO `sys_role_menu`
VALUES (2, 103);
INSERT INTO `sys_role_menu`
VALUES (2, 104);
INSERT INTO `sys_role_menu`
VALUES (2, 500);
INSERT INTO `sys_role_menu`
VALUES (2, 501);
INSERT INTO `sys_role_menu`
VALUES (2, 1001);
INSERT INTO `sys_role_menu`
VALUES (2, 1002);
INSERT INTO `sys_role_menu`
VALUES (2, 1003);
INSERT INTO `sys_role_menu`
VALUES (2, 1004);
INSERT INTO `sys_role_menu`
VALUES (2, 1005);
INSERT INTO `sys_role_menu`
VALUES (2, 1006);
INSERT INTO `sys_role_menu`
VALUES (2, 1007);
INSERT INTO `sys_role_menu`
VALUES (2, 1008);
INSERT INTO `sys_role_menu`
VALUES (2, 1009);
INSERT INTO `sys_role_menu`
VALUES (2, 1010);
INSERT INTO `sys_role_menu`
VALUES (2, 1011);
INSERT INTO `sys_role_menu`
VALUES (2, 1012);
INSERT INTO `sys_role_menu`
VALUES (2, 1017);
INSERT INTO `sys_role_menu`
VALUES (2, 1018);
INSERT INTO `sys_role_menu`
VALUES (2, 1019);
INSERT INTO `sys_role_menu`
VALUES (2, 1020);
INSERT INTO `sys_role_menu`
VALUES (2, 1021);
INSERT INTO `sys_role_menu`
VALUES (2, 1022);
INSERT INTO `sys_role_menu`
VALUES (2, 1023);
INSERT INTO `sys_role_menu`
VALUES (2, 1024);
INSERT INTO `sys_role_menu`
VALUES (2, 1025);
INSERT INTO `sys_role_menu`
VALUES (2, 1040);
INSERT INTO `sys_role_menu`
VALUES (2, 1041);
INSERT INTO `sys_role_menu`
VALUES (2, 1042);
INSERT INTO `sys_role_menu`
VALUES (2, 1043);
INSERT INTO `sys_role_menu`
VALUES (2, 1044);
INSERT INTO `sys_role_menu`
VALUES (2, 1045);
INSERT INTO `sys_role_menu`
VALUES (2, 1061);
INSERT INTO `sys_role_menu`
VALUES (2, 1062);
INSERT INTO `sys_role_menu`
VALUES (2, 1063);
INSERT INTO `sys_role_menu`
VALUES (2, 1064);
INSERT INTO `sys_role_menu`
VALUES (5, 110);
INSERT INTO `sys_role_menu`
VALUES (5, 1051);
INSERT INTO `sys_role_menu`
VALUES (5, 1052);
INSERT INTO `sys_role_menu`
VALUES (5, 1053);
INSERT INTO `sys_role_menu`
VALUES (5, 1054);
INSERT INTO `sys_role_menu`
VALUES (5, 1083);
INSERT INTO `sys_role_menu`
VALUES (5, 1084);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `user_id`     bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `dept_id`     bigint(20)                                                    NULL DEFAULT NULL COMMENT '部门ID',
    `username`    varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '用户账号',
    `nick_name`   varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '用户昵称',
    `user_type`   varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT '00' COMMENT '用户类型（00系统用户）',
    `email`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT '' COMMENT '用户邮箱',
    `phonenumber` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT '' COMMENT '手机号码',
    `sex`         tinyint(1)                                                    NULL DEFAULT NULL COMMENT '用户性别（0男 1女 2未知）',
    `avatar`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '头像地址',
    `password`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '密码',
    `status`      tinyint(1)                                                    NULL DEFAULT NULL COMMENT '帐号状态（0正常 1停用）',
    `del_flag`    char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci      NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    `login_ip`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT '' COMMENT '最后登陆IP',
    `login_date`  datetime                                                      NULL DEFAULT NULL COMMENT '最后登陆时间',
    `create_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                      NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                      NULL DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '用户信息表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user`
VALUES (1, 101, 'admin', 'TwelveT', '00', '2471835953@qq.com', '15788888888', 0, '/avatar.jpg',
        '$2a$10$MBq3PvaIX9Yghhu.mm45wO8IC4WKXAUST9LvneCsQ71k/mrOdN6SO', 0, '0', '127.0.0.1', '2018-03-16 11:33:00',
        'admin', '2021-02-11 11:33:00', 'admin', '2021-03-03 10:19:38', '管理员');
INSERT INTO `sys_user`
VALUES (4, 103, 'demo', 'demo', '00', '8888@qq.com', '15478978787', 2, '/avatar.jpg',
        '$2a$10$MBq3PvaIX9Yghhu.mm45wO8IC4WKXAUST9LvneCsQ71k/mrOdN6SO', 0, '0', '', NULL, 'admin',
        '2020-11-19 14:06:45', 'admin', '2022-03-21 18:03:54', NULL);

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post`
(
    `user_id` bigint(20) NOT NULL COMMENT '用户ID',
    `post_id` bigint(20) NOT NULL COMMENT '岗位ID',
    PRIMARY KEY (`user_id`, `post_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '用户与岗位关联表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
INSERT INTO `sys_user_post`
VALUES (1, 1);
INSERT INTO `sys_user_post`
VALUES (1, 2);
INSERT INTO `sys_user_post`
VALUES (2, 1);
INSERT INTO `sys_user_post`
VALUES (2, 2);
INSERT INTO `sys_user_post`
VALUES (2, 3);
INSERT INTO `sys_user_post`
VALUES (2, 4);
INSERT INTO `sys_user_post`
VALUES (3, 1);
INSERT INTO `sys_user_post`
VALUES (4, 3);
INSERT INTO `sys_user_post`
VALUES (4, 4);
INSERT INTO `sys_user_post`
VALUES (5, 4);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `user_id` bigint(20) NOT NULL COMMENT '用户ID',
    `role_id` bigint(20) NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_unicode_ci COMMENT = '用户和角色关联表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role`
VALUES (1, 1);
INSERT INTO `sys_user_role`
VALUES (4, 2);
INSERT INTO `sys_user_role`
VALUES (4, 5);

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`
(
    `branch_id`     bigint(20)                                                    NOT NULL COMMENT 'branch transaction id',
    `xid`           varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'global transaction id',
    `context`       varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'undo_log context,such as serialization',
    `rollback_info` longblob                                                      NOT NULL COMMENT 'rollback info',
    `log_status`    int(11)                                                       NOT NULL COMMENT '0:normal status,1:defense status',
    `log_created`   datetime(6)                                                   NOT NULL COMMENT 'create datetime',
    `log_modified`  datetime(6)                                                   NOT NULL COMMENT 'modify datetime',
    UNIQUE INDEX `ux_undo_log` (`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = 'AT transaction mode undo table'
  ROW_FORMAT = DYNAMIC;


-- ----------------------------
-- Table structure for i18n
-- ----------------------------
DROP TABLE IF EXISTS `i18n`;
CREATE TABLE `i18n`
(
    `i18n_id`     bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `code`        varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '唯一Code',
    `type`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT '' COMMENT '语言类型：zh_CN,en_US...',
    `value`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NOT NULL COMMENT '翻译值',
    `create_time` datetime                                                      NOT NULL COMMENT '创建时间',
    `update_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                      NOT NULL COMMENT '更新时间',
    `remark`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`i18n_id`) USING BTREE,
    UNIQUE INDEX `un_code_type` (`code`, `type`) USING BTREE COMMENT 'Code翻译唯一'
) ENGINE = InnoDB
  AUTO_INCREMENT = 209
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '国际化表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of i18n
-- ----------------------------
INSERT INTO `i18n`
VALUES (1, 'system.menu.index', 'zh_CN', '欢迎页', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29', '欢迎页');
INSERT INTO `i18n`
VALUES (2, 'system.menu.system', 'zh_CN', '系统管理', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '系统管理');
INSERT INTO `i18n`
VALUES (3, 'system.menu.monitor', 'zh_CN', '系统监控', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '系统监控');
INSERT INTO `i18n`
VALUES (4, 'system.menu.human', 'zh_CN', '人力管理', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29', '人力管理');
INSERT INTO `i18n`
VALUES (5, 'system.menu.log', 'zh_CN', '日志管理', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29', '日志管理');
INSERT INTO `i18n`
VALUES (6, 'system.menu.tool', 'zh_CN', '工具箱', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29', '工具箱');
INSERT INTO `i18n`
VALUES (7, 'system.menu.system.menu', 'zh_CN', '菜单管理', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '菜单管理');
INSERT INTO `i18n`
VALUES (8, 'system.menu.system.dictionaries', 'zh_CN', '字典管理', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '字典管理');
INSERT INTO `i18n`
VALUES (9, 'system.menu.system.client', 'zh_CN', 'Oauth2终端', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        'Oauth2终端');
INSERT INTO `i18n`
VALUES (10, 'system.menu.system.dfs', 'zh_CN', '文件管理', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '文件管理');
INSERT INTO `i18n`
VALUES (11, 'system.menu.system.token', 'zh_CN', '令牌管理', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '令牌管理');
INSERT INTO `i18n`
VALUES (12, 'system.menu.system.i18n', 'zh_CN', '国际化', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '国际化');
INSERT INTO `i18n`
VALUES (13, 'system.menu.monitor.redis', 'zh_CN', '缓存监控', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '缓存监控');
INSERT INTO `i18n`
VALUES (14, 'system.menu.monitor.job', 'zh_CN', '定时任务', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '定时任务');
INSERT INTO `i18n`
VALUES (15, 'system.menu.human.team', 'zh_CN', '职员管理', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '职员管理');
INSERT INTO `i18n`
VALUES (16, 'system.menu.human.role', 'zh_CN', '角色管理', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '角色管理');
INSERT INTO `i18n`
VALUES (17, 'system.menu.human.dept', 'zh_CN', '部门管理', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '部门管理');
INSERT INTO `i18n`
VALUES (18, 'system.menu.human.post', 'zh_CN', '岗位管理', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '岗位管理');
INSERT INTO `i18n`
VALUES (19, 'system.menu.log.operation', 'zh_CN', '操作日志', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '操作日志');
INSERT INTO `i18n`
VALUES (20, 'system.menu.log.login', 'zh_CN', '登录日志', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '登录日志');
INSERT INTO `i18n`
VALUES (21, 'system.menu.log.job', 'zh_CN', '定时任务日志', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '定时任务日志');
INSERT INTO `i18n`
VALUES (22, 'system.menu.tool.gen', 'zh_CN', '开发平台', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '开发平台');
INSERT INTO `i18n`
VALUES (23, 'system.menu.tool.gen.ds', 'zh_CN', '数据源', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '数据源');
INSERT INTO `i18n`
VALUES (24, 'system.menu.tool.gen.code', 'zh_CN', '代码生成', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '代码生成');
INSERT INTO `i18n`
VALUES (25, 'system.menu.tool.gen.metadata', 'zh_CN', '元数据管理', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '元数据管理');
INSERT INTO `i18n`
VALUES (26, 'system.menu.tool.gen.metadata.type', 'zh_CN', '字段管理', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '字段管理');
INSERT INTO `i18n`
VALUES (27, 'system.menu.tool.gen.metadata.template', 'zh_CN', '模板管理', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '模板管理');
INSERT INTO `i18n`
VALUES (28, 'system.menu.tool.gen.metadata.group', 'zh_CN', '模板分组', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '模板分组');
INSERT INTO `i18n`
VALUES (29, 'system.menu.human.team.query', 'zh_CN', '用户查询', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '用户查询');
INSERT INTO `i18n`
VALUES (30, 'system.menu.human.team.insert', 'zh_CN', '用户新增', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '用户新增');
INSERT INTO `i18n`
VALUES (31, 'system.menu.human.team.update', 'zh_CN', '用户修改', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '用户修改');
INSERT INTO `i18n`
VALUES (32, 'system.menu.human.team.remove', 'zh_CN', '用户删除', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '用户删除');
INSERT INTO `i18n`
VALUES (33, 'system.menu.human.team.export', 'zh_CN', '用户导出', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '用户导出');
INSERT INTO `i18n`
VALUES (34, 'system.menu.human.team.import', 'zh_CN', '用户导入', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '用户导入');
INSERT INTO `i18n`
VALUES (35, 'system.menu.human.team.resetPwd', 'zh_CN', '重置密码', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '重置密码');
INSERT INTO `i18n`
VALUES (36, 'system.menu.human.role.query', 'zh_CN', '角色查询', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '角色查询');
INSERT INTO `i18n`
VALUES (37, 'system.menu.human.role.insert', 'zh_CN', '角色新增', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '角色新增');
INSERT INTO `i18n`
VALUES (38, 'system.menu.human.role.update', 'zh_CN', '角色修改', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '角色修改');
INSERT INTO `i18n`
VALUES (39, 'system.menu.human.role.remove', 'zh_CN', '角色删除', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '角色删除');
INSERT INTO `i18n`
VALUES (40, 'system.menu.human.role.export', 'zh_CN', '角色导出', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '角色导出');
INSERT INTO `i18n`
VALUES (41, 'system.menu.system.menu.query', 'zh_CN', '菜单查询', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '菜单查询');
INSERT INTO `i18n`
VALUES (42, 'system.menu.system.menu.insert', 'zh_CN', '菜单新增', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '菜单新增');
INSERT INTO `i18n`
VALUES (43, 'system.menu.system.menu.update', 'zh_CN', '菜单修改', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '菜单修改');
INSERT INTO `i18n`
VALUES (44, 'system.menu.system.menu.remove', 'zh_CN', '菜单删除', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '菜单删除');
INSERT INTO `i18n`
VALUES (45, 'system.menu.human.dept.query', 'zh_CN', '部门查询', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '部门查询');
INSERT INTO `i18n`
VALUES (46, 'system.menu.human.dept.insert', 'zh_CN', '部门新增', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '部门新增');
INSERT INTO `i18n`
VALUES (47, 'system.menu.human.dept.update', 'zh_CN', '部门修改', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '部门修改');
INSERT INTO `i18n`
VALUES (48, 'system.menu.human.dept.remove', 'zh_CN', '部门删除', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '部门删除');
INSERT INTO `i18n`
VALUES (49, 'system.menu.human.post.query', 'zh_CN', '岗位查询', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '岗位查询');
INSERT INTO `i18n`
VALUES (50, 'system.menu.human.post.insert', 'zh_CN', '岗位新增', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '岗位新增');
INSERT INTO `i18n`
VALUES (51, 'system.menu.human.post.update', 'zh_CN', '岗位修改', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '岗位修改');
INSERT INTO `i18n`
VALUES (52, 'system.menu.human.post.remove', 'zh_CN', '岗位删除', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '岗位删除');
INSERT INTO `i18n`
VALUES (53, 'system.menu.human.post.export', 'zh_CN', '岗位导出', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '岗位导出');
INSERT INTO `i18n`
VALUES (54, 'system.menu.system.dictionaries.query', 'zh_CN', '字典查询', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '字典查询');
INSERT INTO `i18n`
VALUES (55, 'system.menu.system.dictionaries.insert', 'zh_CN', '字典新增', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '字典新增');
INSERT INTO `i18n`
VALUES (56, 'system.menu.system.dictionaries.update', 'zh_CN', '字典修改', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '字典修改');
INSERT INTO `i18n`
VALUES (57, 'system.menu.system.dictionaries.remove', 'zh_CN', '字典删除', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '字典删除');
INSERT INTO `i18n`
VALUES (58, 'system.menu.system.dictionaries.export', 'zh_CN', '字典导出', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '字典导出');
INSERT INTO `i18n`
VALUES (59, 'system.menu.system.client.query', 'zh_CN', '终端查询', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '终端查询');
INSERT INTO `i18n`
VALUES (60, 'system.menu.system.client.insert', 'zh_CN', '终端新增', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '终端新增');
INSERT INTO `i18n`
VALUES (61, 'system.menu.system.client.update', 'zh_CN', '终端修改', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '终端修改');
INSERT INTO `i18n`
VALUES (62, 'system.menu.system.client.remove', 'zh_CN', '终端删除', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '终端删除');
INSERT INTO `i18n`
VALUES (63, 'system.menu.monitor.job.query', 'zh_CN', '任务查询', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '任务查询');
INSERT INTO `i18n`
VALUES (64, 'system.menu.monitor.job.insert', 'zh_CN', '任务新增', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '任务新增');
INSERT INTO `i18n`
VALUES (65, 'system.menu.monitor.job.update', 'zh_CN', '任务修改', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '任务修改');
INSERT INTO `i18n`
VALUES (66, 'system.menu.monitor.job.remove', 'zh_CN', '任务删除', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '任务删除');
INSERT INTO `i18n`
VALUES (67, 'system.menu.log.operation.query', 'zh_CN', '操作查询', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '操作查询');
INSERT INTO `i18n`
VALUES (68, 'system.menu.log.operation.remove', 'zh_CN', '操作删除', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '操作删除');
INSERT INTO `i18n`
VALUES (69, 'system.menu.log.operation.export', 'zh_CN', '日志导出', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '日志导出');
INSERT INTO `i18n`
VALUES (70, 'system.menu.log.login.query', 'zh_CN', '登录查询', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '登录查询');
INSERT INTO `i18n`
VALUES (71, 'system.menu.log.login.remove', 'zh_CN', '登录删除', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '登录删除');
INSERT INTO `i18n`
VALUES (72, 'system.menu.log.login.export', 'zh_CN', '日志导出', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '日志导出');
INSERT INTO `i18n`
VALUES (73, 'system.menu.log.job.query', 'zh_CN', '操作查询', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '操作查询');
INSERT INTO `i18n`
VALUES (74, 'system.menu.log.job.remove', 'zh_CN', '操作删除', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '操作删除');
INSERT INTO `i18n`
VALUES (75, 'system.menu.log.job.export', 'zh_CN', '日志导出', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '日志导出');
INSERT INTO `i18n`
VALUES (76, 'system.menu.system.dfs.remove', 'zh_CN', 'DFS文件删除', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', 'DFS文件删除');
INSERT INTO `i18n`
VALUES (77, 'system.menu.tool.gen.code.query', 'zh_CN', '生成查询', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '生成查询');
INSERT INTO `i18n`
VALUES (78, 'system.menu.tool.gen.code.edit', 'zh_CN', '生成修改', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '生成修改');
INSERT INTO `i18n`
VALUES (79, 'system.menu.tool.gen.code.import', 'zh_CN', '导入代码', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '导入代码');
INSERT INTO `i18n`
VALUES (80, 'system.menu.tool.gen.code.remove', 'zh_CN', '生成删除', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '生成删除');
INSERT INTO `i18n`
VALUES (81, 'system.menu.tool.gen.code.preview', 'zh_CN', '预览代码', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '预览代码');
INSERT INTO `i18n`
VALUES (82, 'system.menu.tool.gen.code.code', 'zh_CN', '生成代码', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '生成代码');
INSERT INTO `i18n`
VALUES (83, 'system.menu.system.token.remove', 'zh_CN', '强退用户', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '强退用户');
INSERT INTO `i18n`
VALUES (84, 'system.menu.tool.gen.ds.query', 'zh_CN', '数据源查询', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '数据源查询');
INSERT INTO `i18n`
VALUES (85, 'system.menu.tool.gen.ds.add', 'zh_CN', '数据源新增', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '数据源新增');
INSERT INTO `i18n`
VALUES (86, 'system.menu.tool.gen.ds.edit', 'zh_CN', '数据源修改', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '数据源修改');
INSERT INTO `i18n`
VALUES (87, 'system.menu.tool.gen.ds.remove', 'zh_CN', '数据源删除', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '数据源删除');
INSERT INTO `i18n`
VALUES (88, 'system.menu.tool.gen.metadata.type.query', 'zh_CN', '字段类型管理查询', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '字段类型管理查询');
INSERT INTO `i18n`
VALUES (89, 'system.menu.tool.gen.metadata.type.add', 'zh_CN', '字段类型管理新增', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '字段类型管理新增');
INSERT INTO `i18n`
VALUES (90, 'system.menu.tool.gen.metadata.type.edit', 'zh_CN', '字段类型管理修改', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '字段类型管理修改');
INSERT INTO `i18n`
VALUES (91, 'system.menu.tool.gen.metadata.type.remove', 'zh_CN', '字段类型管理删除', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '字段类型管理删除');
INSERT INTO `i18n`
VALUES (92, 'system.menu.tool.gen.metadata.group.query', 'zh_CN', '模板分组查询', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '模板分组查询');
INSERT INTO `i18n`
VALUES (93, 'system.menu.tool.gen.metadata.group.add', 'zh_CN', '模板分组新增', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '模板分组新增');
INSERT INTO `i18n`
VALUES (94, 'system.menu.tool.gen.metadata.group.edit', 'zh_CN', '模板分组修改', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '模板分组修改');
INSERT INTO `i18n`
VALUES (95, 'system.menu.tool.gen.metadata.group.remove', 'zh_CN', '模板分组删除', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '模板分组删除');
INSERT INTO `i18n`
VALUES (96, 'system.menu.tool.gen.metadata.template.query', 'zh_CN', '模板管理查询', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '模板管理查询');
INSERT INTO `i18n`
VALUES (97, 'system.menu.tool.gen.metadata.template.add', 'zh_CN', '模板管理新增', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '模板管理新增');
INSERT INTO `i18n`
VALUES (98, 'system.menu.tool.gen.metadata.template.edit', 'zh_CN', '模板管理修改', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '模板管理修改');
INSERT INTO `i18n`
VALUES (99, 'system.menu.tool.gen.metadata.template.remove', 'zh_CN', '模板管理删除', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '模板管理删除');
INSERT INTO `i18n`
VALUES (100, 'system.menu.system.i18n.query', 'zh_CN', '国际化查询', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '国际化查询');
INSERT INTO `i18n`
VALUES (101, 'system.menu.system.i18n.add', 'zh_CN', '国际化新增', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '国际化新增');
INSERT INTO `i18n`
VALUES (102, 'system.menu.system.i18n.edit', 'zh_CN', '国际化修改', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '国际化修改');
INSERT INTO `i18n`
VALUES (103, 'system.menu.system.i18n.remove', 'zh_CN', '国际化删除', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '国际化删除');
INSERT INTO `i18n`
VALUES (104, 'system.menu.system.i18n.export', 'zh_CN', '国际化导出', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '国际化导出');
INSERT INTO `i18n`
VALUES (105, 'system.menu.index', 'en_US', 'Welcome Page', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '欢迎页');
INSERT INTO `i18n`
VALUES (106, 'system.menu.system', 'en_US', 'System Management', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '系统管理');
INSERT INTO `i18n`
VALUES (107, 'system.menu.monitor', 'en_US', 'System Monitoring', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '系统监控');
INSERT INTO `i18n`
VALUES (108, 'system.menu.human', 'en_US', 'Human Resources Management', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '人力管理');
INSERT INTO `i18n`
VALUES (109, 'system.menu.log', 'en_US', 'Log Management', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '日志管理');
INSERT INTO `i18n`
VALUES (110, 'system.menu.tool', 'en_US', 'Toolbox', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29', '工具箱');
INSERT INTO `i18n`
VALUES (111, 'system.menu.system.menu', 'en_US', 'Menu Management', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '菜单管理');
INSERT INTO `i18n`
VALUES (112, 'system.menu.system.dictionaries', 'en_US', 'Dictionary Management', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '字典管理');
INSERT INTO `i18n`
VALUES (113, 'system.menu.system.client', 'en_US', 'Oauth2 Terminal', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', 'Oauth2终端');
INSERT INTO `i18n`
VALUES (114, 'system.menu.system.dfs', 'en_US', 'File Management', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '文件管理');
INSERT INTO `i18n`
VALUES (115, 'system.menu.system.token', 'en_US', 'Token Management', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '令牌管理');
INSERT INTO `i18n`
VALUES (116, 'system.menu.system.i18n', 'en_US', 'Internationalization', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '国际化');
INSERT INTO `i18n`
VALUES (117, 'system.menu.monitor.redis', 'en_US', 'Cache Monitoring', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '缓存监控');
INSERT INTO `i18n`
VALUES (118, 'system.menu.monitor.job', 'en_US', 'Scheduled Tasks', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '定时任务');
INSERT INTO `i18n`
VALUES (119, 'system.menu.human.team', 'en_US', 'Staff Management', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '职员管理');
INSERT INTO `i18n`
VALUES (120, 'system.menu.human.role', 'en_US', 'Role Management', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '角色管理');
INSERT INTO `i18n`
VALUES (121, 'system.menu.human.dept', 'en_US', 'Department Management', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '部门管理');
INSERT INTO `i18n`
VALUES (122, 'system.menu.human.post', 'en_US', 'Position Management', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '岗位管理');
INSERT INTO `i18n`
VALUES (123, 'system.menu.log.operation', 'en_US', 'Operation Logs', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '操作日志');
INSERT INTO `i18n`
VALUES (124, 'system.menu.log.login', 'en_US', 'Login Logs', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '登录日志');
INSERT INTO `i18n`
VALUES (125, 'system.menu.log.job', 'en_US', 'Scheduled Task Logs', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '定时任务日志');
INSERT INTO `i18n`
VALUES (126, 'system.menu.tool.gen', 'en_US', 'Development Platform', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '开发平台');
INSERT INTO `i18n`
VALUES (127, 'system.menu.tool.gen.ds', 'en_US', 'Data Sources', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '数据源');
INSERT INTO `i18n`
VALUES (128, 'system.menu.tool.gen.code', 'en_US', 'Code Generation', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '代码生成');
INSERT INTO `i18n`
VALUES (129, 'system.menu.tool.gen.metadata', 'en_US', 'Metadata Management', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '元数据管理');
INSERT INTO `i18n`
VALUES (130, 'system.menu.tool.gen.metadata.type', 'en_US', 'Field Management', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '字段管理');
INSERT INTO `i18n`
VALUES (131, 'system.menu.tool.gen.metadata.template', 'en_US', 'Template Management', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '模板管理');
INSERT INTO `i18n`
VALUES (132, 'system.menu.tool.gen.metadata.group', 'en_US', 'Template Groups', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '模板分组');
INSERT INTO `i18n`
VALUES (133, 'system.menu.human.team.query', 'en_US', 'User Query', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '用户查询');
INSERT INTO `i18n`
VALUES (134, 'system.menu.human.team.insert', 'en_US', 'User Addition', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '用户新增');
INSERT INTO `i18n`
VALUES (135, 'system.menu.human.team.update', 'en_US', 'User Modification', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '用户修改');
INSERT INTO `i18n`
VALUES (136, 'system.menu.human.team.remove', 'en_US', 'User Deletion', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '用户删除');
INSERT INTO `i18n`
VALUES (137, 'system.menu.human.team.export', 'en_US', 'User Export', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '用户导出');
INSERT INTO `i18n`
VALUES (138, 'system.menu.human.team.import', 'en_US', 'User Import', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '用户导入');
INSERT INTO `i18n`
VALUES (139, 'system.menu.human.team.resetPwd', 'en_US', 'Reset Password', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '重置密码');
INSERT INTO `i18n`
VALUES (140, 'system.menu.human.role.query', 'en_US', 'Role Query', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '角色查询');
INSERT INTO `i18n`
VALUES (141, 'system.menu.human.role.insert', 'en_US', 'Role Addition', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '角色新增');
INSERT INTO `i18n`
VALUES (142, 'system.menu.human.role.update', 'en_US', 'Role Modification', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '角色修改');
INSERT INTO `i18n`
VALUES (143, 'system.menu.human.role.remove', 'en_US', 'Role Deletion', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '角色删除');
INSERT INTO `i18n`
VALUES (144, 'system.menu.human.role.export', 'en_US', 'Role Export', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '角色导出');
INSERT INTO `i18n`
VALUES (145, 'system.menu.system.menu.query', 'en_US', 'Menu Query', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '菜单查询');
INSERT INTO `i18n`
VALUES (146, 'system.menu.system.menu.insert', 'en_US', 'Menu Addition', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '菜单新增');
INSERT INTO `i18n`
VALUES (147, 'system.menu.system.menu.update', 'en_US', 'Menu Modification', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '菜单修改');
INSERT INTO `i18n`
VALUES (148, 'system.menu.system.menu.remove', 'en_US', 'Menu Deletion', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '菜单删除');
INSERT INTO `i18n`
VALUES (149, 'system.menu.human.dept.query', 'en_US', 'Department Query', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '部门查询');
INSERT INTO `i18n`
VALUES (150, 'system.menu.human.dept.insert', 'en_US', 'Department Addition', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '部门新增');
INSERT INTO `i18n`
VALUES (151, 'system.menu.human.dept.update', 'en_US', 'Department Modification', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '部门修改');
INSERT INTO `i18n`
VALUES (152, 'system.menu.human.dept.remove', 'en_US', 'Department Deletion', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '部门删除');
INSERT INTO `i18n`
VALUES (153, 'system.menu.human.post.query', 'en_US', 'Position Query', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '岗位查询');
INSERT INTO `i18n`
VALUES (154, 'system.menu.human.post.insert', 'en_US', 'Position Addition', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '岗位新增');
INSERT INTO `i18n`
VALUES (155, 'system.menu.human.post.update', 'en_US', 'Position Modification', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '岗位修改');
INSERT INTO `i18n`
VALUES (156, 'system.menu.human.post.remove', 'en_US', 'Position Deletion', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '岗位删除');
INSERT INTO `i18n`
VALUES (157, 'system.menu.human.post.export', 'en_US', 'Position Export', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '岗位导出');
INSERT INTO `i18n`
VALUES (158, 'system.menu.system.dictionaries.query', 'en_US', 'Dictionary Query', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '字典查询');
INSERT INTO `i18n`
VALUES (159, 'system.menu.system.dictionaries.insert', 'en_US', 'Dictionary Addition', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '字典新增');
INSERT INTO `i18n`
VALUES (160, 'system.menu.system.dictionaries.update', 'en_US', 'Dictionary Modification', '2024-03-31 11:32:29',
        'admin', '2024-03-31 11:32:29', '字典修改');
INSERT INTO `i18n`
VALUES (161, 'system.menu.system.dictionaries.remove', 'en_US', 'Dictionary Deletion', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '字典删除');
INSERT INTO `i18n`
VALUES (162, 'system.menu.system.dictionaries.export', 'en_US', 'Dictionary Export', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '字典导出');
INSERT INTO `i18n`
VALUES (163, 'system.menu.system.client.query', 'en_US', 'Terminal Query', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '终端查询');
INSERT INTO `i18n`
VALUES (164, 'system.menu.system.client.insert', 'en_US', 'Terminal Addition', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '终端新增');
INSERT INTO `i18n`
VALUES (165, 'system.menu.system.client.update', 'en_US', 'Terminal Modification', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '终端修改');
INSERT INTO `i18n`
VALUES (166, 'system.menu.system.client.remove', 'en_US', 'Terminal Deletion', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '终端删除');
INSERT INTO `i18n`
VALUES (167, 'system.menu.monitor.job.query', 'en_US', 'Task Query', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '任务查询');
INSERT INTO `i18n`
VALUES (168, 'system.menu.monitor.job.insert', 'en_US', 'Task Addition', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '任务新增');
INSERT INTO `i18n`
VALUES (169, 'system.menu.monitor.job.update', 'en_US', 'Task Modification', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '任务修改');
INSERT INTO `i18n`
VALUES (170, 'system.menu.monitor.job.remove', 'en_US', 'Task Deletion', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '任务删除');
INSERT INTO `i18n`
VALUES (171, 'system.menu.log.operation.query', 'en_US', 'Operation Query', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '操作查询');
INSERT INTO `i18n`
VALUES (172, 'system.menu.log.operation.remove', 'en_US', 'Operation Deletion', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '操作删除');
INSERT INTO `i18n`
VALUES (173, 'system.menu.log.operation.export', 'en_US', 'Log Export', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '日志导出');
INSERT INTO `i18n`
VALUES (174, 'system.menu.log.login.query', 'en_US', 'Login Query', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '登录查询');
INSERT INTO `i18n`
VALUES (175, 'system.menu.log.login.remove', 'en_US', 'Login Deletion', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '登录删除');
INSERT INTO `i18n`
VALUES (176, 'system.menu.log.login.export', 'en_US', 'Log Export', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '日志导出');
INSERT INTO `i18n`
VALUES (177, 'system.menu.log.job.query', 'en_US', 'Operation Query', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '操作查询');
INSERT INTO `i18n`
VALUES (178, 'system.menu.log.job.remove', 'en_US', 'Operation Deletion', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '操作删除');
INSERT INTO `i18n`
VALUES (179, 'system.menu.log.job.export', 'en_US', 'Log Export', '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29',
        '日志导出');
INSERT INTO `i18n`
VALUES (180, 'system.menu.system.dfs.remove', 'en_US', 'DFS File Deletion', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', 'DFS文件删除');
INSERT INTO `i18n`
VALUES (181, 'system.menu.tool.gen.code.query', 'en_US', 'Generation Query', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '生成查询');
INSERT INTO `i18n`
VALUES (182, 'system.menu.tool.gen.code.edit', 'en_US', 'Generation Modification', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '生成修改');
INSERT INTO `i18n`
VALUES (183, 'system.menu.tool.gen.code.import', 'en_US', 'Code Import', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '导入代码');
INSERT INTO `i18n`
VALUES (184, 'system.menu.tool.gen.code.remove', 'en_US', 'Generation Deletion', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '生成删除');
INSERT INTO `i18n`
VALUES (185, 'system.menu.tool.gen.code.preview', 'en_US', 'Code Preview', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '预览代码');
INSERT INTO `i18n`
VALUES (186, 'system.menu.tool.gen.code.code', 'en_US', 'Code Generation', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '生成代码');
INSERT INTO `i18n`
VALUES (187, 'system.menu.system.token.remove', 'en_US', 'Force Logout User', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '强退用户');
INSERT INTO `i18n`
VALUES (188, 'system.menu.tool.gen.ds.query', 'en_US', 'Data Source Query', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '数据源查询');
INSERT INTO `i18n`
VALUES (189, 'system.menu.tool.gen.ds.add', 'en_US', 'Data Source Addition', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '数据源新增');
INSERT INTO `i18n`
VALUES (190, 'system.menu.tool.gen.ds.edit', 'en_US', 'Data Source Modification', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '数据源修改');
INSERT INTO `i18n`
VALUES (191, 'system.menu.tool.gen.ds.remove', 'en_US', 'Data Source Deletion', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '数据源删除');
INSERT INTO `i18n`
VALUES (192, 'system.menu.tool.gen.metadata.type.query', 'en_US', 'Field Type Management Query', '2024-03-31 11:32:29',
        'admin', '2024-03-31 11:32:29', '字段类型管理查询');
INSERT INTO `i18n`
VALUES (193, 'system.menu.tool.gen.metadata.type.add', 'en_US', 'Field Type Management Addition', '2024-03-31 11:32:29',
        'admin', '2024-03-31 11:32:29', '字段类型管理新增');
INSERT INTO `i18n`
VALUES (194, 'system.menu.tool.gen.metadata.type.edit', 'en_US', 'Field Type Management Modification',
        '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29', '字段类型管理修改');
INSERT INTO `i18n`
VALUES (195, 'system.menu.tool.gen.metadata.type.remove', 'en_US', 'Field Type Management Deletion',
        '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29', '字段类型管理删除');
INSERT INTO `i18n`
VALUES (196, 'system.menu.tool.gen.metadata.group.query', 'en_US', 'Template Group Query', '2024-03-31 11:32:29',
        'admin', '2024-03-31 11:32:29', '模板分组查询');
INSERT INTO `i18n`
VALUES (197, 'system.menu.tool.gen.metadata.group.add', 'en_US', 'Template Group Addition', '2024-03-31 11:32:29',
        'admin', '2024-03-31 11:32:29', '模板分组新增');
INSERT INTO `i18n`
VALUES (198, 'system.menu.tool.gen.metadata.group.edit', 'en_US', 'Template Group Modification', '2024-03-31 11:32:29',
        'admin', '2024-03-31 11:32:29', '模板分组修改');
INSERT INTO `i18n`
VALUES (199, 'system.menu.tool.gen.metadata.group.remove', 'en_US', 'Template Group Deletion', '2024-03-31 11:32:29',
        'admin', '2024-03-31 11:32:29', '模板分组删除');
INSERT INTO `i18n`
VALUES (200, 'system.menu.tool.gen.metadata.template.query', 'en_US', 'Template Management Query',
        '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29', '模板管理查询');
INSERT INTO `i18n`
VALUES (201, 'system.menu.tool.gen.metadata.template.add', 'en_US', 'Template Management Addition',
        '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29', '模板管理新增');
INSERT INTO `i18n`
VALUES (202, 'system.menu.tool.gen.metadata.template.edit', 'en_US', 'Template Management Modification',
        '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29', '模板管理修改');
INSERT INTO `i18n`
VALUES (203, 'system.menu.tool.gen.metadata.template.remove', 'en_US', 'Template Management Deletion',
        '2024-03-31 11:32:29', 'admin', '2024-03-31 11:32:29', '模板管理删除');
INSERT INTO `i18n`
VALUES (204, 'system.menu.system.i18n.query', 'en_US', 'Internationalization Query', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '国际化查询');
INSERT INTO `i18n`
VALUES (205, 'system.menu.system.i18n.add', 'en_US', 'Internationalization Addition', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '国际化新增');
INSERT INTO `i18n`
VALUES (206, 'system.menu.system.i18n.edit', 'en_US', 'Internationalization Modification', '2024-03-31 11:32:29',
        'admin', '2024-03-31 11:32:29', '国际化修改');
INSERT INTO `i18n`
VALUES (207, 'system.menu.system.i18n.remove', 'en_US', 'Internationalization Deletion', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '国际化删除');
INSERT INTO `i18n`
VALUES (208, 'system.menu.system.i18n.export', 'en_US', 'Internationalization Export', '2024-03-31 11:32:29', 'admin',
        '2024-03-31 11:32:29', '国际化导出');


-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
