package com.hmx.utils.files;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author ZRR
 * @Date 2017年2月22日 文件操作工具类
 */
public class FilesTools {

	private static final Log LOGGER = LogFactory.getLog(FilesTools.class);

	/**
	 * 将存放在sourceFilePath目录下的源文件，打包成fileName名称的zip文件，并存放到zipFilePath路径下
	 * 
	 * @param sourceFilePath
	 *            :待压缩的文件路径
	 * @param zipFilePath
	 *            :压缩后存放路径
	 * @param fileName
	 *            :压缩后文件的名称
	 * @return
	 */
	public static boolean fileToZip(String fileName, String path, String zipPath) {
		String sourceFilePath = path;
		String zipFilePath = zipPath;
		isHaveCatalog(zipPath);

		boolean flag = false;
		File sourceFile = new File(sourceFilePath);
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		ZipOutputStream zos = null;

		if (sourceFile.exists() == false) {
			LOGGER.warn("待压缩的文件目录：" + sourceFilePath + "不存在.");
		} else {
			try {
				File zipFile = new File(zipFilePath + File.separator + fileName + ".zip");
				if (zipFile.exists()) {
					LOGGER.warn(zipFilePath + "目录下存在名字为:" + fileName + ".zip" + "打包文件.");
				} else {
					File[] sourceFiles = sourceFile.listFiles();
					if (null == sourceFiles || sourceFiles.length < 1) {
						LOGGER.warn("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");
					} else {
						fos = new FileOutputStream(zipFile);
						zos = new ZipOutputStream(new BufferedOutputStream(fos));
						byte[] bufs = new byte[1024 * 10];
						for (int i = 0; i < sourceFiles.length; i++) {
							// 创建ZIP实体，并添加进压缩包
							ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
							zos.putNextEntry(zipEntry);
							// 读取待压缩的文件并写进压缩包里
							fis = new FileInputStream(sourceFiles[i]);
							bis = new BufferedInputStream(fis, 1024 * 10);
							int read = 0;
							while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
								zos.write(bufs, 0, read);
							}
						}
						flag = true;
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} finally {
				// 关闭流
				try {
					if (null != bis)
						bis.close();
					if (null != zos)
						zos.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				} finally {
					delAllFile(sourceFilePath);
				}
			}
		}
		return flag;
	}

	/**
	 * 如果文件夹不存在则创建
	 */
	private static void isHaveCatalog(String filePath) {
		File file = new File(filePath);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		}
	}
	
	
	/** 
	 * 删除单个文件 
	 * @param   sPath    被删除文件的文件名 
	 * @return 单个文件删除成功返回true，否则返回false 
	 */  
	public static boolean deleteFile(String sPath) {  
		sPath = getRootPath() + sPath;
		LOGGER.warn("删除文件 " + sPath);
		boolean flag = false;  
		File file = new File(sPath);  
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        LOGGER.warn("删除成功");
	        flag = true;  
	    }  
	    return flag;  
	}  
	
	/**
	 * @param path
	 *            文件夹名称
	 * @return </br>
	 *         删除文件夹所有文件 , 不删除文件夹
	 */
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);

		if (!file.exists())
			return flag;

		if (!file.isDirectory())
			return flag;

		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + File.separator + tempList[i]);// 先删除文件夹里面的文件
				flag = true;
			}
		}
		return flag;
	}
	
	public static String getRootPath() {
		String classPath = FilesTools.class.getClassLoader().getResource("/").getPath();
		String rootPath = "";
		// windows下
		if ("\\".equals(File.separator)) {
			rootPath = classPath.substring(1, classPath.indexOf("/WEB-INF/classes"));
			rootPath = rootPath.replace("/", "\\");
		}
		// linux下
		if ("/".equals(File.separator)) {
			rootPath = classPath.substring(0, classPath.indexOf("/WEB-INF/classes"));
			rootPath = rootPath.replace("\\", "/");
		}
		
		return rootPath;
	}
	
	public static String getDownloadPath() {
		String downloadPath = "";
		// windows下
		if ("\\".equals(File.separator)) {
			downloadPath = downloadPath+"\\resource\\downloadfile\\";
			downloadPath = downloadPath.replace("/", "\\");
		}
		// linux下
		if ("/".equals(File.separator)) {
			downloadPath = downloadPath+"/resource/downloadfile/";
			downloadPath = downloadPath.replace("\\", "/");
		}
		return downloadPath;
	}

	public static void main(String[] args) {
		System.out.println(FilesTools.delAllFile("F:\\HangTianOnLine\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\HangTian\\supervision\\enclosure"));
	}

	// public static void generateCore(String name,String url,String path)
	// throws WriterException, IOException {
	// isHaveCatalog(path);
	//
	// String filePath = path;
	// String fileName = name + ".png";
	//
	// String content = url;// 内容
	//
	// int width = 200; // 图像宽度
	// int height = 200; // 图像高度
	// String format = "png"; //图像类型
	//
	// Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType,
	// Object>();
	// hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
	//
	// BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
	// BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
	// Path path = FileSystems.getDefault().getPath(filePath, fileName);
	// MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 输出图像
	// }
}
