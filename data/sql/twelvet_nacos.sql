DROP DATABASE IF EXISTS `twelvet_nacos`;

CREATE DATABASE  `twelvet_nacos` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

USE `twelvet_nacos`;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `c_use` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `effect` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `c_schema` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  `encrypted_data_key` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfo_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 491 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info
-- ----------------------------
INSERT INTO `config_info` VALUES (1, 'twelvet-app-dev.yml', 'DEFAULT_GROUP', '# 服务调用超时时间(ms)\nribbon:\n  ReadTimeout: 8000\n  ConnectTimeout: 8000\n\n# 认证配置\nsecurity:\n  oauth2:\n    # 配置Resource Client_id信息\n    client:\n      client-id: twelvet\n      client-secret: 123456\n      scope: server\n    resource:\n      loadBalanced: true\n      token-info-uri: http://twelvet-auth/oauth/check_token\n    ignore:\n      urls:\n        - /error\n        - /actuator/**\n        - /v3/api-docs\nspring:\n  cache:\n    type: redis\n  jackson:\n    default-property-inclusion: non_null\n    time-zone: GMT+8\n  cloud:\n    # 配置sentinel控制台\n    sentinel:\n      # 取消控制台懒加载\n      eager: true\n      transport:\n        # 控制台地址：Port端口\n        dashboard: twelvet-sentinel:8101\n\n# feign 配置\nfeign:\n  sentinel:\n    enabled: true\n  okhttp:\n    enabled: true\n  httpclient:\n    enabled: false\n  client:\n    config:\n      default:\n        connectTimeout: 10000\n        readTimeout: 10000\n  compression:\n    request:\n      enabled: true\n    response:\n      enabled: true\n\n# 暴露监控端点\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \'*\'\n\n# Mybatis配置\nmybatis:\n  # 搜索指定包别名\n  typeAliasesPackage: com.twelvet.api.*.domain\n  # 配置mapper的扫描，找到所有的mapper.xml映射文件\n  mapperLocations: classpath*:mapper/**/*Mapper.xml\n  # Mybatis开启日志打印\n  configuration:\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\n\n# 分布式事务配置\nseata:\n  # 未配置分布式事务,不要打开,会报错，且spring.datasource.dynami.seata需要同时开启\n  enabled: false\n  application-id: ${twelvet.name}\n  # 事务分组,nacos配置上必须相对应的配置\n  tx-service-group: ${twelvet.name}-group\n  # 开启自动数据源代理\n  enable-auto-data-source-proxy: true\n  config:\n    type: nacos\n    nacos:\n      server-addr: http://nacos.twelvet.cn\n      namespace: 60ca01e2-221e-47af-b7e5-64f2a7336973\n      group: DEFAULT_GROUP\n  registry:\n    type: nacos\n    nacos:\n      server-addr: http://nacos.twelvet.cn\n      application: seata-server\n      group: DEFAULT_GROUP\n      namespace: 60ca01e2-221e-47af-b7e5-64f2a7336973\n      cluster: DEFAULT\n\n# swagger 配置\nswagger:\n  enabled: true\n  title: TwelveT Swagger API\n  gateway: http://${GATEWAY_HOST:twelvet-gateway}:${GATEWAY-PORT:8080}\n  token-url: ${swagger.gateway}/auth/oauth2/token\n  scope: server\n  services:\n    twelvet-auth: auth\n    twelvet-server-system: system', '9ed668bbbddc2c3dd60daa9efdd18c35', '2020-06-04 12:38:30', '2023-02-14 11:27:16', 'nacos', '127.0.0.1', '', 'eeb43899-8a88-4f5b-b0e0-d7c8fd09b86e', '公共配置', 'null', 'null', 'yaml', 'null', '');
INSERT INTO `config_info` VALUES (2, 'twelvet-auth-dev.yml', 'DEFAULT_GROUP', 'spring: \n  # 模板引擎配置\n  freemarker:\n    allow-request-override: false\n    allow-session-override: false\n    check-template-location: true\n    expose-request-attributes: false\n    expose-session-attributes: false\n    expose-spring-macro-helpers: true\n    prefer-file-system-access: true\n    # 后缀名\n    suffix: .ftl  \n    content-type: text/html\n    enabled: true\n    # 缓存配置\n    cache: true \n    # 模板加载路径 按需配置\n    template-loader-path: classpath:/templates/ \n    charset: UTF-8\n  data:\n    redis:\n      host: twelvet-redis\n      port: 6379\n      # 连接超时时间\n      timeout: 30s\n      lettuce:\n        pool:\n          # 连接池中的最小空闲连接\n          min-idle: 0\n          # 连接池中的最大空闲连接\n          max-idle: 8\n          # 连接池的最大数据库连接数\n          max-active: 8\n          # #连接池最大阻塞等待时间（使用负值表示没有限制）\n          max-wait: -1ms\n  \nswagger:\n  title: TwelveT Swagger API\n  version: 2.7.0\n  description: 授权中心服务\n  license: Powered By TwelveT\n  licenseUrl: https://twelvet.cn\n  terms-of-service-url: https://twelvet.cn\n  contact:\n    name: TwelveT\n    email: 2471835953@qq.com\n    url: https://twelvet.cn\n  authorization:\n    name: password\n    token-url-list:\n      - http://${GATEWAY_HOST:localhost}:${GATEWAY_PORT:88}/auth/oauth/token', 'd349f9d0d3f2e3c634d4fd5474ef8d6e', '2020-06-07 19:45:01', '2023-07-08 09:23:13', 'nacos', '127.0.0.1', '', 'eeb43899-8a88-4f5b-b0e0-d7c8fd09b86e', '认证服务器配置', 'null', 'null', 'yaml', 'null', '');
INSERT INTO `config_info` VALUES (3, 'twelvet-server-system-dev.yml', 'DEFAULT_GROUP', 'spring: \n  datasource:\n    dynamic:\n      hikari:\n        # 连接测试查询\n        connection-test-query: SELECT 1 FROM DUAL\n        # 连接最大存活时间.不等于0且小于30秒，会被重置为默认值30分钟.设置应该比mysql设置的超时时间短\n        max-lifetime: 540000\n        # 只有空闲连接数大于最大连接数且空闲时间超过该值，才会被释放\n        idle-timeout: 500000\n        # 最小空闲连接，默认值10，小于0或大于maximum-pool-size，都会重置为maximum-pool-size\n        minimum-idle: 10\n        # 最大连接数，小于等于0会被重置为默认值10；大于零小于1会被重置为minimum-idle的值\n        maximum-pool-size: 12\n        # 连接超时时间:毫秒，小于250毫秒，否则被重置为默认值30秒\n        connection-timeout: 60000\n      # 设置默认的数据源或者数据源组,默认值即为master\n      primary: master \n      datasource:\n        # 主库数据源\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://twelvet-mysql:3306/twelvet?allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8\n          username: root\n          password: 123456\n        shardingSphere:\n          driver-class-name: org.apache.shardingsphere.driver.ShardingSphereDriver\n          url: jdbc:shardingsphere:classpath:sharding-jdbc.yml\n        # 从库数据源\n        # slave:\n        # username:\n        # password:\n        # url:\n        # driver-class-name:\n      # 开启seata代理，开启后默认每个数据源都代理,分布式事务必须开启,否则关闭\n      seata: false\n  data:\n    redis:\n      host: twelvet-redis\n      port: 6379\n      # 连接超时时间\n      timeout: 30s\n      lettuce:\n        pool:\n          # 连接池中的最小空闲连接\n          min-idle: 0\n          # 连接池中的最大空闲连接\n          max-idle: 8\n          # 连接池的最大数据库连接数\n          max-active: 8\n          # #连接池最大阻塞等待时间（使用负值表示没有限制）\n          max-wait: -1ms\n\nswagger:\n  title: TwelveT Swagger API\n  version: 2.7.0\n  description: 系统服务\n  license: Powered By TwelveT\n  licenseUrl: https://twelvet.cn\n  terms-of-service-url: https://twelvet.cn\n  contact:\n    name: TwelveT\n    email: 2471835953@qq.com\n    url: https://twelvet.cn\n  authorization:\n    name: password\n    token-url-list:\n      - http://${GATEWAY_HOST:localhost}:${GATEWAY_PORT:88}/auth/oauth/token', 'b6c1b3e176c7003c0f68429b7efe56af', '2020-06-07 19:45:29', '2023-07-08 09:21:56', 'nacos', '127.0.0.1', '', 'eeb43899-8a88-4f5b-b0e0-d7c8fd09b86e', '系统模块', 'null', 'null', 'yaml', 'null', '');
INSERT INTO `config_info` VALUES (4, 'twelvet-gateway-dev.yml', 'DEFAULT_GROUP', 'spring: \n  cloud:\n    gateway:\n      globalcors:\n        corsConfigurations:\n          \'[/**]\':\n            allowedOriginPatterns: \"*\"\n            allowed-methods: \"*\"\n            allowed-headers: \"*\"\n            allow-credentials: true\n            exposedHeaders: \"Content-Disposition,Content-Type,Cache-Control\"\n      loadbalancer: \n        use404: true\n      discovery:\n        locator:\n          # 开启服务名称小写匹配\n          lowerCaseServiceId: true\n          # 开启动态创建路由（不用每个都写死）\n          enabled: true\n      # 路由配置\n      routes:\n        # 认证中心\n        - id: twelvet-auth\n          uri: lb://twelvet-auth\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        # 系统模块\n        - id: twelvet-server-system\n          # 匹配后提供服务的路由地址\n          uri: lb://twelvet-server-system\n           # 断言，路径匹配的进行路由\n          predicates:\n            - Path=/system/**\n          filters:\n            # 转发请求时去除路由前缀（system）\n            - StripPrefix=1\n            # 请求重写，将内部开放服务重写(重要配置，请勿轻易修改，否则将暴露用户信息API)\n            - RewritePath=/user/info(?<segment>/?.*), /from-user-info$\\{segment}\n        # 定时任务\n        - id: twelvet-server-job\n          uri: lb://twelvet-server-job\n          predicates:\n            - Path=/job/**\n          filters:\n            - StripPrefix=1\n        # DFS文件系统\n        - id: twelvet-server-dfs\n          uri: lb://twelvet-server-dfs\n          predicates:\n            - Path=/dfs/**\n          filters:\n            - StripPrefix=1\n            # 请求重写，不允许直接上传文件\n            - RewritePath=/upload(?<segment>/?.*), /from-upload$\\{segment}\n        # 代码生成器\n        - id: twelvet-server-gen\n          uri: lb://twelvet-server-gen\n          predicates:\n            - Path=/gen/**\n          filters:\n            - StripPrefix=1\n\n# 防止xss攻击\nsecurity:\n  xss:\n    enabled: true\n    excludeUrls: \n      ', '4e9defd7c947ea00a19e448e239b1b1d', '2020-06-12 11:46:10', '2022-10-08 09:58:44', 'nacos', '127.0.0.1', '', 'eeb43899-8a88-4f5b-b0e0-d7c8fd09b86e', '网关配置', 'null', 'null', 'yaml', 'null', '');
INSERT INTO `config_info` VALUES (5, 'twelvet-visual-monitor-dev.yml', 'DEFAULT_GROUP', '# Spring\r\nspring: \r\n  security:\r\n    user:\r\n      name: twelvet\r\n      password: 123456\r\n  boot:\r\n    admin:\r\n      ui:\r\n        title: TwelveT服务监控中心', '315056a443f809904b98f0184580cbce', '2020-09-06 23:43:00', '2021-07-04 18:26:02', NULL, '223.104.67.29', '', 'eeb43899-8a88-4f5b-b0e0-d7c8fd09b86e', '服务监控中心', 'null', 'null', 'yaml', 'null', '');
INSERT INTO `config_info` VALUES (6, 'sentinel-twelvet-gateway', 'DEFAULT_GROUP', '[\r\n    {\r\n        \"resource\": \"twelvet-auth\",\r\n        \"count\": 10,\r\n        \"grade\": 1,\r\n        \"limitApp\": \"default\",\r\n        \"strategy\": 0,\r\n        \"controlBehavior\": 0\r\n    },\r\n	{\r\n        \"resource\": \"twelvet-server-system\",\r\n        \"count\": 10,\r\n        \"grade\": 1,\r\n        \"limitApp\": \"default\",\r\n        \"strategy\": 0,\r\n        \"controlBehavior\": 0\r\n    }\r\n]', '2197166e05df7bbfde1019b539977de7', '2020-09-07 20:50:09', '2021-04-24 23:36:00', NULL, '117.136.32.221', '', 'eeb43899-8a88-4f5b-b0e0-d7c8fd09b86e', '流量控制', 'null', 'null', 'json', 'null', '');
INSERT INTO `config_info` VALUES (7, 'twelvet-server-job-dev.yml', 'DEFAULT_GROUP', '# Spring\nspring: \n  datasource:\n    dynamic:\n      hikari:\n      # 连接测试查询\n      connection-test-query: SELECT 1 FROM DUAL\n      # 连接最大存活时间.不等于0且小于30秒，会被重置为默认值30分钟.设置应该比mysql设置的超时时间短\n      max-lifetime: 540000\n      # 只有空闲连接数大于最大连接数且空闲时间超过该值，才会被释放\n      idle-timeout: 500000\n      # 最小空闲连接，默认值10，小于0或大于maximum-pool-size，都会重置为maximum-pool-size\n      minimum-idle: 10\n      # 最大连接数，小于等于0会被重置为默认值10；大于零小于1会被重置为minimum-idle的值\n      maximum-pool-size: 12\n      # 连接超时时间:毫秒，小于250毫秒，否则被重置为默认值30秒\n      connection-timeout: 60000\n      # 设置默认的数据源或者数据源组,默认值即为master\n      primary: master\n      datasource:\n        # 主库数据源\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://twelvet-mysql:3306/twelvet?allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8\n          username: root\n          password: 123456\n        # 从库数据源\n        # slave:\n        # username:\n        # password:\n        # url:\n        # driver-class-name:\n      # 开启seata代理，开启后默认每个数据源都代理,分布式事务必须开启,否则关闭\n      seata: false\n  \nswagger:\n  title: TwelveT Swagger API\n  version: 2.7.0\n  description: 分布式调度服务\n  license: Powered By TwelveT\n  licenseUrl: https://twelvet.cn\n  terms-of-service-url: https://twelvet.cn\n  contact:\n    name: TwelveT\n    email: 2471835953@qq.com\n    url: https://twelvet.cn\n  authorization:\n    name: twelvet\n    auth-regex: ^.*$\n    authorization-scope-list:\n      - scope: server\n        description: server all\n    token-url-list:\n      - http://${GATEWAY_HOST:twelvet-gateway}:${GATEWAY_PORT:88}/auth/oauth/token\n# 开启Swagger增强模式\nknife4j:\n  enable: true', '3f1de1384e90f569cf6e3bd1857290d4', '2020-11-03 15:47:23', '2023-02-14 14:33:06', 'nacos', '127.0.0.1', '', 'eeb43899-8a88-4f5b-b0e0-d7c8fd09b86e', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (8, 'twelvet-server-dfs-dev.yml', 'DEFAULT_GROUP', 'spring: \n  datasource:\n   dynamic:\n    hikari:\n      # 连接测试查询\n      connection-test-query: SELECT 1 FROM DUAL\n      # 连接最大存活时间.不等于0且小于30秒，会被重置为默认值30分钟.设置应该比mysql设置的超时时间短\n      max-lifetime: 540000\n      # 只有空闲连接数大于最大连接数且空闲时间超过该值，才会被释放\n      idle-timeout: 500000\n      # 最小空闲连接，默认值10，小于0或大于maximum-pool-size，都会重置为maximum-pool-size\n      minimum-idle: 10\n      # 最大连接数，小于等于0会被重置为默认值10；大于零小于1会被重置为minimum-idle的值\n      maximum-pool-size: 12\n      # 连接超时时间:毫秒，小于250毫秒，否则被重置为默认值30秒\n      connection-timeout: 60000\n    # 设置默认的数据源或者数据源组,默认值即为master\n    primary: master\n    datasource:\n      # 主库数据源\n      master:\n        driver-class-name: com.mysql.cj.jdbc.Driver\n        url: jdbc:mysql://twelvet-mysql:3306/twelvet?allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8\n        username: root\n        password: 123456\n      # 从库数据源\n      # slave:\n      # username:\n      # password:\n      # url:\n      # driver-class-name:\n    # 开启seata代理，开启后默认每个数据源都代理,分布式事务必须开启,否则关闭\n    seata: false\n\n##qiniu账户信息配置##\noss:\n  qiniu:\n    ## key\n    accessKey: accessKey\n    # 秘钥\n    secretKey: secretKey\n    # 储存桶\n    bucketName: twelvet\n\nswagger:\n  title: TwelveT Swagger API\n  version: 2.7.0\n  description: DFS服务\n  license: Powered By TwelveT\n  licenseUrl: https://twelvet.cn\n  terms-of-service-url: https://twelvet.cn\n  contact:\n    name: TwelveT\n    email: 2471835953@qq.com\n    url: https://twelvet.cn\n  authorization:\n    name: password\n    token-url-list:\n      - http://${GATEWAY_HOST:localhost}:${GATEWAY_PORT:88}/auth/oauth/token\n# 开启Swagger增强模式\nknife4j:\n  enable: true\n  ', 'da651e9fa2a69e0a0707e3041814d997', '2020-12-13 18:42:02', '2023-02-14 14:32:51', 'nacos', '127.0.0.1', '', 'eeb43899-8a88-4f5b-b0e0-d7c8fd09b86e', '分布式文件系统', 'null', 'null', 'yaml', 'null', '');
INSERT INTO `config_info` VALUES (9, 'store.mode', 'DEFAULT_GROUP', 'db', 'd77d5e503ad1439f585ac494268b351b', '2021-03-10 14:55:40', '2021-03-10 14:55:40', NULL, '119.29.118.110', '', '60ca01e2-221e-47af-b7e5-64f2a7336973', NULL, NULL, NULL, NULL, NULL, '');
INSERT INTO `config_info` VALUES (10, 'store.db.datasource', 'DEFAULT_GROUP', 'druid', '3d650fb8a5df01600281d48c47c9fa60', '2021-03-10 14:55:40', '2021-03-10 14:55:40', NULL, '119.29.118.110', '', '60ca01e2-221e-47af-b7e5-64f2a7336973', NULL, NULL, NULL, NULL, NULL, '');
INSERT INTO `config_info` VALUES (11, 'store.db.dbType', 'DEFAULT_GROUP', 'mysql', '81c3b080dad537de7e10e0987a4bf52e', '2021-03-10 14:55:41', '2021-03-10 14:55:41', NULL, '119.29.118.110', '', '60ca01e2-221e-47af-b7e5-64f2a7336973', NULL, NULL, NULL, NULL, NULL, '');
INSERT INTO `config_info` VALUES (12, 'store.db.driverClassName', 'DEFAULT_GROUP', 'com.mysql.jdbc.Driver', '683cf0c3a5a56cec94dfac94ca16d760', '2021-03-10 14:55:41', '2021-03-10 14:55:41', NULL, '119.29.118.110', '', '60ca01e2-221e-47af-b7e5-64f2a7336973', NULL, NULL, NULL, NULL, NULL, '');
INSERT INTO `config_info` VALUES (13, 'store.db.url', 'DEFAULT_GROUP', 'jdbc:mysql://twelvet-mysql:3306/twelvet_seata?allowPublicKeyRetrieval=true&useUnicode=true', 'afaf8f99e8c60ca2837ae6e54ffbfb8e', '2021-03-10 14:55:41', '2021-03-10 14:59:51', NULL, '117.136.31.81', '', '60ca01e2-221e-47af-b7e5-64f2a7336973', 'null', 'null', 'null', 'null', 'null', '');
INSERT INTO `config_info` VALUES (14, 'store.db.user', 'DEFAULT_GROUP', 'root', 'b08b1d0e53062cd03513b2fde5488e2f', '2021-03-10 14:55:42', '2021-03-10 14:59:21', NULL, '117.136.31.81', '', '60ca01e2-221e-47af-b7e5-64f2a7336973', 'null', 'null', 'null', 'null', 'null', '');
INSERT INTO `config_info` VALUES (15, 'store.db.password', 'DEFAULT_GROUP', '123456', 'd3e297a95855225cc7cf5494c92b7f0b', '2021-03-10 14:55:42', '2021-03-10 14:57:53', NULL, '117.136.31.81', '', '60ca01e2-221e-47af-b7e5-64f2a7336973', 'null', 'null', 'null', 'null', 'null', '');
INSERT INTO `config_info` VALUES (16, 'store.db.minConn', 'DEFAULT_GROUP', '5', 'e4da3b7fbbce2345d7772b0674a318d5', '2021-03-10 14:55:42', '2021-03-10 14:55:42', NULL, '119.29.118.110', '', '60ca01e2-221e-47af-b7e5-64f2a7336973', NULL, NULL, NULL, NULL, NULL, '');
INSERT INTO `config_info` VALUES (17, 'store.db.maxConn', 'DEFAULT_GROUP', '30', '34173cb38f07f89ddbebc2ac9128303f', '2021-03-10 14:55:42', '2021-03-10 14:55:42', NULL, '119.29.118.110', '', '60ca01e2-221e-47af-b7e5-64f2a7336973', NULL, NULL, NULL, NULL, NULL, '');
INSERT INTO `config_info` VALUES (18, 'store.db.globalTable', 'DEFAULT_GROUP', 'global_table', '8b28fb6bb4c4f984df2709381f8eba2b', '2021-03-10 14:55:43', '2021-03-10 14:55:43', NULL, '119.29.118.110', '', '60ca01e2-221e-47af-b7e5-64f2a7336973', NULL, NULL, NULL, NULL, NULL, '');
INSERT INTO `config_info` VALUES (19, 'store.db.branchTable', 'DEFAULT_GROUP', 'branch_table', '54bcdac38cf62e103fe115bcf46a660c', '2021-03-10 14:55:43', '2021-03-10 14:55:43', NULL, '119.29.118.110', '', '60ca01e2-221e-47af-b7e5-64f2a7336973', NULL, NULL, NULL, NULL, NULL, '');
INSERT INTO `config_info` VALUES (20, 'store.db.queryLimit', 'DEFAULT_GROUP', '100', 'f899139df5e1059396431415e770c6dd', '2021-03-10 14:55:43', '2021-03-10 14:55:43', NULL, '119.29.118.110', '', '60ca01e2-221e-47af-b7e5-64f2a7336973', NULL, NULL, NULL, NULL, NULL, '');
INSERT INTO `config_info` VALUES (21, 'store.db.lockTable', 'DEFAULT_GROUP', 'lock_table', '55e0cae3b6dc6696b768db90098b8f2f', '2021-03-10 14:55:44', '2021-03-10 14:55:44', NULL, '119.29.118.110', '', '60ca01e2-221e-47af-b7e5-64f2a7336973', NULL, NULL, NULL, NULL, NULL, '');
INSERT INTO `config_info` VALUES (22, 'store.db.maxWait', 'DEFAULT_GROUP', '5000', 'a35fe7f7fe8217b4369a0af4244d1fca', '2021-03-10 14:55:44', '2021-03-10 14:55:44', NULL, '119.29.118.110', '', '60ca01e2-221e-47af-b7e5-64f2a7336973', NULL, NULL, NULL, NULL, NULL, '');
INSERT INTO `config_info` VALUES (23, 'service.vgroupMapping.twelvet-system-group', 'DEFAULT_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2021-03-10 16:03:54', '2021-03-10 16:03:54', NULL, '117.136.31.81', '', '60ca01e2-221e-47af-b7e5-64f2a7336973', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (24, 'service.vgroupMapping.twelvet-dfs-group', 'DEFAULT_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2021-03-10 16:27:41', '2021-03-10 16:27:41', NULL, '117.136.31.81', '', '60ca01e2-221e-47af-b7e5-64f2a7336973', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (25, 'twelvet-server-gen-dev.yml', 'DEFAULT_GROUP', '# spring配置\nspring: \n  datasource:\n    dynamic:\n      hikari:\n        # 连接测试查询\n        connection-test-query: SELECT 1 FROM DUAL\n        # 连接最大存活时间.不等于0且小于30秒，会被重置为默认值30分钟.设置应该比mysql设置的超时时间短\n        max-lifetime: 540000\n        # 只有空闲连接数大于最大连接数且空闲时间超过该值，才会被释放\n        idle-timeout: 500000\n        # 最小空闲连接，默认值10，小于0或大于maximum-pool-size，都会重置为maximum-pool-size\n        minimum-idle: 10\n        # 最大连接数，小于等于0会被重置为默认值10；大于零小于1会被重置为minimum-idle的值\n        maximum-pool-size: 12\n        # 连接超时时间:毫秒，小于250毫秒，否则被重置为默认值30秒\n        connection-timeout: 60000\n      # 设置默认的数据源或者数据源组,默认值即为master\n      primary: master\n      datasource:\n        # 主库数据源\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://twelvet-mysql:3306/twelvet?allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8\n          username: root\n          password: 123456\n        # 从库数据源\n        # slave:\n        # username:\n        # password:\n        # url:\n        # driver-class-name:\n      # 开启seata代理，开启后默认每个数据源都代理,分布式事务必须开启,否则关闭\n      seata: false\n  data:\n    redis:\n      host: twelvet-redis\n      port: 6379\n      # 连接超时时间\n      timeout: 30s\n      lettuce:\n        pool:\n          # 连接池中的最小空闲连接\n          min-idle: 0\n          # 连接池中的最大空闲连接\n          max-idle: 8\n          # 连接池的最大数据库连接数\n          max-active: 8\n          # #连接池最大阻塞等待时间（使用负值表示没有限制）\n          max-wait: -1ms\n\n# 代码生成\ngen: \n  # 作者\n  author: TwelveT\n  # 默认生成包路径 system 需改成自己的模块名称 如 system monitor tool\n  packageName: com.twelvet.server.system\n  # 自动去除表前缀，默认是false\n  autoRemovePre: false\n  # 表前缀（生成类名不会包含表前缀，多个用逗号分隔）\n  tablePrefix: sys_\n\nswagger:\n  title: TwelveT Swagger API\n  version: 2.7.0\n  description: CRUD服务\n  license: Powered By TwelveT\n  licenseUrl: https://twelvet.cn\n  terms-of-service-url: https://twelvet.cn\n  contact:\n    name: TwelveT\n    email: 2471835953@qq.com\n    url: https://twelvet.cn\n  authorization:\n    name: password\n    token-url-list:\n      - http://${GATEWAY_HOST:localhost}:${GATEWAY_PORT:88}/auth/oauth/token\n# 开启Swagger增强模式\nknife4j:\n  enable: true', '8129b0c90395b6f3cf2f80ea22e1feb6', '2021-03-20 13:04:24', '2023-07-08 09:22:27', 'nacos', '127.0.0.1', '', 'eeb43899-8a88-4f5b-b0e0-d7c8fd09b86e', '代码生成器', 'null', 'null', 'yaml', 'null', '');

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS `config_info_aggr`;
CREATE TABLE `config_info_aggr`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfoaggr_datagrouptenantdatum`(`data_id`, `group_id`, `tenant_id`, `datum_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '增加租户字段' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info_aggr
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS `config_info_beta`;
CREATE TABLE `config_info_beta`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfobeta_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_beta' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info_beta
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS `config_info_tag`;
CREATE TABLE `config_info_tag`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfotag_datagrouptenanttag`(`data_id`, `group_id`, `tenant_id`, `tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_tag' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info_tag
-- ----------------------------

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS `config_tags_relation`;
CREATE TABLE `config_tags_relation`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `tag_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`) USING BTREE,
  UNIQUE INDEX `uk_configtagrelation_configidtag`(`id`, `tag_name`, `tag_type`) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_tag_relation' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_tags_relation
-- ----------------------------

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS `group_capacity`;
CREATE TABLE `group_capacity`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_group_id`(`group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '集群、各Group容量信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of group_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS `his_config_info`;
CREATE TABLE `his_config_info`  (
  `id` bigint(64) UNSIGNED NOT NULL,
  `nid` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `op_type` char(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`nid`) USING BTREE,
  INDEX `idx_gmt_create`(`gmt_create`) USING BTREE,
  INDEX `idx_gmt_modified`(`gmt_modified`) USING BTREE,
  INDEX `idx_did`(`data_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '多租户改造' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('nacos', 'ROLE_ADMIN');

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS `tenant_capacity`;
CREATE TABLE `tenant_capacity`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数',
  `max_aggr_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '租户容量信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tenant_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `tenant_info`;
CREATE TABLE `tenant_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint(20) NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_info_kptenantid`(`kp`, `tenant_id`) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'tenant_info' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tenant_info
-- ----------------------------
INSERT INTO `tenant_info` VALUES (1, '1', 'eeb43899-8a88-4f5b-b0e0-d7c8fd09b86e', 'dev', '开发环境', 'nacos', 1591243291556, 1591243291556);
INSERT INTO `tenant_info` VALUES (2, '1', '1edbee6b-43a7-4b97-a0f7-2804a8bb1bf9', 'test', '测试环境', 'nacos', 1598779661839, 1598779661839);
INSERT INTO `tenant_info` VALUES (4, '1', 'c4d16870-3d8d-40a8-b396-6737705dbde8', 'pre', '灰度环境', 'nacos', 1598779690682, 1598779690682);
INSERT INTO `tenant_info` VALUES (5, '1', '94664454-62b0-415a-9392-7c0ce2d11b2f', 'pro', '生产环境', 'nacos', 1598779700041, 1598779700041);
INSERT INTO `tenant_info` VALUES (6, '1', '60ca01e2-221e-47af-b7e5-64f2a7336973', 'seata', '分布式事务', 'nacos', 1615359228918, 1615359228918);

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions` (
    `role` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `resource` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `action` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    UNIQUE KEY `uk_role_permission` (`role`,`resource`,`action`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', 1);

SET FOREIGN_KEY_CHECKS = 1;
