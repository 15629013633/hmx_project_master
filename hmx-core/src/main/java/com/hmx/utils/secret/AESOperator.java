package com.hmx.utils.secret;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;  
import javax.crypto.spec.IvParameterSpec;  
import javax.crypto.spec.SecretKeySpec;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import sun.misc.BASE64Decoder;  
import sun.misc.BASE64Encoder;  
  
/** 
 * AES 是一种可逆加密算法，对用户的敏感信息加密处理 对原始数据进行AES加密后，在进行Base64编码转化； 
 */  
public class AESOperator {  
    /* 
     * 加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。 
     */  
//  a0b891c2d563e4f7  
    private String sKey = "abcdef0123456789";  
    private String ivParameter = "0123456789abcdef";  
    private static AESOperator instance = null;  
  
    private AESOperator() {  
  
    }  
  
    public static AESOperator getInstance() {  
        if (instance == null)  
            instance = new AESOperator();  
        return instance;  
    }  
  
    // 加密  
    public  String encrypt(String sSrc){  
        String result = "";  
        try {  
            Cipher cipher;  
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  
            byte[] raw = sKey.getBytes();  
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");  
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度  
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);  
            byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));  
            result = new BASE64Encoder().encode(encrypted);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }   
        // 此处使用BASE64做转码。  
        return result;  
                  
    }  
  
    // 解密  
    public String decrypt(String sSrc){  
        try {  
            byte[] raw = sKey.getBytes("ASCII");  
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");  
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());  
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);  
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);// 先用base64解密  
            byte[] original = cipher.doFinal(encrypted1);  
            String originalString = new String(original, "utf-8");  
            return originalString;  
        } catch (Exception ex) {  
            ex.printStackTrace();  
            return null;  
        }  
    }  
  
public static void main(String[] args){  
   /* // 需要加密的字串  
    String cSrc = "{\"id\":1,\"password\":\"c32077d73bbc8310a21ae02e08915e79\",\"userAliase\":\"张三\",\"userName\":\"zhangsan\",\"gender\":1,\"userBirthday\":\"2017-07-12 18:57:05\",\"renzhengPhone\":1,\"userPhone\":\"15989511029\",\"renzhengQq\":\"1\",\"userQq\":\"42310267\",\"userTelephone\":\"15989511029\",\"userEMail\":\"42310267@qq.com\",\"userAddress\":\"sz\"}";  
    System.out.println(cSrc + "  长度为" + cSrc.length());  
    // 加密  
    long lStart = System.currentTimeMillis();  
    String enString = AESOperator.getInstance().encrypt(cSrc);  
    System.out.println("加密后的字串是：" + enString + "长度为" + enString.length());  
      
    long lUseTime = System.currentTimeMillis() - lStart;  
    System.out.println("加密耗时：" + lUseTime + "毫秒");  
    // 解密  
    lStart = System.currentTimeMillis();  
    String DeString = AESOperator.getInstance().decrypt(enString);  
    System.out.println("解密后的字串是：" + DeString);  
    lUseTime = System.currentTimeMillis() - lStart;  
    System.out.println("解密耗时：" + lUseTime + "毫秒");  
    } */ 
	Integer payId=1;
	Integer sortNum=1;
	Map<String,Integer>map=new HashMap<String,Integer>();
	map.put("payId", payId);
	map.put("orderType", 1);
	map.put("orderId", 34);
	map.put("sortNum", sortNum);
	
	
	 System.out.println("加密后的字串0是：" + new Gson().toJson(map) + "长度为" + new Gson().toJson(map).length());  
	
	String passbackParams=new Gson().toJson(map);
			//AESOperator.getInstance().encrypt(new Gson().toJson(map)); 
	 System.out.println("加密后的字串1是：" + passbackParams + "长度为" + passbackParams.length());  
	 //MeKqnQcpc6g5jCxA66Q40ce6JkTb3EGFPD4wPospE8dlrFlUZPoUnTWKK4h3/dSwMxh6z2XvEl+v
	 
	 //UrlEncode
	 
	 //MeKqnQcpc6g5jCxA66Q40ce6JkTb3EGFPD4wPospE8dlrFlUZPoUnTWKK4h3/dSwMxh6z2XvEl v
	 //String enString = AESOperator.getInstance().decrypt("MeKqnQcpc6g5jCxA66Q40ce6JkTb3EGFPD4wPospE8dlrFlUZPoUnTWKK4h3/dSwMxh6z2XvEl+v");  
	 //System.out.println("解密后的字串是：" + enString); 
	 Map<String,Integer>backOrderMap=new Gson().fromJson(passbackParams,new TypeToken<Map<String, Integer>>() {  
     }.getType());
	 
	 Integer backPayId=backOrderMap.get("payId");
 	Integer backOrderType=backOrderMap.get("orderType");
 	Integer backOrderId=backOrderMap.get("orderId");
 	System.out.println("解密后的字串是：" + backPayId + "长度为" ); 
 	System.out.println("解密后的字串是：" + backOrderType + "长度为" ); 
 	System.out.println("解密后的字串是：" + backOrderId + "长度为" ); 
 	
 	
 	
 	
 	/*
 	try{ 
	 	String encode = URLEncoder.encode("MeKqnQcpc6g5jCxA66Q40XA/az/XnZG5nv1ZG6OahypIJm9/wDsu5rGrcDqX3jQoFT8yhkGn266Z", "UTF-8");
	 	System.out.println("乱码" + encode);
	 	System.out.println("乱码长度" + encode.length());
	 	String decode = URLDecoder.decode(encode, "UTF-8");// GBK解码
	 	System.out.println(decode);
	 	System.out.println("长度" +decode.length());
		}catch(Exception e){
			e.printStackTrace();
		}
 	
	}
	
*/
}    
      //MeKqnQcpc6g5jCxA66Q40ce6JkTb3EGFPD4wPospE8dlrFlUZPoUnTWKK4h3/dSwMxh6z2XvEl+v
      //MeKqnQcpc6g5jCxA66Q40ce6JkTb3EGFPD4wPospE8dlrFlUZPoUnTWKK4h3/dSwMxh6z2XvEl+v
	/*String passbackParams="MeKqnQcpc6g5jCxA66Q40ce6JkTb3EGFPD4wPospE8dlrFlUZPoUnTWKK4h3/dSwMxh6z2XvEl+v"; 
	String orderMap= AESOperator.getInstance().decrypt(passbackParams);
	
	Map<String,Integer>backOrderMap=new Gson().fromJson(orderMap,new TypeToken<Map<String, Integer>>() {  
    }.getType());
	//获取支付参数
	Integer backPayId=backOrderMap.get("payId");
	Integer backOrderType=backOrderMap.get("orderType");
	Integer backOrderId=backOrderMap.get("orderId");
	
	}*/
	
}  