[中文](https://github.com/twelvet-projects/twelvet/blob/master/README_ZH.md) | [English](https://github.com/twelvet-projects/twelvet/blob/master/README.md)

# 🚀twelvet

# JDK >= 17

[![AUR](https://img.shields.io/github/license/twelvet-projects/twelvet)](https://github.com/twelvet-projects/twelvet/blob/master/LICENSE)
[![](https://img.shields.io/badge/Author-TwelveT-orange.svg)](https://twelvet.cn)
[![](https://img.shields.io/badge/version-2.7.5-success)](https://github.com/twelvet-projects/twelvet)
[![GitHub stars](https://img.shields.io/github/stars/twelvet-projects/twelvet.svg?style=social&label=Stars)](https://github.com/twelvet-projects/twelvet/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/twelvet-projects/twelvet.svg?style=social&label=Fork)](https://github.com/twelvet-projects/twelvet/network/members)
[![star](https://gitee.com/twelvet/twelvet/badge/star.svg?theme=white)](https://gitee.com/twelvet/twelvet/stargazers)
[![fork](https://gitee.com/twelvet/twelvet/badge/fork.svg?theme=white)](https://gitee.com/twelvet/twelvet/members)

一款基于Spring Cloud Alibaba的权限管理系统，集成市面上流行库，可以作用为快速开发的一个框架使用

一套以微服务架构的脚手架,使用Spring Cloud Alibaba系列进行架构,学习并了解它将能快速掌握微服务核心基础。
此项目是为了减少业务代码的重复轮子,它具有一个系统该有的通用性核心业务代码,无论是微服务还是单体,都是通用的业务
但更多的,是为了学习微服务的理念以及开发 您可以使用它进行网站管理后台，网站会员中心，CMS，CRM，OA等待系统的开发,
当然,不仅仅是一些小系统,我们可以生产更多的服务模块,不断完善项目。

系统初心是为了能够更快地完成业务的需求，带来更好的体验、更多的时间。它将会用于孵化一些实用的功能点。
我们希望它们是轻量级，可移植性高的功能插件。

后端源码：https://github.com/twelvet-projects/twelvet

前端源码：https://github.com/twelvet-s/twelvet-ui

技术文档：https://doc.twelvet.cn/

官方博客：https://twelvet.cn

## 🍎 分支说明

| 分支                   | 说明                                                                 | 额外说明            |
|----------------------|--------------------------------------------------------------------|-----------------|
| master               | java17 + springboot 3.x + springcloud 2022 + spring cloud alibaba  | 主要版本            |
| jdk8                 | java8 + springboot 2.7.x + springcloud 2021 + spring cloud alibaba | jdk8            |
| spring-cloud-tencent | java17 + springboot 3.x + springcloud 2022 + spring cloud tencent  | 示范分支，不支持兼容过多新特性 |

## 项目结构

~~~
com.twelvet     
├── twelvet-ui              // 前端框架 [80]
├── twelvet-gateway         // 网关模块 [88]
├── twelvet-nacos           // nacos [8848]
├── twelvet-auth            // 认证中心 [8888]
├── twelvet-api             // 接口模块
│       └── twelvet-api-system                             // 系统接口
│       └── twelvet-api-dfs                                // DFS接口
│       └── twelvet-api-job                                // 定时任务接口
│       └── twelvet-api-ai                                  // AI接口
├── twelvet-framework       // 核心模块
│       └── twelvet-framework-core                         // 核心模块
│       └── twelvet-framework-log                          // 日志记录
│       └── twelvet-framework-datascope                    // 数据权限
│       └── twelvet-framework-jdbc                         // jdbc
│       └── twelvet-framework-swagger                      // swagger文档
│       └── twelvet-framework-redis                        // 缓存服务
│       └── twelvet-framework-security                     // 安全模块
│       └── twelvet-framework-utils                        // 工具模块
├── twelvet-server         // 业务模块
│       └── twelvet-server-system                          // 系统模块 [8081]
│       └── twelvet-server-job                             // 定时任务 [8082]
│       └── twelvet-server-dfs                             // DFS服务 [8083]
│       └── twelvet-server-gen                             // 代码生成 [8084]
│       └── twelvet-server-ai                               // AI模块 [8085]
├── twelvet-visual        // 图形化管理模块
|       └── twelvet-visual-sentinel                        // sentinel [8101]
│       └── twelvet-visual-monitor                         // 监控中心 [8102]
├──pom.xml                // 公共依赖
~~~

## 内置功能

1. 用户管理：用户是系统操作者，该功能主要完成系统用户配置。
2. 部门管理：配置系统组织机构（公司、部门、小组），树结构展现支持数据权限。
3. 岗位管理：配置系统用户所属担任职务。
4. 菜单管理：配置系统菜单，操作权限，按钮权限标识等。
5. 角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。
6. 字典管理：对系统中经常使用的一些较为固定的数据进行维护。
7. 参数管理：对系统动态配置常用参数。
8. 异步：登录日志/系统操作日志/系统登录日志记记录和查询。
9. 定时任务：在线（添加、修改、删除）任务调度包含执行结果日志。
10. 代码生成：一键生成CRUD前后端代码，为业务开发提供更快的速度。
11. 服务监控：监视当前系统CPU、内存、磁盘、堆栈等相关信息。
12. 连接池监视：监视当前系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈。
13. 分布式文件储存。
14. Swagger网关聚合文档。
15. Sentinel限流中心。
16. Nacos注册 + 配置中心。
17. RAG 知识库

## 演示图

<table>
    <tr>
        <td><img src="https://static.twelvet.cn/twelvet/1.jpg"/></td>
        <td><img src="https://static.twelvet.cn/twelvet/2.jpg"/></td>
    </tr>
    <tr>
        <td><img src="https://static.twelvet.cn/twelvet/3.jpg"/></td>
        <td><img src="https://static.twelvet.cn/twelvet/4.jpg"/></td>
    </tr>
    <tr>
        <td><img src="https://static.twelvet.cn/twelvet/5.jpg"/></td>
        <td><img src="https://static.twelvet.cn/twelvet/6.jpg"/></td>
    </tr>
    <tr>
        <td><img src="https://static.twelvet.cn/twelvet/7.jpg"/></td>
        <td><img src="https://static.twelvet.cn/twelvet/8.jpg"/></td>
    </tr>
</table>

## 在线体验

- admin/123456

演示地址：[https://cloud.twelvet.cn](https://cloud.twelvet.cn)

## 架构图

<img src="https://static.twelvet.cn/twelvet/framework.png"/>

## ✈️✈️✈️快速启动微服务

内存 > 16
需要自行安装maven、docker、docker-compose、node、yarn

```shell
# mvn twelvet
cd ./twelvet && mvn clean && mvn install
# mvn twelvet-auth
cd ../twelvet-auth && mvn clean && mvn install
# mvn twelvet-gateway
cd ../twelvet-gateway && mvn clean && mvn install
# mvn twelvet-server-system
cd ../twelvet-server/twelvet-server-system && mvn clean && mvn install
# 进入脚本目录
cd ../../docker
# 可执行权限
chmod 751 deploy.sh
# 执行启动（按需执行参数，[init|port|base|server|stop|rm]）
# 初始化
./deploy.sh init
# 基础服务
./deploy.sh base
# 启动twelvet
./deploy.sh server
# 启动UI
./deploy.sh nginx
```

## 开源共建

### 开源协议

twelvet 开源软件遵循 [Apache 2.0 协议](https://www.apache.org/licenses/LICENSE-2.0.html)。
允许商业使用，但务必保留类作者、Copyright 信息。

### 其他说明

1. 欢迎提交 [PR](https://github.com/twelvet-projects/twelvet/pulls)，注意对应提交对应分支
   代码规范 [spring-javaformat](https://github.com/spring-io/spring-javaformat)

   <details>
    <summary>代码规范说明</summary>

    1. 由于 <a href="https://github.com/spring-io/spring-javaformat" target="_blank">spring-javaformat</a>
       强制所有代码按照指定格式排版，未按此要求提交的代码将不能通过合并（打包）
    2. 如果使用 IntelliJ IDEA
       开发，请安装自动格式化软件 <a href="https://repo1.maven.org/maven2/io/spring/javaformat/spring-javaformat-intellij-idea-plugin/" target="_blank">
       spring-javaformat-intellij-idea-plugin</a>
    3. 其他开发工具，请参考 <a href="https://github.com/spring-io/spring-javaformat" target="_blank">
       spring-javaformat</a>
       说明，或`提交代码前`在项目根目录运行下列命令（需要开发者电脑支持`mvn`命令）进行代码格式化
       ```
       mvn spring-javaformat:apply
       ```
   </details>

2. 欢迎提交 [issue](https://github.com/twelvet-projects/twelvet/issues)，请写清楚遇到问题的原因、开发环境、复显步骤。

## 🤝鸣谢

感谢jetbrains提供的许可证[![jetbrains](https://cloud.twelvet.cn/jetbrains.png)](https://www.jetbrains.com?from=https://github.com/twelvet-projects/twelvet)
