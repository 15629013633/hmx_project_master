package com.hmx.system.test;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 *
 * 参考  https://blog.csdn.net/qq_38108719/article/details/84567042
 *
 * Created by Administrator on 2019/7/17.
 */
public class ALiYunSignUtils {
    public static String Format = "json";                                        //返回值的类型，支持JSON与XML
    public static String Version = "2016-11-01";                                //API版本号
    public static String Signature = "";                                        //签名结果字符串
    public static String SignatureMethod = "HMAC-SHA1";                         //签名方式
//    public static String SignatureNonce = UUID.randomUUID().toString();         //唯一随机数，用于防止网络重复攻击
    public static String SignatureNonce = System.currentTimeMillis()+"";         //唯一随机数，用于防止网络重复攻击
    public static String SignatureVersion = "1.0";                              //签名算法版本
    public static String AccessKeyId = "LTAIaeZo5lNZpDBd";                      //填写自己的key
    public static String AccessKeySecret = "HSB7GdPp998ziaGiVK6rgbrHhHSwAl";    //填写自己的secret
    public static String Timestamp = getTimestamp();                            //请求的时间戳

    private static final String ISO8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static String getTimestamp() {
        //1、取得本地时间：
        final java.util.Calendar cal = java.util.Calendar.getInstance();
        //System.out.println(cal.getTime());
        //2、取得时间偏移量：
        final int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
        //System.out.println(zoneOffset);
        //3、取得夏令时差：
        final int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
        //System.out.println(dstOffset);
        //4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        SimpleDateFormat df = new SimpleDateFormat(ISO8601_DATE_FORMAT);
        return df.format(cal.getTime());
    }
    public static String getSignature(String Action, String DomainName) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        Map map = new HashMap();
        map.put("Format", Format);
        map.put("Version", Version);
        map.put("SignatureMethod", SignatureMethod);
        map.put("SignatureNonce", SignatureNonce);
        map.put("SignatureVersion", SignatureVersion);
        map.put("AccessKeyId", AccessKeyId);
        map.put("Timestamp", Timestamp);
        map.put("Action", Action);
        map.put("DomainName", DomainName);
        //1 构造规范化的请求字符串CQString
        //1.1参数排序
        String[] sortedKeys = (String[]) map.keySet().toArray(new String[]{});
        Arrays.sort(sortedKeys);
        //1.2参数编码
        StringBuilder CQString = new StringBuilder();
        for (String key : sortedKeys) {
            // 这里注意对key和value进行编码
            CQString.append("&").append(percentEncode(key))
                    .append("=").append(percentEncode((String) map.get(key)));
        }
        //2 将上述的CQString构造成待签名的字符串
        String StringToSign = "GET" + "&" +
                percentEncode("/") + "&" +
                percentEncode(CQString.toString().substring(1));
        System.out.println("StringToSign:" + StringToSign);

        //3 计算待签名字符串StringToSign的HMAC值
        String key = AccessKeySecret + "&";
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
        //System.out.println(key.getBytes());
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            mac.init(secretKey);
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        byte[] HMAC=mac.doFinal(StringToSign.getBytes());


        //4 按照 Base64 编码规则把上面的 HMAC 值编码成字符串，即得到签名值（Signature）
        String Signature= Base64.encodeBase64String(HMAC);

        return Signature;
    }



    private static final String ENCODING = "UTF-8";
    private static String percentEncode(String value) throws UnsupportedEncodingException {
        return value != null ? URLEncoder.encode(value, ENCODING).
                replace("+", "%20").
                replace("*", "%2A").
                replace("%7E", "~") : null;
    }
    private static String getSignature(String action, String domainName, String appName, String streamName, String endTime, String startTime) throws UnsupportedEncodingException {
        Map map = new HashMap();
        map.put("Format", Format);
        map.put("Version", Version);
        map.put("SignatureMethod", SignatureMethod);
        map.put("SignatureNonce", SignatureNonce);
        map.put("SignatureVersion", SignatureVersion);
        map.put("AccessKeyId", AccessKeyId);
        map.put("Timestamp", Timestamp);
        map.put("Action", action);
        map.put("DomainName", domainName);
        map.put("AppName",appName);
        map.put("EndTime",endTime);
        map.put("StartTime",startTime);
        map.put("StreamName",streamName);
        //1 构造规范化的请求字符串CQString
        //1.1参数排序
        String[] sortedKeys = (String[]) map.keySet().toArray(new String[]{});
        Arrays.sort(sortedKeys);
        //1.2参数编码
        StringBuilder CQString = new StringBuilder();
        for (String key : sortedKeys) {
            // 这里注意对key和value进行编码
            CQString.append("&").append(percentEncode(key))
                    .append("=").append(percentEncode((String) map.get(key)));
        }
        //2 将上述的CQString构造成待签名的字符串
        String StringToSign = "GET" + "&" +
                percentEncode("/") + "&" +
                percentEncode(CQString.toString().substring(1));
        //3 计算待签名字符串StringToSign的HMAC值
        String key = AccessKeySecret + "&";
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
        //System.out.println(key.getBytes());
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            mac.init(secretKey);
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        byte[] HMAC=mac.doFinal(StringToSign.getBytes());


        //4 按照 Base64 编码规则把上面的 HMAC 值编码成字符串，即得到签名值（Signature）
        String Signature= Base64.encodeBase64String(HMAC); //.getEncoder().encodeToString(HMAC);
        //String Signature = new sun.misc.BASE64Encoder().encode(HMAC);

        return Signature;
    }
    public static void main(String[] args) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException {
        // TODO Auto-generated method stub
        //String Action = "SetLiveStreamsNotifyUrlConfig";
        String Action = "DescribeLiveStreamsOnlineList";
        //String Action = "AddLiveRecordNotifyConfig";
        String DomainName = "zb.ahich.cn";
//        String DomainName = "zb.0898zlb.com";
//        String NotifyUrl = "http://crown.free.idcfengye.com/System/LiveSet/zbCallBack";
//        boolean NeedStatusNotify = true;
        //String AppName = "xzlive";
        //String StreamName = "xz_1234";
        //String EndTime = "2018-09-28T16:36:00Z";
        //String StartTime = "2018-09-26T16:36:00Z";
        String signResult = getSignature(Action, DomainName);
        System.out.println(signResult);
        //System.out.println(getSignature222(Action, DomainName,NotifyUrl,NeedStatusNotify));
        String url = "https://live.aliyuncs.com/?"
                + "Format=" + Format
                + "&Version=" + Version
                + "&Signature=" + signResult
                + "&SignatureMethod=" + SignatureMethod
                + "&SignatureNonce=" + SignatureNonce
                + "&SignatureVersion=" +SignatureVersion
                + "&AccessKeyId=" + AccessKeyId
                + "&Timestamp=" + Timestamp
                + "&Action=" + Action
                + "&DomainName=" + DomainName;
                //+ "&NeedStatusNotify="+NeedStatusNotify
                //+ "&AppName=" + AppName
                //+ "&StreamName=" + StreamName
                //+ "&EndTime="+"2018-09-28T16:36:00Z"
                //+ "&StartTime="+"2018-09-26T16:36:00Z"




//                + "&NotifyUrl="+"http://crown.free.idcfengye.com/System/LiveSet/zbCallBack";
        System.out.println(url);
        String result = HttpRequestUtil.doGet(url);
        System.out.println("result=" + result);
        //添加录制回调


    }

    private static String getSignature222(String action, String domainName, String NotifyUrl,boolean NeedStatusNotify) throws UnsupportedEncodingException {
        Map map = new HashMap();
        map.put("Format", Format);
        map.put("Version", Version);
        map.put("SignatureMethod", SignatureMethod);
        map.put("SignatureNonce", SignatureNonce);
        map.put("SignatureVersion", SignatureVersion);
        map.put("AccessKeyId", AccessKeyId);
        map.put("Timestamp", Timestamp);
        map.put("Action", action);
        map.put("DomainName", domainName);
        map.put("NotifyUrl",NotifyUrl);
        //map.put("NeedStatusNotify",NeedStatusNotify);
        //1 构造规范化的请求字符串CQString
        //1.1参数排序
        String[] sortedKeys = (String[]) map.keySet().toArray(new String[]{});
        Arrays.sort(sortedKeys);
        //1.2参数编码
        StringBuilder CQString = new StringBuilder();
        for (String key : sortedKeys) {
            // 这里注意对key和value进行编码
            CQString.append("&").append(percentEncode(key))
                    .append("=").append(percentEncode((String) map.get(key)));
        }
        //2 将上述的CQString构造成待签名的字符串
        String StringToSign = "GET" + "&" +
                percentEncode("/") + "&" +
                percentEncode(CQString.toString().substring(1));
        //3 计算待签名字符串StringToSign的HMAC值
        String key = AccessKeySecret + "&";
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
        //System.out.println(key.getBytes());
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            mac.init(secretKey);
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        byte[] HMAC=mac.doFinal(StringToSign.getBytes());


        //4 按照 Base64 编码规则把上面的 HMAC 值编码成字符串，即得到签名值（Signature）
        String Signature= Base64.encodeBase64String(HMAC); //.getEncoder().encodeToString(HMAC);
        //String Signature = new sun.misc.BASE64Encoder().encode(HMAC);

        return Signature;
    }

}
