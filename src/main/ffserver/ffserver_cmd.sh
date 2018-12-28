#!/bin/bash

#######################################################
#功能：启动ffserver实例，将MP4视频转换为rtsp流                           #
#将该文件放到ffserver下                                #
#请先创建一下目录：                                    #
#ffserver/pid                                         #
#######################################################

pid_file=./pid/$2

if [ $1x == "start"x ]; then
    conf_param="RTSPPort $2\nRTSPBindAddress 0.0.0.0\n\n<Stream $2>\n    File "$3"\n    Format rtp\n</Stream>"
    echo -e $conf_param > $pid_file
    nohup ./ffserver -f $pid_file > /dev/null &
    echo "success"
elif [ $1x == "stop"x ]; then
    pid=`ps axu | grep "ffserver" | grep $2 | grep -v "grep" | awk '{print $2}'`
    kill -s 9 $pid
    rm -rf $pid_file
    echo "success"
elif [ $1x == "duration"x ]; then
    video_time=`./ffmpeg -i $3 2>&1 | grep 'Duration' | cut -d ' ' -f 4 | sed s/,//`
    echo $video_time
else
    echo "usage: ./ffserver_cmd.sh start port videoPath "
    echo "usage: ./ffserver_cmd.sh stop  port videoPath "
fi