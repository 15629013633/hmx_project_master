package com.hmx.system;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2019/5/2.
 */
public class Test {
    public static void main(String[] args ) {
        // TODO Auto-generated method stub
        String str = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        System.out.println(System.currentTimeMillis());

    }

    /**
     * 获取8位不重复随机码（取当前时间戳转化为十六进制）
     * @author ShelWee
     * @param time
     * @return
     */
    public static String toHex(long time){
        return Integer.toHexString((int)time);
    }
}
