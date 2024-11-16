DROP DATABASE IF EXISTS `twelvet_ai`;

CREATE DATABASE `twelvet_ai` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

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
    `model_name`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '知识库名称',
    `welcome_msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci        NOT NULL COMMENT '欢迎语',
    `multi_round` int(4)                                                       NOT NULL DEFAULT 0 COMMENT '上下文记忆会话数',
    `top_k`       int(4)                                                       NOT NULL DEFAULT 1 COMMENT '向量匹配条数',
    `model_sort`  int(4)                                                       NULL     DEFAULT 0 COMMENT '知识库排序',
    PRIMARY KEY (`model_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = 'AI知识库'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ai_doc
-- ----------------------------
DROP TABLE IF EXISTS `ai_doc`;
CREATE TABLE `ai_doc`
(
    `doc_id`   bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT '文档ID',
    `model_id` bigint(20)                                                    NOT NULL COMMENT '知识库ID',
    `doc_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '文档名称',
    PRIMARY KEY (`doc_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = 'AI知识库文档'
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
    `slice_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '分片名称',
    `content`    text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci         NULL DEFAULT NULL COMMENT '分片内容',
    PRIMARY KEY (`slice_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = 'AI知识库文档分片'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;