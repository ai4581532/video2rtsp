package com.charley.rtsp.entity;

import java.io.Serializable;

/**
 * @author wchongxiang_vendor
 * @version 1.0
 * @ClassName TaskModel
 * @Description: TODO
 * @date 2018/10/31 15:58
 **/
public class TaskModel implements Serializable {

    private String streamId;

    private String streamUrl;

    private String file;

    private int duration;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }
}
