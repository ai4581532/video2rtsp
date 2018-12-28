package com.charley.rtsp.controller;

import com.alibaba.fastjson.JSON;
import com.charley.rtsp.dao.TaskDao;
import com.charley.rtsp.entity.ResultModel;
import com.charley.rtsp.entity.TaskModel;
import com.charley.rtsp.service.FFserverService;
import com.charley.rtsp.util.FileUtil;
import com.charley.rtsp.util.ShellUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @ClassName FFserverController
 * @Description: ffserver 控制器
 * @author wchongxiang_vendor
 * @date 2018/12/27 16:12
 * @version 1.0
 **/
@RestController
@RequestMapping("/ffserver")
public class FFserverController {

    @Autowired
    private FFserverService ffService;
    @Autowired
    private TaskDao taskDao;

    @RequestMapping("/test")
    public String test(){
        ShellUtil.runCmd("ping www.jd.com");
        return "ok";
    }

    @RequestMapping("/list")
    public String list(){
        ResultModel rs = new ResultModel();
        rs.setData(ffService.list());
        return JSON.toJSONString(rs);
    }

    @RequestMapping("/query")
    public String query(String streamId){
        ResultModel rs = new ResultModel();
        rs.setData(ffService.query(streamId));
        return JSON.toJSONString(rs);
    }

    @RequestMapping("/start")
    public String start(String file){
        ResultModel rs = new ResultModel();

        if(file ==null){
            rs.setSuccess(false);
            rs.setMessage("param file is null");
            return JSON.toJSONString(rs);
        }

        //判断文件是否存在
        if (!FileUtil.fileExist(file)) {
            rs.setSuccess(false);
            rs.setMessage("file not exists");
            return JSON.toJSONString(rs);
        }

        //获取端口资源，分配给流服务
        String streamId = ffService.getPort();
        if(null==streamId){
            rs.setSuccess(false);
            rs.setMessage("port resources ["+taskDao.getPortRange()+"] are running out");
            return JSON.toJSONString(rs);
        }

        //启动服务
        TaskModel taskModel = ffService.start(streamId,file);
        if(taskModel==null){
            rs.setSuccess(false);
            rs.setMessage("operation fail");
        }else {
            rs.setData(taskModel);
        }

        return JSON.toJSONString(rs);
    }

    @RequestMapping("/stop")
    public String stop(String streamId){
        ResultModel rs = new ResultModel();

        if(streamId ==null){
            rs.setSuccess(false);
            rs.setMessage("param streamId is null");
            return JSON.toJSONString(rs);
        }

        TaskModel taskModel = taskDao.get(streamId);
        if(null==taskModel){
            rs.setSuccess(false);
            rs.setMessage("this id not have start stream server");
            return JSON.toJSONString(rs);
        }

        boolean flag = ffService.stop(streamId,taskModel.getFile());
        if(!flag){
            rs.setSuccess(false);
            rs.setMessage("operation fail");
        }

        return JSON.toJSONString(rs);
    }

}
