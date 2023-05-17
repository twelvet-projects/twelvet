[中文](https://github.com/twelvet-s/twelvet/blob/master/README.md) | [English](https://github.com/twelvet-s/twelvet/blob/master/README_EN.md)
# twelvet

[![AUR](https://img.shields.io/github/license/twelvet-s/twelvet)](https://github.com/twelvet-s/twelvet/blob/master/LICENSE)
[![](https://img.shields.io/badge/Author-TwelveT-orange.svg)](https://twelvet.cn)
[![](https://img.shields.io/badge/version-2.7.5-success)](https://gitee.com/twelvet/twelvet)
[![GitHub stars](https://img.shields.io/github/stars/twelvet-s/twelvet.svg?style=social&label=Stars)](https://github.com/twelvet-s/twelvet/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/twelvet-s/twelvet.svg?style=social&label=Fork)](https://github.com/twelvet-s/twelvet/network/members)
[![star](https://gitee.com/twelvet/twelvet/badge/star.svg?theme=white)](https://gitee.com/twelvet/twelvet/stargazers)
[![fork](https://gitee.com/twelvet/twelvet/badge/fork.svg?theme=white)](https://gitee.com/twelvet/twelvet/members)

A permission management system based on Spring Cloud Alibaba that integrates popular libraries on the market and can act as a framework for rapid development.

A scaffolding framework based on microservices architecture, using the Spring Cloud Alibaba series for architecture. Learning and understanding it will enable you to quickly grasp the core basics of microservices. This project aims to reduce duplication of business code and has a common core business code that is universal for both microservices and monoliths.

But more importantly, it is for learning the concept of microservices and development. You can use it for website management backstage, website member center, CMS, CRM, OA and other systems development. Of course, not just small systems, we can produce more service modules and continuously improve the project.

The initial intention of the system is to be able to quickly meet the business needs, to bring better experience and more time. It will be used to incubate some practical functional points. We hope that they are lightweight, highly portable functional plugins.

At the same time, we hope that more developers can quickly obtain better solutions in it, and try to reduce our learning costs as much as possible. Therefore, we should invest more time in other more meaningful things. We know the importance of knowledge, but we don't just want a single "knowledge". Go feel / care for more brilliance, no matter people, things, or objects, they will become your best inspiration.

Backend source code: https://github.com/twelvet-s/twelvet

Frontend source code: https://github.com/twelvet-s/twelvet-ui

Technical documents: https://twelvet.cn/docs/

Official blog: https://twelvet.cn

## System Module

~~~
com.twelvet     
├── twelvet-ui              // Front-end Framework [80]
├── twelvet-gateway         // Gateway module [88]
├── twelvet-nacos           // nacos [8848]
├── twelvet-auth            // Authentication Center [8888]
├── twelvet-api             // Interface module
│       └── twelvet-api-system                             // System interface
│       └── twelvet-api-dfs                                // DFS interface
│       └── twelvet-api-job                                // Scheduled task interface
├── twelvet-framework       // Core module
│       └── twelvet-framework-core                         // Core module
│       └── twelvet-framework-log                          // Logging
│       └── twelvet-framework-datascope                    // Data permission
│       └── twelvet-framework-jdbc                         // jdbc
│       └── twelvet-framework-swagger                      // swagger document
│       └── twelvet-framework-redis                        // Cache service
│       └── twelvet-framework-security                     // Security module
│       └── twelvet-framework-utils                        // Tool module
├── twelvet-server         // Business module
│       └── twelvet-server-system                          // System module [8081]
│       └── twelvet-server-job                             // Scheduled task [8082]
│       └── twelvet-server-dfs                             // DFS service [8083]
│       └── twelvet-server-gen                             // Code generation [8084]
├── twelvet-visual        // Graphic Management Module
|       └── twelvet-visual-sentinel                        // sentinel [8101]
│       └── twelvet-visual-monitor                         // Monitoring center [8102]
├──pom.xml                // Public dependencies
~~~

## Built-in Functions

1. User management: Users are operators of the system, and this function mainly completes the configuration of system users.
2. Department management: configure the system organization structure (company, department, group), tree structure display supports data permissions.
3. Post management: Configure the positions held by system users.
4. Menu Management: Configure system menus, operation permissions, button permission identifiers, etc.
5. Role Management: Role menu permission allocation, set role data range permission division by organization.
6. Dictionary management: Maintain some relatively fixed data commonly used in the system.
7. Parameter management: Dynamic configuration of commonly used parameters in the system.
8. Asynchronous: Login log / system operation log / system login log recording and inquiry.
9. Scheduled task: Online (add, modify, delete) task scheduling includes execution result logs.
10. Code generation: One-click generation of CRUD front-end and back-end code, providing faster speed for business development.
11. Service monitoring: Monitor current system CPU, memory, disk, stack and other related information.
12. Connection pool monitoring: Monitor the status of the current system database connection pool, and analyze SQL to find out the system performance bottleneck.
13. Distributed file storage.
14. Swagger gateway aggregation document.
15. Sentinel flow restriction center.
16. Nacos registration + configuration center.


## Demonstration

<table>
    <tr>
        <td><img src="https://twelvet.cn/assets/images/twelvet/1.png"/></td>
        <td><img src="https://twelvet.cn/assets/images/twelvet/2.png"/></td>
    </tr>
    <tr>
        <td><img src="https://twelvet.cn/assets/images/twelvet/3.png"/></td>
        <td><img src="https://twelvet.cn/assets/images/twelvet/4.png"/></td>
    </tr>
    <tr>
        <td><img src="https://twelvet.cn/assets/images/twelvet/5.png"/></td>
        <td><img src="https://twelvet.cn/assets/images/twelvet/6.png"/></td>
    </tr>
</table>

## Online Experience

- admin/123456

Demonstration address：[https://cloud.twelvet.cn](https://cloud.twelvet.cn)

## Architecture Diagram
<img src="https://twelvet.cn/assets/images/twelvet/map.png"/>

## One-click Docker startup for Linux (minimal service startup)
Memory > 16
Maven, Docker, Docker-compose, Node, and Yarn need to be installed manually.
```shell
# mvn
mvn clean && mvn install
# Enter the script directory
cd ./docker
# Set executable permissions
chmod 751 deploy.sh
# Perform startup (execute parameters as needed, [init | port | base | server | stop | rm])
# Initialization
./deploy.sh init
# Basic services
./deploy.sh base
# Start Twelvet
./deploy.sh server
# Start UI
./deploy.sh nginx
```
