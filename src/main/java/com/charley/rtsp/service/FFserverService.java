package com.charley.rtsp.service;

import com.charley.rtsp.dao.TaskDao;
import com.charley.rtsp.util.ServerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName FFserverService
 * @Description: TODO
 * @author wchongxiang_vendor
 * @date 2018/12/28 15:40
 * @version 1.0
 **/
@Service
public class FFserverService {

    @Autowired
    private TaskDao taskDao;
    @Autowired
    private ServerUtil serverUtil;

    public String getPort(){
        String port = taskDao.takePort();
        if(null!= port){
            String checkValue = serverUtil.checkPort(port);
            if(null!=checkValue){
                return this.getPort();
            }else{
                return port;
            }
        }
        return null;
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
