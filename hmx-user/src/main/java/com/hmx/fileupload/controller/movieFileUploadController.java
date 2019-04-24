package com.hmx.fileupload.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hmx.utils.result.Config;
import com.hmx.utils.result.ResultBean;
import com.hmx.utils.upload.InitVodClients;
/**
 * 用户获取视频链接
 * @author liY
 *
 */
@RestController
@RequestMapping("/movie")
public class movieFileUploadController {
	
	@Autowired
	private InitVodClients initVodClients;
	/**
	 * 获取视频播放地址
	 * @param videoId
	 * @return
	 */
	@RequestMapping("/getUrl")
	public ResultBean getVideoPathUrl(String videoId){
		if(StringUtils.isEmpty(videoId)){
			return new ResultBean().setCode(Config.FAIL_FIELD_EMPTY).setContent("视频编号不能为空");
		}
		try{
			Map<String,Object> resultMap = initVodClients.getUrl(videoId);
			boolean flag = Boolean.parseBoolean(resultMap.get("flag").toString());
			if(!flag){
				return new ResultBean().setCode(Config.FAIL_CODE).setContent(resultMap.get("content").toString());
			}
			return new ResultBean().setCode(Config.SUCCESS_CODE).setContent("获取播放地址成功").put("url", resultMap.get("url"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new ResultBean().setCode(Config.FAIL_CODE).setContent("获取播放地址异常");
		}
	}
}
