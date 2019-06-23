package com.hmx.timer;

import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.hmx.category.dto.HmxCategoryContentDto;
import com.hmx.category.dto.HmxCategoryDto;
import com.hmx.category.entity.HmxCategory;
import com.hmx.category.entity.HmxCategoryContent;
import com.hmx.category.entity.HmxCategoryContentTrans;
import com.hmx.category.service.HmxCategoryContentService;
import com.hmx.category.service.HmxCategoryService;
import com.hmx.images.dto.HmxImagesDto;
import com.hmx.images.entity.HmxImages;
import com.hmx.images.service.HmxImagesService;
import com.hmx.movie.dto.HmxMovieDto;
import com.hmx.movie.entity.HmxMovie;
import com.hmx.movie.service.HmxMovieService;
import com.hmx.system.dto.HmxVideoDto;
import com.hmx.system.dto.MesgPushDto;
import com.hmx.system.entity.HmxVideo;
import com.hmx.system.entity.MesgPush;
import com.hmx.system.service.HmxVideoService;
import com.hmx.system.service.MesgPushService;
import com.hmx.utils.upload.InitVodClients;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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

    @Autowired
    private MesgPushService mesgPushService;

    @Autowired
    private HmxCategoryService hmxCategoryService;

    @Autowired
    private HmxImagesService hmxImagesService;

    /**
     * 每10分钟去数据库坐一次更新，入库时间超过8小时的则将状态更新为正常
     */
    //以毫秒为单位
    //@Scheduled(fixedRate = 600000)  //10分钟
    @Scheduled(initialDelay = 60000*2,fixedRate = 60000)  //1分钟
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
     * initialDelay  表示延迟多久再第一次执行任务
     * 每10分钟去数据库坐一次更新，入库时间超过8小时的则将状态更新为正常
     */
    //以毫秒为单位
    @Scheduled(initialDelay = 60000*2,fixedRate = 60000*30)  //第一次延时2分钟执行，然后每30分钟执行一次
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

//        HmxCategoryContentDto hmxCategoryContentDto = new HmxCategoryContentDto();
//        hmxCategoryContentDto.setState(1);
//        List<HmxCategoryContent> list = hmxCategoryContentService.list(hmxCategoryContentDto);
//        if(null != list && list.size() > 0){
//            for(HmxCategoryContent content : list){
//                Date addTime = content.getCreateTime();
//                Calendar dateOne=Calendar.getInstance();
//                Calendar dateTwo=Calendar.getInstance();
//                dateOne.setTime(new Date());//设置为当前系统时间
//                dateTwo.setTime(addTime); //获取数据库中的时间
//                long timeOne=dateOne.getTimeInMillis();
//                long timeTwo=dateTwo.getTimeInMillis();
//                long minute=(timeOne-timeTwo)/(1000*60);//转化minute
//                //判断账户锁定时间是否大于480分钟
//                //if(minute>480){
//                content.setState(0);
//                hmxCategoryContentService.update(content);
//                //}
//                //System.out.println("title="+content.getCategoryTitle());
//            }
//        }
    }

    /**
     * 每天上午10点将系统中最新加入的一条消息做极光推送
     */
    //以毫秒为单位
    //@Scheduled(initialDelay = 60000*1,fixedRate = 60000)  //1分钟
    @Scheduled(cron = "0 0 10 * * *")
    public void scheduledPushJg(){
        //从系统中查询一条最新的消息，去极光消息表里面比对这条内容是否推送过，没有推送过则推送
        System.out.println("定时任务--开始推送极光消息");
        try {
            HmxCategoryContentDto hmxCategoryContentDto = new HmxCategoryContentDto();
            //hmxCategoryContentDto.setState(1);
            List<HmxCategoryContent> list = hmxCategoryContentService.selectNewest(hmxCategoryContentDto);
            if(null != list && list.size() > 0){
                HmxCategoryContent content = list.get(0);
                MesgPushDto mesgPushDto = new MesgPushDto();
                mesgPushDto.setCreateTime(System.currentTimeMillis());
                //根据分类获取分类类型
                Integer categoryId = content.getCategoryId();
                HmxCategoryDto hmxCategoryDto = new HmxCategoryDto();
                hmxCategoryDto.setCategoryId(categoryId);
                List<HmxCategory> categoryList = hmxCategoryService.list(hmxCategoryDto);
                if(null  != categoryList && categoryList.size() > 0){
                    HmxCategory category = categoryList.get(0);
                    //检查这条消息是否已经推送过
                    MesgPush push = mesgPushService.info(content.getCategoryContentId());
                    if(push != null){
                        if(push.getStatus() == 0){//消息已经存在极光消息表但是没有推送出去
                            boolean isPush = mesgPushService.jpush(content.getCategoryContentId()+"");
                            if(true == isPush){
                                System.out.println("定时任务--将已经存在极光表中的未推送的最新的消息推送出去成功");
                                return;
                            }else {
                                System.out.println("定时任务--将已经存在极光表中的未推送的最新的消息推送出去失败");
                                return;
                            }
                        }else if(push.getStatus() == 1){//消息已经存在极光消息表并且已经推送出去
                            System.out.println("此条最新消息已经推送过，不必再推送，在" + push.getCreateTime());
                            return;
                        }
                    }
                    mesgPushDto.setContentTpye(category.getCategoryType());
                    mesgPushDto.setContentId(content.getCategoryContentId());
                    mesgPushDto.setTitle(content.getCategoryTitle());
                    mesgPushDto.setSubTitle(content.getSubTitle());
                    mesgPushDto.setContentDes(content.getContentDesc());
                    mesgPushDto.setContentImage("");
                    //获取内容图片
                    HmxImagesDto hmxImagesDto = new HmxImagesDto();
                    hmxImagesDto.setCategoryContentId(content.getCategoryContentId());
                    List<HmxImages> imagesList = hmxImagesService.list(hmxImagesDto);
                    if(null != imagesList && imagesList.size() > 0){
                        for(HmxImages images : imagesList){
                            if(!StringUtils.isEmpty(images.getImageUrl())){
                                mesgPushDto.setContentImage(images.getImageUrl());
                                break;
                            }
                            if(!StringUtils.isEmpty(images.getTransImage())){
                                mesgPushDto.setContentImage(images.getTransImage());
                                break;
                            }
                            if(!StringUtils.isEmpty(images.getVerticalImage())){
                                mesgPushDto.setContentImage(images.getVerticalImage());
                                break;
                            }
                        }
                    }

                    boolean flag = mesgPushService.insert(mesgPushDto);
                    if(flag){
                        System.out.println("定时任务--将消息推送到极光平台");
                        boolean mark = mesgPushService.jpush(mesgPushDto.getContentId()+"");
                        if(mark == true) {
                            System.out.println("定时任务--将消息成功推送到极光平台成功");
                            mesgPushDto.setStatus(1);
                            mesgPushService.update(mesgPushDto);
                        }else {
                            System.out.println("定时任务--将消息成功推送到极光平台失败");
                        }
                    }
                }else {
                    System.out.println("没查到分类");
                    return;
                }

            }
        }catch (Exception e){
            System.out.println("定时任务--推送消息失败");
            e.printStackTrace();
        }
        System.out.println("定时任务--推送消息成功结束");
    }

}
