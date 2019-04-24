package com.hmx.utils.upload;

import com.aliyun.vod.upload.impl.UploadImageImpl;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.*;
import com.aliyun.vod.upload.resp.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UploadVideoDemo {
	private Logger logger = LoggerFactory.getLogger(UploadVideoDemo.class);
    //账号AK信息请填写(必选)
	@Value("${accessKeyId}")
    private String accessKeyId;
    //账号AK信息请填写(必选)
	@Value("${accessKeySecret}")
    private String accessKeySecret;
	@Value("${imageType}")
	private String imageType;
	@Value("${imageExt}")
	private String imageExt;
	@Value("${videoExt}")
	private String videoExt;

	public boolean imageExt(String ext,String extArrayStr){
		String[] extArray = extArrayStr.split(",");
		if(extArray != null && extArray.length>0){
			for(String e : extArray){
				if(ext.equals(e.toLowerCase().trim())){
					return true;
				}
			}
		}
		return false;
	}
    /**
     * 图片上传接口，本地文件上传示例
     * 参数参考文档 https://help.aliyun.com/document_detail/55619.html
     *
     */
    public Map<String,Object> hmxUploadImageLocalFile(InputStream inputStream,String fileName) {
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	resultMap.put("flag", false);
    	resultMap.put("url", null);
    	try {
    		/* 图片类型（必选）取值范围：default（默认)，cover（封面），watermark（水印）*/
    		UploadImageRequest request = new UploadImageRequest(accessKeyId, accessKeySecret, imageType);
    		request.setImageType(imageType);
    		/* 图片文件扩展名（可选）取值范围：png，jpg，jpeg */
    		String[] extArray = fileName.split("\\.");
    		String ext = extArray[extArray.length-1].toLowerCase();
    		boolean flag = imageExt(ext.trim(),imageExt);
    		if(!flag){
    			resultMap.put("content", "图片格式不正确，暂时只支持"+imageExt+"格式!");
    			return resultMap;
    		}
    		request.setImageExt(ext);
    		/* 图片标题（可选）长度不超过128个字节，UTF8编码 */
    		//request.setTitle("图片标题");
    		/* 图片标签（可选）单个标签不超过32字节，最多不超过16个标签，多个用逗号分隔，UTF8编码 */
    		//request.setTags("标签1,标签2");
    		/* 存储区域（可选）*/
    		//request.setStorageLocation("out-4f3952f78c0211e8b3020013e7.oss-cn-shanghai.aliyuncs.com");
    		/* 流式上传时，InputStream为必选，fileName为源文件名称，如:文件名称.png(可选)*/
//    		String fileName = "C:\\Users\\Administrator\\Desktop\\gif\\t010d337c5658f73a5e.jpg";
    		request.setFileName(fileName);
    		request.setInputStream(inputStream);
    		/* 开启默认上传进度回调 */
    		//request.setPrintProgress(false);
    		/* 设置自定义上传进度回调 (必须继承 VoDProgressListener) */
    		// request.setProgressListener(new PutObjectProgressListener());
    		UploadImageImpl uploadImage = new UploadImageImpl();
    		UploadImageResponse response = uploadImage.upload(request);
			logger.info("RequestId=" + response.getRequestId());
    		if (response.isSuccess()) {
				logger.info("ImageId=" + response.getImageId());
				logger.info("ImageURL=" + response.getImageURL() + "\n");
    			resultMap.put("flag", true);
    			String url = response.getImageURL();
    			url = url.substring(0, url.lastIndexOf("?"));
    			resultMap.put("url", url);
    			resultMap.put("content", "上传图片成功");
    		} else {
				logger.info("ErrorCode=" + response.getCode() + "\n");
				logger.info("ErrorMessage=" + response.getMessage() + "\n");
    			resultMap.put("content", "上传图片失败:"+response.getMessage());
    		}
    		return resultMap;
		} catch (Exception e) {
			resultMap.put("content", "上传图片异常:"+e.getMessage());
			return resultMap;
		}finally {
			try {
				if(inputStream != null){
					inputStream.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
    
    /**
     * 本地文件上传接口
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @param title
     * @param fileName
     */
    public Map<String,Object> hmxUploadVideo(InputStream inputStream,  String fileName , String title) {
		logger.info("--------------------get in hmxUploadVideo---------------------");
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	resultMap.put("flag", false);
    	resultMap.put("videoId", null);
    	try{
			logger.info("--------------------start upload video---------------------");
			logger.info("--------------------start UploadStreamRequest----------------");
    		UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);
    		String[] extArray = fileName.split("\\.");
    		String ext = extArray[extArray.length-1].toLowerCase();
    		boolean flag = imageExt(ext.trim(),videoExt);
    		if(!flag){
    			resultMap.put("content", "视频格式不正确，暂时只支持"+videoExt+"格式!");
    			return resultMap;
    		}
            /* 是否使用默认水印(可选)，指定模板组ID时，根据模板组配置确定是否使用默认水印*/
           //request.setShowWaterMark(true);
           /* 设置上传完成后的回调URL(可选)，建议通过点播控制台配置消息监听事件，参见文档 https://help.aliyun.com/document_detail/57029.html */
           //request.setCallback("http://callback.sample.com");
           /* 自定义消息回调设置，参数说明参考文档 https://help.aliyun.com/document_detail/86952.html#UserData */
           //request.setUserData(""{\"Extend\":{\"test\":\"www\",\"localId\":\"xxxx\"},\"MessageCallback\":{\"CallbackURL\":\"http://test.test.com\"}}"");
           /* 视频分类ID(可选) */
           //request.setCateId(0);
           /* 视频标签,多个用逗号分隔(可选) */
           //request.setTags("标签1,标签2");
           /* 视频描述(可选) */
           //request.setDescription("视频描述");
           /* 封面图片(可选) */
           //request.setCoverURL("http://cover.sample.com/sample.jpg");
           /* 模板组ID(可选) */
           //request.setTemplateGroupId("8c4792cbc8694e7084fd5330e56a33d");
           /* 工作流ID(可选) */
           //request.setWorkflowId("d4430d07361f0*be1339577859b0177b");
           /* 存储区域(可选) */
           //request.setStorageLocation("in-201703232118266-5sejdln9o.oss-cn-shanghai.aliyuncs.com");
           /* 开启默认上传进度回调 */
           // request.setPrintProgress(true);
           /* 设置自定义上传进度回调 (必须继承 VoDProgressListener) */
           // request.setProgressListener(new PutObjectProgressListener());
           UploadVideoImpl uploader = new UploadVideoImpl();
			logger.info("--------------------start UploadStreamResponse----------------");
           UploadStreamResponse response = uploader.uploadStream(request);
    		//请求视频点播服务的请求ID
			logger.info("RequestId=" + response.getRequestId());
    		if (response.isSuccess()) {
				logger.info("VideoId=" + response.getVideoId());
    			resultMap.put("flag", true);
    			resultMap.put("videoId", response.getVideoId());
    			resultMap.put("content", "上传视频成功");
    		} else {
    			/* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
				logger.info("VideoId=" + response.getVideoId());
				logger.info("ErrorCode=" + response.getCode());
				logger.info("ErrorMessage=" + response.getMessage());
    			resultMap.put("content", "上传视频失败"+response.getMessage());
    		}
			logger.info("--------------------end upload video---------------------");
    		return resultMap;
    	} catch (Exception e) {
			resultMap.put("content", "上传视频异常:"+e.getMessage());
			return resultMap;
		}finally {
			try {
				if(inputStream != null){
					inputStream.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
}