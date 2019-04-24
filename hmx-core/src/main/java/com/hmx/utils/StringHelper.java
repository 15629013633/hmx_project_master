package com.hmx.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class StringHelper {
	/**
	 * 判断非空
	 * 
	 * @param String
	 * @return
	 */
	public static boolean isNotBlank(String str){
		return org.apache.commons.lang3.StringUtils.isNotBlank(str);
	}
	
	/**
	 * 判断为空
	 * 
	 * @param String
	 * @return
	 */
	public static boolean isBlank(String str){
		return !isNotBlank(str);
	}
	
	/**
	 * 生成随机字符串
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "abcdefghijkmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	/**
	 * 生成随机6位整数的字符串类型
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomInt() { // length表示生成字符串的长度
		
		return String.valueOf((int)((Math.random()*9+1)*100000));
	}
	
	/** 
     * 手机号验证 
     * @author Lzh
     * @param  str 
     * @return 验证通过返回true 
     */  
    public static boolean isMobile(String str) {   
        Pattern p = null;  
        Matcher m = null;  
        boolean b = false;   
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号  
        m = p.matcher(str);  
        b = m.matches();   
        return b;  
    }
    
    public static void main(String[] args) {
		System.out.println(isData("test"));
	}
    
    /** 
     * 必须由字母和数字组成 
     * @author Lzh
     * @param  str 
     * @return 验证通过返回true 
     */  
    public static boolean isData(String str) {
    	if(str == null || str.length() <= 1){
    		return false;
    	}
    	
    	Pattern p = Pattern.compile(".*\\d+.*"); 
    	
    	if(!p.matcher(str).matches()){
    		return false;
    	}
    	
    	p = Pattern.compile(".*[a-zA-Z]+.*"); 
    	
    	return p.matcher(str).matches();
    	
    }  
	
    /**
     * 验证邮箱
     * @author Lzh
     * @param email
     * @return 验证通过返回true 
     */
    public static boolean isEmail(String str) {
    	String reg="^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    	String pwd=str;
    	return pwd.matches(reg); 
    }  
    
    /**
     * 验证QQ号码
     * @author Lzh
     * @param 要求5~15位，不能以0开头，只能是数字
     * @return 验证通过返回true 
     */
    public static boolean isQQ(String str) {
    	String reg="[1-9][0-9]{4,14}";
    	String pwd=str;
    	return pwd.matches(reg); 
    }  
    
	/**
	 * 检查是否为数字
	 * 
	 * @param number
	 * @return
	 */
	public static boolean isNumber(String number) {
		if (null == number || "".equals(number))
			return false;
		Pattern p = Pattern.compile("^(-?\\d+)(\\.\\d+)?$");
		Matcher m = p.matcher(number);
		return m.matches();
	}
	
	/**
	 * 检查是否为手机号
	 * @author chenhm
	 */
	public static boolean isMobileNO(String number) {
		if (null == number || "".equals(number))
			return false;
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9])|17[6])\\d{8}$");
		Matcher m = p.matcher(number);
		return m.matches();
	}

	/**
	 * 检查是否为整数
	 * 
	 * @param number
	 * @return
	 */
	public static boolean isInteger(String number) {
		if (null == number || "".equals(number))
			return false;
		Pattern p = Pattern.compile("^(-?\\d+)$");
		Matcher m = p.matcher(number);
		return m.matches();
	}

	/**
	 * 设置默认字符串
	 * 
	 * @param requestValue
	 *            请求值
	 * @param defaultVal
	 *            请求为空的时候设置的默认值
	 * @return 结果
	 */
	public static String setDefaultString(Object str, String defaultVal) {
		if (str == null) {
			return defaultVal;
		}
		return StringHelper.isNullOrEmpty(str.toString()) ? defaultVal : str.toString();
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 *            进行判断的字符串
	 * @return 如果为null 或者"" 则返回true代表为空 如果字符串不为空则返回false
	 */
	public final static boolean isNullOrEmpty(String str) {
		if (str == null || str.length() <= 0) {
			return true;
		}
		return false;
	}

	/**
	 * 系统自动生成编号（以“前缀+14位时间码（20161219120000，可截取部分）+随机数（指定长度）”）
	 * 
	 * @param prefix
	 *            前缀
	 * @param dataBegin
	 *            时间码开始截取点（最长14，最小0）
	 * @param dataEnd
	 *            时间吗结束截取点（最长14，最小1且大于开始点，否则不截取）
	 * @param randomLen
	 *            后缀随机数长度
	 * @return systemNum 系统生成编号
	 */
	public static String getSystemNum(String prefix, int dataBegin, int dataEnd, int randomLen) {
		// 前缀
		StringBuffer systemNum = new StringBuffer(prefix);

		// 中间时间码
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String dataStr = format.format(new Date());
		if (dataBegin >= 0 && dataEnd > 0 && dataBegin < 14 && dataEnd < 14 && dataBegin < dataEnd) {
			dataStr = dataStr.substring(dataBegin, dataEnd);
		}
		systemNum.append(dataStr);

		// 后缀随机数
		String base = "0123456789";
		Random random = new Random();
		for (int i = 0; i < randomLen; i++) {
			int number = random.nextInt(base.length());
			systemNum.append(base.charAt(number));
		}

		return systemNum.toString();
	}

	/**
	 * 去掉结尾的某些字符串
	 * 
	 * @param string
	 * @param charsToTrim
	 * @return
	 */
	public static String trimEnd(String string, Character... charsToTrim) {
		if (string == null || charsToTrim == null)
			return string;

		int lengthToKeep = string.length();
		for (int index = string.length() - 1; index >= 0; index--) {
			boolean removeChar = false;
			if (charsToTrim.length == 0) {
				if (Character.isWhitespace(string.charAt(index))) {
					lengthToKeep = index;
					removeChar = true;
				}
			} else {
				for (int trimCharIndex = 0; trimCharIndex < charsToTrim.length; trimCharIndex++) {
					if (string.charAt(index) == charsToTrim[trimCharIndex]) {
						lengthToKeep = index;
						removeChar = true;
						break;
					}
				}
			}
			if (!removeChar)
				break;
		}
		return string.substring(0, lengthToKeep);
	}

	/**
	 * 去掉开始的某些字符
	 * 
	 * @param string
	 * @param charsToTrim
	 * @return
	 */
	public static String trimStart(String string, Character... charsToTrim) {
		if (string == null || charsToTrim == null)
			return string;

		int startingIndex = 0;
		for (int index = 0; index < string.length(); index++) {
			boolean removeChar = false;
			if (charsToTrim.length == 0) {
				if (Character.isWhitespace(string.charAt(index))) {
					startingIndex = index + 1;
					removeChar = true;
				}
			} else {
				for (int trimCharIndex = 0; trimCharIndex < charsToTrim.length; trimCharIndex++) {
					if (string.charAt(index) == charsToTrim[trimCharIndex]) {
						startingIndex = index + 1;
						removeChar = true;
						break;
					}
				}
			}
			if (!removeChar)
				break;
		}
		return string.substring(startingIndex);
	}

	/**
	 * 去掉字符串前后的某些字符
	 * 
	 * @param string
	 * @param charsToTrim
	 * @return
	 */
	public static String trim(String string, Character... charsToTrim) {
		return trimEnd(trimStart(string, charsToTrim), charsToTrim);
	}

	/**
	 * 判断是否为身份证号
	 * 
	 * @param param
	 *            判断字符串
	 * @return 是合法身份证true， 否则false
	 */
	public static boolean isIdCardNumber(String param) {
		if (null == param || "".equals(param))
			return false;
		Pattern p = Pattern.compile("^[1-9]\\d{5}\\d{4}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$"); // 匹配
		Matcher m = p.matcher(param);
		return m.matches();
	}

	/**
	 * 截取出指定字符串中的身份证号
	 * 
	 * @param values
	 *            指定字符串
	 * @return 身份证号
	 */
	public static String subIdCardNumber(String values) {
		String str = "[1-9]\\d{5}((19|20)\\d{2})((0[1-9])|(1[0-2]))(((0|1|2)[0-9])|(3[0,1]))\\d{3}[xX\\d]";
		Pattern pattern = Pattern.compile(str);
		Matcher matcher = pattern.matcher(values);
		if (matcher.find()) {
			return matcher.group();
		}
		return null;
	}

	/**
	 * 将数组以某种字符串连接为一个新的字符串
	 * 
	 * @param separator
	 * @param stringarray
	 * @param startindex
	 * @param count
	 * @return
	 */
	public static String join(String separator, String[] stringarray, int startindex) {
		String result = "";

		if (stringarray == null)
			return null;

		for (int index = startindex; index < stringarray.length; index++) {
			if (separator != null && index > startindex)
				result += separator;

			if (stringarray[index] != null)
				result += stringarray[index];
		}

		return result;
	}

	/**
	 * 判断字符串是否为中文
	 * 
	 * @param str
	 * @return true是 false不是
	 */
//	public static boolean isChinese(String str) {
//		if (null == str || "".equals(str))
//			return false;
//		Pattern p = Pattern.compile("[\\u4E00-\\u9FBF]+");
//		Matcher m = p.matcher(str.trim());
//		return m.matches();
//	}

	// 根据Unicode编码完美的判断中文汉字和符号
	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}

	// 完整的判断中文汉字和符号
	public static boolean isChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

	// 只能判断部分CJK字符（CJK统一汉字）
	public static boolean isChineseByREG(String str) {
		if (str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
		return pattern.matcher(str.trim()).find();
	}

	// 只能判断部分CJK字符（CJK统一汉字）
	public static boolean isChineseByName(String str) {
		if (str == null) {
			return false;
		}
		// 大小写不同：\\p 表示包含，\\P 表示不包含
		// \\p{Cn} 的意思为 Unicode 中未被定义字符的编码，\\P{Cn} 就表示 Unicode中已经被定义字符的编码
		String reg = "\\p{InCJK Unified Ideographs}&&\\P{Cn}";
		Pattern pattern = Pattern.compile(reg);
		return pattern.matcher(str.trim()).find();
	}

	// 以下为服务器端判断客户端浏览器类型的方法  
	public static String getBrowser(HttpServletRequest request) {  
        String UserAgent = request.getHeader("USER-AGENT").toLowerCase();  
        if (UserAgent != null) {  
            if (UserAgent.indexOf("msie") >= 0)  
                return "IE";  
            if (UserAgent.indexOf("firefox") >= 0)  
                return "FF";  
            if (UserAgent.indexOf("safari") >= 0)  
                return "SF";  
        }  
        return null;  
    }

	/**
	 * 
	 * @param path
	 * @param ossIsTest 是否正式运营 true false
	 * @return
	 */
	public static String getAliOssKeyByPath(String path,boolean ossIsFormal) {
		return true == ossIsFormal?"formal"+ path :"test"+ path ;
	}
}
