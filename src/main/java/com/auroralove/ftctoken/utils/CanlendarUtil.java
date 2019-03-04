package com.auroralove.ftctoken.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 系统日期工具类
 *
 * @author zyu
 * @date 2019/2/24
 */
public class CanlendarUtil {

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static Date getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        return today;
    }

    /**
     * 获取未来 第 past 天的日期
     * @param past
     * @return
     */
    public static Date getFetureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        return today;
    }

    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime 当前时间
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     * @author jqlin
     */
    public static boolean isEffectiveDate(String startDate, String endDate) {
        String format = "HH:mm:ss";
        String nowTime = null;
        Date startTime = null;
        Date endTime = null;
        try {
            nowTime =new SimpleDateFormat(format).format(new Date());
//            startTime = new SimpleDateFormat(format).parse(startDate);
//            endTime = new SimpleDateFormat(format).parse(endDate);

        } catch (Exception e) {
            e.printStackTrace();
        }
//        if (nowTime.getTime() == startTime.getTime()
//                || nowTime.getTime() == endTime.getTime()) {
//            return true;
//        }
        if (startDate.compareTo(nowTime)<0 && endDate.compareTo(nowTime)>0){
            return false;
        }
        return true;
    }
}
