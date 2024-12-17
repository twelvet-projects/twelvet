DROP DATABASE IF EXISTS `twelvet_ai`;

CREATE DATABASE `twelvet_ai` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;


USE `twelvet_ai`;

-- ----------------------------
-- Table structure for ai_model
-- ----------------------------
DROP TABLE IF EXISTS `ai_model`;
CREATE TABLE `ai_model`
(
    `model_id`    bigint(20)                                                   NOT NULL AUTO_INCREMENT COMMENT '知识库ID',
    `model_name`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '知识库名称',
    `welcome_msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci        NOT NULL COMMENT '欢迎语',
    `multi_round` int(4)                                                       NOT NULL DEFAULT 0 COMMENT '上下文记忆会话数',
    `top_k`       int(4)                                                       NOT NULL DEFAULT 1 COMMENT '向量匹配条数',
    `model_sort`  int(4)                                                       NOT NULL DEFAULT 0 COMMENT '知识库排序',
    `create_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建者',
    `create_time` datetime                                                     NOT NULL COMMENT '创建时间',
    `update_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新者',
    `update_time` datetime                                                     NOT NULL COMMENT '更新时间',
    `del_flag`    tinyint(1)                                                   NOT NULL DEFAULT 0 COMMENT '是否删除 0：正常，0：删除',
    PRIMARY KEY (`model_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = 'AI知识库'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ai_doc
-- ----------------------------
DROP TABLE IF EXISTS `ai_doc`;
CREATE TABLE `ai_doc`
(
    `doc_id`      bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT '文档ID',
    `model_id`    bigint(20)                                                    NOT NULL COMMENT '知识库ID',
    `doc_name`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文档名称',
    `create_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '创建者',
    `create_time` datetime                                                      NOT NULL COMMENT '创建时间',
    `update_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '更新者',
    `update_time` datetime                                                      NOT NULL COMMENT '更新时间',
    `del_flag`    tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '是否删除 0：正常，0：删除',
    PRIMARY KEY (`doc_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = 'AI知识库文档'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ai_doc_slice
-- ----------------------------
DROP TABLE IF EXISTS `ai_doc_slice`;
CREATE TABLE `ai_doc_slice`
(
    `slice_id`   bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT '分片ID',
    `model_id`   bigint(20)                                                    NOT NULL COMMENT '知识库ID',
    `doc_id`     bigint(20)                                                    NOT NULL COMMENT '文档ID',
    `vector_id`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT 0 COMMENT '向量ID',
    `slice_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分片名称',
    `content`    text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NOT NULL COMMENT '分片内容',
    `del_flag`   tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '是否删除 0：正常，0：删除',
    PRIMARY KEY (`slice_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = 'AI知识库文档分片'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ai_chat_history
-- ----------------------------
DROP TABLE IF EXISTS `ai_chat_history`;
CREATE TABLE `ai_chat_history`
(
    `chat_history_id` bigint                                                        NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `msg_id`          varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息唯一id',
    `user_id`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '归属的消息用户ID',
    `model_id`        bigint(20)                                                    NOT NULL COMMENT '知识库ID',
    `send_user_id`    varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '消息发送人ID',
    `send_user_name`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息发送人名称',
    `create_by_type`  varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '发送消息用户类型',
    `create_time`     datetime(3)                                                   NOT NULL COMMENT '创建发送时间',
    `del_flag`        tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '1:已删除，0：未删除',
    PRIMARY KEY (`chat_history_id`) USING BTREE,
    UNIQUE INDEX `idx_msg_id` (`msg_id` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = 'AI聊天记录表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ai_chat_history_content
-- ----------------------------
DROP TABLE IF EXISTS `ai_chat_history_content`;
CREATE TABLE `ai_chat_history_content`
(
    `chat_history_content_id` bigint(20)                                            NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `chat_history_id`         bigint                                                NOT NULL COMMENT 'AI客服会话记录ID',
    `content`                 text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息内容',
    PRIMARY KEY (`chat_history_content_id`) USING BTREE,
    UNIQUE INDEX `idx_chi` (`chat_history_id` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = 'AI会话内容详情'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;