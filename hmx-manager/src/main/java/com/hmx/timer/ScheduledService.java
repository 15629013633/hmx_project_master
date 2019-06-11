package com.hmx.timer;

import com.hmx.category.dto.HmxCategoryContentDto;
import com.hmx.category.entity.HmxCategoryContent;
import com.hmx.category.entity.HmxCategoryContentTrans;
import com.hmx.category.service.HmxCategoryContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by songjinbao on 2019/6/3.
 */
@Component
@EnableScheduling
public class ScheduledService {

    @Autowired
    private HmxCategoryContentService hmxCategoryContentService;

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
//        HmxCategoryContentTrans trans = hmxCategoryContentService.selectCategoryContentById(128);
//        //获取到所有的state=1的内容，如果检测到时间超过8小时则将state置为0
//        System.out.println("title="+trans.getCategoryTitle());
    }

//    @Scheduled(fixedRate = 4000)
//    public void scheduled1(){
//        System.out.println("******************************");
//    }

}
