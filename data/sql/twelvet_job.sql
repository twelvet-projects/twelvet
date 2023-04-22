USE `twelvet`;

DROP TABLE IF EXISTS QRTZ_FIRED_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_PAUSED_TRIGGER_GRPS;
DROP TABLE IF EXISTS QRTZ_SCHEDULER_STATE;
DROP TABLE IF EXISTS QRTZ_LOCKS;
DROP TABLE IF EXISTS QRTZ_SIMPLE_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_SIMPROP_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_CRON_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_BLOB_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_JOB_DETAILS;
DROP TABLE IF EXISTS QRTZ_CALENDARS;
DROP TABLE IF EXISTS `sys_job`;

-- ----------------------------
-- 1、存储每一个已配置的 jobDetail 的详细信息
-- ----------------------------
create table QRTZ_JOB_DETAILS
(
    sched_name        varchar(120) not null comment '调度名称',
    job_name          varchar(200) not null comment '任务名称',
    job_group         varchar(200) not null comment '任务组名',
    description       varchar(250) null comment '相关介绍',
    job_class_name    varchar(250) not null comment '执行任务类名称',
    is_durable        varchar(1)   not null comment '是否持久化',
    is_nonconcurrent  varchar(1)   not null comment '是否并发',
    is_update_data    varchar(1)   not null comment '是否更新数据',
    requests_recovery varchar(1)   not null comment '是否接受恢复执行',
    job_data          blob null comment '存放持久化job对象',
    primary key (sched_name, job_name, job_group)
) engine=innodb CHARACTER SET = utf8 COLLATE = utf8_general_ci comment = '任务详细信息表';

INSERT INTO `qrtz_job_details` VALUES ('TWTScheduler', 'TASK_CLASS_NAME1', 'DEFAULT', NULL, 'com.twelvet.server.job.util.QuartzJobExecution', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000F5441534B5F50524F5045525449455373720021636F6D2E7477656C7665742E6170692E6A6F622E646F6D61696E2E5379734A6F62000000000000000102000849000A636F6E63757272656E7449000D6D697366697265506F6C6963794900067374617475734C000E63726F6E45787072657373696F6E7400124C6A6176612F6C616E672F537472696E673B4C000C696E766F6B6554617267657471007E00094C00086A6F6247726F757071007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C00076A6F624E616D6571007E000978720038636F6D2E7477656C7665742E6672616D65776F726B2E636F72652E6170706C69636174696F6E2E646F6D61696E2E42617365456E7469747900000000000000010200094C0009626567696E54696D6571007E00094C0008637265617465427971007E00094C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C0007656E6454696D6571007E00094C0006706172616D7371007E00034C000672656D61726B71007E00094C000B73656172636856616C756571007E00094C0008757064617465427971007E00094C000A75706461746554696D6571007E000C78707074000561646D696E7372000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001622CDE29E078707074000070707000000001000000030000000074000E30202A2F3135202A202A202A203F7400137477745461736B2E7477744E6F506172616D7374000744454641554C547372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000000000001740018E7B3BBE7BB9FE9BB98E8AEA4EFBC88E697A0E58F82EFBC897800);
INSERT INTO `qrtz_job_details` VALUES ('TWTScheduler', 'TASK_CLASS_NAME106', 'DEFAULT', NULL, 'com.twelvet.server.job.util.QuartzDisallowConcurrentExecution', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000F5441534B5F50524F5045525449455373720021636F6D2E7477656C7665742E6170692E6A6F622E646F6D61696E2E5379734A6F62000000000000000102000849000A636F6E63757272656E7449000D6D697366697265506F6C6963794900067374617475734C000E63726F6E45787072657373696F6E7400124C6A6176612F6C616E672F537472696E673B4C000C696E766F6B6554617267657471007E00094C00086A6F6247726F757071007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C00076A6F624E616D6571007E000978720038636F6D2E7477656C7665742E6672616D65776F726B2E636F72652E6170706C69636174696F6E2E646F6D61696E2E42617365456E7469747900000000000000010200094C0009626567696E54696D6571007E00094C0008637265617465427971007E00094C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C0007656E6454696D6571007E00094C0006706172616D7371007E00034C000672656D61726B71007E00094C000B73656172636856616C756571007E00094C0008757064617465427971007E00094C000A75706461746554696D6571007E000C78707074000561646D696E7372000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001764D4CF5D078707074000070707000000000000000010000000074000D302F35202A202A202A202A203F7400187477745461736B2E747774506172616D732827747774272974000744454641554C547372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000000000006A740018E7B3BBE7BB9FE9BB98E8AEA4EFBC88E69C89E58F82EFBC897800);

-- ----------------------------
-- 2、 存储已配置的 Trigger 的信息
-- ----------------------------
create table QRTZ_TRIGGERS
(
    sched_name     varchar(120) not null comment '调度名称',
    trigger_name   varchar(200) not null comment '触发器的名字',
    trigger_group  varchar(200) not null comment '触发器所属组的名字',
    job_name       varchar(200) not null comment 'qrtz_job_details表job_name的外键',
    job_group      varchar(200) not null comment 'qrtz_job_details表job_group的外键',
    description    varchar(250) null comment '相关介绍',
    next_fire_time bigint(13) null comment '上一次触发时间（毫秒）',
    prev_fire_time bigint(13) null comment '下一次触发时间（默认为-1表示不触发）',
    priority       integer null comment '优先级',
    trigger_state  varchar(16)  not null comment '触发器状态',
    trigger_type   varchar(8)   not null comment '触发器的类型',
    start_time     bigint(13) not null comment '开始时间',
    end_time       bigint(13) null comment '结束时间',
    calendar_name  varchar(200) null comment '日程表名称',
    misfire_instr  smallint(2) null comment '补偿执行的策略',
    job_data       blob null comment '存放持久化job对象',
    primary key (sched_name, trigger_name, trigger_group),
    foreign key (sched_name, job_name, job_group) references QRTZ_JOB_DETAILS (sched_name, job_name, job_group)
) engine=innodb CHARACTER SET = utf8 COLLATE = utf8_general_ci comment = '触发器详细信息表';

-- ----------------------------
-- 3、 存储简单的 Trigger，包括重复次数，间隔，以及已触发的次数
-- ----------------------------
create table QRTZ_SIMPLE_TRIGGERS
(
    sched_name      varchar(120) not null comment '调度名称',
    trigger_name    varchar(200) not null comment 'qrtz_triggers表trigger_name的外键',
    trigger_group   varchar(200) not null comment 'qrtz_triggers表trigger_group的外键',
    repeat_count    bigint(7) not null comment '重复的次数统计',
    repeat_interval bigint(12) not null comment '重复的间隔时间',
    times_triggered bigint(10) not null comment '已经触发的次数',
    primary key (sched_name, trigger_name, trigger_group),
    foreign key (sched_name, trigger_name, trigger_group) references QRTZ_TRIGGERS (sched_name, trigger_name, trigger_group)
) engine=innodb CHARACTER SET = utf8 COLLATE = utf8_general_ci comment = '简单触发器的信息表';

-- ----------------------------
-- 4、 存储 Cron Trigger，包括 Cron 表达式和时区信息
-- ----------------------------
create table QRTZ_CRON_TRIGGERS
(
    sched_name      varchar(120) not null comment '调度名称',
    trigger_name    varchar(200) not null comment 'qrtz_triggers表trigger_name的外键',
    trigger_group   varchar(200) not null comment 'qrtz_triggers表trigger_group的外键',
    cron_expression varchar(200) not null comment 'cron表达式',
    time_zone_id    varchar(80) comment '时区',
    primary key (sched_name, trigger_name, trigger_group),
    foreign key (sched_name, trigger_name, trigger_group) references QRTZ_TRIGGERS (sched_name, trigger_name, trigger_group)
) engine=innodb CHARACTER SET = utf8 COLLATE = utf8_general_ci comment = 'Cron类型的触发器表';

-- ----------------------------
-- 5、 Trigger 作为 Blob 类型存储(用于 Quartz 用户用 JDBC 创建他们自己定制的 Trigger 类型，JobStore 并不知道如何存储实例的时候)
-- ----------------------------
create table QRTZ_BLOB_TRIGGERS
(
    sched_name    varchar(120) not null comment '调度名称',
    trigger_name  varchar(200) not null comment 'qrtz_triggers表trigger_name的外键',
    trigger_group varchar(200) not null comment 'qrtz_triggers表trigger_group的外键',
    blob_data     blob null comment '存放持久化Trigger对象',
    primary key (sched_name, trigger_name, trigger_group),
    foreign key (sched_name, trigger_name, trigger_group) references QRTZ_TRIGGERS (sched_name, trigger_name, trigger_group)
) engine=innodb CHARACTER SET = utf8 COLLATE = utf8_general_ci comment = 'Blob类型的触发器表';

-- ----------------------------
-- 6、 以 Blob 类型存储存放日历信息， quartz可配置一个日历来指定一个时间范围
-- ----------------------------
create table QRTZ_CALENDARS
(
    sched_name    varchar(120) not null comment '调度名称',
    calendar_name varchar(200) not null comment '日历名称',
    calendar      blob         not null comment '存放持久化calendar对象',
    primary key (sched_name, calendar_name)
) engine=innodb CHARACTER SET = utf8 COLLATE = utf8_general_ci comment = '日历信息表';

-- ----------------------------
-- 7、 存储已暂停的 Trigger 组的信息
-- ----------------------------
create table QRTZ_PAUSED_TRIGGER_GRPS
(
    sched_name    varchar(120) not null comment '调度名称',
    trigger_group varchar(200) not null comment 'qrtz_triggers表trigger_group的外键',
    primary key (sched_name, trigger_group)
) engine=innodb CHARACTER SET = utf8 COLLATE = utf8_general_ci comment = '暂停的触发器表';

-- ----------------------------
-- 8、 存储与已触发的 Trigger 相关的状态信息，以及相联 Job 的执行信息
-- ----------------------------
create table QRTZ_FIRED_TRIGGERS
(
    sched_name        varchar(120) not null comment '调度名称',
    entry_id          varchar(95)  not null comment '调度器实例id',
    trigger_name      varchar(200) not null comment 'qrtz_triggers表trigger_name的外键',
    trigger_group     varchar(200) not null comment 'qrtz_triggers表trigger_group的外键',
    instance_name     varchar(200) not null comment '调度器实例名',
    fired_time        bigint(13) not null comment '触发的时间',
    sched_time        bigint(13) not null comment '定时器制定的时间',
    priority          integer      not null comment '优先级',
    state             varchar(16)  not null comment '状态',
    job_name          varchar(200) null comment '任务名称',
    job_group         varchar(200) null comment '任务组名',
    is_nonconcurrent  varchar(1) null comment '是否并发',
    requests_recovery varchar(1) null comment '是否接受恢复执行',
    primary key (sched_name, entry_id)
) engine=innodb CHARACTER SET = utf8 COLLATE = utf8_general_ci comment = '已触发的触发器表';

-- ----------------------------
-- 9、 存储少量的有关 Scheduler 的状态信息，假如是用于集群中，可以看到其他的 Scheduler 实例
-- ----------------------------
create table QRTZ_SCHEDULER_STATE
(
    sched_name        varchar(120) not null comment '调度名称',
    instance_name     varchar(200) not null comment '实例名称',
    last_checkin_time bigint(13) not null comment '上次检查时间',
    checkin_interval  bigint(13) not null comment '检查间隔时间',
    primary key (sched_name, instance_name)
) engine=innodb CHARACTER SET = utf8 COLLATE = utf8_general_ci comment = '调度器状态表';

-- ----------------------------
-- 10、 存储程序的悲观锁的信息(假如使用了悲观锁)
-- ----------------------------
create table QRTZ_LOCKS
(
    sched_name varchar(120) not null comment '调度名称',
    lock_name  varchar(40)  not null comment '悲观锁名称',
    primary key (sched_name, lock_name)
) engine=innodb CHARACTER SET = utf8 COLLATE = utf8_general_ci comment = '存储的悲观锁信息表';

-- ----------------------------
-- 11、 Quartz集群实现同步机制的行锁表
-- ----------------------------
create table QRTZ_SIMPROP_TRIGGERS
(
    sched_name    varchar(120) not null comment '调度名称',
    trigger_name  varchar(200) not null comment 'qrtz_triggers表trigger_name的外键',
    trigger_group varchar(200) not null comment 'qrtz_triggers表trigger_group的外键',
    str_prop_1    varchar(512) null comment 'String类型的trigger的第一个参数',
    str_prop_2    varchar(512) null comment 'String类型的trigger的第二个参数',
    str_prop_3    varchar(512) null comment 'String类型的trigger的第三个参数',
    int_prop_1    int null comment 'int类型的trigger的第一个参数',
    int_prop_2    int null comment 'int类型的trigger的第二个参数',
    long_prop_1   bigint null comment 'long类型的trigger的第一个参数',
    long_prop_2   bigint null comment 'long类型的trigger的第二个参数',
    dec_prop_1    numeric(13, 4) null comment 'decimal类型的trigger的第一个参数',
    dec_prop_2    numeric(13, 4) null comment 'decimal类型的trigger的第二个参数',
    bool_prop_1   varchar(1) null comment 'Boolean类型的trigger的第一个参数',
    bool_prop_2   varchar(1) null comment 'Boolean类型的trigger的第二个参数',
    primary key (sched_name, trigger_name, trigger_group),
    foreign key (sched_name, trigger_name, trigger_group) references QRTZ_TRIGGERS (sched_name, trigger_name, trigger_group)
) engine=innodb CHARACTER SET = utf8 COLLATE = utf8_general_ci comment = '同步机制的行锁表';

CREATE TABLE `sys_job`  (
    `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
    `job_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '任务名称',
    `job_group` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
    `invoke_target` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '调用目标字符串',
    `cron_expression` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'cron执行表达式',
    `misfire_policy` tinyint(1) NULL DEFAULT NULL COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
    `concurrent` tinyint(1) NULL DEFAULT NULL COMMENT '是否并发执行（0允许 1禁止）',
    `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1暂停）',
    `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
    `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注信息',
    PRIMARY KEY (`job_id`, `job_name`, `job_group`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 107 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '定时任务调度表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_job
-- ----------------------------
INSERT INTO `sys_job` VALUES (1, '系统默认（无参）', 'DEFAULT', 'twtTask.twtNoParams', '0 */15 * * * ?', 3, 1, '0', 'admin', '2018-03-16 11:33:00', 'admin', '2021-08-06 23:02:47', '');
INSERT INTO `sys_job` VALUES (106, '系统默认（有参）', 'DEFAULT', 'twtTask.twtParams(\'twt\')', '0/5 * * * * ?', 1, 1, '0', 'admin', '2020-12-10 23:37:38', 'admin', '2021-08-06 23:02:45', '');

commit;
