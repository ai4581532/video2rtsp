package com.charley.rtsp.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

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

    @Value("${ffserver.home:../ffserver}")
    public String ffserverHome;
    @Value("${ffserver.host:127.0.0.1}")
    public String ffserverHost;

    private static final String CMD_START = "./server_cmd.sh start %s %s";
    private static final String CMD_STOP = "./server_cmd.sh stop %s %s";
    private static final String CMD_DURATION = "./server_cmd.sh duration %s";

    private static final String CMD_CHECK_PORT = " lsof -i:%s";
    private static final String CMD_PID = "ps -ef|grep ffserver | grep -v grep |grep %s|cut -c 9-15";

    public String start(String port, String file){
        if (StringUtils.isEmpty(file) || StringUtils.isEmpty(port)) {
            LOGGER.error("[Start] >>> param port or file  is null");
            return "param port or file  is null";
        }
        String cmd = String.format(CMD_START, port, file);
        LOGGER.info("[Start] >>> cmd:[{}]", cmd);
        String res = ShellUtil.runCmd(cmd, new File(ffserverHome));
        LOGGER.info("[Start] >>> return:[{}]",res);
        return res;
    }

    public String stop(String port, String file){
        if (StringUtils.isEmpty(file) || StringUtils.isEmpty(port)) {
            LOGGER.error("[Stop] >>> param port or file  is null");
            return "param port or file  is null";
        }
        String cmd = String.format(CMD_STOP, port, file);
        LOGGER.info("[Stop] >>> cmd:[{}]", cmd);
        String res = ShellUtil.runCmd(cmd, new File(ffserverHome));
        LOGGER.info("[Stop] >>> return:[{}]",res);
        return res;
    }

    public String checkPort(String port){
        if (StringUtils.isEmpty(port)) {
            LOGGER.error("[Check] >>> param port is null");
            return "param port is null";
        }
        String cmd = String.format(CMD_CHECK_PORT, port);
        LOGGER.info("[Check] >>> cmd:[{}]", cmd);
        String checkValue =  ShellUtil.runCmd(cmd, new File(ffserverHome));
        LOGGER.info("[Check] >>> return:[{}]", checkValue);
        if(StringUtils.isEmpty(checkValue)){
            return null;
        }else{
            if(checkValue.contains("ffserver")){
                String pidCmd = String.format(CMD_PID, port);
                String pid = ShellUtil.runCmd(pidCmd, new File(ffserverHome));
                LOGGER.info("[Check] >>> get pid cmd:[{}]", pidCmd);
                ShellUtil.runCmd("kill -9 "+ pid, new File(ffserverHome));
                return null;
            }else{
                return "port is used,"+ cmd +" return {"+checkValue+"}";
            }
        }
    }

    public int getDuration(String file){
        if (StringUtils.isEmpty(file)) {
            LOGGER.error("[Duration] >>> param file is null");
            return 0;
        }
        String cmd = String.format(CMD_DURATION, file);
        LOGGER.info("[Duration] >>> cmd:[{}]", cmd);
        String time = ShellUtil.runCmd(cmd, new File(ffserverHome));
        LOGGER.info("[Duration] >>> return:[{}]", time);
        return TimeUtil.timeConvert(time);
    }
}
