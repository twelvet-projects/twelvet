DROP DATABASE IF EXISTS `twelvet_nacos`;

CREATE DATABASE `twelvet_nacos` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

USE `twelvet_nacos`;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info`
(
    `id`                 bigint(20)                                        NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`            varchar(255) CHARACTER SET utf8 COLLATE utf8_bin  NOT NULL COMMENT 'data_id',
    `group_id`           varchar(128) CHARACTER SET utf8 COLLATE utf8_bin  NULL     DEFAULT NULL COMMENT 'group_id',
    `content`            longtext CHARACTER SET utf8 COLLATE utf8_bin      NOT NULL COMMENT 'content',
    `md5`                varchar(32) CHARACTER SET utf8 COLLATE utf8_bin   NULL     DEFAULT NULL COMMENT 'md5',
    `gmt_create`         datetime                                          NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`       datetime                                          NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `src_user`           text CHARACTER SET utf8 COLLATE utf8_bin          NULL COMMENT 'source user',
    `src_ip`             varchar(50) CHARACTER SET utf8 COLLATE utf8_bin   NULL     DEFAULT NULL COMMENT 'source ip',
    `app_name`           varchar(128) CHARACTER SET utf8 COLLATE utf8_bin  NULL     DEFAULT NULL COMMENT 'app_name',
    `tenant_id`          varchar(128) CHARACTER SET utf8 COLLATE utf8_bin  NULL     DEFAULT '' COMMENT '租户字段',
    `c_desc`             varchar(256) CHARACTER SET utf8 COLLATE utf8_bin  NULL     DEFAULT NULL COMMENT 'configuration description',
    `c_use`              varchar(64) CHARACTER SET utf8 COLLATE utf8_bin   NULL     DEFAULT NULL COMMENT 'configuration usage',
    `effect`             varchar(64) CHARACTER SET utf8 COLLATE utf8_bin   NULL     DEFAULT NULL COMMENT '配置生效的描述',
    `type`               varchar(64) CHARACTER SET utf8 COLLATE utf8_bin   NULL     DEFAULT NULL COMMENT '配置的类型',
    `c_schema`           text CHARACTER SET utf8 COLLATE utf8_bin          NULL COMMENT '配置的模式',
    `encrypted_data_key` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '密钥',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_configinfo_datagrouptenant` (`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 11
  CHARACTER SET = utf8
  COLLATE = utf8_bin COMMENT = 'config_info'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info
-- ----------------------------
INSERT INTO `config_info`
VALUES (1, 'twelvet-app-dev.yml', 'DEFAULT_GROUP',
        '# 配置文件加密根密码\njasypt:\n  encryptor:\n    password: twelvet\n    algorithm: PBEWithMD5AndDES\n    iv-generator-classname: org.jasypt.iv.NoIvGenerator\n\n# 服务调用超时时间(ms)\nribbon:\n  ReadTimeout: 8000\n  ConnectTimeout: 8000\n\n# 认证配置\nsecurity:\n  oauth2:\n    # 配置Resource Client_id信息\n    client:\n      client-id: twelvet\n      client-secret: 123456\n      scope: server\n    resource:\n      loadBalanced: true\n      token-info-uri: http://twelvet-auth/oauth/check_token\n    ignore:\n      urls:\n        - /error\n        - /actuator/**\n        - /v3/api-docs\nspring:\n  data:\n    redis:\n      host: twelvet-redis\n      port: 6379\n      # 连接超时时间\n      timeout: 30s\n      lettuce:\n        pool:\n          # 连接池中的最小空闲连接\n          min-idle: 0\n          # 连接池中的最大空闲连接\n          max-idle: 8\n          # 连接池的最大数据库连接数\n          max-active: 8\n          # #连接池最大阻塞等待时间（使用负值表示没有限制）\n          max-wait: -1ms\n  # 资源信息\n  messages:\n    # 国际化资源文件路径\n    basename: i18n/system/twelvet,i18n/security/twelvet\n    # 缓存持续时间(秒)\n    cache-duration: 1800\n  cache:\n    type: redis\n  jackson:\n    default-property-inclusion: non_null\n    time-zone: GMT+8\n  cloud:\n    openfeign:\n      sentinel:\n        enabled: true\n      okhttp:\n        enabled: false\n      httpclient:\n        enabled: false\n      compression:\n        request:\n          enabled: true\n        response:\n          enabled: true\n    # 配置sentinel控制台\n    sentinel:\n      # 取消控制台懒加载\n      eager: true\n      transport:\n        # 控制台地址：Port端口\n        dashboard: twelvet-sentinel:8101\n\n# 暴露监控端点\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \' *\'\n\n# Mybatis配置\nmybatis:\n  # 搜索指定包别名\n  typeAliasesPackage: com.twelvet.api.*.domain\n  # 配置mapper的扫描，找到所有的mapper.xml映射文件\n  mapperLocations: classpath*:mapper/**/*Mapper.xml\n  configuration:\n    # 下划线转驼峰自动对应实体\n    map-underscore-to-camel-case: true\n    # Mybatis开启日志打印\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\n\n# 分布式事务配置\nseata:\n  # 未配置分布式事务,不要打开,会报错，且spring.datasource.dynami.seata需要同时开启\n  enabled: false\n  application-id: ${twelvet.name}\n  # 事务分组,nacos配置上必须相对应的配置\n  tx-service-group: ${twelvet.name}-group\n  # 开启自动数据源代理\n  enable-auto-data-source-proxy: true\n  config:\n    type: nacos\n    nacos:\n      server-addr: http://nacos.twelvet.cn\n      namespace: dev\n      group: DEFAULT_GROUP\n  registry:\n    type: nacos\n    nacos:\n      server-addr: http://nacos.twelvet.cn\n      application: seata-server\n      group: DEFAULT_GROUP\n      namespace: dev\n      cluster: DEFAULT\n\n# swagger 配置\nspringdoc:\n  api-docs:\n    # 生产需要关闭Swagger数据来源\n    enabled: true\nswagger:\n  title: TwelveT Swagger API\n  gateway: http://${GATEWAY_HOST:twelvet-gateway}:${GATEWAY-PORT:8080}\n  token-url: ${swagger.gateway}/auth/oauth2/token\n  scope: server\n  services:\n    twelvet-auth: auth\n    twelvet-server-system: system\n\n# xxl-job配置\nxxl:\n  job:\n    admin:\n      # 调度中心地址\n      addresses: http://127.0.0.1:9080/xxl-job-admin',
        '5a012c2fbdbcbbcf40ff93fbe2cba1af', '2025-02-27 14:28:18', '2025-05-05 16:43:27', 'nacos', '0:0:0:0:0:0:0:1',
        '', 'dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info`
VALUES (2, 'twelvet-auth-dev.yml', 'DEFAULT_GROUP',
        'spring: \r\n  # 模板引擎配置\r\n  freemarker:\r\n    allow-request-override: false\r\n    allow-session-override: false\r\n    check-template-location: true\r\n    expose-request-attributes: false\r\n    expose-session-attributes: false\r\n    expose-spring-macro-helpers: true\r\n    prefer-file-system-access: true\r\n    # 后缀名\r\n    suffix: .ftl  \r\n    content-type: text/html\r\n    enabled: true\r\n    # 缓存配置\r\n    cache: true \r\n    # 模板加载路径 按需配置\r\n    template-loader-path: classpath:/templates/ \r\n    charset: UTF-8\r\n  \r\nswagger:\r\n  title: TwelveT Swagger API\r\n  version: 2.7.0\r\n  description: 授权中心服务\r\n  license: Powered By TwelveT\r\n  licenseUrl: https://twelvet.cn\r\n  terms-of-service-url: https://twelvet.cn\r\n  contact:\r\n    name: TwelveT\r\n    email: 2471835953@qq.com\r\n    url: https://twelvet.cn\r\n  authorization:\r\n    name: password\r\n    token-url-list:\r\n      - http://${GATEWAY_HOST:localhost}:${GATEWAY_PORT:88}/auth/oauth/token\r\n\r\n# 第三方登录授权配置\r\noauth2:\r\n  github:\r\n    clientId: twelvet\r\n    clientSecret: twelvet\r\n    redirectUri: http://localhost:8000/login/oauth2/github',
        'aea93e48dade383a558e5181f6b9adbb', '2025-02-27 14:28:36', '2025-02-27 14:28:36', 'nacos', '0:0:0:0:0:0:0:1',
        '', 'dev', '认证服务器配置', NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info`
VALUES (3, 'twelvet-server-system-dev.yml', 'DEFAULT_GROUP',
        'spring: \n  datasource:\n    dynamic:\n      hikari:\n        # 连接测试查询\n        connection-test-query: SELECT 1 FROM DUAL\n        # 连接最大存活时间.不等于0且小于30秒，会被重置为默认值30分钟.设置应该比mysql设置的超时时间短\n        max-lifetime: 540000\n        # 只有空闲连接数大于最大连接数且空闲时间超过该值，才会被释放\n        idle-timeout: 500000\n        # 最小空闲连接，默认值10，小于0或大于maximum-pool-size，都会重置为maximum-pool-size\n        minimum-idle: 10\n        # 最大连接数，小于等于0会被重置为默认值10；大于零小于1会被重置为minimum-idle的值\n        maximum-pool-size: 12\n        # 连接超时时间:毫秒，小于250毫秒，否则被重置为默认值30秒\n        connection-timeout: 60000\n      # 设置默认的数据源或者数据源组,默认值即为master\n      primary: master \n      datasource:\n        # 主库数据源\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://twelvet-mysql:3306/twelvet?allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8\n          username: root\n          password: 123456\n        # shardingSphere:\n        #   driver-class-name: org.apache.shardingsphere.driver.ShardingSphereDriver\n        #   url: jdbc:shardingsphere:classpath:sharding-jdbc.yml\n        # 从库数据源\n        # slave:\n        # username:\n        # password:\n        # url:\n        # driver-class-name:\n      # 开启seata代理，开启后默认每个数据源都代理,分布式事务必须开启,否则关闭\n      seata: false\n\nswagger:\n  title: TwelveT Swagger API\n  version: 2.7.0\n  description: 系统服务\n  license: Powered By TwelveT\n  licenseUrl: https://twelvet.cn\n  terms-of-service-url: https://twelvet.cn\n  contact:\n    name: TwelveT\n    email: 2471835953@qq.com\n    url: https://twelvet.cn\n  authorization:\n    name: password\n    token-url-list:\n      - http://${GATEWAY_HOST:localhost}:${GATEWAY_PORT:88}/auth/oauth/token\n\n# xxl-job配置\nxxl:\n  job:\n    executor:\n      # 执行器名称\n      appname: twelvet-server-system\n      # 执行器地址\n      address: http://127.0.0.1:9999/\n      # 授权token\n      accessToken: twelvet123456',
        'bceb4cf23ef2f1b0335274ba5c34090a', '2025-02-27 14:28:57', '2025-05-05 16:56:38', 'nacos', '0:0:0:0:0:0:0:1',
        '', 'dev', '系统模块', '', '', 'yaml', '', '');
INSERT INTO `config_info`
VALUES (4, 'twelvet-gateway-dev.yml', 'DEFAULT_GROUP',
        'spring: \r\n  cloud:\r\n    gateway:\r\n      globalcors:\r\n        corsConfigurations:\r\n          \'[/**]\':\r\n            allowedOriginPatterns: \"*\"\r\n            allowed-methods: \"*\"\r\n            allowed-headers: \"*\"\r\n            allow-credentials: true\r\n            exposedHeaders: \"Content-Disposition,Content-Type,Cache-Control\"\r\n      loadbalancer: \r\n        use404: true\r\n      discovery:\r\n        locator:\r\n          # 开启服务名称小写匹配\r\n          lowerCaseServiceId: true\r\n          # 开启动态创建路由（不用每个都写死）\r\n          enabled: true\r\n      # 路由配置\r\n      routes:\r\n        # 认证中心\r\n        - id: twelvet-auth\r\n          uri: lb://twelvet-auth\r\n          predicates:\r\n            - Path=/auth/**\r\n          filters:\r\n            - StripPrefix=1\r\n        # 系统模块\r\n        - id: twelvet-server-system\r\n          # 匹配后提供服务的路由地址\r\n          uri: lb://twelvet-server-system\r\n           # 断言，路径匹配的进行路由\r\n          predicates:\r\n            - Path=/system/**\r\n          filters:\r\n            # 转发请求时去除路由前缀（system）\r\n            - StripPrefix=1\r\n            # 请求重写，将内部开放服务重写(重要配置，请勿轻易修改，否则将暴露用户信息API)\r\n            - RewritePath=/user/info(?<segment>/?.*), /from-user-info$\\{segment}\r\n        # 定时任务\r\n        - id: twelvet-server-job\r\n          uri: lb://twelvet-server-job\r\n          predicates:\r\n            - Path=/job/**\r\n          filters:\r\n            - StripPrefix=1\r\n        # DFS文件系统\r\n        - id: twelvet-server-dfs\r\n          uri: lb://twelvet-server-dfs\r\n          predicates:\r\n            - Path=/dfs/**\r\n          filters:\r\n            - StripPrefix=1\r\n            # 请求重写，不允许直接上传文件\r\n            - RewritePath=/upload(?<segment>/?.*), /from-upload$\\{segment}\r\n        # 代码生成器\r\n        - id: twelvet-server-gen\r\n          uri: lb://twelvet-server-gen\r\n          predicates:\r\n            - Path=/gen/**\r\n          filters:\r\n            - StripPrefix=1\r\n        # AI服务\r\n        - id: twelvet-server-ai\r\n          uri: lb://twelvet-server-ai\r\n          predicates:\r\n            - Path=/ai/**\r\n          filters:\r\n            - StripPrefix=1\r\n\r\n      ',
        'bfada49d253131cf4727b853edde7e56', '2025-02-27 14:29:13', '2025-02-27 14:29:13', 'nacos', '0:0:0:0:0:0:0:1',
        '', 'dev', '网关配置', NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info`
VALUES (5, 'twelvet-visual-monitor-dev.yml', 'DEFAULT_GROUP',
        '# Spring\r\nspring: \r\n  security:\r\n    user:\r\n      name: twelvet\r\n      password: 123456\r\n  boot:\r\n    admin:\r\n      ui:\r\n        title: TwelveT服务监控中心',
        '315056a443f809904b98f0184580cbce', '2025-02-27 14:29:31', '2025-02-27 14:29:31', 'nacos', '0:0:0:0:0:0:0:1',
        '', 'dev', '服务监控中心', NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info`
VALUES (6, 'sentinel-twelvet-gateway', 'DEFAULT_GROUP',
        '[\r\n    {\r\n        \"resource\": \"twelvet-auth\",\r\n        \"count\": 10,\r\n        \"grade\": 1,\r\n        \"limitApp\": \"default\",\r\n        \"strategy\": 0,\r\n        \"controlBehavior\": 0\r\n    },\r\n	{\r\n        \"resource\": \"twelvet-server-system\",\r\n        \"count\": 10,\r\n        \"grade\": 1,\r\n        \"limitApp\": \"default\",\r\n        \"strategy\": 0,\r\n        \"controlBehavior\": 0\r\n    }\r\n]',
        '4e3273cff89f8407ec43168edeb1977f', '2025-02-27 14:29:46', '2025-02-27 14:29:46', 'nacos', '0:0:0:0:0:0:0:1',
        '', 'dev', '流量控制', NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info`
VALUES (7, 'twelvet-server-job-dev.yml', 'DEFAULT_GROUP',
        '# Spring\r\nspring: \r\n  datasource:\r\n    dynamic:\r\n      hikari:\r\n        # 连接测试查询\r\n        connection-test-query: SELECT 1 FROM DUAL\r\n        # 连接最大存活时间.不等于0且小于30秒，会被重置为默认值30分钟.设置应该比mysql设置的超时时间短\r\n        max-lifetime: 540000\r\n        # 只有空闲连接数大于最大连接数且空闲时间超过该值，才会被释放\r\n        idle-timeout: 500000\r\n        # 最小空闲连接，默认值10，小于0或大于maximum-pool-size，都会重置为maximum-pool-size\r\n        minimum-idle: 10\r\n        # 最大连接数，小于等于0会被重置为默认值10；大于零小于1会被重置为minimum-idle的值\r\n        maximum-pool-size: 12\r\n        # 连接超时时间:毫秒，小于250毫秒，否则被重置为默认值30秒\r\n        connection-timeout: 60000\r\n        # 设置默认的数据源或者数据源组,默认值即为master\r\n      primary: master\r\n      datasource:\r\n        # 主库数据源\r\n        master:\r\n          driver-class-name: com.mysql.cj.jdbc.Driver\r\n          url: jdbc:mysql://twelvet-mysql:3306/twelvet?allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8\r\n          username: root\r\n          password: 123456\r\n        # 从库数据源\r\n        # slave:\r\n        # username:\r\n        # password:\r\n        # url:\r\n        # driver-class-name:\r\n      # 开启seata代理，开启后默认每个数据源都代理,分布式事务必须开启,否则关闭\r\n      seata: false\r\n  \r\nswagger:\r\n  title: TwelveT Swagger API\r\n  version: 2.7.0\r\n  description: 分布式调度服务\r\n  license: Powered By TwelveT\r\n  licenseUrl: https://twelvet.cn\r\n  terms-of-service-url: https://twelvet.cn\r\n  contact:\r\n    name: TwelveT\r\n    email: 2471835953@qq.com\r\n    url: https://twelvet.cn\r\n  authorization:\r\n    name: twelvet\r\n    auth-regex: ^.*$\r\n    authorization-scope-list:\r\n      - scope: server\r\n        description: server all\r\n    token-url-list:\r\n      - http://${GATEWAY_HOST:twelvet-gateway}:${GATEWAY_PORT:88}/auth/oauth/token\r\n# 开启Swagger增强模式\r\nknife4j:\r\n  enable: true',
        '1502952fa21f91b4e020935d34438d99', '2025-02-27 14:30:03', '2025-02-27 14:30:03', 'nacos', '0:0:0:0:0:0:0:1',
        '', 'dev', NULL, NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info`
VALUES (8, 'twelvet-server-dfs-dev.yml', 'DEFAULT_GROUP',
        'spring: \r\n  datasource:\r\n   dynamic:\r\n    hikari:\r\n      # 连接测试查询\r\n      connection-test-query: SELECT 1 FROM DUAL\r\n      # 连接最大存活时间.不等于0且小于30秒，会被重置为默认值30分钟.设置应该比mysql设置的超时时间短\r\n      max-lifetime: 540000\r\n      # 只有空闲连接数大于最大连接数且空闲时间超过该值，才会被释放\r\n      idle-timeout: 500000\r\n      # 最小空闲连接，默认值10，小于0或大于maximum-pool-size，都会重置为maximum-pool-size\r\n      minimum-idle: 10\r\n      # 最大连接数，小于等于0会被重置为默认值10；大于零小于1会被重置为minimum-idle的值\r\n      maximum-pool-size: 12\r\n      # 连接超时时间:毫秒，小于250毫秒，否则被重置为默认值30秒\r\n      connection-timeout: 60000\r\n    # 设置默认的数据源或者数据源组,默认值即为master\r\n    primary: master\r\n    datasource:\r\n      # 主库数据源\r\n      master:\r\n        driver-class-name: com.mysql.cj.jdbc.Driver\r\n        url: jdbc:mysql://twelvet-mysql:3306/twelvet?allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8\r\n        username: root\r\n        password: 123456\r\n      # 从库数据源\r\n      # slave:\r\n      # username:\r\n      # password:\r\n      # url:\r\n      # driver-class-name:\r\n    # 开启seata代理，开启后默认每个数据源都代理,分布式事务必须开启,否则关闭\r\n    seata: false\r\n\r\n# 通用OSS S3配置\r\noss:\r\n  # 开启ossTemplte（默认开启）\r\n  enable: true\r\n  accessKey: twelvet\r\n  secretKey: twelvet\r\n  region: us-east-2\r\n  bucketName: twelvet-static\r\n  endpoint: s3.us-east-2.amazonaws.com\r\n  # 自带controller\r\n  http:\r\n    # 是否开启（默认关闭）\r\n    enable: false\r\n    prefix: /s3\r\n\r\nswagger:\r\n  title: TwelveT Swagger API\r\n  version: 2.7.0\r\n  description: DFS服务\r\n  license: Powered By TwelveT\r\n  licenseUrl: https://twelvet.cn\r\n  terms-of-service-url: https://twelvet.cn\r\n  contact:\r\n    name: TwelveT\r\n    email: 2471835953@qq.com\r\n    url: https://twelvet.cn\r\n  authorization:\r\n    name: password\r\n    token-url-list:\r\n      - http://${GATEWAY_HOST:localhost}:${GATEWAY_PORT:88}/auth/oauth/token\r\n# 开启Swagger增强模式\r\nknife4j:\r\n  enable: true\r\n  ',
        '4327ab008d3e427b10ba9bdb40237261', '2025-02-27 14:30:18', '2025-02-27 14:30:18', 'nacos', '0:0:0:0:0:0:0:1',
        '', 'dev', '分布式文件系统', NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info`
VALUES (9, 'twelvet-server-gen-dev.yml', 'DEFAULT_GROUP',
        '# Spring\r\nspring: \r\n  datasource:\r\n    type: com.zaxxer.hikari.HikariDataSource\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://twelvet-mysql:3306/twelvet_gen?allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8\r\n    username: root\r\n    password: 123456\r\n    dynamic:\r\n      hikari:\r\n        # 连接测试查询\r\n        connection-test-query: SELECT 1 FROM DUAL\r\n        # 连接最大存活时间.不等于0且小于30秒，会被重置为默认值30分钟.设置应该比mysql设置的超时时间短\r\n        max-lifetime: 540000\r\n        # 只有空闲连接数大于最大连接数且空闲时间超过该值，才会被释放\r\n        idle-timeout: 500000\r\n        # 最小空闲连接，默认值10，小于0或大于maximum-pool-size，都会重置为maximum-pool-size\r\n        minimum-idle: 10\r\n        # 最大连接数，小于等于0会被重置为默认值10；大于零小于1会被重置为minimum-idle的值\r\n        maximum-pool-size: 12\r\n        # 连接超时时间:毫秒，小于250毫秒，否则被重置为默认值30秒\r\n        connection-timeout: 60000\r\n        # 设置默认的数据源或者数据源组,默认值即为master\r\n      primary: master\r\n      datasource:\r\n        # 主库数据源\r\n        master:\r\n          driver-class-name: com.mysql.cj.jdbc.Driver\r\n          url: jdbc:mysql://twelvet-mysql:3306/twelvet_gen?allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8\r\n          username: root\r\n          password: 123456\r\n        # 从库数据源\r\n        # slave:\r\n        # username:\r\n        # password:\r\n        # url:\r\n        # driver-class-name:\r\n      # 开启seata代理，开启后默认每个数据源都代理,分布式事务必须开启,否则关闭\r\n      seata: false\r\n\r\n# 代码生成\r\ngen: \r\n  # 作者\r\n  author: TwelveT\r\n  # 默认生成包路径 system 需改成自己的模块名称 如 system monitor tool\r\n  packageName: com.twelvet.server.system\r\n  # 自动去除表前缀，默认是false\r\n  autoRemovePre: false\r\n  # 表前缀（生成类名不会包含表前缀，多个用逗号分隔）\r\n  tablePrefix: sys_\r\n\r\nswagger:\r\n  title: TwelveT Swagger API\r\n  version: 2.7.0\r\n  description: CRUD服务\r\n  license: Powered By TwelveT\r\n  licenseUrl: https://twelvet.cn\r\n  terms-of-service-url: https://twelvet.cn\r\n  contact:\r\n    name: TwelveT\r\n    email: 2471835953@qq.com\r\n    url: https://twelvet.cn\r\n  authorization:\r\n    name: password\r\n    token-url-list:\r\n      - http://${GATEWAY_HOST:localhost}:${GATEWAY_PORT:88}/auth/oauth/token\r\n# 开启Swagger增强模式\r\nknife4j:\r\n  enable: true',
        '1332e5e902e649bf07a127ee8d32ce57', '2025-02-27 14:30:38', '2025-02-27 14:30:38', 'nacos', '0:0:0:0:0:0:0:1',
        '', 'dev', '代码生成器', NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info`
VALUES (10, 'twelvet-server-ai-dev.yml', 'DEFAULT_GROUP',
        '# Spring\r\nspring: \r\n  ai:\r\n    # alibaba AI\r\n    dashscope:\r\n      # https://bailian.console.aliyun.com/?apiKey=1#/api-key\r\n      api-key: key\r\n    vectorstore:\r\n      pgvector:\r\n        # 指定表名称\r\n        table-name: ai_vector\r\n        # 最近邻搜索索引类型\r\n        index-type: HNSW\r\n        # 搜索距离类型\r\n        distance-type: COSINE_DISTANCE\r\n        # 嵌入维度\r\n        dimensions: 1536\r\n        # 不存在表屎尝试初始化\r\n        initialize-schema: true\r\n  datasource:\r\n    dynamic:\r\n      hikari:\r\n        # 连接测试查询\r\n        connection-test-query: SELECT 1\r\n        # 连接最大存活时间.不等于0且小于30秒，会被重置为默认值30分钟.设置应该比mysql设置的超时时间短\r\n        max-lifetime: 540000\r\n        # 只有空闲连接数大于最大连接数且空闲时间超过该值，才会被释放\r\n        idle-timeout: 500000\r\n        # 最小空闲连接，默认值10，小于0或大于maximum-pool-size，都会重置为maximum-pool-size\r\n        minimum-idle: 10\r\n        # 最大连接数，小于等于0会被重置为默认值10；大于零小于1会被重置为minimum-idle的值\r\n        maximum-pool-size: 12\r\n        # 连接超时时间:毫秒，小于250毫秒，否则被重置为默认值30秒\r\n        connection-timeout: 60000\r\n        # 设置默认的数据源或者数据源组,默认值即为master\r\n      primary: vector\r\n      datasource:\r\n        # 主库数据源\r\n        master:\r\n          driver-class-name: com.mysql.cj.jdbc.Driver\r\n          url: jdbc:mysql://twelvet-mysql:3306/twelvet_ai?allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8\r\n          username: root\r\n          password: 123456\r\n        # 向量postgresql数据源\r\n        vector:\r\n          driver-class-name: org.postgresql.Driver\r\n          username: postgres\r\n          password: postgres\r\n          url: jdbc:postgresql://twelvet-pgsql:5432/twelvet_ai\r\n      # 开启seata代理，开启后默认每个数据源都代理,分布式事务必须开启,否则关闭\r\n      seata: false\r\n\r\nswagger:\r\n  title: TwelveT Swagger API\r\n  version: 2.7.0\r\n  description: ai服务\r\n  license: Powered By TwelveT\r\n  licenseUrl: https://twelvet.cn\r\n  terms-of-service-url: https://twelvet.cn\r\n  contact:\r\n    name: TwelveT\r\n    email: 2471835953@qq.com\r\n    url: https://twelvet.cn\r\n  authorization:\r\n    name: password\r\n    token-url-list:\r\n      - http://${GATEWAY_HOST:localhost}:${GATEWAY_PORT:88}/auth/oauth/token\r\n# 开启Swagger增强模式\r\nknife4j:\r\n  enable: true',
        '35057710e16990d788e1ef6dd6f2f881', '2025-02-27 14:30:52', '2025-02-27 14:30:52', 'nacos', '0:0:0:0:0:0:0:1',
        '', 'dev', NULL, NULL, NULL, 'yaml', NULL, '');

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS `config_info_beta`;
CREATE TABLE `config_info_beta`
(
    `id`                 bigint(20)                                        NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`            varchar(255) CHARACTER SET utf8 COLLATE utf8_bin  NOT NULL COMMENT 'data_id',
    `group_id`           varchar(128) CHARACTER SET utf8 COLLATE utf8_bin  NOT NULL COMMENT 'group_id',
    `app_name`           varchar(128) CHARACTER SET utf8 COLLATE utf8_bin  NULL     DEFAULT NULL COMMENT 'app_name',
    `content`            longtext CHARACTER SET utf8 COLLATE utf8_bin      NOT NULL COMMENT 'content',
    `beta_ips`           varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL     DEFAULT NULL COMMENT 'betaIps',
    `md5`                varchar(32) CHARACTER SET utf8 COLLATE utf8_bin   NULL     DEFAULT NULL COMMENT 'md5',
    `gmt_create`         datetime                                          NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`       datetime                                          NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `src_user`           text CHARACTER SET utf8 COLLATE utf8_bin          NULL COMMENT 'source user',
    `src_ip`             varchar(50) CHARACTER SET utf8 COLLATE utf8_bin   NULL     DEFAULT NULL COMMENT 'source ip',
    `tenant_id`          varchar(128) CHARACTER SET utf8 COLLATE utf8_bin  NULL     DEFAULT '' COMMENT '租户字段',
    `encrypted_data_key` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '密钥',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_configinfobeta_datagrouptenant` (`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_bin COMMENT = 'config_info_beta'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info_beta
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_gray
-- ----------------------------
DROP TABLE IF EXISTS `config_info_gray`;
CREATE TABLE `config_info_gray`
(
    `id`                 bigint(20) UNSIGNED                                     NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`            varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'data_id',
    `group_id`           varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'group_id',
    `content`            longtext CHARACTER SET utf8 COLLATE utf8_general_ci     NOT NULL COMMENT 'content',
    `md5`                varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT 'md5',
    `src_user`           text CHARACTER SET utf8 COLLATE utf8_general_ci         NULL COMMENT 'src_user',
    `src_ip`             varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT 'src_ip',
    `gmt_create`         datetime(3)                                             NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'gmt_create',
    `gmt_modified`       datetime(3)                                             NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'gmt_modified',
    `app_name`           varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT 'app_name',
    `tenant_id`          varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT '' COMMENT 'tenant_id',
    `gray_name`          varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'gray_name',
    `gray_rule`          text CHARACTER SET utf8 COLLATE utf8_general_ci         NOT NULL COMMENT 'gray_rule',
    `encrypted_data_key` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'encrypted_data_key',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_configinfogray_datagrouptenantgray` (`data_id`, `group_id`, `tenant_id`, `gray_name`) USING BTREE,
    INDEX `idx_dataid_gmt_modified` (`data_id`, `gmt_modified`) USING BTREE,
    INDEX `idx_gmt_modified` (`gmt_modified`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = 'config_info_gray'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info_gray
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS `config_info_tag`;
CREATE TABLE `config_info_tag`
(
    `id`           bigint(20)                                       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`      varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
    `group_id`     varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
    `tenant_id`    varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL     DEFAULT '' COMMENT 'tenant_id',
    `tag_id`       varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
    `app_name`     varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL     DEFAULT NULL COMMENT 'app_name',
    `content`      longtext CHARACTER SET utf8 COLLATE utf8_bin     NOT NULL COMMENT 'content',
    `md5`          varchar(32) CHARACTER SET utf8 COLLATE utf8_bin  NULL     DEFAULT NULL COMMENT 'md5',
    `gmt_create`   datetime                                         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime                                         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `src_user`     text CHARACTER SET utf8 COLLATE utf8_bin         NULL COMMENT 'source user',
    `src_ip`       varchar(50) CHARACTER SET utf8 COLLATE utf8_bin  NULL     DEFAULT NULL COMMENT 'source ip',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_configinfotag_datagrouptenanttag` (`data_id`, `group_id`, `tenant_id`, `tag_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_bin COMMENT = 'config_info_tag'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info_tag
-- ----------------------------

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS `config_tags_relation`;
CREATE TABLE `config_tags_relation`
(
    `id`        bigint(20)                                       NOT NULL COMMENT 'id',
    `tag_name`  varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
    `tag_type`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin  NULL DEFAULT NULL COMMENT 'tag_type',
    `data_id`   varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
    `group_id`  varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
    `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
    `nid`       bigint(20)                                       NOT NULL AUTO_INCREMENT COMMENT 'nid, 自增长标识',
    PRIMARY KEY (`nid`) USING BTREE,
    UNIQUE INDEX `uk_configtagrelation_configidtag` (`id`, `tag_name`, `tag_type`) USING BTREE,
    INDEX `idx_tenant_id` (`tenant_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_bin COMMENT = 'config_tag_relation'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_tags_relation
-- ----------------------------

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS `group_capacity`;
CREATE TABLE `group_capacity`
(
    `id`                bigint(20) UNSIGNED                              NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `group_id`          varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
    `quota`             int(10) UNSIGNED                                 NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
    `usage`             int(10) UNSIGNED                                 NOT NULL DEFAULT 0 COMMENT '使用量',
    `max_size`          int(10) UNSIGNED                                 NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
    `max_aggr_count`    int(10) UNSIGNED                                 NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数，，0表示使用默认值',
    `max_aggr_size`     int(10) UNSIGNED                                 NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
    `max_history_count` int(10) UNSIGNED                                 NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
    `gmt_create`        datetime                                         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`      datetime                                         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_group_id` (`group_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_bin COMMENT = '集群、各Group容量信息表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of group_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS `his_config_info`;
CREATE TABLE `his_config_info`
(
    `id`                 bigint(20) UNSIGNED                               NOT NULL COMMENT 'id',
    `nid`                bigint(20) UNSIGNED                               NOT NULL AUTO_INCREMENT COMMENT 'nid, 自增标识',
    `data_id`            varchar(255) CHARACTER SET utf8 COLLATE utf8_bin  NOT NULL COMMENT 'data_id',
    `group_id`           varchar(128) CHARACTER SET utf8 COLLATE utf8_bin  NOT NULL COMMENT 'group_id',
    `app_name`           varchar(128) CHARACTER SET utf8 COLLATE utf8_bin  NULL     DEFAULT NULL COMMENT 'app_name',
    `content`            longtext CHARACTER SET utf8 COLLATE utf8_bin      NOT NULL COMMENT 'content',
    `md5`                varchar(32) CHARACTER SET utf8 COLLATE utf8_bin   NULL     DEFAULT NULL COMMENT 'md5',
    `gmt_create`         datetime                                          NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`       datetime                                          NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `src_user`           text CHARACTER SET utf8 COLLATE utf8_bin          NULL COMMENT 'source user',
    `src_ip`             varchar(50) CHARACTER SET utf8 COLLATE utf8_bin   NULL     DEFAULT NULL COMMENT 'source ip',
    `op_type`            char(10) CHARACTER SET utf8 COLLATE utf8_bin      NULL     DEFAULT NULL COMMENT 'operation type',
    `tenant_id`          varchar(128) CHARACTER SET utf8 COLLATE utf8_bin  NULL     DEFAULT '' COMMENT '租户字段',
    `encrypted_data_key` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '密钥',
    `publish_type`       varchar(50) CHARACTER SET utf8 COLLATE utf8_bin   NULL     DEFAULT 'formal' COMMENT 'publish type gray or formal',
    `gray_name`          varchar(50) CHARACTER SET utf8 COLLATE utf8_bin   NULL     DEFAULT NULL COMMENT 'gray name',
    `ext_info`           longtext CHARACTER SET utf8 COLLATE utf8_bin      NULL COMMENT 'ext info',
    PRIMARY KEY (`nid`) USING BTREE,
    INDEX `idx_gmt_create` (`gmt_create`) USING BTREE,
    INDEX `idx_gmt_modified` (`gmt_modified`) USING BTREE,
    INDEX `idx_did` (`data_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 11
  CHARACTER SET = utf8
  COLLATE = utf8_bin COMMENT = '多租户改造'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of his_config_info
-- ----------------------------
INSERT INTO `his_config_info`
VALUES (0, 1, 'twelvet-app-dev.yml', 'DEFAULT_GROUP', '',
        '# 配置文件加密根密码\r\njasypt:\r\n  encryptor:\r\n    password: twelvet\r\n    algorithm: PBEWithMD5AndDES\r\n    iv-generator-classname: org.jasypt.iv.NoIvGenerator\r\n\r\n# 服务调用超时时间(ms)\r\nribbon:\r\n  ReadTimeout: 8000\r\n  ConnectTimeout: 8000\r\n\r\n# 认证配置\r\nsecurity:\r\n  oauth2:\r\n    # 配置Resource Client_id信息\r\n    client:\r\n      client-id: twelvet\r\n      client-secret: 123456\r\n      scope: server\r\n    resource:\r\n      loadBalanced: true\r\n      token-info-uri: http://twelvet-auth/oauth/check_token\r\n    ignore:\r\n      urls:\r\n        - /error\r\n        - /actuator/**\r\n        - /v3/api-docs\r\nspring:\r\n  data:\r\n    redis:\r\n      host: twelvet-redis\r\n      port: 6379\r\n      # 连接超时时间\r\n      timeout: 30s\r\n      lettuce:\r\n        pool:\r\n          # 连接池中的最小空闲连接\r\n          min-idle: 0\r\n          # 连接池中的最大空闲连接\r\n          max-idle: 8\r\n          # 连接池的最大数据库连接数\r\n          max-active: 8\r\n          # #连接池最大阻塞等待时间（使用负值表示没有限制）\r\n          max-wait: -1ms\r\n  # 资源信息\r\n  messages:\r\n    # 国际化资源文件路径\r\n    basename: i18n/system/twelvet,i18n/security/twelvet\r\n    # 缓存持续时间(秒)\r\n    cache-duration: 1800\r\n  cache:\r\n    type: redis\r\n  jackson:\r\n    default-property-inclusion: non_null\r\n    time-zone: GMT+8\r\n  cloud:\r\n    openfeign:\r\n      sentinel:\r\n        enabled: true\r\n      okhttp:\r\n        enabled: false\r\n      httpclient:\r\n        enabled: false\r\n      compression:\r\n        request:\r\n          enabled: true\r\n        response:\r\n          enabled: true\r\n    # 配置sentinel控制台\r\n    sentinel:\r\n      # 取消控制台懒加载\r\n      eager: true\r\n      transport:\r\n        # 控制台地址：Port端口\r\n        dashboard: twelvet-sentinel:8101\r\n\r\n# 暴露监控端点\r\nmanagement:\r\n  endpoints:\r\n    web:\r\n      exposure:\r\n        include: \' *\'\r\n\r\n# Mybatis配置\r\nmybatis:\r\n  # 搜索指定包别名\r\n  typeAliasesPackage: com.twelvet.api.*.domain\r\n  # 配置mapper的扫描，找到所有的mapper.xml映射文件\r\n  mapperLocations: classpath*:mapper/**/*Mapper.xml\r\n  configuration:\r\n    # 下划线转驼峰自动对应实体\r\n    map-underscore-to-camel-case: true\r\n    # Mybatis开启日志打印\r\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\r\n\r\n# 分布式事务配置\r\nseata:\r\n  # 未配置分布式事务,不要打开,会报错，且spring.datasource.dynami.seata需要同时开启\r\n  enabled: false\r\n  application-id: ${twelvet.name}\r\n  # 事务分组,nacos配置上必须相对应的配置\r\n  tx-service-group: ${twelvet.name}-group\r\n  # 开启自动数据源代理\r\n  enable-auto-data-source-proxy: true\r\n  config:\r\n    type: nacos\r\n    nacos:\r\n      server-addr: http://nacos.twelvet.cn\r\n      namespace: 60ca01e2-221e-47af-b7e5-64f2a7336973\r\n      group: DEFAULT_GROUP\r\n  registry:\r\n    type: nacos\r\n    nacos:\r\n      server-addr: http://nacos.twelvet.cn\r\n      application: seata-server\r\n      group: DEFAULT_GROUP\r\n      namespace: 60ca01e2-221e-47af-b7e5-64f2a7336973\r\n      cluster: DEFAULT\r\n\r\n# swagger 配置\r\nspringdoc:\r\n  api-docs:\r\n    # 生产需要关闭Swagger数据来源\r\n    enabled: true\r\nswagger:\r\n  title: TwelveT Swagger API\r\n  gateway: http://${GATEWAY_HOST:twelvet-gateway}:${GATEWAY-PORT:8080}\r\n  token-url: ${swagger.gateway}/auth/oauth2/token\r\n  scope: server\r\n  services:\r\n    twelvet-auth: auth\r\n    twelvet-server-system: system',
        'd5e0e910b4f8a659c05ec58b487a9ff8', '2025-02-27 14:28:18', '2025-02-27 14:28:18', 'nacos', '0:0:0:0:0:0:0:1',
        'I', 'dev', '', 'formal', '', '{\"src_user\":\"nacos\",\"type\":\"yaml\"}');
INSERT INTO `his_config_info`
VALUES (0, 2, 'twelvet-auth-dev.yml', 'DEFAULT_GROUP', '',
        'spring: \r\n  # 模板引擎配置\r\n  freemarker:\r\n    allow-request-override: false\r\n    allow-session-override: false\r\n    check-template-location: true\r\n    expose-request-attributes: false\r\n    expose-session-attributes: false\r\n    expose-spring-macro-helpers: true\r\n    prefer-file-system-access: true\r\n    # 后缀名\r\n    suffix: .ftl  \r\n    content-type: text/html\r\n    enabled: true\r\n    # 缓存配置\r\n    cache: true \r\n    # 模板加载路径 按需配置\r\n    template-loader-path: classpath:/templates/ \r\n    charset: UTF-8\r\n  \r\nswagger:\r\n  title: TwelveT Swagger API\r\n  version: 2.7.0\r\n  description: 授权中心服务\r\n  license: Powered By TwelveT\r\n  licenseUrl: https://twelvet.cn\r\n  terms-of-service-url: https://twelvet.cn\r\n  contact:\r\n    name: TwelveT\r\n    email: 2471835953@qq.com\r\n    url: https://twelvet.cn\r\n  authorization:\r\n    name: password\r\n    token-url-list:\r\n      - http://${GATEWAY_HOST:localhost}:${GATEWAY_PORT:88}/auth/oauth/token\r\n\r\n# 第三方登录授权配置\r\noauth2:\r\n  github:\r\n    clientId: twelvet\r\n    clientSecret: twelvet\r\n    redirectUri: http://localhost:8000/login/oauth2/github',
        'aea93e48dade383a558e5181f6b9adbb', '2025-02-27 14:28:35', '2025-02-27 14:28:36', 'nacos', '0:0:0:0:0:0:0:1',
        'I', 'dev', '', 'formal', '', '{\"src_user\":\"nacos\",\"type\":\"yaml\",\"c_desc\":\"认证服务器配置\"}');
INSERT INTO `his_config_info`
VALUES (0, 3, 'twelvet-server-system-dev.yml', 'DEFAULT_GROUP', '',
        'spring: \r\n  datasource:\r\n    dynamic:\r\n      hikari:\r\n        # 连接测试查询\r\n        connection-test-query: SELECT 1 FROM DUAL\r\n        # 连接最大存活时间.不等于0且小于30秒，会被重置为默认值30分钟.设置应该比mysql设置的超时时间短\r\n        max-lifetime: 540000\r\n        # 只有空闲连接数大于最大连接数且空闲时间超过该值，才会被释放\r\n        idle-timeout: 500000\r\n        # 最小空闲连接，默认值10，小于0或大于maximum-pool-size，都会重置为maximum-pool-size\r\n        minimum-idle: 10\r\n        # 最大连接数，小于等于0会被重置为默认值10；大于零小于1会被重置为minimum-idle的值\r\n        maximum-pool-size: 12\r\n        # 连接超时时间:毫秒，小于250毫秒，否则被重置为默认值30秒\r\n        connection-timeout: 60000\r\n      # 设置默认的数据源或者数据源组,默认值即为master\r\n      primary: master \r\n      datasource:\r\n        # 主库数据源\r\n        master:\r\n          driver-class-name: com.mysql.cj.jdbc.Driver\r\n          url: jdbc:mysql://twelvet-mysql:3306/twelvet?allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8\r\n          username: root\r\n          password: 123456\r\n        # shardingSphere:\r\n        #   driver-class-name: org.apache.shardingsphere.driver.ShardingSphereDriver\r\n        #   url: jdbc:shardingsphere:classpath:sharding-jdbc.yml\r\n        # 从库数据源\r\n        # slave:\r\n        # username:\r\n        # password:\r\n        # url:\r\n        # driver-class-name:\r\n      # 开启seata代理，开启后默认每个数据源都代理,分布式事务必须开启,否则关闭\r\n      seata: false\r\n\r\nswagger:\r\n  title: TwelveT Swagger API\r\n  version: 2.7.0\r\n  description: 系统服务\r\n  license: Powered By TwelveT\r\n  licenseUrl: https://twelvet.cn\r\n  terms-of-service-url: https://twelvet.cn\r\n  contact:\r\n    name: TwelveT\r\n    email: 2471835953@qq.com\r\n    url: https://twelvet.cn\r\n  authorization:\r\n    name: password\r\n    token-url-list:\r\n      - http://${GATEWAY_HOST:localhost}:${GATEWAY_PORT:88}/auth/oauth/token',
        '1a164426997df2f0d0ad404e1c8a14ed', '2025-02-27 14:28:57', '2025-02-27 14:28:57', 'nacos', '0:0:0:0:0:0:0:1',
        'I', 'dev', '', 'formal', '', '{\"src_user\":\"nacos\",\"type\":\"yaml\",\"c_desc\":\"系统模块\"}');
INSERT INTO `his_config_info`
VALUES (0, 4, 'twelvet-gateway-dev.yml', 'DEFAULT_GROUP', '',
        'spring: \r\n  cloud:\r\n    gateway:\r\n      globalcors:\r\n        corsConfigurations:\r\n          \'[/**]\':\r\n            allowedOriginPatterns: \"*\"\r\n            allowed-methods: \"*\"\r\n            allowed-headers: \"*\"\r\n            allow-credentials: true\r\n            exposedHeaders: \"Content-Disposition,Content-Type,Cache-Control\"\r\n      loadbalancer: \r\n        use404: true\r\n      discovery:\r\n        locator:\r\n          # 开启服务名称小写匹配\r\n          lowerCaseServiceId: true\r\n          # 开启动态创建路由（不用每个都写死）\r\n          enabled: true\r\n      # 路由配置\r\n      routes:\r\n        # 认证中心\r\n        - id: twelvet-auth\r\n          uri: lb://twelvet-auth\r\n          predicates:\r\n            - Path=/auth/**\r\n          filters:\r\n            - StripPrefix=1\r\n        # 系统模块\r\n        - id: twelvet-server-system\r\n          # 匹配后提供服务的路由地址\r\n          uri: lb://twelvet-server-system\r\n           # 断言，路径匹配的进行路由\r\n          predicates:\r\n            - Path=/system/**\r\n          filters:\r\n            # 转发请求时去除路由前缀（system）\r\n            - StripPrefix=1\r\n            # 请求重写，将内部开放服务重写(重要配置，请勿轻易修改，否则将暴露用户信息API)\r\n            - RewritePath=/user/info(?<segment>/?.*), /from-user-info$\\{segment}\r\n        # 定时任务\r\n        - id: twelvet-server-job\r\n          uri: lb://twelvet-server-job\r\n          predicates:\r\n            - Path=/job/**\r\n          filters:\r\n            - StripPrefix=1\r\n        # DFS文件系统\r\n        - id: twelvet-server-dfs\r\n          uri: lb://twelvet-server-dfs\r\n          predicates:\r\n            - Path=/dfs/**\r\n          filters:\r\n            - StripPrefix=1\r\n            # 请求重写，不允许直接上传文件\r\n            - RewritePath=/upload(?<segment>/?.*), /from-upload$\\{segment}\r\n        # 代码生成器\r\n        - id: twelvet-server-gen\r\n          uri: lb://twelvet-server-gen\r\n          predicates:\r\n            - Path=/gen/**\r\n          filters:\r\n            - StripPrefix=1\r\n        # AI服务\r\n        - id: twelvet-server-ai\r\n          uri: lb://twelvet-server-ai\r\n          predicates:\r\n            - Path=/ai/**\r\n          filters:\r\n            - StripPrefix=1\r\n\r\n      ',
        'bfada49d253131cf4727b853edde7e56', '2025-02-27 14:29:12', '2025-02-27 14:29:13', 'nacos', '0:0:0:0:0:0:0:1',
        'I', 'dev', '', 'formal', '', '{\"src_user\":\"nacos\",\"type\":\"yaml\",\"c_desc\":\"网关配置\"}');
INSERT INTO `his_config_info`
VALUES (0, 5, 'twelvet-visual-monitor-dev.yml', 'DEFAULT_GROUP', '',
        '# Spring\r\nspring: \r\n  security:\r\n    user:\r\n      name: twelvet\r\n      password: 123456\r\n  boot:\r\n    admin:\r\n      ui:\r\n        title: TwelveT服务监控中心',
        '315056a443f809904b98f0184580cbce', '2025-02-27 14:29:30', '2025-02-27 14:29:31', 'nacos', '0:0:0:0:0:0:0:1',
        'I', 'dev', '', 'formal', '', '{\"src_user\":\"nacos\",\"type\":\"yaml\",\"c_desc\":\"服务监控中心\"}');
INSERT INTO `his_config_info`
VALUES (0, 6, 'sentinel-twelvet-gateway', 'DEFAULT_GROUP', '',
        '[\r\n    {\r\n        \"resource\": \"twelvet-auth\",\r\n        \"count\": 10,\r\n        \"grade\": 1,\r\n        \"limitApp\": \"default\",\r\n        \"strategy\": 0,\r\n        \"controlBehavior\": 0\r\n    },\r\n	{\r\n        \"resource\": \"twelvet-server-system\",\r\n        \"count\": 10,\r\n        \"grade\": 1,\r\n        \"limitApp\": \"default\",\r\n        \"strategy\": 0,\r\n        \"controlBehavior\": 0\r\n    }\r\n]',
        '4e3273cff89f8407ec43168edeb1977f', '2025-02-27 14:29:46', '2025-02-27 14:29:46', 'nacos', '0:0:0:0:0:0:0:1',
        'I', 'dev', '', 'formal', '', '{\"src_user\":\"nacos\",\"type\":\"yaml\",\"c_desc\":\"流量控制\"}');
INSERT INTO `his_config_info`
VALUES (0, 7, 'twelvet-server-job-dev.yml', 'DEFAULT_GROUP', '',
        '# Spring\r\nspring: \r\n  datasource:\r\n    dynamic:\r\n      hikari:\r\n        # 连接测试查询\r\n        connection-test-query: SELECT 1 FROM DUAL\r\n        # 连接最大存活时间.不等于0且小于30秒，会被重置为默认值30分钟.设置应该比mysql设置的超时时间短\r\n        max-lifetime: 540000\r\n        # 只有空闲连接数大于最大连接数且空闲时间超过该值，才会被释放\r\n        idle-timeout: 500000\r\n        # 最小空闲连接，默认值10，小于0或大于maximum-pool-size，都会重置为maximum-pool-size\r\n        minimum-idle: 10\r\n        # 最大连接数，小于等于0会被重置为默认值10；大于零小于1会被重置为minimum-idle的值\r\n        maximum-pool-size: 12\r\n        # 连接超时时间:毫秒，小于250毫秒，否则被重置为默认值30秒\r\n        connection-timeout: 60000\r\n        # 设置默认的数据源或者数据源组,默认值即为master\r\n      primary: master\r\n      datasource:\r\n        # 主库数据源\r\n        master:\r\n          driver-class-name: com.mysql.cj.jdbc.Driver\r\n          url: jdbc:mysql://twelvet-mysql:3306/twelvet?allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8\r\n          username: root\r\n          password: 123456\r\n        # 从库数据源\r\n        # slave:\r\n        # username:\r\n        # password:\r\n        # url:\r\n        # driver-class-name:\r\n      # 开启seata代理，开启后默认每个数据源都代理,分布式事务必须开启,否则关闭\r\n      seata: false\r\n  \r\nswagger:\r\n  title: TwelveT Swagger API\r\n  version: 2.7.0\r\n  description: 分布式调度服务\r\n  license: Powered By TwelveT\r\n  licenseUrl: https://twelvet.cn\r\n  terms-of-service-url: https://twelvet.cn\r\n  contact:\r\n    name: TwelveT\r\n    email: 2471835953@qq.com\r\n    url: https://twelvet.cn\r\n  authorization:\r\n    name: twelvet\r\n    auth-regex: ^.*$\r\n    authorization-scope-list:\r\n      - scope: server\r\n        description: server all\r\n    token-url-list:\r\n      - http://${GATEWAY_HOST:twelvet-gateway}:${GATEWAY_PORT:88}/auth/oauth/token\r\n# 开启Swagger增强模式\r\nknife4j:\r\n  enable: true',
        '1502952fa21f91b4e020935d34438d99', '2025-02-27 14:30:02', '2025-02-27 14:30:03', 'nacos', '0:0:0:0:0:0:0:1',
        'I', 'dev', '', 'formal', '', '{\"src_user\":\"nacos\",\"type\":\"yaml\"}');
INSERT INTO `his_config_info`
VALUES (0, 8, 'twelvet-server-dfs-dev.yml', 'DEFAULT_GROUP', '',
        'spring: \r\n  datasource:\r\n   dynamic:\r\n    hikari:\r\n      # 连接测试查询\r\n      connection-test-query: SELECT 1 FROM DUAL\r\n      # 连接最大存活时间.不等于0且小于30秒，会被重置为默认值30分钟.设置应该比mysql设置的超时时间短\r\n      max-lifetime: 540000\r\n      # 只有空闲连接数大于最大连接数且空闲时间超过该值，才会被释放\r\n      idle-timeout: 500000\r\n      # 最小空闲连接，默认值10，小于0或大于maximum-pool-size，都会重置为maximum-pool-size\r\n      minimum-idle: 10\r\n      # 最大连接数，小于等于0会被重置为默认值10；大于零小于1会被重置为minimum-idle的值\r\n      maximum-pool-size: 12\r\n      # 连接超时时间:毫秒，小于250毫秒，否则被重置为默认值30秒\r\n      connection-timeout: 60000\r\n    # 设置默认的数据源或者数据源组,默认值即为master\r\n    primary: master\r\n    datasource:\r\n      # 主库数据源\r\n      master:\r\n        driver-class-name: com.mysql.cj.jdbc.Driver\r\n        url: jdbc:mysql://twelvet-mysql:3306/twelvet?allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8\r\n        username: root\r\n        password: 123456\r\n      # 从库数据源\r\n      # slave:\r\n      # username:\r\n      # password:\r\n      # url:\r\n      # driver-class-name:\r\n    # 开启seata代理，开启后默认每个数据源都代理,分布式事务必须开启,否则关闭\r\n    seata: false\r\n\r\n# 通用OSS S3配置\r\noss:\r\n  # 开启ossTemplte（默认开启）\r\n  enable: true\r\n  accessKey: twelvet\r\n  secretKey: twelvet\r\n  region: us-east-2\r\n  bucketName: twelvet-static\r\n  endpoint: s3.us-east-2.amazonaws.com\r\n  # 自带controller\r\n  http:\r\n    # 是否开启（默认关闭）\r\n    enable: false\r\n    prefix: /s3\r\n\r\nswagger:\r\n  title: TwelveT Swagger API\r\n  version: 2.7.0\r\n  description: DFS服务\r\n  license: Powered By TwelveT\r\n  licenseUrl: https://twelvet.cn\r\n  terms-of-service-url: https://twelvet.cn\r\n  contact:\r\n    name: TwelveT\r\n    email: 2471835953@qq.com\r\n    url: https://twelvet.cn\r\n  authorization:\r\n    name: password\r\n    token-url-list:\r\n      - http://${GATEWAY_HOST:localhost}:${GATEWAY_PORT:88}/auth/oauth/token\r\n# 开启Swagger增强模式\r\nknife4j:\r\n  enable: true\r\n  ',
        '4327ab008d3e427b10ba9bdb40237261', '2025-02-27 14:30:18', '2025-02-27 14:30:18', 'nacos', '0:0:0:0:0:0:0:1',
        'I', 'dev', '', 'formal', '', '{\"src_user\":\"nacos\",\"type\":\"yaml\",\"c_desc\":\"分布式文件系统\"}');
INSERT INTO `his_config_info`
VALUES (0, 9, 'twelvet-server-gen-dev.yml', 'DEFAULT_GROUP', '',
        '# Spring\r\nspring: \r\n  datasource:\r\n    type: com.zaxxer.hikari.HikariDataSource\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://twelvet-mysql:3306/twelvet_gen?allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8\r\n    username: root\r\n    password: 123456\r\n    dynamic:\r\n      hikari:\r\n        # 连接测试查询\r\n        connection-test-query: SELECT 1 FROM DUAL\r\n        # 连接最大存活时间.不等于0且小于30秒，会被重置为默认值30分钟.设置应该比mysql设置的超时时间短\r\n        max-lifetime: 540000\r\n        # 只有空闲连接数大于最大连接数且空闲时间超过该值，才会被释放\r\n        idle-timeout: 500000\r\n        # 最小空闲连接，默认值10，小于0或大于maximum-pool-size，都会重置为maximum-pool-size\r\n        minimum-idle: 10\r\n        # 最大连接数，小于等于0会被重置为默认值10；大于零小于1会被重置为minimum-idle的值\r\n        maximum-pool-size: 12\r\n        # 连接超时时间:毫秒，小于250毫秒，否则被重置为默认值30秒\r\n        connection-timeout: 60000\r\n        # 设置默认的数据源或者数据源组,默认值即为master\r\n      primary: master\r\n      datasource:\r\n        # 主库数据源\r\n        master:\r\n          driver-class-name: com.mysql.cj.jdbc.Driver\r\n          url: jdbc:mysql://twelvet-mysql:3306/twelvet_gen?allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8\r\n          username: root\r\n          password: 123456\r\n        # 从库数据源\r\n        # slave:\r\n        # username:\r\n        # password:\r\n        # url:\r\n        # driver-class-name:\r\n      # 开启seata代理，开启后默认每个数据源都代理,分布式事务必须开启,否则关闭\r\n      seata: false\r\n\r\n# 代码生成\r\ngen: \r\n  # 作者\r\n  author: TwelveT\r\n  # 默认生成包路径 system 需改成自己的模块名称 如 system monitor tool\r\n  packageName: com.twelvet.server.system\r\n  # 自动去除表前缀，默认是false\r\n  autoRemovePre: false\r\n  # 表前缀（生成类名不会包含表前缀，多个用逗号分隔）\r\n  tablePrefix: sys_\r\n\r\nswagger:\r\n  title: TwelveT Swagger API\r\n  version: 2.7.0\r\n  description: CRUD服务\r\n  license: Powered By TwelveT\r\n  licenseUrl: https://twelvet.cn\r\n  terms-of-service-url: https://twelvet.cn\r\n  contact:\r\n    name: TwelveT\r\n    email: 2471835953@qq.com\r\n    url: https://twelvet.cn\r\n  authorization:\r\n    name: password\r\n    token-url-list:\r\n      - http://${GATEWAY_HOST:localhost}:${GATEWAY_PORT:88}/auth/oauth/token\r\n# 开启Swagger增强模式\r\nknife4j:\r\n  enable: true',
        '1332e5e902e649bf07a127ee8d32ce57', '2025-02-27 14:30:37', '2025-02-27 14:30:38', 'nacos', '0:0:0:0:0:0:0:1',
        'I', 'dev', '', 'formal', '', '{\"src_user\":\"nacos\",\"type\":\"yaml\",\"c_desc\":\"代码生成器\"}');
INSERT INTO `his_config_info`
VALUES (0, 10, 'twelvet-server-ai-dev.yml', 'DEFAULT_GROUP', '',
        '# Spring\r\nspring: \r\n  ai:\r\n    # alibaba AI\r\n    dashscope:\r\n      # https://bailian.console.aliyun.com/?apiKey=1#/api-key\r\n      api-key: key\r\n    vectorstore:\r\n      pgvector:\r\n        # 指定表名称\r\n        table-name: ai_vector\r\n        # 最近邻搜索索引类型\r\n        index-type: HNSW\r\n        # 搜索距离类型\r\n        distance-type: COSINE_DISTANCE\r\n        # 嵌入维度\r\n        dimensions: 1536\r\n        # 不存在表屎尝试初始化\r\n        initialize-schema: true\r\n  datasource:\r\n    dynamic:\r\n      hikari:\r\n        # 连接测试查询\r\n        connection-test-query: SELECT 1\r\n        # 连接最大存活时间.不等于0且小于30秒，会被重置为默认值30分钟.设置应该比mysql设置的超时时间短\r\n        max-lifetime: 540000\r\n        # 只有空闲连接数大于最大连接数且空闲时间超过该值，才会被释放\r\n        idle-timeout: 500000\r\n        # 最小空闲连接，默认值10，小于0或大于maximum-pool-size，都会重置为maximum-pool-size\r\n        minimum-idle: 10\r\n        # 最大连接数，小于等于0会被重置为默认值10；大于零小于1会被重置为minimum-idle的值\r\n        maximum-pool-size: 12\r\n        # 连接超时时间:毫秒，小于250毫秒，否则被重置为默认值30秒\r\n        connection-timeout: 60000\r\n        # 设置默认的数据源或者数据源组,默认值即为master\r\n      primary: vector\r\n      datasource:\r\n        # 主库数据源\r\n        master:\r\n          driver-class-name: com.mysql.cj.jdbc.Driver\r\n          url: jdbc:mysql://twelvet-mysql:3306/twelvet_ai?allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8\r\n          username: root\r\n          password: 123456\r\n        # 向量postgresql数据源\r\n        vector:\r\n          driver-class-name: org.postgresql.Driver\r\n          username: postgres\r\n          password: postgres\r\n          url: jdbc:postgresql://twelvet-pgsql:5432/twelvet_ai\r\n      # 开启seata代理，开启后默认每个数据源都代理,分布式事务必须开启,否则关闭\r\n      seata: false\r\n\r\nswagger:\r\n  title: TwelveT Swagger API\r\n  version: 2.7.0\r\n  description: ai服务\r\n  license: Powered By TwelveT\r\n  licenseUrl: https://twelvet.cn\r\n  terms-of-service-url: https://twelvet.cn\r\n  contact:\r\n    name: TwelveT\r\n    email: 2471835953@qq.com\r\n    url: https://twelvet.cn\r\n  authorization:\r\n    name: password\r\n    token-url-list:\r\n      - http://${GATEWAY_HOST:localhost}:${GATEWAY_PORT:88}/auth/oauth/token\r\n# 开启Swagger增强模式\r\nknife4j:\r\n  enable: true',
        '35057710e16990d788e1ef6dd6f2f881', '2025-02-27 14:30:51', '2025-02-27 14:30:52', 'nacos', '0:0:0:0:0:0:0:1',
        'I', 'dev', '', 'formal', '', '{\"src_user\":\"nacos\",\"type\":\"yaml\"}');

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`
(
    `role`     varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT 'role',
    `resource` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'resource',
    `action`   varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci   NOT NULL COMMENT 'action',
    UNIQUE INDEX `uk_role_permission` (`role`, `resource`, `action`) USING BTREE
) ENGINE = MyISAM
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permissions
-- ----------------------------

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`
(
    `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'username',
    `role`     varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'role',
    UNIQUE INDEX `idx_user_role` (`username`, `role`) USING BTREE
) ENGINE = MyISAM
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles`
VALUES ('nacos', 'ROLE_ADMIN');

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS `tenant_capacity`;
CREATE TABLE `tenant_capacity`
(
    `id`                bigint(20) UNSIGNED                              NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id`         varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
    `quota`             int(10) UNSIGNED                                 NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
    `usage`             int(10) UNSIGNED                                 NOT NULL DEFAULT 0 COMMENT '使用量',
    `max_size`          int(10) UNSIGNED                                 NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
    `max_aggr_count`    int(10) UNSIGNED                                 NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数',
    `max_aggr_size`     int(10) UNSIGNED                                 NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
    `max_history_count` int(10) UNSIGNED                                 NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
    `gmt_create`        datetime                                         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`      datetime                                         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_tenant_id` (`tenant_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_bin COMMENT = '租户容量信息表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tenant_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `tenant_info`;
CREATE TABLE `tenant_info`
(
    `id`            bigint(20)                                       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `kp`            varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'kp',
    `tenant_id`     varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
    `tenant_name`   varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_name',
    `tenant_desc`   varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tenant_desc',
    `create_source` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin  NULL DEFAULT NULL COMMENT 'create_source',
    `gmt_create`    bigint(20)                                       NOT NULL COMMENT '创建时间',
    `gmt_modified`  bigint(20)                                       NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_tenant_info_kptenantid` (`kp`, `tenant_id`) USING BTREE,
    INDEX `idx_tenant_id` (`tenant_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8
  COLLATE = utf8_bin COMMENT = 'tenant_info'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tenant_info
-- ----------------------------
INSERT INTO `tenant_info`
VALUES (1, '1', 'dev', 'dev', 'dev', 'nacos', 1740637667539, 1740637667539);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`
(
    `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT 'username',
    `password` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'password',
    `enabled`  tinyint(1)                                              NOT NULL COMMENT 'enabled',
    PRIMARY KEY (`username`) USING BTREE
) ENGINE = MyISAM
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users`
VALUES ('nacos', '$2a$10$z5mam8ZqfokkFp00EmT10.Ylk0ifSg..HwW.jb9ca1Gfk438OIuCe', 1);

SET FOREIGN_KEY_CHECKS = 1;
