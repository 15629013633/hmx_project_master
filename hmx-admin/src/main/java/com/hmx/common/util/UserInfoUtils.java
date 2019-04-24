package com.hmx.common.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class UserInfoUtils {
   public static UserDetails getLoginInfo(){
       return (UserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
   }

   //字符串转化时间
   public static Date stringToTime(String time,String layout){
       SimpleDateFormat format = new SimpleDateFormat(layout == null || layout.isEmpty() ? "yyyy-MM-dd HH:mm:ss":layout);
       try {
           return format.parse(time);
       } catch (ParseException e) {
           e.printStackTrace();
       }
       return null;
   }

   //时间转化为字符串
   public static String timeToString(Date time,String layout){
       SimpleDateFormat format = new SimpleDateFormat(layout == null || layout.isEmpty()? "yyyy-MM-dd HH:mm:ss":layout);
       return format.format(time);
   }
}
