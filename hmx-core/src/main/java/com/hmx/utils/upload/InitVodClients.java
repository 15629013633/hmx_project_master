package com.hmx.utils.upload;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse.PlayInfo;
import com.hmx.utils.logger.LogHelper;
/**
 * 账号AccessKey初始化
 * 如国内请使用 cn-shanghai
 * @author liY
 *
 */
@Component
public class InitVodClients {
	
	//账号AK信息请填写(必选)
	@Value("${accessKeyId}")
    private String accessKeyId;
    //账号AK信息请填写(必选)
	@Value("${accessKeySecret}")
    private String accessKeySecret;
	
	public DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
	    String regionId = "cn-shanghai";  // 点播服务接入区域
	    DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
	    DefaultAcsClient client = new DefaultAcsClient(profile);
	    return client;
	}

	public GetPlayInfoResponse getPlayInfo(DefaultAcsClient client,String videoId) throws Exception {
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId(videoId);
        return client.getAcsResponse(request);
    }
	public Map<String,Object> getUrl(String videoId){
		Map<String,Object> resultMap = new HashMap<String,Object>();
    	resultMap.put("flag", false);
    	resultMap.put("url", null);//播放时长
    	resultMap.put("duration", null);//播放时长
        DefaultAcsClient client = null;
		try {
			client = initVodClient(accessKeyId, accessKeySecret);
		} catch (ClientException e1) {
			// TODO Auto-generated catch block
			LogHelper.logger().info("获取连接异常" + e1.getMessage());
			resultMap.put("content", "获取连接异常");
			return resultMap;
		}
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        try {
            response = getPlayInfo(client,videoId);
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            // 播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
//            	System.out.println("------------"+playInfo.getPlayURL());
                LogHelper.logger().info("PlayInfo.PlayURL = " + playInfo.getPlayURL());
            }
            // Base信息
            LogHelper.logger().info("VideoBase.Title = " + response.getVideoBase().getTitle());
            if(playInfoList == null || playInfoList.size()<=0){
            	resultMap.put("content", "获取视频链接失败");
    			return resultMap;
            }else{
            	PlayInfo playInfo = playInfoList.get(playInfoList.size()-1);
            	resultMap.put("flag", true);
            	//会返回两个url，第一个是m3u8格式的，我们用第二个就好(url有时效)
            	String url = playInfo.getPlayURL();
    			url = url.substring(0, url.lastIndexOf("?"));
            	resultMap.put("url", url);
            	resultMap.put("duration", playInfo.getDuration());
            	resultMap.put("content", "获取视频链接成功");
            	return resultMap;
            }
        } catch (Exception e) {
        	LogHelper.logger().info("ErrorMessage = " + e.getLocalizedMessage());
        	resultMap.put("content", "获取视频链接请求异常");
            return resultMap;
        }
    }
}
