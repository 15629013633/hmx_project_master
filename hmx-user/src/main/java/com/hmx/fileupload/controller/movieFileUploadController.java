package com.hmx.fileupload.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.hmx.system.dto.HmxVideoDto;
import com.hmx.system.entity.HmxVideo;
import com.hmx.system.service.HmxVideoService;
import org.springframework.beans.BeanUtils;
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

	@Autowired
	private HmxVideoService hmxVideoService;
	/**
	 * 获取视频播放地址
	 * @param videoId
	 * @return
	 */
	@RequestMapping("/getUrl")
	public ResultBean getVideoPathUrl(String videoId){
//		HmxVideoDto hmxVideoDto1 =  new HmxVideoDto();
//		hmxVideoDto1.setVideoId("0fb0a24f7d414c57b80647208b9ccbee");
//		List<HmxVideo> hmxVideoList = hmxVideoService.list(hmxVideoDto1);
//		HmxVideo video = hmxVideoList.get(0);
//		video.setPlayUrl("222222");
//		hmxVideoService.update(video);
		//Map<String,Object> testMap = initVodClients.getUrl(videoId);
		if(StringUtils.isEmpty(videoId)){
			return new ResultBean().setCode(Config.FAIL_FIELD_EMPTY).setContent("视频编号不能为空");
		}
		try{
			//先从数据库中查询，没查到则去阿里服务器查
			HmxVideoDto hmxVideoDto = new HmxVideoDto();
			hmxVideoDto.setVideoId(videoId);
			List<HmxVideo> videoList  = hmxVideoService.list(hmxVideoDto);
			//转码了一般至少有2个码率的   mp3的有两个品质
			if(null != videoList && videoList.size() > 1){
				return new ResultBean().setCode(Config.SUCCESS_CODE).setContent("获取播放地址成功").put("url", videoList);
			}
			Map<String,Object> resultMap = initVodClients.getUrl(videoId);
			boolean flag = Boolean.parseBoolean(resultMap.get("flag").toString());
			if(!flag){
				return new ResultBean().setCode(Config.FAIL_CODE).setContent(resultMap.get("content").toString());
			}
			List<GetPlayInfoResponse.PlayInfo> playInfoList = (List<GetPlayInfoResponse.PlayInfo>)resultMap.get("url");
			List<HmxVideo> resultList = new ArrayList<>();
			if(null != playInfoList && playInfoList.size() > 0){
				for(GetPlayInfoResponse.PlayInfo playInfo : playInfoList){
					HmxVideo hmxVideo = new HmxVideo();
					hmxVideo.setVideoId(videoId);
					BeanUtils.copyProperties(playInfo, hmxVideo);
					hmxVideo.setPlayUrl(playInfo.getPlayURL());
					try {
						//查询是否已经插入数据库
						HmxVideoDto videoDto = new HmxVideoDto();
						videoDto.setJobId(playInfo.getJobId());
						List<HmxVideo> list = hmxVideoService.list(videoDto);
						if(null == list || list.size() == 0){
							hmxVideoService.insert(hmxVideo);
						}
						resultList.add(hmxVideo);
					}catch (Exception e){
						e.printStackTrace();
					}
				}
			}
			return new ResultBean().setCode(Config.SUCCESS_CODE).setContent("获取播放地址成功").put("url", resultList);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			return new ResultBean().setCode(Config.FAIL_CODE).setContent("获取播放地址异常");
		}
	}
}
