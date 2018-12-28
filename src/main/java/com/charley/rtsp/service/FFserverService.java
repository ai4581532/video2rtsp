package com.charley.rtsp.service;

import com.charley.rtsp.dao.TaskDao;
import com.charley.rtsp.entity.TaskModel;
import com.charley.rtsp.util.ServerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author wchongxiang_vendor
 * @version 1.0
 * @ClassName FFserverService
 * @Description: TODO
 * @date 2018/12/28 15:40
 **/
@Service
public class FFserverService {

    @Autowired
    private TaskDao taskDao;
    @Autowired
    private ServerUtil serverUtil;

    /**
     * 获取系统端口
     *
     * @return
     */
    public String getPort() {
        String port = taskDao.takePort();
        if (null != port) {
            String checkValue = serverUtil.checkPort(port);
            if (null != checkValue) {
                return this.getPort();
            } else {
                return port;
            }
        }
        return null;
    }

    /**
     * 获取任务列表
     *
     * @return
     */
    public List<TaskModel> list() {
        Collection<TaskModel> collection = taskDao.getAll();
        List<TaskModel> taskList = new ArrayList<>(collection);
        return taskList;
    }

    /**
     * 获取任务详情
     *
     * @param streamId
     * @return
     */
    public TaskModel query(String streamId) {
        TaskModel taskModel = taskDao.get(streamId);
        return taskModel;
    }

    /**
     * 开启流
     *
     * @param streamId
     * @param file
     * @return
     */
    public TaskModel start(String streamId, String file) {
        TaskModel taskModel = new TaskModel();
        int time = serverUtil.getDuration(file);
        if (time == 0) {
            //回收port资源
            taskDao.putPort(streamId);
            return null;
        }
        String res = serverUtil.start(streamId, file);
        if (!"success".equals(res)) {
            //回收port资源
            taskDao.putPort(streamId);
            return null;
        } else {
            taskModel.setStreamId(streamId);
            taskModel.setStreamUrl("rtsp://" + serverUtil.ffserverHost + ":" + streamId + "/" + streamId);
            taskModel.setFile(file);
            taskModel.setDuration(time);
            taskDao.add(taskModel);
        }
        return taskModel;
    }

    /**
     * 停止流
     *
     * @param streamId
     * @param file
     * @return
     */
    public boolean stop(String streamId, String file) {
        String res = serverUtil.stop(streamId, file);
        if (!"success".equals(res)) {
            return false;
        } else {
            taskDao.remove(streamId);
            taskDao.putPort(streamId);
        }
        return true;
    }

}
