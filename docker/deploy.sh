#!/bin/sh

# 使用说明，用来提示输入参数
usage() {
  echo "Usage: sh 执行脚本.sh [init|port|base|server|nginx|stop|rm]"
  exit 1
}

# 初始化
init() {
  # copy sql
  echo "begin copy sql "
  cp ../data/sql/twelvet.sql ./mysql/db
  cp ../data/sql/twelvet_nacos.sql ./mysql/db

  # copy jar
  echo "begin copy twelvet-gateway "
  cp ../twelvet-gateway/target/twelvet-gateway.jar ./twelvet/gateway/jar

  echo "begin copy twelvet-auth "
  cp ../twelvet-auth/target/twelvet-auth.jar ./twelvet/auth/jar

  echo "begin copy twelvet-server-system "
  cp ../twelvet-server/twelvet-server-system/target/twelvet-server-system.jar ./twelvet/server/system/jar
}

# 开启所需端口
port() {
  firewall-cmd --add-port=8080/tcp --permanent
  firewall-cmd --add-port=8848/tcp --permanent
  firewall-cmd --add-port=6379/tcp --permanent
  firewall-cmd --add-port=3306/tcp --permanent
  service firewalld restart
}

# 启动基础环境（必须）
base() {
  docker-compose up -d twelvet-mysql twelvet-redis
}

# 启动程序模块（必须）
server() {
  docker-compose up -d twelvet-nacos twelvet-gateway twelvet-auth twelvet-server-system
}

# 启动nginx（必须）
nginx() {
  if [ ! -d "./twelvet-ui" ];then
    rm -rf ./twelvet-ui

    # 获取前端UI
    git clone https://gitee.com/twelvet/twelvet-ui twelvet-ui

    # 执行打包
    cd ./twelvet-ui && yarn install && yarn run build

    # 移动打包文件
    \cp -rf ./dist/* ../nginx/html/
  else
    echo "前端已初始化"
  fi
  
  docker-compose up -d twelvet-nginx
}

# 关闭所有环境/模块
stop() {
  docker-compose stop
}

# 删除所有环境/模块
rm() {
  docker-compose rm
}

# 根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$1" in
"init")
  init
  ;;
"port")
  port
  ;;
"base")
  base
  ;;
"server")
  server
  ;;
"nginx")
  nginx
  ;;
"stop")
  stop
  ;;
"rm")
  rm
  ;;
*)
  usage
  ;;
esac

