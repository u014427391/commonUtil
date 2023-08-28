package com.example.common.util.datetime;


import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {

    public static final String df1 = "yyyy-MM-dd HH:mm:ss";
    public static final String df2 = "yyyy-MM-dd";
    public static final String df3 = "HH:mm:ss";
    public static final String MORNING = "morning";
    public static final String AFTERNOON = "afternoon";
    public static final String NIGHT = "night";


    public static final String MORNING_START = "00:00:00";
    public static final String MORNING_END = "14:00:00";
    public static final String AFTERNOON_START = "14:00:01";
    public static final String AFTERNOON_END = "19:00:00";
    public static final String NIGHT_START = "19:00:01";
    public static final String NIGHT_END = "23:59:59";

    /**
     * 判断某个时间是否在一个时间段内
     * @Date 2023/07/18 11:09
     * @Param [nowTime, beginTime, endTime]
     * @return boolean
     */
    public static Boolean betweenTimeFormatOfTime(Date date , String format , String startTimeStr , String endTimeStr) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        Date beginTime = null;
        Date endTime = null;
        try {
            date = df.parse(df.format(date));
            beginTime = df.parse(startTimeStr);
            endTime = df.parse(endTimeStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date.getTime() >= beginTime.getTime() && date.getTime() <= endTime.getTime();
    }

    /**
     * 判断某个时间是否在一个时间段内
     * @Date 2023/07/18 11:09
     * @Param [nowTime, beginTime, endTime]
     * @return boolean
     */
    public static boolean betweenTime(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断时间段，上午/下午/晚上
     * @Date 2023/07/18 14:40
     * @return java.lang.String
     */
    public static String checkTime() {
        String timeFlag = StrUtil.EMPTY;
        Boolean isMorning = betweenTimeFormatOfTime(new Date(), df3, MORNING_START, MORNING_END);
        if (isMorning) timeFlag = MORNING;
        Boolean isAfternoon = betweenTimeFormatOfTime(new Date(), df3, AFTERNOON_START, AFTERNOON_END);
        if (isAfternoon) timeFlag = AFTERNOON;
        Boolean isNight = betweenTimeFormatOfTime(new Date(), df3, NIGHT_START, NIGHT_END);
        if (isNight) timeFlag = NIGHT;
        return timeFlag;
    }

    /**
     * 计算当前时间距离某个时间的秒数
     * @Date 2023/07/18 14:21
     * @Param [dataCharSequence]
     * @return int
     */
    public static Integer getSecondsNextTime(String dataCharSequence) {
        return Convert.toInt(DateUtil.between(new Date(), DateUtil.parse(dataCharSequence), DateUnit.SECOND ));
    }

    /**
     * 获取date对应星期
     * @return
     */
    public static int getWeekNum(Date date){
        Calendar cal = Calendar.getInstance();cal.setTime(date);
        int weekNum = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (weekNum==0) weekNum=7;
        return weekNum;
    }


}
