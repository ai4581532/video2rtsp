package com.charley.rtsp.util;

import com.charley.rtsp.handler.ProcessStreamHandler;

import java.io.File;
import java.io.IOException;

/**
 * @ClassName ShellUtil
 * @Description: shell命令执行util类
 * @author wchongxiang_vendor
 * @date 2018/12/27 15:12
 * @version 1.0
 **/
public class ShellUtil {

    public static boolean runCmd(String cmd){
        return runCmd(cmd, null);
    }

    public static boolean runCmd(String cmd, File dir){
        Process process = null;
        try {
            System.out.println("cmd start");
            String[] command = { "/bin/sh", "-c", cmd };
            process = Runtime.getRuntime().exec(command, null, dir);

            new ProcessStreamHandler(process.getInputStream(), "INFO").start();
            new ProcessStreamHandler(process.getErrorStream(),"ERR").start();

            int value = process.waitFor();
            if(value == 0) {
                System.out.println("complete");
                return true;
            }else{
                System.out.println("failure");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return true;
    }

}
