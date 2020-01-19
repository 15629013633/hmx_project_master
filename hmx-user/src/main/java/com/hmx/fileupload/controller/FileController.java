package com.hmx.fileupload.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hmx.utils.enums.UploadFileType;
import com.hmx.utils.oss.upload.UploadUtil;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.ResultBean;

/**
 * oss文件上传
 * @author liY
 *
 */
@RestController
@RequestMapping("/fileUpload")
public class FileController {

	@Autowired
	private UploadUtil uploadUtil;
	/**
	 * @param file
	 * @param module
	 * @return
	 */
	@RequestMapping("/upload")
	@ResponseBody
	public ResultBean fileUpload( @RequestParam MultipartFile file ,@RequestParam Integer fileType, @RequestParam( required = false) String module ){
		if ( file == null ) {
			return new ResultBean().setCode(Config.UPLOAD_ERROR).setContent("文件为空");
		}
		String path =  "";
		if ( StringUtils.isEmpty( module ) ) {
			path =  File.separator+"files"+File.separator+"default"+File.separator;
		}else{
			path =  File.separator+"files"+File.separator+module+File.separator;
		}
		if(fileType == null){
			return new ResultBean().setCode(Config.UPLOAD_ERROR).setContent("文件不能为空");
		}
		List<String> fileTypeStr = UploadFileType.getName(fileType);
		if(fileTypeStr == null){
			return new ResultBean().setCode(Config.UPLOAD_ERROR).setContent("文件类型不正确");
		}
		try {
			String virtualPath = uploadUtil.uploadFile( file , path, fileTypeStr,fileType+"");
			
			if ( StringUtils.isEmpty( virtualPath ) ) {
				return new ResultBean().setCode(Config.UPLOAD_ERROR).setContent("文件上传异常");
			}
			return new ResultBean().setCode(Config.SUCCESS_CODE).setContent("上传成功").put("virtualPath", virtualPath);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultBean().setCode(Config.UPLOAD_ERROR).setContent("文件上传异常:"+ e.getMessage() );
		}
	} 
}
