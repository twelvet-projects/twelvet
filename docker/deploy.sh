#!/bin/sh

# 使用说明，用来提示输入参数
usage() {
  echo "Usage: sh 执行脚本.sh [port|base|server|stop|rm]"
  exit 1
}

# 检查是否存在网络组
check_network() {

  # 遍历网络组
  for twelvet_network in $(docker network list); do
    # 存在网络组跳出
    if "$twelvet_network".NAME -eq "twelvet-network"; then
      return
    fi
  done

  docker network create twelvet-network
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
  docker-compose up -d twelvet-mysql twelvet-redis twelvet-nacos
}

# 启动程序模块（必须）
server() {
  docker-compose up -d twelvet-gateway twelvet-auth twelvet-server-system
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
"port")
  port
  ;;
"base")
  check_network
  base
  ;;
"server")
  check_network
  server
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
