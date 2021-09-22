#!/bin/sh

# 使用说明，用来提示输入参数
usage() {
	echo "Usage: sh 执行脚本.sh [port|base|service|stop|rm]"
	exit 1
}

# 开启所需端口
port(){
	firewall-cmd --add-port=80/tcp --permanent
	firewall-cmd --add-port=88/tcp --permanent
	firewall-cmd --add-port=8848/tcp --permanent
	firewall-cmd --add-port=9848/tcp --permanent
	firewall-cmd --add-port=9849/tcp --permanent
	firewall-cmd --add-port=6379/tcp --permanent
	firewall-cmd --add-port=3306/tcp --permanent
	firewall-cmd --add-port=8888/tcp --permanent
	firewall-cmd --add-port=8081/tcp --permanent
	firewall-cmd --add-port=8082/tcp --permanent
	firewall-cmd --add-port=8083/tcp --permanent
	firewall-cmd --add-port=8084/tcp --permanent
	firewall-cmd --add-port=8100/tcp --permanent
	service firewalld restart
}

# 启动基础环境（必须）
base(){
	docker-compose up -d twelvet-mysql twelvet-redis twelvet-nacos twelvet-nginx
}

# 启动程序模块（必须）
service(){
	docker-compose up -d twelvet-gateway twelvet-auth twelvet-service-system
}

# 关闭所有环境/模块
stop(){
	docker-compose stop
}

# 删除所有环境/模块
rm(){
	docker-compose rm
}

# 根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$1" in
"port")
	port
;;
"base")
	base
;;
"service")
	service
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
