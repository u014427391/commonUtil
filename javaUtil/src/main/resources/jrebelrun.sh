#!/bin/bash
jps -l | grep appr-law-items-service.jar | xargs echo 'kill '
jps -l | grep appr-law-items-service.jar | awk  '{print $1}'  | xargs kill
sleep 3
nohup java -jar -Xms512m -Xmx512m -Xbootclasspath/p:/tmp/rebelboot.jar -javaagent:jrebel.jar -Drebel.remoting_plugin=true -Drebel.remoting_port=7071 -Dserver.port=8081 appr-law-items-service.jar >8081.logs &

