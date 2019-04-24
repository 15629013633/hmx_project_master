package com.hmx.fileupload.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hmx.utils.result.Config;
import com.hmx.utils.result.ResultBean;
import com.hmx.utils.upload.UploadVideoDemo;
/**
 * 用户图片上传
 * @author liY
 *
 */
@RestController
@RequestMapping("/imageUpload")
public class ImageFileUploadController {
	
	@Autowired
	private UploadVideoDemo uploadVideoDemo;

	@RequestMapping("/upload")
	public ResultBean fileImageUpload(MultipartFile file){
		if(file == null){
			return new ResultBean().setCode(Config.FAIL_CODE).setContent("上传图片文件不能为空");
		}
		try {
			Map<String,Object> resultMap = uploadVideoDemo.hmxUploadImageLocalFile(file.getInputStream(), file.getOriginalFilename());
			boolean flag = Boolean.parseBoolean(resultMap.get("flag").toString());
			if(!flag){
				return new ResultBean().setCode(Config.FAIL_CODE).setContent(resultMap.get("content").toString());
			}
			return new ResultBean().setCode(Config.SUCCESS_CODE).setContent("上传图片成功").put("url", resultMap.get("url"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return new ResultBean().setCode(Config.FAIL_CODE).setContent("图片上传异常");
		}
	}
}
