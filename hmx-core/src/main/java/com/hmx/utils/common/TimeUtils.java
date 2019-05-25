package com.hmx.utils.common;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2019/5/26.
 */
public class TimeUtils {
    public static Date getNeedTime(int hour, int minute, int second, int addDay, int ...args){
        Calendar calendar = Calendar.getInstance();
        if(addDay != 0){
            calendar.add(Calendar.DATE,addDay);
        }
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,second);
        if(args.length==1){
            calendar.set(Calendar.MILLISECOND,args[0]);
        }
        return calendar.getTime();
    }

    public static Date getPastWeak(){
        Calendar c = Calendar.getInstance();

        //过去七天
        c.setTime(new Date());
        c.add(Calendar.DATE, - 7);
        Date d = c.getTime();
        return d;
    }

    public static Date getPastMonth(){
        Calendar c = Calendar.getInstance();
        //过去一月
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        //String mon = format.format(m);
        System.out.println("过去一个月："+m);
        return m;
    }

    public static Date getPastYear(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, -1);
        Date y = c.getTime();
        return y;
    }
    public static void main(String[] arg0){

        System.out.println(new Date());
        System.out.println(getPastMonth());

        Date start = getNeedTime(0,0,0,7);
        Date end = getNeedTime(23,59,59,0);
        Date now = new Date();
//        if(now.getTime() >= start.getTime() && now.getTime() <= end.getTime()){
//            System.out.println("当前时间在中间");
//        }
        System.out.println(start);
        System.out.println(end);
    }
}
