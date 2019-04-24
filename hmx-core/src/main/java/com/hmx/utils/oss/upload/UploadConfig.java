package com.hmx.utils.oss.upload;

import java.io.File;
import java.util.Arrays;
import java.util.List;


public class UploadConfig {

	
	//因为项目为2个,且2个项目均有上传,解决方法是上传到同一个物理文件夹中.通过虚拟路径访问
	
	public static String SERVICEPATH = File.separator + "files";

	//public static Long DEFFILEMAXSIZE = 1024 * 1024 * 5L; // 2M
	//10M = 10乘以1024再乘以1024等于10485760字节
	public static Long DEFFILEMAXSIZE = 10240 * 1024 * 1L;

	public static Long DEFFILEMINSIZE = 100L; // 100B

	public static List<String> ILLEGALTYPE = Arrays.asList("jsp", "bat", "shell");// 非法类型

	public static String root = File.separator + "WEB-INF" + File.separator + "File";

	public static String projectroot = File.separator + "file";

	// 添加程序需要的一些类型 ==================
	public static List<String> IMAGETYPE = Arrays.asList("jpg", "bmp", "gif", "png", "jpeg", "bmp", "ai", "pdg","pdf");// 图片类型

	//允许大多文件上传
	public static List<String> FILETYPE = Arrays.asList("doc", "docx","text", "xls", "xlsx","jpg", "bmp", "gif", "png", "jpeg", "bmp", "ai", "pdg","pdf","xls", "xlsx","rar", "RAR", "zip", "ZIP", "mp4", "flv", "wmv", "swf","epub");// 上传的文件类型

	public static List<String> XLSXType = Arrays.asList("xls", "xlsx");// 报表类型

	public static List<String> TARTYPE = Arrays.asList("rar", "RAR", "zip", "ZIP");// 压缩文件类型

	public static List<String> ENTRUSTTYPE = Arrays.asList("jpg", "pdf");// 委托付款函扫描件类型

	public static List<String> SMALLTICKET = Arrays.asList("jpg", "bmp", "gif", "png", "jpeg", "bmp", "ai", "pdg", "pdf");// 小票
	
	// 添加程序需要的一些类型 end ==================

	// ！！！！！！注意：以下路径的文件每次重新发布需要备份并覆盖回去！！！！！

	/**
	 * 默认路径
	 */
	public static String DEFPATH = root + File.separator + "defPath" + File.separator;

	/**
	 * pdf上传
	 */
	public static String PDF = root + File.separator + "pdf" + File.separator;

	/**
	 * 临时文件路径
	 */
	public static String tmp = projectroot + File.separator + "tmp" + File.separator;
}
