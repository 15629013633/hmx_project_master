package com.hmx.system;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2019/5/2.
 */
public class Test {
    public static Date dateAdd(int days) {
        // 日期处理模块 (将日期加上某些天或减去天数)返回字符串
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, days); // 日期减 如果不够减会将月变动
        return canlendar.getTime();
    }
    public static void main(String[] args) {
        Date date = Test.dateAdd(-180);
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("50天前日期为：" + sdfd.format(date));
        SimpleDateFormat sdf = new SimpleDateFormat("E");
        System.out.println("为：" + sdf.format(date));
    }
}
