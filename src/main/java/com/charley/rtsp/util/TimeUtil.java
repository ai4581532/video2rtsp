package com.charley.rtsp.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName TimeUtil
 * @Description: TODO
 * @author wchongxiang_vendor
 * @date 2018/12/28 15:30
 * @version 1.0
 **/
public class TimeUtil {

    /**
     * @Desc:时分秒毫秒，格式为hh:mi:ss.ms
     * @param time
     * @return 毫秒数
     */
    public static int timeConvert(String time) {
        if (StringUtils.isEmpty(time)) {
            return 0;
        }
        try {
            // 查找字符串中的字符 ":" 从开始处开始查找,返回所在字符的索引
            int index1 = time.indexOf(":");
            // 查找字符串中的字符 ":" 从第 index+1 个开始查找,返回所在字符的索引
            int index2 = time.indexOf(":", index1 + 1);
            int index3 = time.indexOf(".", index2 + 1);
            int hh = Integer.parseInt(time.substring(0, index1));
            int mi = Integer.parseInt(time.substring(index1 + 1, index2));
            int ss = Integer.parseInt(time.substring(index2 + 1, index3));
            int ms = Integer.parseInt(time.substring(index3 + 1));
            return (hh * 60 * 60 + mi * 60 + ss) * 1000 + ms;
        } catch (NumberFormatException ne) {
            return 0;
        }
    }
}
