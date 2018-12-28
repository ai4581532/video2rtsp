package com.charley.rtsp.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author wchongxiang_vendor
 * @version 1.0
 * @ClassName ProcessStreamHandler
 * @Description: TODO
 * @date 2018/12/27 17:30
 **/
public class ProcessStreamHandler extends Thread {

    InputStream is;
    String type;

    public ProcessStreamHandler(InputStream is, String type) {
        this.is = is;
        this.type = type;
    }

    @Override
    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line ;
            while ((line = br.readLine()) != null){
                System.out.println(type + ">>>" + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
