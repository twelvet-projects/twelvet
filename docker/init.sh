#!/bin/sh

# 复制项目的文件到对应docker路径，便于一键生成镜像。
usage() {
	echo "Usage: sh init.sh"
	exit 1
}


# copy sql
echo "begin copy sql "
cp ../data/sql/ywelvet.sql ./mysql/db
cp ../data/sql/twelvet_nacos.sql ./mysql/db

mvn clean && mvn install

# copy jar
echo "begin copy twelvet-gateway "
cp ../twelvet-gateway/target/twelvet-gateway.jar ./twelvet/gateway/jar

echo "begin copy twelvet-auth "
cp ../twelvet-auth/target/twelvet-auth.jar ./twelvet/auth/jar

echo "begin copy twelvet-server-system "
cp ../twelvet-server/twelvet-server-system/target/twelvet-server-system.jar ./twelvet/server/system/jar
