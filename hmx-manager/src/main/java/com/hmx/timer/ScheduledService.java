package com.hmx.timer;

import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.hmx.category.dto.HmxCategoryContentDto;
import com.hmx.category.entity.HmxCategoryContent;
import com.hmx.category.entity.HmxCategoryContentTrans;
import com.hmx.category.service.HmxCategoryContentService;
import com.hmx.movie.dto.HmxMovieDto;
import com.hmx.movie.entity.HmxMovie;
import com.hmx.movie.service.HmxMovieService;
import com.hmx.system.dto.HmxVideoDto;
import com.hmx.system.entity.HmxVideo;
import com.hmx.system.service.HmxVideoService;
import com.hmx.utils.upload.InitVodClients;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by songjinbao on 2019/6/3.
 */
@Component
@EnableScheduling
public class ScheduledService {

    @Autowired
    private HmxCategoryContentService hmxCategoryContentService;

    @Autowired
    private HmxMovieService hmxMovieService;

    @Autowired
    private InitVodClients initVodClients;

    @Autowired
    private HmxVideoService hmxVideoService;

    /**
     * 每10分钟去数据库坐一次更新，入库时间超过8小时的则将状态更新为正常
     */
    //以毫秒为单位
    //@Scheduled(fixedRate = 600000)  //10分钟
    @Scheduled(fixedRate = 60000)  //1分钟
    public void scheduled(){
        System.out.println("内容状态更新定时任务");
        HmxCategoryContentDto hmxCategoryContentDto = new HmxCategoryContentDto();
        hmxCategoryContentDto.setState(1);
        List<HmxCategoryContent> list = hmxCategoryContentService.list(hmxCategoryContentDto);
        if(null != list && list.size() > 0){
            for(HmxCategoryContent content : list){
                Date addTime = content.getCreateTime();
                Calendar dateOne=Calendar.getInstance();
                Calendar dateTwo=Calendar.getInstance();
                dateOne.setTime(new Date());//设置为当前系统时间
                dateTwo.setTime(addTime); //获取数据库中的时间
                long timeOne=dateOne.getTimeInMillis();
                long timeTwo=dateTwo.getTimeInMillis();
                long minute=(timeOne-timeTwo)/(1000*60);//转化minute
                //判断账户锁定时间是否大于480分钟
                //if(minute>480){
                    content.setState(0);
                    hmxCategoryContentService.update(content);
                //}
                //System.out.println("title="+content.getCategoryTitle());
            }
        }
    }

    /**
     * 每10分钟去数据库坐一次更新，入库时间超过8小时的则将状态更新为正常
     */
    //以毫秒为单位
    @Scheduled(fixedRate = 600000*3)  //30分钟
    //@Scheduled(fixedRate = 60000)  //5分钟
    public void scheduledViode(){
        System.out.println("视频播放地址更新定时任务");

        HmxMovieDto hmxMovieDto = new HmxMovieDto();
        //hmxMovieDto.setVideoId("4acdc813cc554a4d8d72aac79531e190");
        List<HmxMovie> movieList = hmxMovieService.list(hmxMovieDto);
        if(null != movieList && movieList.size() > 0){
            for(HmxMovie movie : movieList){
                try {
                    String videoId = movie.getVideoId();
                    Map<String,Object> resultMap = initVodClients.getUrl(videoId);
                    boolean flag = Boolean.parseBoolean(resultMap.get("flag").toString());
                    if(flag){
                        List<GetPlayInfoResponse.PlayInfo> playInfoList = (List<GetPlayInfoResponse.PlayInfo>)resultMap.get("url");
                        if(null != playInfoList && playInfoList.size() > 0){
                            for(GetPlayInfoResponse.PlayInfo playInfo : playInfoList){
                                try {
                                    HmxVideo hmxVideo = new HmxVideo();
                                    hmxVideo.setVideoId(videoId);
                                    BeanUtils.copyProperties(playInfo, hmxVideo);
                                    hmxVideo.setPlayUrl(playInfo.getPlayURL());
                                    try {
                                        //查询是否已经插入数据库
                                        HmxVideoDto videoDto = new HmxVideoDto();
                                        videoDto.setJobId(playInfo.getJobId());
                                        videoDto.setDefinition(playInfo.getDefinition());
                                        List<HmxVideo> list = hmxVideoService.list(videoDto);
                                        if(null == list || list.size() == 0){
                                            hmxVideoService.insert(hmxVideo);
                                        }else if(list.size() == 1){//更新playUrl
                                            HmxVideo video = list.get(0);
                                            video.setPlayUrl(playInfo.getPlayURL());
                                            hmxVideoService.update(video);
                                        }
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }

        HmxCategoryContentDto hmxCategoryContentDto = new HmxCategoryContentDto();
        hmxCategoryContentDto.setState(1);
        List<HmxCategoryContent> list = hmxCategoryContentService.list(hmxCategoryContentDto);
        if(null != list && list.size() > 0){
            for(HmxCategoryContent content : list){
                Date addTime = content.getCreateTime();
                Calendar dateOne=Calendar.getInstance();
                Calendar dateTwo=Calendar.getInstance();
                dateOne.setTime(new Date());//设置为当前系统时间
                dateTwo.setTime(addTime); //获取数据库中的时间
                long timeOne=dateOne.getTimeInMillis();
                long timeTwo=dateTwo.getTimeInMillis();
                long minute=(timeOne-timeTwo)/(1000*60);//转化minute
                //判断账户锁定时间是否大于480分钟
                //if(minute>480){
                content.setState(0);
                hmxCategoryContentService.update(content);
                //}
                //System.out.println("title="+content.getCategoryTitle());
            }
        }
    }

}
