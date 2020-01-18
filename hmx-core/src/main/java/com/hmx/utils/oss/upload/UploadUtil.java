package com.hmx.utils.oss.upload;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.processing.FilerException;
import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import com.hmx.utils.StringHelper;
import com.hmx.utils.files.FilesTools;
import com.hmx.utils.logger.LogHelper;
import com.hmx.utils.random.RandomHelper;
import com.hmx.utils.oss.upload.exception.FileIllegalException;
import com.hmx.utils.oss.upload.exception.FileIsNullException;
import com.hmx.utils.oss.upload.exception.FileSizeOutException;
import com.hmx.utils.oss.upload.exception.FileSizeSmallException;
import com.hmx.utils.oss.upload.exception.UploadException;


/**
 * liY 下载文件组件
 */
@Component
public class UploadUtil {

	//window测试
	private static final String pdfDir = "F:\\fileTest\\file\\pdfFile\\";
	private static final String imageDir = "F:\\fileTest\\file\\imageFile\\";
//	private static final String htmlDir = "E:\\fileTest\\htmlFile";
	//private static final String txtFileDir = "E:\\fileTest\\txtFile\\";

	//linux部署--测试环境
//	private static final String pdfDir = "http://120.79.169.165/home/back/pdfFile/";
//	private static final String htmlDir = "/usr/local/tomcat/webapps/ROOT/htmlFile";
//	private static final String txtFileDir = "/home/back/txtFile/";
//	private static final String ipPort = "http://www.sskj.art:8080/";

	//linux部署--生产环境
//	private static final String pdfDir = "http://120.24.222.160/home/file/pdfFile/";
//	private static final String imageDir = "http://120.24.222.160/home/file/imageFile/";
	/*private static final String htmlDir = "/home/hmx/htmlFile";
	private static final String txtFileDir = "/home/hmx/txtFile/";*/
	private static final String ipPort = "http://www.zghmx.cn/";

	@Value("${oss.endpoint}")
	public String endpoint;
	@Value("${oss.accessKeyId}")
	public String accessKeyId;
	@Value("${oss.accessKeySecret}")
	public String accessKeySecret;
	@Value("${oss.bucketName}")
	public String bucketName;
	@Value("${oss.ossIsFormal}")
	public boolean ossIsFormal;
	@Value("${oss.physicsrootpath}")
	public String physicsrootpath;
	@Value("${oss.serverip}")
	public String serverip;
	
	private Log logger = LogFactory.getLog(UploadUtil.class);

	/**
	 * @param file
	 *            文件对象
	 * @return 文件名 + 文件路径
	 * @throws FileIsNullException
	 *             文件为空
	 * @throws FileSizeOutException
	 *             文件过大
	 * @throws FileSizeSmallException
	 *             文件太小
	 * @throws
	 *
	 * @throws UploadException
	 *             系统错误
	 */
	public String uploadFile(MultipartFile file, String contentFlow,List<String> fileTypeList, String fileType) throws FileIsNullException,
			FileSizeOutException, FileSizeSmallException, FileIllegalException, UploadException {
		return uploadFile(file, contentFlow, fileTypeList,fileType, null, null);
	}

	/**
	 * @param file
	 *            文件对象
	 * @param fileType
	 *            文件类型
	 * @param fileMaxSize
	 *            文件最大 限制
	 * @param fileMinSize
	 *            文件最小限制
	 * @return 文件名 + 文件路径
	 * @throws FileIsNullException
	 *             文件为空
	 * @throws FileSizeOutException
	 *             文件过大
	 * @throws FileSizeSmallException
	 *             文件太小
	 * @throws
	 *
	 * @throws UploadException
	 *             系统错误
	 */
	public String uploadFile(MultipartFile file, String contentFlow, List<String> fileTypeList,String fileType, Long fileMaxSize,
			Long fileMinSize) throws FileIsNullException, FileSizeOutException, FileSizeSmallException,
			FileIllegalException, UploadException {

		try {
			
			if (file == null || file.getSize() == 0)
				throw new FileIsNullException("文件为空 ");

			if (fileMaxSize == null)
				fileMaxSize = UploadConfig.DEFFILEMAXSIZE;
			if (fileMinSize == null)
				fileMinSize = UploadConfig.DEFFILEMINSIZE;

			long fileSize = file.getSize();
		/*	if (fileSize > fileMaxSize)
				throw new FileSizeOutException("文件过大 size : " + fileSize);

			if (fileSize < fileMinSize)
				throw new FileSizeSmallException("文件过小 size : " + fileSize);*/

			String fileName = file.getOriginalFilename().trim();

			String[] fileNames = fileName.split("\\.");

			String name = fileNames[0];
			String suffix = fileNames[1].toLowerCase();


			if(null == fileTypeList || fileTypeList.size() == 0){
				if(UploadConfig.ILLEGALTYPE.contains(suffix)){
					throw new FileIllegalException("文件类型非法；type:" + suffix);
				}
			}else {
				if(!fileTypeList.contains(suffix)){
					throw new FileIllegalException("文件类型非法；type:" + suffix);
				}
			}

			LogHelper.logger().debug( "文件通过检查 开始写入");
			String newName = contentFlow + "." + suffix;
			String realPath = "";
			if("1".equals(fileType)){//pdf
				realPath = pdfDir + newName;
			}else if("2".equals(fileType)){//图片
				realPath = imageDir + newName;
			}

			file.transferTo(new File(realPath));
			System.out.println( "文件上传成功  PATH : " + realPath);
			return realPath;

		} catch (FileIsNullException e1) {
			throw new UploadException(e1.getMessage());
		} catch (FileSizeOutException e2) {
			throw new UploadException(e2.getMessage());
		} catch (FileSizeSmallException e3) {
			throw new UploadException(e3.getMessage());
		} catch (FileIllegalException e4) {
			throw new UploadException(e4.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new UploadException("文件上传 系统异常");
		}
	}





//	public String uploadImage(BufferedImage image, String path, String name) throws FileIsNullException, FileSizeOutException, FileSizeSmallException,
//	FileIllegalException, UploadException {
//		if (image == null)
//			throw new FileIsNullException("文件为空 ");
//
//		File targetFile = new File( (this.physicsrootpath.isEmpty()?FilesTools.getRootPath():this.physicsrootpath)  + path, name);
//		try {
//			ImageIO.write(image, "jpg", targetFile);
//
//			uploadToAliOss(  path+targetFile.getName() , targetFile);
//		} catch (IOException e) {
//			logger.error(e.getMessage());
//			throw new UploadException("图片文件异常");
//		}
//
//		return UploadConfig.SERVICEPATH +  path + name;
//	}
	
//	private Boolean uploadToAliOss(String path, File uploadFile) throws UploadException {
//		try {
//			// endpoint以杭州为例，其它region请按实际情况填写
//			String endpoint = this.endpoint;
//			// 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录
//			// https://ram.console.aliyun.com 创建
//			String accessKeyId = this.accessKeyId;
//			String accessKeySecret = this.accessKeySecret;
//			// 创建OSSClient实例
//			OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
//			// 上传文件
//			PutObjectResult result = ossClient.putObject(bucketName, StringHelper.getAliOssKeyByPath(path.replace("\\", "/"),ossIsFormal),
//					uploadFile);
//
//			// 关闭client
//			ossClient.shutdown();
//			if (StringUtils.isEmpty(result.getETag())) {
//				throw new UploadException("上传到内部文件服务器失败");
//			}
//			return true;
//		} catch (UploadException e) {
//			e.printStackTrace();
//			throw new UploadException("上传到内部文件服务器失败");
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
	
//	private Boolean uploadInputStreamToAliOss(String path, MultipartFile file) throws UploadException {
//		try {
//			// endpoint以杭州为例，其它region请按实际情况填写
//			String endpoint = this.endpoint;
//			// 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录
//			// https://ram.console.aliyun.com 创建
//			String accessKeyId = this.accessKeyId;
//			String accessKeySecret = this.accessKeySecret;
//			// 创建OSSClient实例
//			OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
//			// 上传文件
//			PutObjectResult result = ossClient.putObject(bucketName, StringHelper.getAliOssKeyByPath(path.replace("\\", "/"),ossIsFormal),
//					file.getInputStream());
//
//			// 关闭client
//			ossClient.shutdown();
//			if (StringUtils.isEmpty(result.getETag())) {
//				throw new UploadException("上传到内部文件服务器失败");
//			}
//			return true;
//		} catch (UploadException e) {
//			e.printStackTrace();
//			throw new UploadException("上传到内部文件服务器失败");
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
	
	
	/**
	 * 上传社区相关图片等
	 * @param file
	 *            文件对象
	 * @param path
	 *            文件存放路径
	 * @param fileType
	 *            文件类型
	 * @param fileMaxSize
	 *            文件最大 限制
	 * @param fileMinSize
	 *            文件最小限制
	 * @return 文件名 + 文件路径
	 * @throws FileIsNullException
	 *             文件为空
	 * @throws FileSizeOutException
	 *             文件过大
	 * @throws FileSizeSmallException
	 *             文件太小
	 * @throws
	 *
	 * @throws UploadException
	 *             系统错误
	 */
//	public String uploadCommunityImage(MultipartFile file, String path, List<String> fileType, Long fileMaxSize,
//			Long fileMinSize, String typeString) throws FileIsNullException, FileSizeOutException, FileSizeSmallException,
//			FileIllegalException, UploadException {
//
//		try {
//
//			if (file == null || file.getSize() == 0)
//				throw new FileIsNullException("文件为空 ");
//
//			if (fileMaxSize == null)
//				fileMaxSize = UploadConfig.DEFFILEMAXSIZE;
//			if (fileMinSize == null)
//				fileMinSize = UploadConfig.DEFFILEMINSIZE;
//
//			long fileSize = file.getSize();
//			if (fileSize > fileMaxSize)
//				throw new FileSizeOutException("文件过大 size : " + fileSize);
//
//			if (fileSize < fileMinSize)
//				throw new FileSizeSmallException("文件过小 size : " + fileSize);
//
//			String fileName = file.getOriginalFilename().trim();
//
//			String[] fileNames = fileName.split("\\.");
//
//			String name = fileNames[0];
//			String suffix = fileNames[1].toLowerCase();
//
//			if (fileType == null || fileType.size() == 0) {
//				if (UploadConfig.ILLEGALTYPE.contains(suffix))
//					throw new FileIllegalException("文件类型非法 type : " + suffix);
//			} else {
//				if (!fileType.contains(suffix))
//					throw new FileIllegalException("文件类型非法 type : " + suffix);
//			}
//
//			LogHelper.logger().debug( "文件通过检查 开始写入");
//
//			//TODO 需要根据上传位置（社区封面、户型图等）和类型等生成相关图片名称
//			String newName = name + RandomHelper.getRandomString(4) + RandomHelper.getSystemNum("", 2, 12, 2) + typeString + "." + suffix;
//			File targetFile = new File( (this.physicsrootpath.isEmpty()?FilesTools.getRootPath():this.physicsrootpath) + path, newName);
//
//		    try{
//				if (!targetFile.exists()) {
//					targetFile.mkdirs();
//				}
//
//				file.transferTo(targetFile);
//			}catch(Exception ex){
//				ex.printStackTrace();
//				logger.error("上传路径填写有误---"+ex.getMessage());
//				//本地保存报错不抛出异常
//				//throw new UploadException(ex.getMessage());
//			}
//			String url = path + newName;
//
//			url = url.replaceAll("\\\\", "/");
//
//			uploadToAliOss(  path + newName , targetFile);
//			LogHelper.logger().debug( "文件上传成功  PATH : " + url);
//			return url;
//
//		} catch (FileIsNullException e1) {
//			throw new UploadException(e1.getMessage());
//		} catch (FileSizeOutException e2) {
//			throw new UploadException(e2.getMessage());
//		} catch (FileSizeSmallException e3) {
//			throw new UploadException(e3.getMessage());
//		} catch (FileIllegalException e4) {
//			throw new UploadException(e4.getMessage());
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e.getMessage());
//			throw new UploadException("文件上传 系统异常");
//		}
//	}


	public static boolean deleteByFileUrl(String fileUrl) throws Exception{
		//  http://www.sskj.art:8080/htmlFile/MeZg7Lb1.html
//		String htmlCode =  fileUrl.substring(fileUrl.length() - 13,fileUrl.length());
//		String code = htmlCode.split(".html")[0];
//		System.out.println("code=" + code);
		//删除txt文件
//		String txtFilePath = txtFileDir + code + ".txt";
//		System.out.println("txtFilePath=" + txtFilePath);
//		File txtFile = new File(txtFilePath);
//		if(txtFile.exists() && txtFile.isFile()){
//			txtFile.delete();
//		}
		//删除pdf文件
//		String pdfFilePath = pdfDir + code + ".pdf";
		System.out.println("fileUrlPath=" + fileUrl);
		File pdfFile = new File(fileUrl);
		if(pdfFile.exists() && pdfFile.isFile()){
			pdfFile.delete();
		}
		return true;
	}

	public static void main(String[] arg0){
//		File pdfFile = new File("E:\\fileTest\\pdfFile\\被评估人_123456781.pdf");
//		if(pdfFile.exists()){
//			System.out.println("Y");
//		}else {
//			System.out.println("N");
//		}
		String fileUrl = "http://www.sskj.art:8080/htmlFile/MeZg7Lb1.html";
		String code =  fileUrl.substring(fileUrl.length() - 13,fileUrl.length());
		System.out.println("code=" + code.split(".html")[0]);
	}
}
