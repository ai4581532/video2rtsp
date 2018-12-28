package com.charley.rtsp.dao;

import com.charley.rtsp.entity.TaskModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

/**
 * @author wchongxiang_vendor
 * @version 1.0
 * @ClassName Task
 * @Description: TODO
 * @date 2018/11/6 18:02
 **/
@Component
public class TaskDao implements ApplicationRunner {

    /**
     * 存放未使用的端口池
     */
    private ConcurrentLinkedQueue<String> portPool;

    /**
     * 存放任务信息的map
     * key为文件路径，value为流相关信息
     */
    private ConcurrentMap<String, TaskModel> map;

    @Value("${ffserver.startPort:40001}")
    public int startPort;
    @Value("${ffserver.endPort:40050}")
    public int endPort;

    public TaskDao() {
        this.map = new ConcurrentHashMap<>(50);
        this.portPool = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //初始化端口池
        for (int i=startPort;i<=endPort;i++) {
            this.portPool.add(String.valueOf(i));
        }
    }

    public String getPortRange(){
        return startPort + "-" + endPort;
    }

    public int putPort(String port){
        if(this.portPool.add(port)){
            return 1;
        }
        return 0 ;
    }

    public String takePort(){
        return this.portPool.poll();
    }

    public TaskModel get(String id) {
        return map.get(id);
    }

    public Collection<TaskModel> getAll() {
        return map.values();
    }

    public int add(TaskModel taskModel) {
        String id = taskModel.getStreamId();
        if (id != null && !map.containsKey(id)) {
            map.put(id, taskModel);
            if(map.get(id)!=null){
                return 1;
            }
        }
        return 0;
    }

    public int remove(String id) {
        if(map.remove(id) != null){
            return 1;
        }
        return 0;
    }

    public int removeAll() {
        int size = map.size();
        try {
            map.clear();
        } catch (Exception e) {
            return 0;
        }
        return size;
    }

    public boolean isHave(String id) {
        return map.containsKey(id);
    }

}
