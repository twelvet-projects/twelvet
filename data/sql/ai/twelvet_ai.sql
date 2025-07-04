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
    `model_id`       bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '模型ID',
    `model_provider` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '供应商，枚举：ModelEnums.ModelProviderEnums',
    `model`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '模型',
    `model_type`     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '模型类型，枚举：ModelEnums.ModelTypeEnums',
    `default_flag`   tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '是否为同一类型模型的默认',
    `alias`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '别名',
    `api_key`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT 'apiKey',
    `base_url`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模型请求地址',
    `temperature`    float                                                         NULL     DEFAULT NULL COMMENT '创意活跃度',
    `top_p`          float                                                         NULL     DEFAULT NULL COMMENT '思维开放度',
    `ext_param`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '特殊参数',
    `create_by`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '创建者',
    `create_time`    datetime                                                      NOT NULL COMMENT '创建时间',
    `update_by`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '更新者',
    `update_time`    datetime                                                      NOT NULL COMMENT '更新时间',
    `del_flag`       tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '是否删除 0：正常，0：删除',
    PRIMARY KEY (`model_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = 'AI大模型'
  ROW_FORMAT = DYNAMIC;
-- ----------------------------
-- Table structure for ai_rag
-- ----------------------------
DROP TABLE IF EXISTS `ai_knowledge`;
CREATE TABLE `ai_knowledge`
(
    `knowledge_id`   bigint(20)                                                   NOT NULL AUTO_INCREMENT COMMENT '知识库ID',
    `knowledge_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '知识库名称',
    `welcome_msg`    text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci        NOT NULL COMMENT '欢迎语',
    `multi_round`    int                                                          NOT NULL DEFAULT 0 COMMENT '上下文记忆会话数',
    `top_k`          int                                                          NOT NULL DEFAULT 1 COMMENT '向量匹配条数',
    `knowledge_sort` int                                                          NOT NULL DEFAULT 0 COMMENT '知识库排序',
    `slice_size`     int                                                          NOT NULL DEFAULT 3000 COMMENT '切片值',
    `score`          double                                                       NOT NULL DEFAULT 0.5 COMMENT '匹配率',
    `create_by`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建者',
    `create_time`    datetime                                                     NOT NULL COMMENT '创建时间',
    `update_by`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新者',
    `update_time`    datetime                                                     NOT NULL COMMENT '更新时间',
    `del_flag`       tinyint(1)                                                   NOT NULL DEFAULT 0 COMMENT '是否删除 0：正常，1：删除',
    PRIMARY KEY (`knowledge_id`) USING BTREE
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
    `doc_id`       bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT '文档ID',
    `knowledge_id` bigint(20)                                                    NOT NULL COMMENT '知识库ID',
    `source_type`  varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '来源类型，枚举：RAGEnums.DocSourceTypeEnums',
    `doc_name`     varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文档名称',
    `create_by`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '创建者',
    `create_time`  datetime                                                      NOT NULL COMMENT '创建时间',
    `update_by`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '更新者',
    `update_time`  datetime                                                      NOT NULL COMMENT '更新时间',
    `del_flag`     tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '是否删除 0：正常，1：删除',
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
    `slice_id`     bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT '分片ID',
    `knowledge_id` bigint(20)                                                    NOT NULL COMMENT '知识库ID',
    `doc_id`       bigint(20)                                                    NOT NULL COMMENT '文档ID',
    `vector_id`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT 0 COMMENT '向量ID',
    `slice_name`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分片名称',
    `content`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NOT NULL COMMENT '分片内容',
    `del_flag`     tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '是否删除 0：正常，1：删除',
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
    `chat_history_id` bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `msg_id`          varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息唯一id',
    `user_id`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '归属的消息用户ID',
    `knowledge_id`    bigint(20)                                                    NOT NULL COMMENT '知识库ID',
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
    `chat_history_id`         bigint(20)                                            NOT NULL COMMENT 'AI客服会话记录ID',
    `content`                 text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息内容',
    PRIMARY KEY (`chat_history_content_id`) USING BTREE,
    UNIQUE INDEX `idx_chi` (`chat_history_id` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = 'AI会话内容详情'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ai_mcp
-- ----------------------------
DROP TABLE IF EXISTS `ai_mcp`;
CREATE TABLE `ai_mcp`
(
    `mcp_id`       bigint                                                        NOT NULL AUTO_INCREMENT COMMENT 'MCP ID',
    `name`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT 'MCP服务名称',
    `description`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '描述',
    `mcp_type`     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '请求类型',
    `sse_base_url` varchar(125) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT 'sse基础访问地址',
    `sse_endpoint` varchar(125) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT 'sse访问端点',
    `command`      varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '命令',
    `args`         varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '参数',
    `env`          varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '环境变量',
    `status_flag`  tinyint(1)                                                    NOT NULL DEFAULT 1 COMMENT '是否启用  1：启用，0：关闭',
    `create_by`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '创建者',
    `create_time`  datetime                                                      NOT NULL COMMENT '创建时间',
    `update_by`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '更新者',
    `update_time`  datetime                                                      NOT NULL COMMENT '更新时间',
    `del_flag`     tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '是否删除 0：正常，1：删除',
    PRIMARY KEY (`mcp_id`) USING BTREE,
    UNIQUE INDEX `idx_name` (`name` ASC) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = 'AI MCP服务'
  ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;