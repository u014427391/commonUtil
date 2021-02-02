#!/bin/bash
APP_NAME=appr-writ-sign.jar
JAVA_PATH=/u01/jdk1.8.0_11/bin

usage(){
    echo "Usage: sh sys.sh [start|stop|restart|status]"
    exit 1
}

#�������Ƿ�������
is_exist(){
  pid=`ps -ef|grep $APP_NAME|grep -v grep|awk '{print $2}' `
  #��������ڷ���1�����ڷ���0     
  if [ -z "${pid}" ]; then
   return 1
  else
    return 0
  fi
}

#��������
start(){
  is_exist
  if [ $? -eq "0" ]; then
    echo "${APP_NAME} is already running. pid=${pid} ."
  else
    nohup ${JAVA_PATH}/java -jar  -Xms512m -Xmx512m -Xdebug  -Dserver.port=8004 ${APP_NAME} >8004.logs &
    tail -f 8004.logs
    echo "${APP_NAME} start success"
  fi
}

#ֹͣ����
stop(){
  is_exist
  if [ $? -eq "0" ]; then
    kill -9 $pid
    echo "killed $pid"
  else
    echo "${APP_NAME} is not running"
  fi  
}

#�������״̬
status(){
  is_exist
  if [ $? -eq "0" ]; then
    echo "${APP_NAME} is running. Pid is ${pid}"
  else
    echo "${APP_NAME} is NOT running."
  fi
}

#����
restart(){
  stop
  start
}

case "$1" in
  "start")
    start
    ;;
  "stop")
    stop
    ;;
  "status")
    status
    ;;
  "restart")
    restart
    ;;
  *)
    usage
    ;;
esac