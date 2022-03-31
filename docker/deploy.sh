#!/bin/sh

# 使用说明，用来提示输入参数
usage() {
  echo "Usage: sh 执行脚本.sh [init|port|start|stop|rm]"
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

# 启动程序模块
start() {
  docker-compose up -d twelvet-mysql twelvet-redis

  echo "正在等待Mysql初始化数据60秒，请等待..."
  sleep 60

  docker-compose up -d twelvet-nacos twelvet-gateway
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
"start")
  start
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
