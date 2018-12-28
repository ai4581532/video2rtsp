package com.charley.rtsp.util;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @ClassName FileUtil
 * @Description: TODO
 * @author wchongxiang_vendor
 * @date 2018/12/28 15:51
 * @version 1.0
 **/
public class FileUtil {

    public static boolean fileExist(String urlStr){
        boolean isExist = false;

        String httpFileFlag = "http";
        if(!urlStr.contains(httpFileFlag)){
            File file = new File(urlStr);
            return  file.exists();
        }

        try{
            // 注：urlStr中需将空格替换为%20,否则报505
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection ) url.openConnection();
            int state = conn.getResponseCode();

            if(state == 200){
                isExist  = true;
            }else{
                isExist  = false;
            }

        }catch(Exception e) {
            isExist = false;
        }

        return isExist;
    }

}
