package com.charley.rtsp.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @ClassName ShellUtil
 * @Description: shell命令执行util类
 * @author wchongxiang_vendor
 * @date 2018/12/27 15:12
 * @version 1.0
 **/
public class ShellUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShellUtil.class);

    public static boolean runCmd(String cmd){
        return runCmd(cmd, new File("d:/"));
    }

    public static boolean runCmd(String cmd, File dir){
        Process process = null;
        try {
            LOGGER.info("cmd start>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            String[] commands = getCommands(cmd);
            if(commands==null){
                LOGGER.error("can't get correct commands");
                return false;
            }
            LOGGER.info("execute cmd:[{}],dir:[{}]", StringUtils.join(commands," "),dir == null ? "null": dir.toString());
            process = Runtime.getRuntime().exec(commands, null, dir);

            InputStreamReader isr = new InputStreamReader(process.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null){
                System.out.println(line);
            }

            int value = process.waitFor();
            if(value == 0) {
                LOGGER.info("cmd complete<<<<<<<<<<<<<<<<<<<<<<<<<");
                return true;
            }else{
                LOGGER.info("cmd failure<<<<<<<<<<<<<<<<<<<<<<<<<<");
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

    private static String[] getCommands(String cmd){
        if(SystemUtil.isLinux()){
            return new String[]{"/bin/sh", "-c", cmd };
        }
        if(SystemUtil.isWindows()){
            return new String[]{"cmd.exe", "/c", cmd };
        }
        return null;
    }

    public static void main(String [] args){
        ShellUtil.runCmd("ping www.jd.com");
    }

}
