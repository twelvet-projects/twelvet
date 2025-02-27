[中文](https://github.com/twelvet-projects/twelvet/blob/master/README_ZH.md) | [English](https://github.com/twelvet-projects/twelvet/blob/master/README.md)

# 🚀twelvet

# JDK >= 17

[![AUR](https://img.shields.io/github/license/twelvet-projects/twelvet)](https://github.com/twelvet-projects/twelvet/blob/master/LICENSE)
[![](https://img.shields.io/badge/Author-TwelveT-orange.svg)](https://twelvet.cn)
[![](https://img.shields.io/badge/version-3.2.0-success)](https://github.com/twelvet-projects/twelvet)
[![GitHub stars](https://img.shields.io/github/stars/twelvet-projects/twelvet.svg?style=social&label=Stars)](https://github.com/twelvet-projects/twelvet/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/twelvet-projects/twelvet.svg?style=social&label=Fork)](https://github.com/twelvet-projects/twelvet/network/members)
[![star](https://gitee.com/twelvet/twelvet/badge/star.svg?theme=white)](https://gitee.com/twelvet/twelvet/stargazers)
[![fork](https://gitee.com/twelvet/twelvet/badge/fork.svg?theme=white)](https://gitee.com/twelvet/twelvet/members)

A permission management system based on Spring Cloud Alibaba that integrates popular libraries on the market and can act
as a framework for rapid development.

A scaffolding framework based on microservices architecture, using the Spring Cloud Alibaba series for architecture.
Learning and understanding it will enable you to quickly grasp the core basics of microservices. This project aims to
reduce duplication of business code and has a common core business code that is universal for both microservices and
monoliths.

But more importantly, it is for learning the concept of microservices and development. You can use it for website
management backstage, website member center, CMS, CRM, OA and other systems development. Of course, not just small
systems, we can produce more service modules and continuously improve the project.

The initial intention of the system is to be able to quickly meet the business needs, to bring better experience and
more time. It will be used to incubate some practical functional points. We hope that they are lightweight, highly
portable functional plugins.

Backend source code: https://github.com/twelvet-projects/twelvet

Frontend source code: https://github.com/twelvet-s/twelvet-ui

Technical documents: https://doc.twelvet.cn/

Official blog: https://twelvet.cn

## 🍎 Branch Description

| Branch               | Description                                                        | Additional Description                                                          |
|----------------------|--------------------------------------------------------------------|---------------------------------------------------------------------------------|
| master               | java17 + springboot 3.x + springcloud 2022 + spring cloud alibaba  | master                                                                          |
| jdk8                 | java8 + springboot 2.7.x + springcloud 2021 + spring cloud alibaba | jdk8                                                                            |
| spring-cloud-tencent | java17 + springboot 3.x + springcloud 2022 + spring cloud tencent  | Demonstration branch, does not support compatibility with too many new features |

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
│       └── twelvet-api-ai                                  // AI interface
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
│       └── twelvet-server-ai                               // AI module [8085]
├── twelvet-visual        // Graphic Management Module
|       └── twelvet-visual-sentinel                        // sentinel [8101]
│       └── twelvet-visual-monitor                         // Monitoring center [8102]
├──pom.xml                // Public dependencies
~~~

## Built-in Functions

1. User management: Users are operators of the system, and this function mainly completes the configuration of system
   users.
2. Department management: configure the system organization structure (company, department, group), tree structure
   display supports data permissions.
3. Post management: Configure the positions held by system users.
4. Menu Management: Configure system menus, operation permissions, button permission identifiers, etc.
5. Role Management: Role menu permission allocation, set role data range permission division by organization.
6. Dictionary management: Maintain some relatively fixed data commonly used in the system.
7. Parameter management: Dynamic configuration of commonly used parameters in the system.
8. Asynchronous: Login log / system operation log / system login log recording and inquiry.
9. Scheduled task: Online (add, modify, delete) task scheduling includes execution result logs.
10. Code generation: One-click generation of CRUD front-end and back-end code, providing faster speed for business
    development.
11. Service monitoring: Monitor current system CPU, memory, disk, stack and other related information.
12. Connection pool monitoring: Monitor the status of the current system database connection pool, and analyze SQL to
    find out the system performance bottleneck.
13. Distributed file storage.
14. Swagger gateway aggregation document.
15. Sentinel flow restriction center.
16. Nacos registration + configuration center.
17. RAG knowledge base

## Demonstration

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

## Online Experience

- admin/123456

Demonstration address：[https://cloud.twelvet.cn](https://cloud.twelvet.cn)

## Architecture Diagram

<img src="https://static.twelvet.cn/twelvet/framework.png"/>

## ✈️✈️✈️ Quickly start Microservices

Memory > 16
Maven, Docker, Docker-compose, Node, and Yarn need to be installed manually.

```shell
# mvn twelvet
cd ./twelvet && mvn clean && mvn install -DskipTests
# mvn twelvet-auth
cd ../twelvet-auth && mvn clean && mvn install -DskipTests
# mvn twelvet-gateway
cd ../twelvet-gateway && mvn clean && mvn install -DskipTests
# mvn twelvet-server-system
cd ../twelvet-server/twelvet-server-system && mvn clean && mvn install -DskipTests
# Enter the script directory
cd ../../docker
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

## Open Source Collaboration

### Open Source License

The Twelvet open-source software follows the MIT
License [Apache 2.0 License](https://www.apache.org/licenses/LICENSE-2.0.html)。
Permits commercial use, but requires the preservation of the original author and copyright information.

### Other terms

1. Welcome to contribute [PR](https://github.com/twelvet-projects/twelvet/pulls)，Make sure to submit to the
   corresponding branch
   Code conventions [spring-javaformat](https://github.com/spring-io/spring-javaformat)

   <details>
    <summary>Code style guidelines</summary>

    1. Due to <a href="https://github.com/spring-io/spring-javaformat" target="_blank">spring-javaformat</a>
       the requirement of enforcing a specific code formatting, any code that is not submitted according to this
       requirement will not be able to be merged (packaged)
    2. If you are using IntelliJ IDEA for development, please install the auto-formatting
       plugin. <a href="https://repo1.maven.org/maven2/io/spring/javaformat/spring-javaformat-intellij-idea-plugin/" target="_blank">
       spring-javaformat-intellij-idea-plugin</a>
    3. For other development tools, please refer to their respective documentation or community for instructions on
       configuring automatic code formatting. <a href="https://github.com/spring-io/spring-javaformat" target="_blank">
       spring-javaformat</a>
       Before committing code, please run the following command in the project root directory (requires developer's
       computer to support the mvn command) to format the code.
       ```
       mvn spring-javaformat:apply
       ```
   </details>

2. Welcome to contribute [issue](https://github.com/twelvet-projects/twelvet/issues)，Please provide clear explanations
   of the issue, development environment, and steps to reproduce.

## 🤝Thank You

Thanks to jetbrains for the
license[![jetbrains](https://cloud.twelvet.cn/jetbrains.png)](https://www.jetbrains.com?from=https://github.com/twelvet-projects/twelvet)
