#!/bin/bash

if [  ! -d backup ] ;then
   echo `mkdir backup`
fi

#这里修改文件名称
oldFile='test-service.jar'

#开始备份
time=$(date +%Y%m%d-%H%M%S)
targetFileName=$oldFile$time
cp $oldFile backup/$targetFileName
echo "backup finish： $targetFileName"