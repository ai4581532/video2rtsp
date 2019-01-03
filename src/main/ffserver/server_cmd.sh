#!/bin/bash

#######################################################
#功能：启动ffserver实例，将MP4视频转换为rtsp流          #
#将该文件放到ffserver下                                #
#请先创建一下目录：                                    #
#ffserver/pid                                         #
#######################################################

pid_file=./pid/$2

if [ $1 == "start" ];then
    conf_param="RTSPPort $2\nRTSPBindAddress 0.0.0.0\n\n<Stream $2>\n    File "$3"\n    Format rtp\n</Stream>"
    echo -e $conf_param > $pid_file
    nohup ./ffserver -f $pid_file > /dev/null &
    echo "success"
elif [ $1 == "stop" ];then
    pid=`ps axu | grep "ffserver" | grep $2 | grep -v "grep" | awk '{print $2}'`
    kill -s 9 $pid
    rm -rf $pid_file
    echo "success"
elif [ $1 == "duration" ];then
    video_time=`./ffmpeg -i $2 2>&1 | grep 'Duration' | cut -d ' ' -f 4 | sed s/,//`
    echo $video_time
elif [$1 == "check" ];then
    server_pid=`ps -ef|grep ffserver | grep -v grep |grep %s|cut -c 9-15`
    if [! -z "$server_pid"];then
        kill -9 $server_pid
    fi
    echo "success"
else
    echo "usage: ./server_cmd.sh start port videoPath "
    echo "usage: ./server_cmd.sh stop  port videoPath "
fi