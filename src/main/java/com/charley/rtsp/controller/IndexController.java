package com.charley.rtsp.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName IndexController
 * @Description: TODO
 * @author wchongxiang_vendor
 * @date 2018/12/27 15:14
 * @version 1.0
 **/
@RestController
@RequestMapping("/")
public class IndexController {

    @RequestMapping("/index")
    public String index(){
        return "welcome video2rtsp word!";
    }
}
