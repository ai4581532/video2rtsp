package com.charley.rtsp.controller;

import com.charley.rtsp.util.ShellUtil;
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

    @RequestMapping("/test")
    public String test(){
        ShellUtil.runCmd("ping www.jd.com");
        return "ok";
    }

}
