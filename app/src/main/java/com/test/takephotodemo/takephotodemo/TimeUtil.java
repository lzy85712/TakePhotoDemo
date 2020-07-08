package com.test.takephotodemo.takephotodemo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间转化控件
 */
public class TimeUtil {
    public static String formatLongToTimerStr(long date) {
        long day = date / (60 * 60 * 24);
        long hour = (date / (60 * 60) - day * 24);
        long min = ((date / 60) - day * 24 * 60 - hour * 60);
        long s = (date - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String strtime = "剩余：" + day + "天" + hour + "小时" + min + "分" + s + "秒";
        return strtime;
    }

    public static long dateDiff(String endTime) {
        // 按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
        long diff;
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = sd.format(curDate);
        try {
            // 获得两个时间的毫秒时间差异
            diff = sd.parse(endTime).getTime()
                    - sd.parse(str).getTime();
            return diff;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
