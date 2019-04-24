package com.hmx.utils.random;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class RandomHelper {

	
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
	 * 生成随机数字字符串
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomNum(int length) { // length表示生成字符串的长度
		String base = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		String randomNum = RandomHelper.getRandomNum(6);
		System.out.println(randomNum);
		BigDecimal zj=new BigDecimal(850);//租金
		BigDecimal zk=new BigDecimal(11);//折扣
		BigDecimal sdimoney=new BigDecimal(0);
		sdimoney=zj.multiply(zk).divide(new BigDecimal(100));
		System.out.println(sdimoney);
	}
	
	/**
	 * 系统自动生成编号（以“前缀+14位时间码（20161219120000，可截取部分）+随机数（指定长度）”）
	 * 
	 * @param dataBegin
	 *            时间码开始截取点（最长14，最小0）
	 * @param dataEnd
	 *            时间吗结束截取点（最长14，最小1且大于开始点，否则不截取）
	 * @param randomLen
	 *            后缀随机数长度
	 * @return systemNum 系统生成编号
	 */
	public static String getSystemNum( int randomLen) {
		StringBuffer systemNum = new StringBuffer();
		SimpleDateFormat format = new SimpleDateFormat("ddHHmmssSSS");
		String dataStr = format.format(new Date());
		systemNum.append(dataStr);
		
		String base = "0123456789";
		Random random = new Random();
		for (int i = 0; i < randomLen; i++) {
			int number = random.nextInt(base.length());
			systemNum.append(base.charAt(number));
		}
		return systemNum.toString();
	}
	
	public static BigDecimal getCouponAmount(int minValue, int maxValue){
		int diffValue = maxValue - minValue + 1;
		int a = (int)(Math.random() * diffValue) + minValue; 
   		return new BigDecimal(a).setScale(2, RoundingMode.HALF_UP);
	}
}
