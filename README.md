# video2rtsp(ffserver版)
本地视频或在线视频转rtsp流服务，极简api，方便使用。

本服务底层依赖ffmpeg和ffserver,原理为使用java执行命令行启动ffserver实例，提供视频转rtsp流服务。
使用springboot2.1等spring技术栈。合理的打包配置，内置相关依赖，提供启动脚本，部署极简。


### 注意事项
1. 该服务依赖ffserver
2. 项目包中已集成ffserver
3. 该服务的实现原理是为每个视频文件动态开启一个转rtsp流的ffserver服务实例，当多个视频同时转流时，将会开启多个实例并占用多个不同的端口
4. 拉流完毕后请调用相关api关闭对应的ffserver实例，以免占用过多的系统资源
5. 目前只支持linux部署。
 
### 配置说明

以下application.properties中配置需根据具体环境修改

    ffserver.home=/www/video2rtsp-1.0.0/ffserver  //ffserver代码目录,已集成在项目打包文件中
    ffserver.host=35.221.88.168 //服务器host
   
### 启动说明

- 项目打包为tar包，解压后目录结构如下：

```
    --- bin  
        --- start.sh     //启动脚本
        --- stop.sh      //停止脚本
    --- boot
        --- xxxx.jar     //可执行jar包
    --- lib              //依赖jar包目录
    --- conf             //配置文件目录
    --- ffserver         //ffserver 相关脚本和代码文件目录
    --- README.md
```

执行start.sh脚本即可启动视频转rtsp流服务
 

### API说明
           
**1.启动视频转流ffserver服务 Get /ffserver/start?file={file} HTTP/1.1**

接口说明：

    该接口只是启动一个ffserver实例，并没有开始推流或者转流,该接口会返回拉流地址和视频时长等信息,当有客户端拉流时才会开始转流
 
参数说明：
 
| 参数名| 类型 | 是否必须 |参数说明|
| ------ | ------ | ------ |------|
| file | string | 是 |转流视频文件地址或者url|
  
 请求示例：
     
     http://localhost:4000/ffserver/start?file=/www/720p.mp4
 
 返回示例：    
     
     {"data":{"duration":1000,"file":"/www/720p.mp4","streamId":"40001","streamUrl":"rtsp://35.221.88.168:40001/40001"},"message":"operation success","success":true}
   
 返回说明：
       
       duration：视频时长
       file:视频文件
       streamId: 流id
       streamUrl: 拉流url
       
     
**2.停止视频转流ffserver服务 Get /ffserver/stop?streamId={streamId} HTTP/1.1**
 
参数说明：
 
| 参数名| 类型 | 是否必须 |参数说明|
| ------ | ------ | ------ |------|
| streamId | string | 是 |转流id|
 
请求示例：
 
     http://localhost:4000/ffserver/stop?streamId=40001
 
返回示例： 
 
     {"message":"operation success","success":true}
     
     
**3.获取视频转流服务信息列表 Get /ffserver/list HTTP/1.1**

接口说明：可以通过此接口查询哪些视频文件已启动转流服务和相关信息


请求示例：
 
     http://localhost:4000/ffserver/list
 
返回示例： 
 
     {"data":[{"duration":1000,"file":"/www/720p.mp4","streamId":"40001","streamUrl":"rtsp://35.221.88.168:40001/40001"},{"duration":1000,"file":"https://video.pearvideo.com/mp4/adshort/20181101/cont-1467566-13162638_adpkg-ad_hd.mp4","streamId":"40002","streamUrl":"rtsp://35.221.88.168:40002/40002"}],"message":"operation success","success":true}     
 
**4.获取视频转流服务信息 Get /ffserver/query?streamId={streamId} HTTP/1.1**

接口说明：可以通过此接口查询该视频文件是否已启动转流服务和相关信息

参数说明：
 
| 参数名| 类型 | 是否必须 |参数说明|
| ------ | ------ | ------ |------|
| streamId | string | 是 |转流id|
 
请求示例：
 
     http://localhost:4000/ffserver/query?streamId=40001
 
返回示例： 
 
     {"data":{"duration":1000,"file":"/www/720p.mp4","streamId":"40001","streamUrl":"rtsp://35.221.88.168:40001/40001"},"message":"operation success","success":true}
 
 
    