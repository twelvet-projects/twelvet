DROP
    DATABASE IF EXISTS `twelvet_gen`;

CREATE
    DATABASE `twelvet_gen` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

SET NAMES utf8mb4;
SET
    FOREIGN_KEY_CHECKS = 0;

USE
    `twelvet_gen`;

-- ----------------------------
-- Table structure for gen_datasource_conf
-- ----------------------------
DROP TABLE IF EXISTS `gen_datasource_conf`;
CREATE TABLE `gen_datasource_conf`
(
    `id`          bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '别名',
    `url`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'jdbcurl',
    `username`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '用户名',
    `password`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '密码',
    `create_time` datetime                                                      NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                                                      NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新',
    `del_flag`    char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci      NULL DEFAULT '0' COMMENT '删除标记',
    `ds_type`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '数据库类型',
    `conf_type`   char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci      NULL DEFAULT NULL COMMENT '配置类型(0：主机模式，1：JDBC)',
    `ds_name`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '数据库名称',
    `instance`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '实例',
    `port`        int(11)                                                       NULL DEFAULT NULL COMMENT '端口',
    `host`        varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主机',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '数据源表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of gen_datasource_conf
-- ----------------------------

-- ----------------------------
-- Table structure for gen_field_type
-- ----------------------------
DROP TABLE IF EXISTS `gen_field_type`;
CREATE TABLE `gen_field_type`
(
    `id`           bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT 'id',
    `column_type`  varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段类型',
    `attr_type`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性类型',
    `package_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性包名',
    `create_time`  datetime                                                      NULL DEFAULT NULL COMMENT '创建时间',
    `create_by`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '创建人',
    `update_time`  datetime                                                      NULL DEFAULT NULL COMMENT '修改时间',
    `update_by`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '修改人',
    `del_flag`     char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci      NULL DEFAULT '0' COMMENT '删除标记',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `column_type` (`column_type`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 32
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '字段类型管理'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gen_field_type
-- ----------------------------
INSERT INTO `gen_field_type`
VALUES (1, 'datetime', 'LocalDateTime', 'java.time.LocalDateTime', '2023-02-06 08:45:10', NULL, NULL, NULL, '0');
INSERT INTO `gen_field_type`
VALUES (2, 'date', 'LocalDate', 'java.time.LocalDate', '2023-02-06 08:45:10', NULL, NULL, NULL, '0');
INSERT INTO `gen_field_type`
VALUES (3, 'tinyint', 'Integer', NULL, '2023-02-06 08:45:11', NULL, NULL, NULL, '0');
INSERT INTO `gen_field_type`
VALUES (4, 'smallint', 'Integer', NULL, '2023-02-06 08:45:11', NULL, NULL, NULL, '0');
INSERT INTO `gen_field_type`
VALUES (5, 'mediumint', 'Integer', NULL, '2023-02-06 08:45:11', NULL, NULL, NULL, '0');
INSERT INTO `gen_field_type`
VALUES (6, 'int', 'Integer', NULL, '2023-02-06 08:45:11', NULL, NULL, NULL, '0');
INSERT INTO `gen_field_type`
VALUES (7, 'integer', 'Integer', NULL, '2023-02-06 08:45:11', NULL, NULL, NULL, '0');
INSERT INTO `gen_field_type`
VALUES (8, 'bigint', 'Long', NULL, '2023-02-06 08:45:11', NULL, NULL, NULL, '0');
INSERT INTO `gen_field_type`
VALUES (9, 'float', 'Float', NULL, '2023-02-06 08:45:11', NULL, NULL, NULL, '0');
INSERT INTO `gen_field_type`
VALUES (10, 'double', 'Double', NULL, '2023-02-06 08:45:11', NULL, NULL, NULL, '0');
INSERT INTO `gen_field_type`
VALUES (11, 'decimal', 'BigDecimal', 'java.math.BigDecimal', '2023-02-06 08:45:11', NULL, NULL, NULL, '0');
INSERT INTO `gen_field_type`
VALUES (12, 'bit', 'Boolean', NULL, '2023-02-06 08:45:11', NULL, NULL, NULL, '0');
INSERT INTO `gen_field_type`
VALUES (13, 'char', 'String', NULL, '2023-02-06 08:45:11', NULL, NULL, NULL, '0');
INSERT INTO `gen_field_type`
VALUES (14, 'varchar', 'String', NULL, '2023-02-06 08:45:11', NULL, NULL, NULL, '0');
INSERT INTO `gen_field_type`
VALUES (15, 'tinytext', 'String', NULL, '2023-02-06 08:45:11', NULL, NULL, NULL, '0');
INSERT INTO `gen_field_type`
VALUES (16, 'text', 'String', NULL, '2023-02-06 08:45:11', NULL, NULL, NULL, '0');
INSERT INTO `gen_field_type`
VALUES (17, 'mediumtext', 'String', NULL, '2023-02-06 08:45:11', NULL, NULL, NULL, '0');
INSERT INTO `gen_field_type`
VALUES (18, 'longtext', 'String', NULL, '2023-02-06 08:45:11', NULL, NULL, NULL, '0');
INSERT INTO `gen_field_type`
VALUES (19, 'timestamp', 'LocalDateTime', 'java.time.LocalDateTime', '2023-02-06 08:45:11', NULL, NULL, NULL, '0');
INSERT INTO `gen_field_type`
VALUES (20, 'NUMBER', 'Integer', NULL, '2023-02-06 08:45:11', NULL, NULL, NULL, '0');
INSERT INTO `gen_field_type`
VALUES (21, 'BINARY_INTEGER', 'Integer', NULL, '2023-02-06 08:45:12', NULL, NULL, NULL, '0');
INSERT INTO `gen_field_type`
VALUES (22, 'BINARY_FLOAT', 'Float', NULL, '2023-02-06 08:45:12', NULL, NULL, NULL, '0');
INSERT INTO `gen_field_type`
VALUES (23, 'BINARY_DOUBLE', 'Double', NULL, '2023-02-06 08:45:12', NULL, NULL, NULL, '0');
INSERT INTO `gen_field_type`
VALUES (24, 'VARCHAR2', 'String', NULL, '2023-02-06 08:45:12', NULL, NULL, NULL, '0');
INSERT INTO `gen_field_type`
VALUES (25, 'NVARCHAR', 'String', NULL, '2023-02-06 08:45:12', NULL, NULL, NULL, '0');
INSERT INTO `gen_field_type`
VALUES (26, 'NVARCHAR2', 'String', NULL, '2023-02-06 08:45:12', NULL, NULL, NULL, '0');
INSERT INTO `gen_field_type`
VALUES (27, 'CLOB', 'String', NULL, '2023-02-06 08:45:12', NULL, NULL, NULL, '0');
INSERT INTO `gen_field_type`
VALUES (28, 'int8', 'Long', NULL, '2023-02-06 08:45:12', NULL, NULL, NULL, '0');
INSERT INTO `gen_field_type`
VALUES (29, 'int4', 'Integer', NULL, '2023-02-06 08:45:12', NULL, NULL, NULL, '0');
INSERT INTO `gen_field_type`
VALUES (30, 'int2', 'Integer', NULL, '2023-02-06 08:45:12', NULL, NULL, NULL, '0');
INSERT INTO `gen_field_type`
VALUES (31, 'numeric', 'BigDecimal', 'java.math.BigDecimal', '2023-02-06 08:45:12', NULL, NULL, NULL, '0');
INSERT INTO `gen_field_type`
VALUES (32, 'json', 'String', NULL, '2023-02-06 08:45:12', NULL, NULL, NULL, '0');

-- ----------------------------
-- Table structure for gen_template
-- ----------------------------
DROP TABLE IF EXISTS `gen_template`;
CREATE TABLE `gen_template`
(
    `id`             bigint(20)                                                    NOT NULL COMMENT '主键',
    `template_name`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模板名称',
    `generator_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模板路径',
    `template_desc`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模板描述',
    `template_code`  text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NOT NULL COMMENT '模板代码',
    `create_time`    datetime                                                      NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    datetime                                                      NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新',
    `del_flag`       char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci      NOT NULL DEFAULT '0' COMMENT '删除标记',
    `create_by`      varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci        NOT NULL DEFAULT ' ' COMMENT '创建人',
    `update_by`      varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci        NOT NULL DEFAULT ' ' COMMENT '修改人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '代码生成业务模板'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table`
(
    `table_id`          bigint(20)                                               NOT NULL AUTO_INCREMENT COMMENT '编号',
    `ds_name`           varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '数据源别名',
    `table_name`        varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '表名称',
    `table_comment`     varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '表描述',
    `sub_table_name`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT NULL COMMENT '关联子表的表名',
    `sub_table_fk_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT NULL COMMENT '子表关联的外键名',
    `class_name`        varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '实体类名称',
    `tpl_category`      varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
    `package_name`      varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '生成包路径',
    `module_name`       varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT NULL COMMENT '生成模块名',
    `business_name`     varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT NULL COMMENT '生成业务名',
    `function_name`     varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT NULL COMMENT '生成功能名',
    `function_author`   varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT NULL COMMENT '生成功能作者',
    `gen_type`          char(1) CHARACTER SET utf8 COLLATE utf8_general_ci       NULL DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
    `gen_path`          varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
    `options`           varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '其它生成选项',
    `create_by`         varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT '' COMMENT '创建者',
    `create_time`       datetime                                                 NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`         varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT '' COMMENT '更新者',
    `update_time`       datetime                                                 NULL DEFAULT NULL COMMENT '更新时间',
    `remark`            varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`table_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '代码生成业务表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of gen_table
-- ----------------------------

-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column`
(
    `column_id`      bigint(20)                                              NOT NULL AUTO_INCREMENT COMMENT '编号',
    `table_id`       varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '归属表编号',
    `column_name`    varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '列名称',
    `column_comment` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '列描述',
    `column_type`    varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '列类型',
    `java_type`      varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'JAVA类型',
    `java_field`     varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'JAVA字段名',
    `is_pk`          char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT NULL COMMENT '是否主键（1是）',
    `is_increment`   char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT NULL COMMENT '是否自增（1是）',
    `is_required`    char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT NULL COMMENT '是否必填（1是）',
    `is_insert`      char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT NULL COMMENT '是否为插入字段（1是）',
    `is_edit`        char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT NULL COMMENT '是否编辑字段（1是）',
    `is_list`        char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT NULL COMMENT '是否列表字段（1是）',
    `is_query`       char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT NULL COMMENT '是否查询字段（1是）',
    `query_type`     varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
    `html_type`      varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
    `dict_type`      varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典类型',
    `sort`           int(11)                                                 NULL DEFAULT NULL COMMENT '排序',
    `create_by`      varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '创建者',
    `create_time`    datetime                                                NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`      varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '更新者',
    `update_time`    datetime                                                NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`column_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '代码生成业务表字段'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of gen_table_column
-- ----------------------------

SET
    FOREIGN_KEY_CHECKS = 1;
