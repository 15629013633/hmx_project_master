package com.hmx.utils.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

public class SMSHelper {
	   /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
 
//调用方式
//public static void main(String[] args) {
// 
//        //手机号  可以多个手机号
//        String phones = "16675325711";
// 
//       //短信内容
//        String content = null;
//		try {
//			content = URLEncoder.encode("【大地在线】您的验证码是23423","UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//       //拼接参数
//        String postData = "type=send&username=hmx008&password=F11A3E1A905E68D15C8FCA6DFD022630&gwid=b89c4aec&mobile="+phones+"&message="+content+"";
// 
//        String url="http://jk.106api.cn/smsUTF8.aspx";
//         
//        //发送并把结果赋给result,返回一个XML信息,解析xml 信息判断
//        String result=SMSHelper.sendPost(url, postData);
//        System.out.println(result);
// 
//    }

    public static Boolean sendSms(String mobile, String code) {
        Boolean flag=true;
        try {
            StringBuffer sb=new StringBuffer();
            sb.append("action=send&");
            sb.append("userid=&");
            sb.append("account=8T00134&");
            sb.append("password=8T0013443&");
            sb.append("mobile="+mobile+"&");
            //sb.append("content=【黄梅戏资源库】您的验证码是"+code+"，请在一分钟内进行验证!如非本人操作，请忽略本短信"+"&");
            sb.append("content=【IFIP】您的验证码是"+ code+"。如非本人操作，请忽略本短信"+"&");
            sb.append("sendTime=&");
            sb.append("extno=&");
            String xmlStr=sendPost2("https://dx.ipyy.net/sms.aspx", sb.toString());
//			String Success=parseXml(xmlStr);//短信发送成功的标记为 Success
//			if ("Success".equals(Success)) {
//				flag=true;
//			}else {
//				flag=false;
//			}

            System.out.println(xmlStr);
        } catch (Exception e) {
            e.printStackTrace();
            flag=false;
        }
        return flag;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost2(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; WinDo1ws NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) {
        sendSms("13076949806","123457");
    }
}
