package com.charley.rtsp.controller;

import com.charley.rtsp.dao.TaskDao;
import com.charley.rtsp.service.FFserverService;
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

    @RequestMapping("/test")
    public String test(){
        ShellUtil.runCmd("ping www.jd.com");
        return "ok";
    }

    public String list(){
        return "ok";
    }

    public String query(){
        return "ok";
    }

    public String start(){
        return "ok";
    }

    public String stop(){
        return "ok";
    }

}
