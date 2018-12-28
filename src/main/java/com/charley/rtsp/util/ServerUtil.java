package com.charley.rtsp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @ClassName ServerUtil
 * @Description: TODO
 * @author wchongxiang_vendor
 * @date 2018/12/28 15:29
 * @version 1.0
 **/
@Component
public class ServerUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerUtil.class);

    public String start(){

        return "ok";
    }

    public String stop(){

        return "ok";
    }

    public String checkPort(String port){

        return "ok";
    }

    public String getDuration(){

        return null;
    }
}
