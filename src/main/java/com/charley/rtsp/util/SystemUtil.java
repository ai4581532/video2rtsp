package com.charley.rtsp.util;
/**
 * @ClassName SystemUtil
 * @Description: TODO
 * @author wchongxiang_vendor
 * @date 2018/12/28 10:51
 * @version 1.0
 **/
public class SystemUtil {

    private static String OS = System.getProperty("os.name").toLowerCase();

    public static boolean isLinux(){
        return OS.indexOf("linux")>=0;
    }

    public static boolean isMac(){
        return OS.indexOf("mac")>=0&&OS.indexOf("os")>0;
    }

    public static boolean isWindows() {
        return OS.indexOf("windows") >= 0;
    }

}
