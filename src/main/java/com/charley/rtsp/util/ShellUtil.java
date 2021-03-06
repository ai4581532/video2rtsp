package com.charley.rtsp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @ClassName ShellUtil
 * @Description: shell命令执行util类
 * @author wchongxiang_vendor
 * @date 2018/12/27 15:12
 * @version 1.0
 **/
public class ShellUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShellUtil.class);

    public static String runCmd(String cmd){
        return runCmd(cmd, new File("d:/"));
    }

    public static String runCmd(String cmd, File dir){
        Process process = null;
        StringBuilder res = new StringBuilder();
        try {
            //LOGGER.info("cmd start>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            String[] commands = getCommands(cmd);
            if(commands==null){
                LOGGER.error("can't get correct commands");
                return null;
            }
            //LOGGER.info("execute cmd:[{}],dir:[{}]", StringUtils.join(commands," "),dir == null ? "null": dir.toString());
            process = Runtime.getRuntime().exec(commands, null, dir);

            BufferedInputStream in = new BufferedInputStream(process.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null){
                res.append(line);
            }

            br.close();
            in.close();

//            int value = process.waitFor();
//            if(value == 0) {
//                //LOGGER.info("cmd complete<<<<<<<<<<<<<<<<<<<<<<<<<");
//            }else{
//                //LOGGER.info("cmd failure<<<<<<<<<<<<<<<<<<<<<<<<<<");
//                return null;
//            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return res.toString();
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
